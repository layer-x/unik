package docker_unik_types
import (
	"github.com/go-martini/martini"
	"net/http"
)


func AddDockerApi(m *martini.ClassicMartini) *martini.ClassicMartini {
	m.Get("/containers/json", func(res http.ResponseWriter, req *http.Request) {
		
	})
}