package main

import (
	"github.com/codegangsta/cli"
	"os"
	"os/user"
	"path/filepath"
	"io/ioutil"
	"github.com/layer-x/layerx-commons/lxerrors"
"github.com/layer-x/unik/cmd/types"
	"github.com/layer-x/unik/cmd/client/commands"
"encoding/json"
)

func main() {

	app := cli.NewApp()
	app.Name = "unik"
	app.Usage = ""
	var forcePush bool
	var forceRmu bool
	var follow bool
	var runInstances int
	var unikernelName string
	var instanceName string
	app.Commands = []cli.Command{
		{
			Name:      "delete",
			Aliases:   []string{"rm"},
			ArgsUsage: "unik rm INSTANCE_ID_1 [INSTANCE_ID_2...]",
			Usage:     "delete running instances",
			Action: func(c *cli.Context) {
				if len(c.Args()) < 1 {
					println("unik: \"rm\" takes at least one argument")
					println("See 'unik rm -h'")
					println("\nUSAGE:\n    unik rm INSTANCE_ID_1 [INSTANCE_ID_2...]\n")
					println("delete running instances")
					os.Exit(-1)
				}
				config, err := getConfig()
				if err != nil {
					println("You must be logged in to run this command.")
					println("Try 'unik target UNIK_URL'")
					os.Exit(-1)
				}
				for _, instanceId := range c.Args() {
					err = commands.Rm(config, instanceId)
					if err != nil {
						println("unik rm failed!")
						println("error: "+err.Error())
						os.Exit(-1)
					}
				}
			},
		},
		{
			Name:      "delete-unikernel",
			Aliases:   []string{"rmu"},
			ArgsUsage: "unik rmu [-f|] UNIKERNEL_NAME_1 [UNIKERNEL_NAME_2...]",
			Usage:     "delete compiled unikernel",
			Flags: []cli.Flag{
				cli.BoolFlag{
					Name: "force, f",
					Usage: "force delete running instances of this unikernel",
					Destination: &forceRmu,
				},
			},
			Action: func(c *cli.Context) {
				if len(c.Args()) < 1 {
					println("unik: \"rmu\" takes at least one argument")
					println("See 'unik rmu -h'")
					println("\nUSAGE:\n    unik rmu UNIKERNEL_NAME_1 [UNIKERNEL_NAME_2...]\n")
					println("delete compiled unikernel")
					os.Exit(-1)
				}
				config, err := getConfig()
				if err != nil {
					println("You must be logged in to run this command.")
					println("Try 'unik target UNIK_URL'")
					os.Exit(-1)
				}
				for _, instanceId := range c.Args() {
					err = commands.Rmu(config, instanceId, forceRmu)
					if err != nil {
						println("unik rmu failed!")
						println("error: "+err.Error())
						os.Exit(-1)
					}
				}
			},
		},
		{
			Name:      "logs",
			Aliases:   []string{"l"},
			ArgsUsage: "unik logs [-f] NAME",
			Usage:     "get stdout/stderr from a running unikernel",
			Flags: []cli.Flag{
				cli.BoolFlag{
					Name: "-follow, f",
					Usage: "Follow logs",
					Destination: &follow,
				},
				cli.StringFlag{
					Name: "name, n",
					Usage: "name=CUSTOM_INSTANCE_NAME",
					Value: "",
					Destination: &instanceName,
				},
			},
			Action: func(c *cli.Context) {
				if len(c.Args()) != 1 {
					println("unik: \"run\" requires exactly 1 argument")
					println("See 'unik logs -h'")
					println("\nUSAGE:\n    unik logs [-f] NAME\n")
					println("get stdout/stderr from a running unikernel")
					os.Exit(-1)
				}
				unikInstanceId := c.Args().Get(0)
				config, err := getConfig()
				if err != nil {
					println("You must be logged in to run this command.")
					println("Try 'unik target UNIK_URL'")
					os.Exit(-1)
				}
				err = commands.Logs(config, unikInstanceId, follow)
				if err != nil {
					println("unik logs failed!")
					println("error: "+err.Error())
					os.Exit(-1)
				}
			},
		},
		{
			Name:      "ps",
			ArgsUsage: "unik ps [-u UNIKERNEL]",
			Usage:     "list running instances",
			Flags: []cli.Flag{
				cli.StringFlag{
					Name: "unikernel, u",
					Usage: "unikernel=NAME_OF_UNIKERNEL",
					Value: "",
					Destination: &unikernelName,
				},
			},
			Action: func(c *cli.Context) {
				if len(c.Args()) != 0 {
					println("unik: \"ps\" takes no arguments")
					println("See 'unik ps -h'")
					println("\nUSAGE:\n    unik ps [-u UNIKERNEL]\n")
					println("list running instances")
					os.Exit(-1)
				}
				config, err := getConfig()
				if err != nil {
					println("You must be logged in to run this command.")
					println("Try 'unik target UNIK_URL'")
					os.Exit(-1)
				}
				err = commands.Ps(config, unikernelName)
				if err != nil {
					println("unik ps failed!")
					println("error: "+err.Error())
					os.Exit(-1)
				}
			},
		},
		{
			Name:      "push",
			Aliases:   []string{"p"},
			ArgsUsage: "unik push [OPTIONS] NAME PATH",
			Usage:     "Push and push a new unikernel from the source code at PATH",
			Flags: []cli.Flag{
				cli.BoolFlag{
					Name: "force, f",
					Usage: "force overwriting previous unikernel",
					Destination: &forcePush,
				},
			},
			Action: func(c *cli.Context) {
				if len(c.Args()) != 2 {
					println("unik: \"push\" requires exactly 2 arguments")
					println("See 'unik push -h'")
					println("\nUSAGE:\n    unik push [-f] NAME PATH\n")
					println("push a new unikernel from the source code at PATH")
					os.Exit(-1)
				}
				unikernelName := c.Args().Get(0)
				path := c.Args().Get(1)
				config, err := getConfig()
				if err != nil {
					println("You must be logged in to run this command.")
					println("Try 'unik target UNIK_URL'")
					os.Exit(-1)
				}
				err = commands.Push(config, unikernelName, path, forcePush)
				if err != nil {
					println("unik push failed!")
					println("error: "+err.Error())
					os.Exit(-1)
				}
			},
		},
		{
			Name:      "run",
			Aliases:   []string{"r"},
			ArgsUsage: "unik run [-i=INSTANCES] NAME",
			Usage:     "run one or more instances of a unikernel",
			Flags: []cli.Flag{
				cli.IntFlag{
					Name: "instances, i",
					Usage: "instances=NUM_OF_INSTANCES",
					Value: 1,
					Destination: &runInstances,
				},
				cli.StringFlag{
					Name: "name, n",
					Usage: "name=CUSTOM_INSTANCE_NAME",
					Value: "",
					Destination: &instanceName,
				},
			},
			Action: func(c *cli.Context) {
				if len(c.Args()) != 1 {
					println("unik: \"run\" requires exactly 1 argument")
					println("See 'unik run -h'")
					println("\nUSAGE:\n    unik run [-i=INSTANCES] NAME\n")
					println("run one or more instances of a unikernel")
					os.Exit(-1)
				}
				unikernelName := c.Args().Get(0)
				config, err := getConfig()
				if err != nil {
					println("You must be logged in to run this command.")
					println("Try 'unik target UNIK_URL'")
					os.Exit(-1)
				}
				if runInstances < 1 {
					runInstances = 1
				}
				err = commands.Run(config, unikernelName, instanceName, runInstances)
				if err != nil {
					println("unik run failed!")
					println("error: "+err.Error())
					os.Exit(-1)
				}
			},
		},
		{
			Name:      "target",
			Aliases:   []string{"t"},
			ArgsUsage: "unik target URL",
			Usage:     "set unik cli endpoint",
			Action: func(c *cli.Context) {
				if len(c.Args()) != 1 {
					println("unik: \"target\" requires exactly 1 argument")
					println("See 'unik target -h'")
					println("\nUSAGE:\n    unik target URL\n")
					println("set unik cli endpoint")
					os.Exit(-1)
				}
				url := c.Args().Get(0)
				err := commands.Target(url)
				if err != nil {
					println("unik target failed!")
					println("error: "+err.Error())
					os.Exit(-1)
				}
			},
		},
		{
			Name:      "unikernels",
			Aliases:   []string{"u"},
			ArgsUsage: "unik unikernels",
			Usage:     "list compiled unikernels",
			Action: func(c *cli.Context) {
				if len(c.Args()) != 0 {
					println("unik: \"unikernels\" takes no arguments")
					println("See 'unik unikernels -h'")
					println("\nUSAGE:\n    unik unikernels\n")
					println("list running unikernels")
					os.Exit(-1)
				}
				config, err := getConfig()
				if err != nil {
					println("You must be logged in to run this command.")
					println("Try 'unik target UNIK_URL'")
					os.Exit(-1)
				}
				err = commands.Images(config)
				if err != nil {
					println("unik unikernels failed!")
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
