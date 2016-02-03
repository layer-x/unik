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

func Push(config types.UnikConfig, unikernelName, path string, force bool) error {
	if strings.Contains(unikernelName, "/") {
		return lxerrors.New("unikernel name cannot contain special characters: '/'", nil)
	}
	url := config.Url
	fmt.Printf("Pushing unikernel %s to Unik Backend at %s... force=%v\n", unikernelName, url, force)
	path = strings.TrimSuffix(path,"/")
	tarPath := path+"/"+unikernelName+".tar.gz"
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

	fmt.Printf("Submitting POST file:%s to %s\n", tarPath, url+"/unikernels/"+unikernelName+fmt.Sprintf("?force=%v",force))
	resp, body, err := lxhttpclient.PostFile(url, "/unikernels/"+unikernelName, "tarfile", tarPath)
	if err != nil {
		return lxerrors.New("failed to submit unikernel to "+url+"/unikernels/"+unikernelName, err)
	}
	if resp.StatusCode != 202 {
		return lxerrors.New("failed to submit unikernel, got response: "+string(body), nil)
	}


	fmt.Printf("App submitted successfully %s\n", url+"/unikernels/"+unikernelName+fmt.Sprintf("?force=%v",force))

	return nil
}
