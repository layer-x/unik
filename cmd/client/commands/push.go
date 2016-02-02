package commands
import (
	"github.com/layer-x/unik/cmd/types"
	"os"
	"github.com/layer-x/layerx-commons/lxerrors"
	"os/exec"
	"fmt"
	"strings"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"path/filepath"
)

func Push(config types.UnikConfig, appName, path string, force bool) error {
	if strings.Contains(appName, "/") {
		return lxerrors.New("app name cannot contain special characters: '/'", nil)
	}
	url := config.Url
	fmt.Printf("Pushing app %s to Unik Backend at %s... force=%v\n", appName, url, force)
	path = strings.TrimSuffix(path,"/")
	tarPath := path+"/"+appName+".tar.gz"
	tarCommand := exec.Command("tar", "-zvcf", filepath.Base(tarPath), "./")
	tarCommand.Stdout = os.Stdout
	tarCommand.Stderr = os.Stderr
	tarCommand.Dir = path
	fmt.Printf("Running: %s\n", tarCommand.Args)
	err := tarCommand.Run()
	//clean up artifacts even if we fail
	defer func(){
		err = os.RemoveAll(tarPath)
		if err != nil {
			fmt.Println("could not clean up tarball at " +tarPath + " " + err.Error())
			os.Exit(-1)
		}
		fmt.Printf("cleaned up tarball %s\n", tarPath)
	}()

	fmt.Printf("App packaged as tarball: %s\n", tarPath)

	fmt.Printf("Submitting POST file:%s to %s\n", tarPath, url+"/apps/"+appName+fmt.Sprintf("?force=%v",force))
	resp, body, err := lxhttpclient.PostFile(url, "/apps/"+appName, "tarfile", tarPath)
	if err != nil {
		return lxerrors.New("failed to submit app to "+url+"/apps/"+appName, err)
	}
	if resp.StatusCode != 202 {
		return lxerrors.New("failed to submit app, got response: "+string(body), nil)
	}


	fmt.Printf("App submitted successfully %s\n", url+"/apps/"+appName+fmt.Sprintf("?force=%v",force))

	return nil
}
