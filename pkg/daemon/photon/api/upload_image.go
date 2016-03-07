package main
import (
	"github.com/vmware/photon-controller-go-sdk/photon"
	"github.com/layer-x/layerx-commons/lxerrors"
	"fmt"
	"flag"
	"github.com/layer-x/layerx-commons/lxlog"
"github.com/Sirupsen/logrus"
	"github.com/vmware/govmomi/find"
	"github.com/vmware/govmomi"
"golang.org/x/net/context"
)

func UploadImage(imagePath string) error {
	options := &photon.ClientOptions{
		IgnoreCertificate: true,
	}
	client := photon.NewClient("https://192.168.209.29:443", "", options)
	imageCreateOptions := &photon.ImageCreateOptions{
		ReplicationType: "EAGER",
	}
	task, err := client.Images.CreateFromFile(imagePath, imageCreateOptions)
	if err != nil {
		return lxerrors.New("starting create from file request", err)
	}
	fmt.Printf("waiting for task "+task.ID+" to finish...\n")
	task, err = client.Tasks.Wait(task.ID)
	if err != nil {
		return lxerrors.New("waiting for task to complete", err)
	}
	fmt.Printf("task status: %s\n", task.State)
	return nil
}

func main() {
	ctx, cancel := context.WithCancel(context.Background())
	defer cancel()
	client, err := govmomi.NewClient(ctx, "https://192.168.0.29:443", true)
	if err != nil {
		lxlog.Fatalf(logrus.Fields{"err": err}, "failed!")
	}
	f := find.NewFinder(client, true)
	ds, err := f.DefaultDatacenter(ctx)
	browser, err := ds.Folders(ctx)
//	imagePath := flag.String("image", "", "path to iamge")
//	flag.Parse()
//	err = UploadImage(*imagePath)
//	if err != nil {
//		lxlog.Fatalf(logrus.Fields{"err": err}, "failed!")
//	}
}