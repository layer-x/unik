package main

import (
	"github.com/layer-x/unik/pkg/types"
	"sync"
	"time"
	"encoding/json"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxfileutils"
	"io/ioutil"
	"github.com/layer-x/layerx-commons/lxmartini"
	"net/http"
	"github.com/go-martini/martini"
	"fmt"
	"github.com/layer-x/unik/pkg/daemon/ec2/ec2_metada_client"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/layer-x/unik/pkg/daemon/ec2/unik_ec2_utils"
	"github.com/Sirupsen/logrus"
	"github.com/layer-x/layerx-commons/lxlog"
	"log"
	"strings"
)

const hubDataFile = "/var/unik/data.json"

func main() {
	fmt.Printf("remember to run as root\n")
	hub, err := NewHubFromData()
	if err != nil {
		hub = NewCleanHub()
	}
	m := lxmartini.QuietMartini()
	m.Get("/", func(res http.ResponseWriter) {
		res.Write([]byte(serveMainPage(hub)))
	})
	m.Post("/unikernels", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		lxlog.Infof("reading in unikernel json")
		data, err := ioutil.ReadAll(req.Body)
		if err != nil {
			log.Fatal(err)
		}
		defer req.Body.Close()
		var unikernel types.Unikernel
		err = json.Unmarshal(data, &unikernel)
		if err != nil {
			fmt.Printf("received: " + string(data) + "\n")
			log.Fatal(err)
		}
		ec2Client, err := ec2_metada_client.NewEC2Client()
		if err != nil {
			log.Fatal(lxerrors.New("could not start ec2 client session", err))
		}
		region, err := ec2_metada_client.GetRegion()
		if err != nil {
			log.Fatal(err)
		}
		newName := unikernel.UnikernelName + "-public"
		lxlog.Infof(logrus.Fields{"name": newName}, "copying image")
		copyImageInput := &ec2.CopyImageInput{
			Name: aws.String(newName),
			SourceImageId: aws.String(unikernel.Id),
			SourceRegion: aws.String(region),
		}
		output, err := ec2Client.CopyImage(copyImageInput)
		if err != nil {
			log.Fatal(err)
		}
		newAmi := *output.ImageId
		time.Sleep(1000 * time.Millisecond)
		createTagsInput := &ec2.CreateTagsInput{
			Resources: aws.StringSlice([]string{newAmi}),
			Tags: []*ec2.Tag{
				&ec2.Tag{
					Key:   aws.String(unik_ec2_utils.UNIKERNEL_ID),
					Value: aws.String(newAmi),
				},
				&ec2.Tag{
					Key:   aws.String(unik_ec2_utils.UNIKERNEL_NAME),
					Value: aws.String(newName),
				},
			},
		}
		lxlog.Infof(logrus.Fields{"tags": createTagsInput}, "tagging unikernel")
		_, err = ec2Client.CreateTags(createTagsInput)
		if err != nil {
			log.Fatal(lxerrors.New("failed to tag unikernel", err))
		}

		lxlog.Infof(logrus.Fields{"ami": newAmi}, "waiting for ami to become available")
		amiState := ""
		retries := 0
		for !strings.Contains(strings.ToLower(amiState), "available") && retries < 360 {
			retries++
			describeImagesInput := &ec2.DescribeImagesInput{
				ImageIds: []*string{aws.String(newAmi)},
			}
			describeOut, err := ec2Client.DescribeImages(describeImagesInput)
			if err != nil {
				log.Fatal(err)
			}
			for _, image := range describeOut.Images {
				if *image.ImageId == newAmi {
					amiState = *image.State
					lxlog.Infof(logrus.Fields{"status": *image.State}, "ami found, status is")
				} else {
					lxlog.Infof(logrus.Fields{"ami": *image.ImageId}, "these are not the amis you are looking for")
				}
			}
			time.Sleep(5000 * time.Millisecond)
		}

		modifyImageAttributeInput := &ec2.ModifyImageAttributeInput{
			ImageId: aws.String(newAmi),
			LaunchPermission: &ec2.LaunchPermissionModifications{
				Add: []*ec2.LaunchPermission{
					&ec2.LaunchPermission{
						Group: aws.String("all"),
					},
				},
			},
		}
		lxlog.Infof(logrus.Fields{"modifyImageAttributeInput": modifyImageAttributeInput}, "making public")
		_, err = ec2Client.ModifyImageAttribute(modifyImageAttributeInput)
		if err != nil {
			log.Fatal(err)
		}
		time.Sleep(2000 * time.Millisecond)
		unikernel.Id = newAmi
		unikernel.UnikernelName = newName
		hub.Unikernels[newAmi] = &unikernel
		hub.lock.Lock()
		defer hub.lock.Unlock()
		hub.Save()
		res.WriteHeader(http.StatusAccepted)
	})
	m.Get("/unikernels", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		lxmartini.Respond(res, hub.Unikernels)
	})
	m.Delete("/unikernels/:unikernel_id", func(res http.ResponseWriter, params martini.Params) {
		unikernelId := params["unikernel_id"]
		hub.lock.Lock()
		defer hub.lock.Unlock()
		delete(hub.Unikernels, unikernelId)
		hub.Save()
		res.WriteHeader(http.StatusNoContent)
	})
	m.RunOnAddr(":9999")
}

