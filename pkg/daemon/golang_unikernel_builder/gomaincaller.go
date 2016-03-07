package main

import (
	"C"
	"net/http"
	"io/ioutil"
	"encoding/json"
	"os"
)

//export gomaincaller
func gomaincaller() {
	var instanceData UnikInstanceData

	resp, err := http.Get("http://169.254.169.254/latest/user-data")
	if err != nil {
		panic(err)
	}
	defer resp.Body.Close()
	data, err := ioutil.ReadAll(resp.Body)
	if err != nil {
		panic(err)
	}
	err = json.Unmarshal(data, &instanceData)
	if err != nil {
		panic(err)
	}
	for key, value := range instanceData.Env {
		os.Setenv(key, value)
	}

	main()
}

//make sure this remains the same as defined in
//github.com/layer-x/unik/pkg/daemon/ec2api/run_unik_instance.go
type UnikInstanceData struct {
	Tags			 map[string]string `json:"Tags"`
	Env				 map[string]string `json:"Env"`
}