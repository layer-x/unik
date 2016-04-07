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
	"github.com/layer-x/unik/pkg/types"
	"bufio"
	"strings"
	"github.com/layer-x/layerx-commons/lxlog"
	"os"
	"github.com/layer-x/unik/pkg/unik_client"
)

type volumeData struct {
	Name string `json:"Name"`
	Size int `json:"Size"`
	Device string `json:"Device"`
}

var remoteAddr string

func main() {
	unikIpPtr := flag.String("ip", "", "unik ip")
	unikernelNamePtr := flag.String("unikernel", "", "unikernel name")
	envStrPtr := flag.String("envStr", "", "one long env string")
	envDelimiterPtr := flag.String("envDelimiter", "", "split env pairs")
	envPairDelimiterPtr := flag.String("envPairDelimiter", "", "split env key and env val")
	volumeDataStringPtr := flag.String("volumeData", "NOTHING", "json encoded volume data string")
	useCfInstanceIndexPtr := flag.Bool("useCfInstanceIndex", false, "use CF_INSTANCE_INDEX env var to set vol name")
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

	logger := lxlog.New("unik-cf-instance-controller")

	logger.WithFields(lxlog.Fields{
		"unik_ip": url, "port": port,
	}).Infof("instance controller initialized with port "+port)

	go monitorInstance(url, instanceName, errc)
	go followLogs(url, instanceName, errc)
	go func(){
		logger.Infof("waiting on remote ip")
		for {
			if remoteAddr != "" {
				logger.WithFields(lxlog.Fields{
					"ip": remoteAddr+":3000",
					"port": port,
				}).Infof("received public ip for instance")
				startRedirectServer(logger, port, remoteAddr+":3000", errc)
				break
			}
			time.Sleep(2000 * time.Millisecond)
		}
	}()
	go func(){
		if *volumeDataStringPtr != "NOTHING" {
			var desiredVolumes []*volumeData
			err = json.Unmarshal([]byte(*volumeDataStringPtr), &desiredVolumes)
			if err != nil {
				panic(lxerrors.New("could not unmarshal volume data", err))
			}
			for _, vol := range desiredVolumes {
				deviceName := vol.Device
				volumeName := vol.Name
				if *useCfInstanceIndexPtr {
					volumeName += "_instance"+os.Getenv("CF_INSTANCE_INDEX")
				}
				size := vol.Size
				logger.WithFields(lxlog.Fields{
					"instanceName": instanceName,
					"deviceName": deviceName,
					"volumeName": volumeName,
					"size": size,
				}).Infof("attaching volume to instance")
				err = retryCreateAndAttachVolume(logger, url, instanceName, volumeName, deviceName, size, 30)
				if err != nil {
					panic(err)
				}
			}
		}
	}()
	err = <-errc
	lxhttpclient.Delete(url, "/instances/"+instanceName, nil)
	logger.WithErr(err).WithFields(lxlog.Fields{"error": err, "instanceName": instanceName, "url": url, "env": *envStrPtr}).Fatalf("unk instance controller terminated!")
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

func retryCreateAndAttachVolume(logger lxlog.Logger, url, instanceName, volumeName, deviceName string, size, retries int) error {
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
			return retryCreateAndAttachVolume(logger, url, instanceName, volumeName, deviceName, size, retries-1)
			time.Sleep(5 * time.Second)
			logger.WithFields(lxlog.Fields{
				"url": url,
				"instanceName": instanceName,
				"volumeName": volumeName,
				"deviceName": deviceName,
			}).Infof("failed to attach volume, retrying %v more times", retries)
		} else {
			return lxerrors.New("error attaching volume", err)
		}
	}
	return nil
}