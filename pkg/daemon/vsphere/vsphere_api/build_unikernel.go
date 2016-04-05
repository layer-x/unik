package vsphere_api

import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxfileutils"
	"github.com/layer-x/layerx-commons/lxlog"
	"mime/multipart"
	"os"
	"strings"
	"io/ioutil"
	"github.com/layer-x/unik/pkg/daemon/state"
	"github.com/layer-x/unik/pkg/daemon/vsphere/vsphere_utils"
	"github.com/layer-x/unik/pkg/types"
	"fmt"
)

const (
	golang_type = iota
	java_type = iota
	unknown_type = iota
)

func BuildUnikernel(logger *lxlog.LxLogger, unikState *state.UnikState, creds Creds, unikernelName, force string, uploadedTar multipart.File, header *multipart.FileHeader, desiredVolumes []*types.VolumeSpec) error {
	vsphereClient, err := vsphere_utils.NewVsphereClient(creds.URL, logger)
	if err != nil {
		return lxerrors.New("initiating vsphere client connection", err)
	}

	unikernelId := unikernelName //vsphere specific
	vmdkFolder := "unik/" + unikernelId

	defer func() {
		if err != nil {
			logger.WithErr(err).Errorf("error encountered, cleaning up unikernel artifacts")
			if !strings.Contains(err.Error(), "already exists") {
				vsphereClient.Rmdir(vmdkFolder)
				delete(unikState.Unikernels, unikernelId)
			}
		}
	}()

	unikernels, err := ListUnikernels(logger, unikState)
	if err != nil {
		return lxerrors.New("could not retrieve list of unikernels", err)
	}
	for _, unikernel := range unikernels {
		if unikernel.UnikernelName == unikernelName {
			if strings.ToLower(force) == "true" {
				logger.WithFields(lxlog.Fields{
					"unikernelName": unikernelName, "ami": unikernel.Id,
				}).Warnf("deleting unikernel before building new unikernel")
				err = DeleteUnikernel(logger, unikState, creds, unikernel.Id, true)
				if err != nil {
					return lxerrors.New("could not delete unikernel", err)
				}
			} else {
				return lxerrors.New("a unikernel already exists for this unikernel. try again with force=true", err)
			}
		}
	}

	unikernelCompilationDir, err := ioutil.TempDir(os.TempDir(), unikernelName + "-src-dir")
	if err != nil {
		return lxerrors.New("creating temporary directory " + unikernelName + "-src-dir", err)
	}
	//clean up artifacts even if we fail
	defer func() {
		err = os.RemoveAll(unikernelCompilationDir)
		if err != nil {
			panic(lxerrors.New("cleaning up unikernel files", err))
		}
		logger.WithFields(lxlog.Fields{
			"files": unikernelCompilationDir,
		}).Infof("cleaned up files")
	}()
	logger.WithFields(lxlog.Fields{
		"path": unikernelCompilationDir,
		"unikernel_name": unikernelName,
	}).Infof("created output directory for unikernel")

	bytesWritten, err := lxfileutils.UntarFileToDirectory(unikernelCompilationDir, uploadedTar, header)
	if err != nil {
		return lxerrors.New("untarring unikernel source to compilation dir", err)
	}
	logger.WithFields(lxlog.Fields{
		"bytes": bytesWritten,
	}).Infof("file written to disk")

	logger.WithFields(lxlog.Fields{
		"path": unikernelCompilationDir, 
		"unikernel_name": unikernelName,
	}).Infof("unikernel tarball untarred")

	//Note: we do some modification of the volume specs here to prep them for AMI staging
	for i, desiredVolume := range desiredVolumes {
		//case 1: no data provided
		if desiredVolume.DataFolder == "" {
			dataFolder := fmt.Sprintf("%sempty%v", unikernelCompilationDir, i)
			err = os.MkdirAll(dataFolder, 0666)
			if err != nil {
				return lxerrors.New("creating empty directory for empty snapshot for desired volume mapping "+desiredVolume.MountPoint, err)
			}
			logger.WithFields(lxlog.Fields{
				"dataFolder": dataFolder,
			}).Infof("empty data folder created for blank snapshot")
			desiredVolume.DataFolder = dataFolder
		} else { //case 2: data folder provided as a tarball
			dataFolder := fmt.Sprintf("%s%s", unikernelCompilationDir, desiredVolume.DataFolder)
			err = os.MkdirAll(dataFolder, 0666)
			if err != nil {
				return lxerrors.New("creating directory for data snapshot for desired volume mapping "+desiredVolume.MountPoint, err)
			}
			bytesWritten, err := lxfileutils.UntarFileToDirectory(dataFolder, desiredVolume.DataTar, desiredVolume.DataTarHeader)
			if err != nil {
				return lxerrors.New("untarring data volume to disk to prepare snapshot", err)
			}
			logger.WithFields(lxlog.Fields{
				"bytes": bytesWritten,
				"dataFolder": dataFolder,
				"desiredVolume.DataTar": desiredVolume.DataTar,
				"desiredVolume.DataTarHeader": desiredVolume.DataTarHeader,
			}).Infof("data volume written to disk")
			desiredVolume.DataFolder = dataFolder
		}
	}

	sourceType, err := determineUnikernelType(unikernelCompilationDir)
	if err != nil {
		return lxerrors.New("determining sources type for compilation", err)
	}

	switch sourceType {
	case golang_type:
		return BuildGolangUnikernel(logger, unikState, unikernelName, unikernelId, unikernelCompilationDir, vmdkFolder, vsphereClient, desiredVolumes)
	case java_type:
		if len(desiredVolumes) > 0 {
			return lxerrors.New("multi-volume support is not enabled for OSv at this time", nil)
		}
		return BuildJavaUnikernel(logger, unikState, unikernelName, unikernelId, unikernelCompilationDir, vmdkFolder, vsphereClient)
	default:
		return lxerrors.New("could not determine source type. root directory of source must contain either pom.xml for java, or *.go file for golang", nil)
	}
}

func determineUnikernelType(unikernelSourceDir string) (int, error) {
	files, err := ioutil.ReadDir(unikernelSourceDir)
	if err != nil {
		return unknown_type, lxerrors.New("reading unikernel source dir " + unikernelSourceDir, err)
	}
	for _, file := range files {
		if strings.Contains(file.Name(), ".go") {
			return golang_type, nil
		}
		if strings.Contains(file.Name(), "pom.xml") {
			return java_type, nil
		}
	}
	return unknown_type, nil
}