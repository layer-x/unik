package daemon

import (
	"encoding/json"
	"fmt"
	"github.com/docker/docker/pkg/ioutils"
	"github.com/go-martini/martini"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/layerx-commons/lxlog"
	"github.com/layer-x/layerx-commons/lxmartini"
	"github.com/layer-x/unik/pkg/types"
	"github.com/pborman/uuid"
	"net/http"
	"strconv"
	"strings"
	"github.com/layer-x/unik/pkg/daemon/ec2"
	"github.com/layer-x/unik/pkg/daemon/vsphere"
	"github.com/layer-x/unik/pkg/daemon/docker_api"
)

type UnikDaemon struct {
	server *martini.ClassicMartini
	cpi    UnikCPI
}

func NewUnikDaemon(provider string, opts map[string]string) *UnikDaemon {
	logger := lxlog.New("daemon-setup")
	var cpi UnikCPI
	switch provider{
	case "ec2":
		cpi = ec2.NewUnikEC2CPI()
		break
	case "vsphere":
		vsphereCpi := vsphere.NewUnikVsphereCPI(logger, opts["vsphereUrl"], opts["vsphereUser"], opts["vspherePass"])
		vsphereCpi.StartInstanceDiscovery(logger)
		vsphereCpi.ListenForBootstrap(logger, 3001)
		cpi = vsphereCpi
		break
	default:
		logger.Fatalf("Unrecognized provider " + provider)
	}
	return &UnikDaemon{
		server: lxmartini.QuietMartini(),
		cpi: cpi,
	}
}

