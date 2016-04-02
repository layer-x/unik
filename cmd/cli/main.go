package main

import (
	"encoding/json"
	"github.com/codegangsta/cli"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/pkg/cli/commands"
	"github.com/layer-x/unik/pkg/types"
	"io/ioutil"
	"os"
	"os/user"
	"path/filepath"
)

func main() {

	app := cli.NewApp()
	app.Name = "unik"
	app.Usage = ""
	var force, follow, destroy, verbose bool
	var runInstances, volumeSize int
	var unikernelName, instanceName, deviceName string
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
					println("USAGE:    unik [-V] rm INSTANCE_ID_1 [INSTANCE_ID_2...]")
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
					err = commands.Rm(config, instanceId, verbose)
					if err != nil {
						println("unik rm failed!")
						println("error: " + err.Error())
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
					Name:        "force, f",
					Usage:       "force delete running instances of this unikernel",
					Destination: &force,
				},
			},
			Action: func(c *cli.Context) {
				if len(c.Args()) < 1 {
					println("unik: \"rmu\" takes at least one argument")
					println("See 'unik rmu -h'")
					println("USAGE:    unik [-V] rmu UNIKERNEL_NAME_1 [UNIKERNEL_NAME_2...]")
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
					err = commands.Rmu(config, instanceId, force, verbose)
					if err != nil {
						println("unik rmu failed!")
						println("error: " + err.Error())
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
					Name:        "follow, f",
					Usage:       "Follow logs",
					Destination: &follow,
				},
				cli.BoolFlag{
					Name:        "destroy, d",
					Usage:       "Destroy instance after disconnect (only works if follow is enabled)",
					Destination: &destroy,
				},
				cli.StringFlag{
					Name:        "name, n",
					Usage:       "name=CUSTOM_INSTANCE_NAME",
					Value:       "",
					Destination: &instanceName,
				},
			},
			Action: func(c *cli.Context) {
				if len(c.Args()) != 1 {
					println("unik: \"run\" requires exactly 1 argument")
					println("See 'unik logs -h'")
					println("USAGE:    unik [-V] logs [-f] NAME")
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
				err = commands.Logs(config, unikInstanceId, follow, destroy)
				if err != nil {
					println("unik logs failed!")
					println("error: " + err.Error())
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
					Name:        "unikernel, u",
					Usage:       "--unikernel=NAME_OF_UNIKERNEL",
					Value:       "",
					Destination: &unikernelName,
				},
			},
			Action: func(c *cli.Context) {
				if len(c.Args()) != 0 {
					println("unik: \"ps\" takes no arguments")
					println("See 'unik ps -h'")
					println("USAGE:    unik [-V] ps [-u UNIKERNEL]")
					println("list running instances")
					os.Exit(-1)
				}
				config, err := getConfig()
				if err != nil {
					println("You must be logged in to run this command.")
					println("Try 'unik target UNIK_URL'")
					os.Exit(-1)
				}
				err = commands.Ps(config, unikernelName, verbose)
				if err != nil {
					println("unik ps failed!")
					println("error: " + err.Error())
					os.Exit(-1)
				}
			},
		},
		{
			Name:      "build",
			Aliases:   []string{"b"},
			ArgsUsage: "unik build [OPTIONS] NAME PATH",
			Usage:     "build a new unikernel from the source code at PATH",
			Flags: []cli.Flag{
				cli.BoolFlag{
					Name:        "force, f",
					Usage:       "force overwriting previous unikernel",
					Destination: &force,
				},
			},
			Action: func(c *cli.Context) {
				if len(c.Args()) != 2 {
					println("unik: \"build\" requires exactly 2 arguments")
					println("See 'unik build -h'")
					println("USAGE:    unik [-V] build [-f] NAME PATH")
					println("build a new unikernel from the source code at PATH")
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
				err = commands.Build(config, unikernelName, path, force, verbose)
				if err != nil {
					println("unik build failed!")
					println("error: " + err.Error())
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
					Name:        "instances, i",
					Usage:       "instances=NUM_OF_INSTANCES",
					Value:       1,
					Destination: &runInstances,
				},
				cli.StringFlag{
					Name:        "name, n",
					Usage:       "name=CUSTOM_INSTANCE_NAME",
					Value:       "",
					Destination: &instanceName,
				},
				cli.StringSliceFlag{
					Name:        "tags, t",
					Usage:       "-t \"key1=value1,key2=value2...\"",
					Value:       &cli.StringSlice{},
				},
				cli.StringSliceFlag{
					Name:        "env, e",
					Usage:       "-e \"key1=value1 -e \"key2=value2\"...\"",
					Value:       &cli.StringSlice{},
				},
			},
			Action: func(c *cli.Context) {
				if len(c.Args()) != 1 {
					println("unik: \"run\" requires exactly 1 argument")
					println("See 'unik run -h'")
					println("USAGE:    unik [-V] run [-i=INSTANCES] NAME")
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
				tags := c.StringSlice("tags")
				env := c.StringSlice("env")
				err = commands.Run(config, unikernelName, instanceName, runInstances, tags, env, verbose)
				if err != nil {
					println("unik run failed!")
					println("error: " + err.Error())
					os.Exit(-1)
				}
			},
		},
		{
			Name:      "push",
			ArgsUsage: "unik push UNIKERNEL_NAME",
			Usage:     "push a unikernel image to unikhub.tk",
			Action: func(c *cli.Context) {
				if len(c.Args()) != 1 {
					println("unik: \"push\" requires exactly 1 argument")
					println("See 'unik push -h'")
					println("USAGE:    unik [-V] push UNIKERNEL_NAME")
					println("push a unikernel image to unikhub.tk")
					os.Exit(-1)
				}
				unikernelName := c.Args().Get(0)
				config, err := getConfig()
				if err != nil {
					println("You must be logged in to run this command.")
					println("Try 'unik target UNIK_URL'")
					os.Exit(-1)
				}
				err = commands.Push(config, unikernelName, verbose)
				if err != nil {
					println("unik push failed!")
					println("error: " + err.Error())
					os.Exit(-1)
				}
			},
		},
		{
			Name:      "pull",
			ArgsUsage: "unik pull UNIKERNEL_NAME",
			Usage:     "pull a unikernel image to unikhub.tk",
			Action: func(c *cli.Context) {
				if len(c.Args()) != 1 {
					println("unik: \"pull\" requires exactly 1 argument")
					println("See 'unik pull -h'")
					println("USAGE:    unik [-V] pull UNIKERNEL_NAME")
					println("pull a unikernel image to unikhub.tk")
					os.Exit(-1)
				}
				unikernelName := c.Args().Get(0)
				config, err := getConfig()
				if err != nil {
					println("You must be logged in to run this command.")
					println("Try 'unik target UNIK_URL'")
					os.Exit(-1)
				}
				err = commands.Pull(config, unikernelName, verbose)
				if err != nil {
					println("unik pull failed!")
					println("error: " + err.Error())
					os.Exit(-1)
				}
			},
		},
		{
			Name:      "target",
			Aliases:   []string{"t"},
			ArgsUsage: "unik target [URL]",
			Usage:     "set unik cli endpoint or view current endpoint",
			Action: func(c *cli.Context) {
				if len(c.Args()) == 0 {
					err := commands.ShowTarget()
					if err != nil {
						println("failed to reveal current target: " + err.Error())
						os.Exit(-1)
					}
					return
				}
				if len(c.Args()) != 1 {
					println("unik: \"target\" requires exactly 1 argument")
					println("See 'unik target -h'")
					println("USAGE:    unik [-V] target URL")
					println("set unik cli endpoint")
					os.Exit(-1)
				}
				url := c.Args().Get(0)
				err := commands.Target(url)
				if err != nil {
					println("unik target failed!")
					println("error: " + err.Error())
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
					println("USAGE:    unik [-V] unikernels [-v]")
					println("list running unikernels")
					os.Exit(-1)
				}
				config, err := getConfig()
				if err != nil {
					println("You must be logged in to run this command.")
					println("Try 'unik target UNIK_URL'")
					os.Exit(-1)
				}
				err = commands.Unikernels(config, verbose)
				if err != nil {
					println("unik unikernels failed!")
					println("error: " + err.Error())
					os.Exit(-1)
				}
			},
		},
		{
			Name:      "list-volumes",
			Aliases:   []string{"lv"},
			ArgsUsage: "unik [-V] list-volumes",
			Usage:     "list unik-managed volumes",
			Action: func(c *cli.Context) {
				if len(c.Args()) != 0 {
					println("unik: \"list-volumes\" takes no arguments")
					println("See 'unik list-volumes -h'")
					println("USAGE:    unik [-V] list-volumes")
					println("list unik-managed volumes")
					os.Exit(-1)
				}
				config, err := getConfig()
				if err != nil {
					println("You must be logged in to run this command.")
					println("Try 'unik target UNIK_URL'")
					os.Exit(-1)
				}
				err = commands.ListVolumes(config, verbose)
				if err != nil {
					println("unik list-volumes failed!")
					println("error: " + err.Error())
					os.Exit(-1)
				}
			},
		},
		{
			Name:      "create-volume",
			Aliases:   []string{"cv"},
			ArgsUsage: "unik [-V] create-volume NAME -s SIZE",
			Usage:     "create a unik-managed volume with NAME of SIZE GB",
			Flags: []cli.Flag{
				cli.IntFlag{
					Name:        "size, s",
					Usage:       "size SIZE_IN_GB",
					Value:       0,
					Destination: &volumeSize,
				},
			},
			Action: func(c *cli.Context) {
				if len(c.Args()) != 1 {
					println("unik: \"create-volume\" takes exactly 1 argument")
					println("See 'unik create-volume -h'")
					println("USAGE:    unik [-V] create-volume NAME SIZE_IN_GB")
					println("create a unik-managed SIZE GB volume with named NAME")
					os.Exit(-1)
				}
				config, err := getConfig()
				if err != nil {
					println("You must be logged in to run this command.")
					println("Try 'unik target UNIK_URL'")
					os.Exit(-1)
				}
				if volumeSize < 1 {
					println("Must specify a positive integer for size with -s flag.")
					println("See 'unik create-volume -h'")
					os.Exit(-1)
				}
				volumeName := c.Args().Get(0)
				err = commands.CreateVolume(config, volumeName, volumeSize, verbose)
				if err != nil {
					println("unik create-volume failed!")
					println("error: " + err.Error())
					os.Exit(-1)
				}
			},
		},
		{
			Name:      "delete-volume",
			Aliases:   []string{"rmv"},
			ArgsUsage: "unik rmu [-f|] VOLUME_NAME_1 [VOLUME_NAME_2...]",
			Usage:     "delete volume",
			Flags: []cli.Flag{
				cli.BoolFlag{
					Name:        "force, f",
					Usage:       "force delete if volume is attached to an instance",
					Destination: &force,
				},
			},
			Action: func(c *cli.Context) {
				if len(c.Args()) < 1 {
					println("unik: \"rmv\" takes at least one argument")
					println("See 'unik rmv -h'")
					println("USAGE:    unik rmv [-f|] VOLUME_NAME_1 [VOLUME_NAME_2...]")
					println("delete volume")
					os.Exit(-1)
				}
				config, err := getConfig()
				if err != nil {
					println("You must be logged in to run this command.")
					println("Try 'unik target UNIK_URL'")
					os.Exit(-1)
				}
				for _, volumeName := range c.Args() {
					err = commands.DeleteVolume(config, volumeName, force, verbose)
					if err != nil {
						println("unik rmu failed!")
						println("error: " + err.Error())
						os.Exit(-1)
					}
				}
			},
		},
		{
			Name:      "attach-volume",
			Aliases:   []string{"av"},
			ArgsUsage: "unik attach-volume INSTANCE_NAME VOLUME_NAME -d DEVICE_NAME",
			Usage:     "attach volume to instance",
			Flags: []cli.Flag{
				cli.StringFlag{
					Name:        "device, d",
					Usage:       "name the device should be attached with, eg '/dev/xvdf",
					Destination: &deviceName,
					Value: "",
				},
			},
			Action: func(c *cli.Context) {
				if len(c.Args()) != 2 {
					println("unik: \"av\" takes exactly 2 arguments")
					println("See 'unik attach-volume -h'")
					println("USAGE:    unik attach-volume INSTANCE_NAME VOLUME_NAME -d DEVICE_NAME")
					println("attach volume to instance")
					os.Exit(-1)
				}
				config, err := getConfig()
				if err != nil {
					println("You must be logged in to run this command.")
					println("Try 'unik target UNIK_URL'")
					os.Exit(-1)
				}
				if deviceName == "" {
					println("Must specify a device name with -d flag.")
					println("See 'unik attach-volume -h'")
					os.Exit(-1)
				}
				unikInstanceName := c.Args().Get(0)
				volumeName := c.Args().Get(1)
				err = commands.AttachVolume(config, unikInstanceName, volumeName, deviceName, verbose)
				if err != nil {
					println("unik attach-volume failed!")
					println("error: " + err.Error())
					os.Exit(-1)
				}
			},
		},
		{
			Name:      "detach-volume",
			Aliases:   []string{"dv"},
			ArgsUsage: "unik detach-volume [-f|] VOLUME_NAME_1 [VOLUME_NAME_2...]",
			Usage:     "detach volume from instance",
			Flags: []cli.Flag{
				cli.BoolFlag{
					Name:        "force, f",
					Usage:       "force detaching the volume",
					Destination: &force,
				},
			},
			Action: func(c *cli.Context) {
				if len(c.Args()) < 1 {
					println("unik: \"dv\" takes at least 1 argument")
					println("See 'unik detach-volume -h'")
					println("USAGE:    unik detach-volume [-f|] VOLUME_NAME_1 [VOLUME_NAME_2...]")
					println("detach volume from instance")
					os.Exit(-1)
				}
				config, err := getConfig()
				if err != nil {
					println("You must be logged in to run this command.")
					println("Try 'unik target UNIK_URL'")
					os.Exit(-1)
				}
				for _, volumeName := range c.Args() {
					err = commands.DetachVolume(config, volumeName, force, verbose)
					if err != nil {
						println("unik detach-volume failed!")
						println("error: " + err.Error())
						os.Exit(-1)
					}
				}
			},
		},
	}

	app.Flags = []cli.Flag{
		cli.BoolFlag{
			Name:        "verbose, V",
			Usage:       "stream logs from the unik backend",
			Destination: &verbose,
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
