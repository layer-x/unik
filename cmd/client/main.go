package main

import (
	"github.com/codegangsta/cli"
	"os"
	"os/user"
	"path/filepath"
	"io/ioutil"
	"github.com/docker/docker/vendor/src/github.com/docker/go/canonical/json"
	"github.com/layer-x/layerx-commons/lxerrors"
"github.com/layer-x/unik/cmd/types"
	"github.com/layer-x/unik/cmd/client/commands"
)

func main() {

	app := cli.NewApp()
	app.Name = "unik"
	app.Usage = ""
	app.Commands = []cli.Command{
		{
			Name:      "push",
			Aliases:   []string{"b"},
			ArgsUsage: "unik build [OPTIONS] NAME PATH",
			Usage:     "Push and build a new unikernel from the source code at PATH",
			Action: func(c *cli.Context) {
				if len(c.Args()) != 2 {
					println("unik: \"build\" requires exactly 2 arguments")
					println("See 'unik build -h'")
					println("\nUSAGE:\n    unik build [OPTIONS] NAME PATH\n")
					println("Build a new unikernel from the source code at PATH")
					os.Exit(-1)
				}
				appName := c.Args().Get(0)
				path := c.Args().Get(1)
				config, err := getConfig()
				if err != nil {
					println("You must be logged in to run this command.")
					println("Try 'unik login -u USERNAME -p PASSWORD UNIK_URL'")
					os.Exit(-1)
				}
				err = commands.Push(config, appName, path)
				if err != nil {
					println("unik push failed!")
					println("error: "+err.Error())
					os.Exit(-1)
				}
			},
		},
	}

	app.Run(os.Args)
}

func getConfig() (types.UnikConfig, error) {
	usr, err := user.Current()
	if err != nil {
		panic("user not found: " + err.Error())
	}
	configPath := filepath.Join(usr.HomeDir, ".unik", "config.json")
	configJson, err := ioutil.ReadFile(configPath)
	if err != nil {
		return types.UnikConfig{}, lxerrors.New("could not read config file", err)
	}
	var config types.UnikConfig
	err = json.Unmarshal(configJson, &config)
	if err != nil {
		return types.UnikConfig{}, lxerrors.New("could not unmarshall config from json", err)
	}
	return config, nil
}