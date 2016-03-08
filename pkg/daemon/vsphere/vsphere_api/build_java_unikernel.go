package vsphere_api
import (
	"mime/multipart"
	"github.com/Sirupsen/logrus"
	"github.com/layer-x/layerx-commons/lxlog"
	"encoding/json"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/pkg/types"
	"time"
	"io/ioutil"
	"os"
	"path/filepath"
	"strings"
	"github.com/layer-x/layerx-commons/lxfileutils"
	"io"
	"os/exec"
	"github.com/layer-x/unik/pkg/daemon/osv/capstan"
	"github.com/layer-x/unik/pkg/daemon/vsphere/vsphere_utils"
)

func BuildUnikernel(creds Creds, unikernelName, force string, uploadedTar multipart.File, handler *multipart.FileHeader) error {
	unikernelId := unikernelName //vsphere specific
	datastoreFolder := VSPHERE_UNIKERNEL_FOLDER + "/" + unikernelId
	vsphereClient, err := vsphere_utils.NewVsphereClient(creds.url)
	if err != nil {
		return lxerrors.New("initiating vsphere client connection", err)
	}
	defer func() {
		if err != nil {
			lxlog.Errorf(logrus.Fields{"error": err}, "error encountered, cleaning up unikernel artifacts")
			vsphereClient.Rmdir(datastoreFolder)
		}
	}()

	unikernels, err := ListUnikernels(creds)
	if err != nil {
		return lxerrors.New("could not retrieve list of unikernels", err)
	}
	for _, unikernel := range unikernels {
		if unikernel.UnikernelName == unikernelName {
			if strings.ToLower(force) == "true" {
				lxlog.Warnf(logrus.Fields{"unikernelName": unikernelName, "ami": unikernel.Id},
					"deleting unikernel before building new unikernel")
				err = DeleteUnikernel(creds, unikernel.Id, true)
				if err != nil {
					return lxerrors.New("could not delete unikernel", err)
				}
			} else {
				return lxerrors.New("a unikernel already exists for this unikernel. try again with force=true", err)
			}
		}
	}

	unikernelPath, err := filepath.Abs("./test_outputs/" + "unikernels/" + unikernelName + "/")
	if err != nil {
		return lxerrors.New("getting absolute path for ./test_outputs/" + "unikernels/" + unikernelName + "/", err)
	}
	err = os.MkdirAll(unikernelPath, 0777)
	if err != nil {
		return lxerrors.New("making directory", err)
	}
	//clean up artifacts even if we fail
	defer func() {
		err = os.RemoveAll(unikernelPath)
		if err != nil {
			panic(lxerrors.New("cleaning up unikernel files", err))
		}
		lxlog.Infof(logrus.Fields{"files": unikernelPath}, "cleaned up files")
	}()
	lxlog.Infof(logrus.Fields{"path": unikernelPath, "unikernel_name": unikernelName}, "created output directory for unikernel")
	savedTar, err := os.OpenFile(unikernelPath + filepath.Base(handler.Filename), os.O_CREATE | os.O_RDWR, 0666)
	if err != nil {
		return lxerrors.New("creating empty file for copying to", err)
	}
	defer savedTar.Close()
	bytesWritten, err := io.Copy(savedTar, uploadedTar)
	if err != nil {
		return lxerrors.New("copying uploaded file to disk", err)
	}
	lxlog.Infof(logrus.Fields{"bytes": bytesWritten}, "file written to disk")
	err = lxfileutils.Untar(savedTar.Name(), unikernelPath)
	if err != nil {
		lxlog.Warnf(logrus.Fields{"saved tar name":savedTar.Name()}, "failed to untar using gzip, trying again without")
		err = lxfileutils.UntarNogzip(savedTar.Name(), unikernelPath)
		if err != nil {
			return lxerrors.New("untarring saved tar", err)
		}
	}
	lxlog.Infof(logrus.Fields{"path": unikernelPath, "unikernel_name": unikernelName}, "unikernel tarball untarred")
	err = capstan.GenerateCapstanFile(unikernelPath)
	if err != nil {
		lxerrors.New("generating capstan file from " + unikernelPath + "/pom.xml", err)
	}
	lxlog.Infof(logrus.Fields{"path": unikernelPath + "/Capstanfile"}, "generated java Capstanfile")

	buildUnikernelCommand := exec.Command("docker", "run",
		"--rm",
		"--privileged",
		"-v", unikernelPath + ":/unikernel",
		"-e", "UNIKERNEL_NAME=" + unikernelName,
		"osvcompiler",
	)
	lxlog.LogCommand(buildUnikernelCommand, true)
	err = buildUnikernelCommand.Run()
	if err != nil {
		return lxerrors.New("building unikernel failed", err)
	}
	lxlog.Infof(logrus.Fields{"unikernel_name": unikernelName}, "unikernel image created")

	err = vsphereClient.Mkdir(datastoreFolder)
	if err != nil {
		return lxerrors.New("creating datastore folder to contain unikernel image", err)
	}

	err = vsphereClient.ImportVmdk(unikernelPath + "/program.vmdk", datastoreFolder)
	if err != nil {
		return lxerrors.New("importing program.vmdk to datastore folder", err)
	}

	unikernelMetadata := &types.Unikernel{
		Id: unikernelId, //same as unikernel name
		UnikernelName: unikernelName,
		CreationDate: time.Now().String(),
		Created: time.Now().Unix(),
		Path: datastoreFolder + "/program.vmdk",
	}
	metadataBytes, err := json.Marshal(unikernelMetadata)
	if err != nil {
		return lxerrors.New("marshalling unikernel metadata", err)
	}
	err = writeFile(unikernelPath + "/metadata.json", metadataBytes)
	if err != nil {
		return lxerrors.New("writing metadata.json", err)
	}
	err = vsphereClient.UploadFile(unikernelPath + "/metadata.json", datastoreFolder + "/metadata.json")
	if err != nil {
		return lxerrors.New("uploading metadata.json", err)
	}

	lxlog.Infof(logrus.Fields{"unikernel": unikernelMetadata}, "saved unikernel metadata")
	return nil
}


func writeFile(path, data []byte) error {
	err := ioutil.WriteFile(path, data, 0777)
	if err != nil {
		err := os.MkdirAll(filepath.Dir(path), 0777)
		if err != nil {
			return err
		}
		f, err := os.Create(path)
		if err != nil {
			return err
		}
		defer f.Close()
		_, err = f.Write(data)
		if err != nil {
			return err
		}
	}
	return nil
}