func serveMainPage(hub *UnikHub) string {
	page := `<!DOCTYPE html>
<html>
<head>
<style>
#header {
    background-color:rgb(5,85,180);
    color:white;
    text-align:center;
    padding:5px;
    font-family: 'Helvetica Neue';
}
#nav {
    line-height:20px;
    background-color:rgb(65,175,255);
    height:300px;
    width:100px;
    float:left;
    padding:5px;
}
#section {
    width:350px;
    float:left;
    padding:10px;
}
#footer {
    background-color:black;
    color:white;
    clear:both;
    text-align:center;
   padding:5px;
}
</style>
</head>
<body>

<div id="header">
<h1>Unik Hub</h1>
</div>

<div id="nav">
The Unikernel Platform<br><br>
by<br>
Idit Levine<br>
Yuval Kohavi<br>
Scott Weiss
</div>

<div id="section">
<h2>Public Images</h2>

<table>
  <tr>
    <th>Name</th>
    <th>AMI-ID</th>
    <th>Created</th>
  </tr>
`
	for unikernelId, unikernel := range hub.Unikernels {
		page += `
  <tr>
    <td><img src="` + hub.Images[unikernelId] + `" height="42" width="42"></td>
    <td>` + unikernel.UnikernelName + `</td>
    <td>` + unikernel.Id + `</td>
    <td>` + unikernel.CreationDate + `</td>
  </tr>`
	}
	page += `
</table>
</div>

<div id="footer">
Advanced Development @ EMC
</div>

</body>
</html>`
	return page
}

type UnikHub struct {
	Unikernels map[string]*types.Unikernel
	Images map[string]string
	lock       *sync.Mutex
	Saved      time.Time `json:"Saved"`
}

func NewCleanHub() *UnikHub {
	unikernels := make(map[string]*types.Unikernel)
	images := make(map[string]string)
	unikernels["ami-b6b5c8d6"] = &types.Unikernel{
		Id: "ami-b6b5c8d6",
		UnikernelName: "steve-jobs-static-website",
		CreationDate: "March 29, 2016 at 6:39:22 PM UTC-4",
	}
	images["ami-b6b5c8d6"] = "http://i.imgur.com/2iSFHSC.png"
	unikernels["ami-c35f2da3"] = &types.Unikernel{
		Id: "ami-c35f2da3",
		UnikernelName: "test-go-app",
		CreationDate: "March 29, 2016 at 7:04:40 PM UTC-4",
	}
	images["ami-c35f2da3"] = "http://natebrennand.github.io/concurrency_and_golang/pics/gopher_head.png"
	return &UnikHub{
		Unikernels: unikernels,
		Images: images,
		lock: &sync.Mutex{},
	}
}

func NewHubFromData() (*UnikHub, error) {
	stateBytes, err := ioutil.ReadFile(hubDataFile)
	if err != nil {
		return nil, lxerrors.New("could not read state file " + hubDataFile, err)
	}
	var unikHub UnikHub
	err = json.Unmarshal(stateBytes, &unikHub)
	if err != nil {
		return nil, lxerrors.New("could not unmarshal state json " + string(stateBytes), err)
	}
	unikHub.lock = &sync.Mutex{}
	return &unikHub, nil
}

func (hub *UnikHub) Save() error {
	hub.lock.Lock()
	defer hub.lock.Unlock()
	hub.Saved = time.Now()
	data, err := json.Marshal(hub)
	if err != nil {
		return lxerrors.New("could not marshal state json", err)
	}
	err = lxfileutils.WriteFile(hubDataFile, data)
	if err != nil {
		return lxerrors.New("could not write hub data file " + hubDataFile, err)
	}
	return nil
}