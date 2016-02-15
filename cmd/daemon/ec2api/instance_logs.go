package ec2api

import (
	"encoding/base64"
	"fmt"
	"github.com/Sirupsen/logrus"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/unik/cmd/daemon/ec2_metada_client"
	"io"
	"net/http"
	"strings"
	"time"
)

func GetLogs(unikInstanceId string) (string, error) {
	unikInstance, err := GetUnikInstanceByPrefixOrName(unikInstanceId)
	if err != nil {
		return "", lxerrors.New("failed to retrieve unik instance", err)
	}
	ec2Client, err := ec2_metada_client.NewEC2Client()
	if err != nil {
		return "", lxerrors.New("could not start ec2 client session", err)
	}
	getConsoleInput := &ec2.GetConsoleOutputInput{InstanceId: aws.String(unikInstance.AmazonID)}
	consoleOutputOutput, err := ec2Client.GetConsoleOutput(getConsoleInput)
	if err != nil {
		return "", lxerrors.New("could not get console output for "+unikInstanceId, err)
	}
	var timeStamp string
	var output string
	if consoleOutputOutput.Timestamp != nil {
		timeStamp = (*consoleOutputOutput.Timestamp).String()
	}
	if consoleOutputOutput.Output != nil {
		data, err := base64.StdEncoding.DecodeString(*consoleOutputOutput.Output)
		if err != nil {
			return "", lxerrors.New("could not decode base64 output", err)
		}
		output = string(data)
	}

	lxlog.Debugf(logrus.Fields{"response length": len(output)}, "received console output reply from aws")
	return fmt.Sprintf("begin logs for unik instance: %s\n"+
		"time: %s\n"+
		"%s",
		*consoleOutputOutput.InstanceId,
		timeStamp,
		output), nil
}

func StreamLogs(unikInstanceId string, w io.Writer, deleteInstanceOnDisconnect bool) error {
	if deleteInstanceOnDisconnect {
		defer DeleteUnikInstance(unikInstanceId)
	}

	linesCounted := -1
	for {
		currentLogs, err := GetLogs(unikInstanceId)
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
			time.Sleep(1000 * time.Millisecond)
			lxlog.Warnf(logrus.Fields{"unik_instance_id": unikInstanceId}, "no new logs since last poll, sleeping")
			continue
		}
	}
}
