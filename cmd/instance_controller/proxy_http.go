package main
import (
	"net/http"
	"strings"
	"net/url"
"net/http/httputil"
	"github.com/layer-x/layerx-commons/lxlog"
"github.com/Sirupsen/logrus"
)

func handler(p *httputil.ReverseProxy) func(http.ResponseWriter, *http.Request) {
	return func(w http.ResponseWriter, r *http.Request) {
		lxlog.Infof(logrus.Fields{"url": r.URL}, "")
		w.Header().Set("X-Unik", "from cf")
		p.ServeHTTP(w, r)
	}
}

func startRedirectServer(port, remoteAddr string, errc chan error) {
	remoteAddr = "http://"+strings.TrimPrefix(remoteAddr, "http://")
	remote, err := url.Parse(remoteAddr)
	if err != nil {
		errc <- err
		return
	}
	proxy := httputil.NewSingleHostReverseProxy(remote)
	http.HandleFunc("/", handler(proxy))
	errc <- http.ListenAndServe(":"+port, nil)
}