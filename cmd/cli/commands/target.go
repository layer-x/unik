package commands

import (
	"encoding/json"
	"fmt"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxhttpclient"
	"github.com/layer-x/unik/types"
	"io/ioutil"
	"os"
	"os/user"
	"path/filepath"
	"strings"
)

func Target(url string) error {
	url = strings.TrimPrefix(url, "http://") + ":3000"
	_, _, err := lxhttpclient.Get(url, "/unikernels", nil)
	if err != nil {
		return lxerrors.New("could not reach endpoint "+url, err)
	}
	config := types.UnikConfig{
		Url: url,
	}
	configJson, err := json.Marshal(config)
	if err != nil {
		return lxerrors.New("invalid config", err)
	}

	usr, err := user.Current()
	if err != nil {
		panic("user not found: " + err.Error())
	}
	configPath := filepath.Join(usr.HomeDir, ".unik", "config.json")

	err = ioutil.WriteFile(configPath, configJson, 0777)
	if err != nil {
		err := os.Mkdir(filepath.Dir(configPath), 0777)
		if err != nil {
			return lxerrors.New("could not create directory "+filepath.Dir(configPath), err)
		}
		f, err := os.Create(configPath)
		if err != nil {
			return lxerrors.New("could not create file "+configPath, err)
		}
		defer f.Close()
		_, err = f.Write(configJson)
		if err != nil {
			return lxerrors.New("could not write config file", err)
		}
	}
	fmt.Printf("Target - Unik EC2 Backend - %s\n", url)

	return nil
}

func ShowTarget() error {
	usr, err := user.Current()
	if err != nil {
		panic("user not found: " + err.Error())
	}
	configPath := filepath.Join(usr.HomeDir, ".unik", "config.json")

	data, err := ioutil.ReadFile(configPath)
	if err != nil {
		return lxerrors.New("could not read config file", err)
	}

	config := types.UnikConfig{}
	err = json.Unmarshal(data, &config)
	if err != nil {
		return lxerrors.New("invalid config", err)
	}

	fmt.Printf("Current Unik EC2 Backend set to %s\n", config.Url)
	return nil
}