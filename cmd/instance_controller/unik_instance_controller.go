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
)

func main() {
	unikIpPtr := flag.String("ip", "", "unik ip")
	unikernelNamePtr := flag.String("unikernel", "", "unikernel name")
	envStrPtr := flag.String("envStr", "", "one long env string")
	envDelimiterPtr := flag.String("envDelimiter", "", "split env pairs")
	envPairDelimiterPtr := flag.String("envPairDelimiter", "", "split env key and env val")
	flag.Parse()
	url := *unikIpPtr
	instanceName, err := bootInstance(url, *unikernelNamePtr, *envStrPtr, *envDelimiterPtr, *envPairDelimiterPtr)
	if err != nil {
		panic(err)
	}
	errc := make(chan error)
	go monitorInstance(url, instanceName, errc)
	go followLogs(url, instanceName, errc)

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