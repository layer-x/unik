package vsphere_api
import (
"strings"
"time"
"github.com/Sirupsen/logrus"
"github.com/layer-x/layerx-commons/lxlog"
"github.com/layer-x/layerx-commons/lxerrors"
"net/http"
"io"
	"fmt"
"github.com/layer-x/layerx-commons/lxhttpclient"
"github.com/layer-x/unik/pkg/daemon/state"
)

func GetLogs(unikState *state.UnikState, creds Creds, unikInstanceId string) (string, error) {
	unikInstance, err := GetUnikInstanceByPrefixOrName(unikState, creds, unikInstanceId)
	if err != nil {
		return "", lxerrors.New("failed to retrieve unik instance", err)
	}
	if unikInstance.PublicIp == "" {
		return "", lxerrors.New("instance does not have a public ip yet", err)
	}
	_, logs, err := lxhttpclient.Get(unikInstance.PublicIp+":3000", "/logs", nil)
	if err != nil {
		return "", lxerrors.New("performing GET on "+unikInstance.PublicIp+":3000/logs", err)
	}

	lxlog.Debugf(logrus.Fields{"response length": len(logs)}, "received console logs from unik instance at "+unikInstance.PublicIp)
	return fmt.Sprintf("begin logs for unik instance: %s\n"+
	"%s",
		unikInstance.UnikInstanceID,
		string(logs)), nil
}

func StreamLogs(unikState *state.UnikState, creds Creds, unikInstanceId string, w io.Writer, deleteInstanceOnDisconnect bool) error {
	if deleteInstanceOnDisconnect {
		defer DeleteUnikInstance(creds, unikInstanceId)
	}

	linesCounted := -1
	for {
		time.Sleep(100 * time.Millisecond)
		currentLogs, err := GetLogs(unikState, creds, unikInstanceId)
		if err != nil {
			return lxerrors.New("could not get logs for unik instance "+unikInstanceId, err)
		}
		logLines := strings.Split(currentLogs, "\n")
		for i, _ := range logLines {
			if linesCounted < len(logLines) && linesCounted < i {
				linesCounted = i

				if f, ok := w.(http.Flusher); ok {
					f.Flush()
				} else {
					lxlog.Errorf(logrus.Fields{}, "no flush!")
					return lxerrors.New("w is not a flusher", nil)
				}

				_, err = w.Write([]byte(logLines[linesCounted] + "\n")) //ignore errors; close comes from external
				if err != nil {
					lxlog.Warnf(logrus.Fields{"lines_written": linesCounted}, "writer closed by external source")
					return nil
				}
			}
		}
		_, err = w.Write([]byte{0}) //ignore errors; close comes from external
		if err != nil {
			lxlog.Warnf(logrus.Fields{"lines_written": linesCounted, "err": err}, "writer closed by external source")
			return nil
		}
		if len(logLines)-1 == linesCounted {
			time.Sleep(2500 * time.Millisecond)
			continue
		}
	}
}

