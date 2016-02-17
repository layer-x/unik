package main

import (
	"flag"
	"math/rand"
	"time"
	"fmt"
	"net/http"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"encoding/json"
	"github.com/layer-x/unik/types"
	"bufio"
	"strings"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/Sirupsen/logrus"
	"os"
	"github.com/layer-x/unik/unik_client"
)

type volumeData struct {
	Name string `json:"Name"`
	Size int `json:"Size"`
}

var remoteAddr string

func main() {
	unikIpPtr := flag.String("ip", "", "unik ip")
	unikernelNamePtr := flag.String("unikernel", "", "unikernel name")
	envStrPtr := flag.String("envStr", "", "one long env string")
	envDelimiterPtr := flag.String("envDelimiter", "", "split env pairs")
	envPairDelimiterPtr := flag.String("envPairDelimiter", "", "split env key and env val")
	volumeDataStringPtr := flag.String("volumeData", "NOTHING", "json encoded volume data string")
	flag.Parse()
	fmt.Printf("environ: %v\n", os.Environ())
	port := os.Getenv("PORT")
	if port == "" {
		panic("must be given a port!")
	}
	url := *unikIpPtr
	instanceName, err := bootInstance(url, *unikernelNamePtr, *envStrPtr, *envDelimiterPtr, *envPairDelimiterPtr)
	if err != nil {
		panic(err)
	}
	errc := make(chan error)

	lxlog.Infof(logrus.Fields{"unik_ip": url, "port": port},"instance controller initialized with port "+port)

	go monitorInstance(url, instanceName, errc)
	go followLogs(url, instanceName, errc)
	go func(){
		for {
			if remoteAddr != "" {
				lxlog.Infof(logrus.Fields{"ip": remoteAddr+":3000", "port": port}, "received public ip for instance")
				startRedirectServer(port, remoteAddr+":3000", errc)
				lxlog.Infof(logrus.Fields{"ip": remoteAddr+":3000", "port": port}, "started!")
				break
			}
			lxlog.Infof(logrus.Fields{"ip": remoteAddr+":3000"}, "waiting on remote ip")
			time.Sleep(1000 * time.Millisecond)
		}
	}()
	go func(){
		if *volumeDataStringPtr != "NOTHING" {
			var desiredVolumes []*volumeData
			err = json.Unmarshal([]byte(*volumeDataStringPtr), &desiredVolumes)
			if err != nil {
				panic(lxerrors.New("could not unmarshal volume data", err))
			}
			deviceNames := []string{
				"/dev/xvdf",
				"/dev/xvdg",
				"/dev/xvdh",
				"/dev/xvdi",
				"/dev/xvdj",
				"/dev/xvdk",
				"/dev/xvdl",
				"/dev/xvdm",
				"/dev/xvdn",
				"/dev/xvdo",
				"/dev/xvdp",
			}
			for i, vol := range desiredVolumes {
				if i >= len(deviceNames) {
					break
				}
				deviceName := deviceNames[i]
				volumeName := vol.Name
				size := vol.Size
				lxlog.Infof(logrus.Fields{
					"instanceName": instanceName,
					"deviceName": deviceName,
					"volumeName": volumeName,
					"size": size,
				},"attaching volume to instance")
				err = retryCreateAndAttachVolume(url, instanceName, volumeName, deviceName, size, 30)
				if err != nil {
					panic(err)
				}
			}
		}
	}()
	err = <-errc
	lxlog.Fatalf(logrus.Fields{"error": err, "instanceName": instanceName, "url": url, "env": *envStrPtr}, "unk instance controller terminated!")
}

func bootInstance(url, unikernelName, envStr, envDelimiter, envPairDelimiter string) (string, error) {
	strlen := 16
	rand.Seed(time.Now().UTC().UnixNano())
	const chars = "abcdefghijklmnopqrstuvwxyz0123456789"
	result := make([]byte, strlen)
	for i := 0; i < strlen; i++ {
		result[i] = chars[rand.Intn(len(chars))]
	}
	uuid := string(result)
	instanceName := unikernelName + "_" + uuid

	path := fmt.Sprintf("/unikernels/%s/run?env=%s&useDelimiter=%s&usePairDelimiter=%s&name=%s",
		unikernelName,
		envStr,
		envDelimiter,
		envPairDelimiter,
		instanceName)

	resp, body, err := lxhttpclient.Post(url, path, nil, nil)
	if err != nil {
		return "", lxerrors.New("failed running unikernel", err)
	}
	if resp.StatusCode != http.StatusOK {
		return "", lxerrors.New("failed running unikernel, got message: " + string(body) + " with status code " + fmt.Sprintf("%v", resp.StatusCode), err)
	}
	return instanceName, nil
}

//run as goroutine
func monitorInstance(url, instanceName string, errc chan error) {
	for {
		unikInstance, err := getUnikInstance(url, instanceName)
		if err != nil {
			errc <- err
		}
		if strings.Contains(unikInstance.State, "terminated") ||
		strings.Contains(unikInstance.State, "shutting-down") ||
		strings.Contains(unikInstance.State, "stopped") {
			errc <- lxerrors.New("instance " + instanceName + " is no longer running!", nil)
		}
		remoteAddr = unikInstance.PublicIp
		time.Sleep(2000 * time.Millisecond)
	}
}

//run as goroutine
func followLogs(url, unikInstanceId string, errc chan error) {
	resp, err := http.Get("http://" + url + "/instances/" + unikInstanceId + "/logs?follow=true&delete=true")
	if err != nil {
		errc <- lxerrors.New("error performing GET request", err)
	}
	reader := bufio.NewReader(resp.Body)
	for {
		line, err := reader.ReadBytes('\n')
		if err != nil {
			errc <- lxerrors.New("reading line", err)
		}
		fmt.Printf("%s", string(line))
	}
}

func getUnikInstance(url, instanceName string) (*types.UnikInstance, error) {
	_, body, err := lxhttpclient.Get(url, "/instances", nil)
	if err != nil {
		return nil, lxerrors.New("error requesting unik instance list", err)
	}
	var unikInstances []*types.UnikInstance
	err = json.Unmarshal(body, &unikInstances)
	if err != nil {
		return nil, lxerrors.New("could not unmarshal unik instance json", err)
	}
	for _, unikInstance := range unikInstances {
		if unikInstance.UnikInstanceName == instanceName {
			return unikInstance, nil
		}
	}
	return nil, lxerrors.New("could not find unik instance " + instanceName, nil)
}

func retryCreateAndAttachVolume(url, instanceName, volumeName, deviceName string, size, retries int) error {
	client := unik_client.NewUnikClient(url)
	_, err := client.CreateVolume(volumeName, size)
	if err != nil {
		var err2 error
		_, err2 = client.GetVolume(volumeName)
		if err2 != nil {
			return lxerrors.New("could not find OR create volume: "+err2.Error(), err)
		}
	}
	_, err = client.AttachVolume(volumeName, instanceName, deviceName)
	if  err != nil {
		if retries > 0 {
			return retryCreateAndAttachVolume(url, instanceName, volumeName, deviceName, size, retries-1)
			time.Sleep(5 * time.Second)
			lxlog.Infof(logrus.Fields{
				"url": url,
				"instanceName": instanceName,
				"volumeName": volumeName,
				"deviceName": deviceName,
			},"failed to attach volume, retrying %v more times", retries)
		} else {
			return lxerrors.New("error attaching volume", err)
		}
	}
	return nil
}