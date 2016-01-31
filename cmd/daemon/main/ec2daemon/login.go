package ec2daemon
import (
"github.com/layer-x/layerx-commons/lxmartini"
"net/http"
)

func (d *UnikEc2Daemon) login(res http.ResponseWriter, username, password string) {
	if d.username == username && d.password == password {
		res.WriteHeader(http.StatusAccepted)
	}
	lxmartini.Respond(res, "invalid login credentials")
}

