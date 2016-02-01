package ec2daemon
import (
	"github.com/go-martini/martini"
	"github.com/layer-x/layerx-commons/lxmartini"
	"fmt"
	"net/http"
)

type app struct {
	name string
	filepath string
}

type UnikEc2Daemon struct {
	server *martini.ClassicMartini
	apps map[string]app
	username string
	password string
}

func NewUnikEc2Daemon(username, password string) *UnikEc2Daemon {
	return &UnikEc2Daemon{
		server: lxmartini.QuietMartini(),
		apps: make(map[string]app),
		username: username,
		password: password,
	}
}

func (d *UnikEc2Daemon) registerHandlers() {
	d.server.Post("/login", func(res http.ResponseWriter, req *http.Request) {
		query := req.URL.Query()
		username := query.Get("username")
		password := query.Get("password")
		d.login(res, username, password)
	})
	d.server.Post("/build", d.buildUnikernel)
	d.server.Get("/instances", d.listUnikInstances)
	d.server.Get("/unikernels", d.listUnikernels)
}


func (d *UnikEc2Daemon) Start(port int) {
	d.registerHandlers()
	d.server.RunOnAddr(fmt.Sprintf(":%v", port))
}
