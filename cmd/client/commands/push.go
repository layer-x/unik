package commands
import (
	"github.com/layer-x/unik/cmd/types"
	"github.com/layer-x/layerx-commons/lxhttpclient"
"io"
	"path/filepath"
	"mime/multipart"
	"bytes"
	"os"
	"github.com/layer-x/layerx-commons/lxerrors"
	"os/exec"
	"fmt"
)

func Push(config types.UnikConfig, appName, path, force bool) error {
	url := config.Url
	fmt.Printf("Pushing app %s to Unik Backend at %s... force=%v\n", appName, url, force)
	tarPath := path+"/"+appName+".tar.gz"
	tarCommand := exec.Command("tar", "-zxcf", tarPath, path+"/*")
	tarCommand.Stdout = os.Stdout
	tarCommand.Stderr = os.Stderr
	fmt.Printf("Running: '%s %v'\n", tarCommand.Path, tarCommand.Args)
	err := tarCommand.Run()
	file, err := os.Open(tarPath)
	if err != nil {
		return lxerrors.New("could not open file "+tarPath+" for reading", err)
	}
	defer file.Close()
	fmt.Printf("App packaged as tarball: %s\n", tarPath)

	body := &bytes.Buffer{}
	writer := multipart.NewWriter(body)
	part, err := writer.CreateFormFile("tarfile", filepath.Base(tarPath))
	if err != nil {
		return err
	}
	_, err = io.Copy(part, file)

	err = writer.Close()
	if err != nil {
		return err
	}
	fmt.Printf("Submitting POST %v bytes to %s\n", body.Len(), url+"/apps/"+appName+fmt.Sprintf("?force=%v",force))

	resp, body, err := lxhttpclient.Post(url, "/apps/"+appName, nil, body)
	if err != nil {
		return lxerrors.New("failed to submit app to "+url+"/apps/"+appName, err)
	}
	if resp.StatusCode != 202 {
		return lxerrors.New("failed to submit app, got response: "+string(body), nil)
	}
	fmt.Printf("App submitted successfully %s\n", body.Len(), url+"/apps/"+appName+fmt.Sprintf("?force=%v",force))

	return nil
}
