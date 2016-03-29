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
		data, err := ioutil.ReadAll(req.Body)
		if err != nil {
			panic(err)
		}
		defer req.Body.Close()
		var unikernel types.Unikernel
		err = json.Unmarshal(data, &unikernel)
		if err != nil {
			panic(err)
		}
		ec2Client, err := ec2_metada_client.NewEC2Client()
		if err != nil {
			panic(lxerrors.New("could not start ec2 client session", err))
		}
		region, err := ec2_metada_client.GetRegion()
		if err != nil {
			panic(err)
		}
		newName := unikernel.UnikernelName + "-public"
		copyImageInput := &ec2.CopyImageInput{
			Name: aws.String(newName),
			SourceImageId: aws.String(unikernel.Id),
			SourceRegion: aws.String(region),
		}
		output, err := ec2Client.CopyImage(copyImageInput)
		if err != nil {
			panic(err)
		}
		newAmi := *output.ImageId
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
		_, err = ec2Client.ModifyImageAttribute(modifyImageAttributeInput)
		if err != nil {
			panic(err)
		}
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
    background-color:rgb(90,85,180);
    color:white;
    text-align:center;
    padding:5px;
    font-family: 'Helvetica Neue';
}
#nav {
    line-height:20px;
    background-color:rgb(180,175,255);
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
<h2>Published Unikernels</h2>

<table>
  <tr>
    <th>Name</th>
    <th>AMI-ID</th>
    <th>Created</th>
  </tr>
`
	for _, unikernel := range hub.Unikernels {
		page += `
  <tr>
    <td>` + unikernel.UnikernelName + `</td>
    <td>` + unikernel.Id + `</td>
    <td>` + unikernel.CreationDate + `</td>
  </tr>`
	}
	page += `
</table>
</div>

<div id="footer">
Office of the CTO @ EMC
</div>

</body>
</html>`
	return page
}

type UnikHub struct {
	Unikernels map[string]*types.Unikernel
	lock       *sync.Mutex
	Saved      time.Time `json:"Saved"`
}

func NewCleanHub() *UnikHub {
	return &UnikHub{
		Unikernels: make(map[string]*types.Unikernel),
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