func (d *UnikDaemon) registerHandlers() {
	streamOrRespond := func(res http.ResponseWriter, req *http.Request, actionName string, action func(logger *lxlog.LxLogger) (interface{}, error)) {
		verbose := req.URL.Query().Get("verbose")
		logger := lxlog.New(actionName)
		if strings.ToLower(verbose) == "true" {
			httpOutStream := ioutils.NewWriteFlusher(res)
			uuid := uuid.New()
			logger.AddWriter(uuid, lxlog.DebugLevel, httpOutStream)
			defer logger.DeleteWriter(uuid)

			jsonObject, err := action(logger)
			if err != nil {
				lxmartini.Respond(res, err)
				logger.WithErr(err).Errorf("error performing action")
				return
			}
			if text, ok := jsonObject.(string); ok {
				_, err = httpOutStream.Write([]byte(text + "\n"))
				return
			}
			if jsonObject != nil {
				httpOutStream.Write([]byte("BEGIN_JSON_DATA\n"))
				data, err := json.Marshal(jsonObject)
				if err != nil {
					lxmartini.Respond(res, lxerrors.New("could not marshal message to json", err))
					return
				}
				data = append(data, byte('\n'))
				_, err = httpOutStream.Write(data)
				if err != nil {
					lxmartini.Respond(res, lxerrors.New("could not write data", err))
					return
				}
				return
			} else {
				res.WriteHeader(http.StatusNoContent)
			}
		}
		jsonObject, err := action(logger)
		if err != nil {
			lxmartini.Respond(res, err)
			logger.WithErr(err).Errorf("error performing action")
			return
		}
		if jsonObject != nil {
			lxmartini.Respond(res, jsonObject)
		} else {
			res.WriteHeader(http.StatusNoContent)
		}
	}

	d.server.Get("/instances", func(res http.ResponseWriter, req *http.Request) {
		streamOrRespond(res, req, "get-instances", func(logger *lxlog.LxLogger) (interface{}, error) {
			unikInstances, err := d.cpi.ListUnikInstances(logger)
			if err != nil {
				logger.WithErr(err).Errorf("could not get unik instance list")
			} else {
				logger.WithFields(lxlog.Fields{
					"instances": unikInstances,
				}).Debugf("Listing all unik instances")
			}
			return unikInstances, err
		})
	})
	d.server.Get("/unikernels", func(res http.ResponseWriter, req *http.Request) {
		streamOrRespond(res, req, "get-unikernels", func(logger *lxlog.LxLogger) (interface{}, error) {
			unikernels, err := d.cpi.ListUnikernels(logger)
			if err != nil {
				logger.WithErr(err).Errorf("could not get unikernel list")
			} else {
				logger.WithFields(lxlog.Fields{
					"unikernels": unikernels,
				}).Debugf("Listing all unikernels")
			}
			return unikernels, err
		})
	})
	d.server.Post("/unikernels/:unikernel_name", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		streamOrRespond(res, req, "build-unikernel", func(logger *lxlog.LxLogger) (interface{}, error) {
			err := req.ParseMultipartForm(0)
			if err != nil {
				return nil, err
			}
			logger.WithFields(lxlog.Fields{
				"req": req,
			}).Debugf("parsing multipart form")
			unikernelName := params["unikernel_name"]
			if unikernelName == "" {
				return nil, lxerrors.New("unikernel must be named", nil)
			}
			logger.WithFields(lxlog.Fields{
				"form": req.Form,
			}).Debugf("parsing form file marked 'tarfile'")
			uploadedTar, header, err := req.FormFile("tarfile")
			if err != nil {
				return nil, err
			}
			defer uploadedTar.Close()
			force := req.FormValue("force")

			var desiredVolumes []*types.VolumeSpec
			volumeOptionsString := req.FormValue("volume_opts")
			if len(volumeOptionsString) > 0 {
				//expected format:
				//"folder1:/dev1,folder2:/dev2,/dev"
				volumeOptions := strings.Split(volumeOptionsString, ",")
				for _, volOpt := range volumeOptions {
					//volopt can have 2 formats:
					//"folder:/devicename" or "INT:/devicename" where int is size
					components := strings.Split(volOpt, ":")
					if len(components) != 2 {
						return nil, lxerrors.New("failed to parse volume options:"+volumeOptionsString+". be careful to not use special characters ':' or ',' in folder or device names", nil)
					}
					size, err := strconv.Atoi(components[0])
					if err != nil { //assume a folder was given
						dataFolder := components[0]
						deviceName := components[1]
						dataFolderTar, dataFolderTarHeader, err := req.FormFile(dataFolder)
						if err != nil {
							return nil, lxerrors.New("parsing form file "+dataFolder, err)
						}
						desiredVolumes = append(desiredVolumes, &types.VolumeSpec{
							MountPoint: deviceName,
							DataFolder: dataFolder,
							DataTar: dataFolderTar,
							DataTarHeader: dataFolderTarHeader,
						})
					} else {
						//create an empty volume as default snapshot for these volumes
						desiredVolumes = append(desiredVolumes, &types.VolumeSpec{
							MountPoint: volOpt,
							Size: int64(size),
						})
					}
				}
			}

			logger.WithFields(lxlog.Fields{
				"unikernelName": unikernelName,
				"force": force,
				"uploadedTar": uploadedTar,
				"volume-spec": desiredVolumes,
			}).Debugf("building unikernel")

			err = d.cpi.BuildUnikernel(logger, unikernelName, force, uploadedTar, header, desiredVolumes)
			if err != nil {
				logger.WithErr(err).WithFields(lxlog.Fields{
					"form": fmt.Sprintf("%v", req.Form), "unikernel_name": unikernelName,
				}).Errorf("building unikernel from unikernel source")
				logger.WithFields(lxlog.Fields{

				}).Warnf("cleaning up unikernel build artifacts (volumes, snapshots)")
				snapshotErr := d.cpi.DeleteArtifactsForUnikernel(logger, unikernelName)
				if snapshotErr != nil {
					logger.WithErr(err).WithFields(lxlog.Fields{
						"unikernel_name": unikernelName,
					}).Errorf("could not remove volume and/or snapshot for instance")
				}
				return nil, err
			}
			logger.Debugf("verifying unikernel succeeded")
			unikernels, err := d.cpi.ListUnikernels(logger)
			if err != nil {
				logger.WithErr(err).Errorf("could not get unikernel list")
				return nil, err
			}
			for _, unikernel := range unikernels {
				if unikernel.UnikernelName == unikernelName {
					return unikernel, nil
				}
			}
			return "unikernel created", nil
		})
	})
	d.server.Post("/unikernels/:unikernel_name/run", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		streamOrRespond(res, req, "run-unikernel", func(logger *lxlog.LxLogger) (interface{}, error) {
			logger.WithFields(lxlog.Fields{
				"request": req, "query": req.URL.Query(),
			}).Debugf("recieved run request")
			unikernelName := params["unikernel_name"]
			if unikernelName == "" {
				logger.WithFields(lxlog.Fields{
					"request": fmt.Sprintf("%v", req),
				}).Errorf("unikernel must be named")
				return nil, lxerrors.New("unikernel must be named", nil)
			}
			instancesStr := req.URL.Query().Get("instances")
			if instancesStr == "" {
				instancesStr = "1"
			}
			instanceName := req.URL.Query().Get("name")
			instances, err := strconv.Atoi(instancesStr)
			if err != nil {
				return nil, err
			}
			fullTagString := req.URL.Query().Get("tags")
			tagPairs := strings.Split(fullTagString, ",")
			tags := make(map[string]string)

			envDelimiter := req.URL.Query().Get("useDelimiter")
			if envDelimiter == "" {
				envDelimiter = ","
			}
			envPairDelimiter := req.URL.Query().Get("usePairDelimiter")
			if envPairDelimiter == "" {
				envPairDelimiter = "="
			}

			for _, tagPair := range tagPairs {
				splitTag := strings.Split(tagPair, "=")
				if len(splitTag) != 2 {
					logger.WithFields(lxlog.Fields{
						"tagPair": tagPair,
					}).Warnf("was given a tag string with an invalid format, ignoring")
					continue
				}
				tags[splitTag[0]] = splitTag[1]
			}

			fullEnvString := req.URL.Query().Get("env")
			envPairs := strings.Split(fullEnvString, envDelimiter)
			env := make(map[string]string)

			for _, envPair := range envPairs {
				splitEnv := strings.Split(envPair, envPairDelimiter)
				if len(splitEnv) != 2 {
					logger.WithFields(lxlog.Fields{
						"envPair": envPair,
					}).Warnf("was given a env string with an invalid format, ignoring")
					continue
				}
				env[splitEnv[0]] = splitEnv[1]
			}

			instanceIds, err := d.cpi.RunUnikInstance(logger, unikernelName, instanceName, int64(instances), tags, env)
			if err != nil {
				return nil, err
			}
			logger.Debugf("verifying instances started")
			successfulInstances := []*types.UnikInstance{}
			unikInstances, err := d.cpi.ListUnikInstances(logger)
			if err != nil {
				logger.WithErr(err).Errorf("could not get unik instance list")
			}
			for _, unikInstance := range unikInstances {
				CheckIds:
				for _, instanceId := range instanceIds {
					if unikInstance.UnikInstanceID == instanceId {
						successfulInstances = append(successfulInstances, unikInstance)
						break CheckIds
					}
				}
			}
			logger.WithFields(lxlog.Fields{
				"instance_ids": instanceIds,
			}).Infof(instancesStr + " instances started of unikernel " + unikernelName)
			return successfulInstances, nil
		})
	})
	d.server.Post("/unikernels/:unikernel_name/push", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		streamOrRespond(res, req, "push-unikernel", func(logger *lxlog.LxLogger) (interface{}, error) {
			unikernelName := params["unikernel_name"]
			logger.WithFields(lxlog.Fields{
				"unikernelName": unikernelName,
			}).Debugf("pushing unikernel to unikhub.tk")
			err := d.cpi.Push(logger, unikernelName)
			if err != nil {
				return nil, lxerrors.New("could not push unikernel to unikhub", err)
			}
			logger.WithFields(lxlog.Fields{
				"unikernelName": unikernelName,
			}).Infof("unikernel pushed")
			return unikernelName, nil
		})
	})
	d.server.Post("/unikernels/:unikernel_name/pull", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		streamOrRespond(res, req, "pull-unikernel", func(logger *lxlog.LxLogger) (interface{}, error) {
			unikernelName := params["unikernel_name"]
			logger.WithFields(lxlog.Fields{
				"unikernelName": unikernelName,
			}).Debugf("pulling unikernel to unikhub.tk")
			err := d.cpi.Pull(logger, unikernelName)
			if err != nil {
				return nil, lxerrors.New("could not pull unikernel to unikhub", err)
			}
			logger.WithFields(lxlog.Fields{
				"unikernelName": unikernelName,
			}).Infof("unikernel pulled")
			return unikernelName, nil
		})
	})
	d.server.Delete("/instances/:instance_id", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		streamOrRespond(res, req, "delete-instance", func(logger *lxlog.LxLogger) (interface{}, error) {
			instanceId := params["instance_id"]
			logger.WithFields(lxlog.Fields{
				"request": req,
			}).Infof("deleting instance " + instanceId)
			err := d.cpi.DeleteUnikInstance(logger, instanceId)
			if err != nil {
				logger.WithErr(err).Errorf("could not delete instance " + instanceId)
				return nil, err
			}
			return nil, err
		})
	})
	d.server.Delete("/unikernels/:unikernel_name", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		streamOrRespond(res, req, "delete-unikernel", func(logger *lxlog.LxLogger) (interface{}, error) {
			unikernelName := params["unikernel_name"]
			if unikernelName == "" {
				logger.WithFields(lxlog.Fields{
					"request": fmt.Sprintf("%v", req),
				}).Errorf("unikernel must be named")
				return nil, lxerrors.New("unikernel must be named", nil)
			}
			forceStr := req.URL.Query().Get("force")
			logger.WithFields(lxlog.Fields{
				"request": req,
			}).Infof("deleting instance " + unikernelName)
			force := false
			if strings.ToLower(forceStr) == "true" {
				force = true
			}
			err := d.cpi.DeleteUnikernelByName(logger, unikernelName, force)
			if err != nil {
				logger.WithErr(err).Errorf("could not delete unikernel " + unikernelName)
				return nil, err
			}
			return nil, nil
		})
	})
	d.server.Get("/instances/:instance_id/logs", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		streamOrRespond(res, req, "get-instance-logs", func(logger *lxlog.LxLogger) (interface{}, error) {
			unikInstanceId := params["instance_id"]
			follow := req.URL.Query().Get("follow")
			res.Write([]byte("getting logs for " + unikInstanceId + "...\n"))
			if f, ok := res.(http.Flusher); ok {
				f.Flush()
			} else {
				logger.Errorf("no flush!")
				return nil, lxerrors.New("not a flusher", nil)
			}
			if strings.ToLower(follow) == "true" {
				deleteOnDisconnectStr := req.URL.Query().Get("delete")
				deleteOnDisconnect := false
				if strings.ToLower(deleteOnDisconnectStr) == "true" {
					deleteOnDisconnect = true
				}

				output := ioutils.NewWriteFlusher(res)
				err := d.cpi.StreamLogs(logger, unikInstanceId, output, deleteOnDisconnect)
				if err != nil {
					logger.WithErr(err).WithFields(lxlog.Fields{
						"unikInstanceId": unikInstanceId,
					}).Warnf("streaming logs stopped")
					return nil, err
				}
			}
			logs, err := d.cpi.GetLogs(logger, unikInstanceId)
			if err != nil {
				logger.WithErr(err).WithFields(lxlog.Fields{
					"unikInstanceId": unikInstanceId,
				}).Errorf("failed to perform get logs request")
				return nil, err
			}
			return logs, nil
		})
	})
	d.server.Get("/volumes", func(res http.ResponseWriter, req *http.Request) {
		streamOrRespond(res, req, "get-volumes", func(logger *lxlog.LxLogger) (interface{}, error) {
			logger.Debugf("listing volumes started")
			volumes, err := d.cpi.ListVolumes(logger)
			if err != nil {
				return nil, lxerrors.New("could not retrieve volumes", err)
			}
			logger.WithFields(lxlog.Fields{
				"volumes": volumes,
			}).Infof("volumes")
			return volumes, nil
		})
	})
	d.server.Post("/volumes/:volume_name", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		streamOrRespond(res, req, "create-volume", func(logger *lxlog.LxLogger) (interface{}, error) {
			volumeName := params["volume_name"]
			sizeStr := req.URL.Query().Get("size")
			if sizeStr == "" {
				sizeStr = "1"
			}
			size, err := strconv.Atoi(sizeStr)
			if err != nil {
				return nil, lxerrors.New("could not parse given size", err)
			}
			logger.WithFields(lxlog.Fields{
				"size": size, "name": volumeName,
			}).Debugf("creating volume started")
			volume, err := d.cpi.CreateVolume(logger, volumeName, size)
			if err != nil {
				return nil, lxerrors.New("could not create volume", err)
			}
			logger.WithFields(lxlog.Fields{
				"volume": volume,
			}).Infof("volume created")
			return volume, nil
		})
	})
	d.server.Delete("/volumes/:volume_name", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		streamOrRespond(res, req, "delete-volume", func(logger *lxlog.LxLogger) (interface{}, error) {
			volumeName := params["volume_name"]
			forceStr := req.URL.Query().Get("force")
			force := false
			if strings.ToLower(forceStr) == "true" {
				force = true
			}

			logger.WithFields(lxlog.Fields{
				"force": force, "name": volumeName,
			}).Debugf("deleting volume started")
			err := d.cpi.DeleteVolume(logger, volumeName, force)
			if err != nil {
				return nil, lxerrors.New("could not create volume", err)
			}
			logger.WithFields(lxlog.Fields{
				"volume": volumeName,
			}).Infof("volume deleted")
			return volumeName, nil
		})
	})
	d.server.Post("/instances/:instance_id/volumes/:volume_name", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		streamOrRespond(res, req, "attach-volume", func(logger *lxlog.LxLogger) (interface{}, error) {
			volumeName := params["volume_name"]
			instanceId := params["instance_id"]
			device := req.URL.Query().Get("device")
			if device == "" {
				return nil, lxerrors.New("must provide a device name in URL query", nil)
			}
			logger.WithFields(lxlog.Fields{
				"instance": instanceId,
				"volume": volumeName,
			}).Debugf("attaching volume to instance")
			err := d.cpi.AttachVolume(logger, volumeName, instanceId, device)
			if err != nil {
				return nil, lxerrors.New("could not attach volume to instance", err)
			}
			logger.WithFields(lxlog.Fields{
				"instance": instanceId,
				"volume": volumeName,
			}).Infof("volume attached")
			return volumeName, nil
		})
	})
	d.server.Post("/volumes/:volume_name/detach", func(res http.ResponseWriter, req *http.Request, params martini.Params) {
		streamOrRespond(res, req, "detach-volume", func(logger *lxlog.LxLogger) (interface{}, error) {
			volumeName := params["volume_name"]
			forceStr := req.URL.Query().Get("force")
			force := false
			if strings.ToLower(forceStr) == "true" {
				force = true
			}
			logger.WithFields(lxlog.Fields{
				"volume": volumeName,
			}).Debugf("detaching volume from any instance")
			err := d.cpi.DetachVolume(logger, volumeName, force)
			if err != nil {
				return nil, lxerrors.New("could not attach volume to instance", err)
			}
			logger.WithFields(lxlog.Fields{
				"volume": volumeName,
			}).Infof("volume detached")
			return volumeName, nil
		})
	})
}

func (d *UnikDaemon) addDockerHandlers(logger *lxlog.LxLogger) {
	d.server = docker_api.AddDockerApi(logger, d.server)
}

func (d *UnikDaemon) Start(logger *lxlog.LxLogger, port int) {
	d.registerHandlers()
	d.addDockerHandlers(logger)
	d.server.RunOnAddr(fmt.Sprintf(":%v", port))
}
