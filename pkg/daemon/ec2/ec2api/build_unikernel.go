package ec2api

import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxfileutils"
	"github.com/layer-x/layerx-commons/lxlog"
	"io"
	"mime/multipart"
	"os"
	"path/filepath"
	"strings"
	"io/ioutil"
)

const (
	golang_type = iota
	java_type = iota
	unknown_type = iota
)

func BuildUnikernel(logger *lxlog.LxLogger, unikernelName, force string, uploadedTar multipart.File, handler *multipart.FileHeader) error {
	unikernels, err := ListUnikernels(logger)
	if err != nil {
		return lxerrors.New("could not retrieve list of unikernels", err)
	}
	for _, unikernel := range unikernels {
		if unikernel.UnikernelName == unikernelName {
			if strings.ToLower(force) == "true" {
				logger.WithFields(lxlog.Fields{
					"unikernelName": unikernelName, 
					"ami": unikernel.Id,
				}).Warnf("deleting unikernel before building new unikernel")
				err = DeleteUnikernel(logger, unikernel.Id, true)
				if err != nil {
					return lxerrors.New("could not delete unikernel", err)
				}
			} else {
				return lxerrors.New("a unikernel already exists for this unikernel. try again with force=true", err)
			}
		}
	}
	unikernelCompilationDir, err := ioutil.TempDir(os.TempDir(), unikernelName+"-src-dir")
	if err != nil {
		return lxerrors.New("creating temporary directory "+unikernelName+"-src-dir", err)
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
	savedTar, err := os.OpenFile(unikernelCompilationDir +filepath.Base(handler.Filename), os.O_CREATE|os.O_RDWR, 0666)
	if err != nil {
		return lxerrors.New("creating empty file for copying to", err)
	}
	defer savedTar.Close()
	bytesWritten, err := io.Copy(savedTar, uploadedTar)
	if err != nil {
		return lxerrors.New("copying uploaded file to disk", err)
	}
	logger.WithFields(lxlog.Fields{
		"bytes": bytesWritten,
	}).Infof("file written to disk")
	err = lxfileutils.Untar(savedTar.Name(), unikernelCompilationDir)
	if err != nil {
		logger.WithFields(lxlog.Fields{
			"saved tar name":savedTar.Name(),
		}).Warnf("failed to untar using gzip, trying again without")
		err = lxfileutils.UntarNogzip(savedTar.Name(), unikernelCompilationDir)
		if err != nil {
			return lxerrors.New("untarring saved tar", err)
		}
	}
	logger.WithFields(lxlog.Fields{
		"path": unikernelCompilationDir, 
		"unikernel_name": unikernelName,
	}).Infof("unikernel tarball untarred")

	sourceType, err := determineUnikernelType(unikernelCompilationDir)
	if err != nil {
		return lxerrors.New("determining sources type for compilation", err)
	}

	switch sourceType {
	case golang_type:
		return BuildGolangUnikernel(logger, unikernelName, unikernelCompilationDir)
	case java_type:
		return BuildJavaUnikernel(logger, unikernelName, unikernelCompilationDir)
	default:
		return lxerrors.New("could not determine source type. root directory of source must contain either pom.xml for java, or *.go file for golang", nil)
	}
}

func determineUnikernelType(unikernelSourceDir string) (int, error) {
	files, err := ioutil.ReadDir(unikernelSourceDir)
	if err != nil {
		return unknown_type, lxerrors.New("reading unikernel source dir "+unikernelSourceDir, err)
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