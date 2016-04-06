package ec2api

import (
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"io"
	"net/http"
	"strings"
	"time"
	"github.com/layer-x/layerx-commons/lxhttpclient"
)

func GetLogs(logger *lxlog.LxLogger, unikInstanceId string) (string, error) {
	unikInstance, err := GetUnikInstanceByPrefixOrName(logger, unikInstanceId)
	if err != nil {
		return "", lxerrors.New("failed to retrieve unik instance", err)
	}
	if unikInstance.PublicIp == "" {
		return "", lxerrors.New("instance does not have a public ip yet", err)
	}
	_, logs, err := lxhttpclient.Get(unikInstance.PublicIp+":9876", "/logs", nil)
	if err != nil {
		return "", lxerrors.New("performing GET on "+unikInstance.PublicIp+":9876/logs", err)
	}

	logger.WithFields(lxlog.Fields{
		"response length": len(logs),
	}).Debugf("received console logs from unik instance at "+unikInstance.PublicIp)
	return fmt.Sprintf("begin logs for unik instance: %s\n"+
		"%s",
		unikInstance.UnikInstanceID,
		string(logs)), nil
}

func StreamLogs(logger *lxlog.LxLogger, unikInstanceId string, w io.Writer, deleteInstanceOnDisconnect bool) error {
	if deleteInstanceOnDisconnect {
		defer DeleteUnikInstance(logger, unikInstanceId)
	}

	linesCounted := -1
	for {
		time.Sleep(100 * time.Millisecond)
		currentLogs, err := GetLogs(logger, unikInstanceId)
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
					logger.Errorf("no flush!")
					return lxerrors.New("w is not a flusher", nil)
				}

				_, err = w.Write([]byte(logLines[linesCounted] + "\n")) //ignore errors; close comes from external
				if err != nil {
					logger.WithFields(lxlog.Fields{
						"lines_written": linesCounted,
					}).Warnf("writer closed by external source")
					return nil
				}
			}
		}
		_, err = w.Write([]byte{0}) //ignore errors; close comes from external
		if err != nil {
			logger.WithErr(err).WithFields(lxlog.Fields{
				"lines_written": linesCounted,
			}).Warnf("writer closed by external source")
			return nil
		}
		if len(logLines)-1 == linesCounted {
			time.Sleep(2500 * time.Millisecond)
			continue
		}
	}
}
