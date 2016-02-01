package ec2daemon
import "github.com/layer-x/layerx-commons/lxerrors"

func deleteApp(appName string, force bool) error {
	unikernels, err := listUnikernels()
	if err != nil {
		return lxerrors.New("could not get unikernel list", err)
	}
	for _, unikernel := range unikernels {
		if unikernel.AppName == appName {
			err = deleteUnikernel(unikernel.AMI, force)
			if err != nil {
				return lxerrors.New("could not delete unikernel "+unikernel.AMI, err)
			}
			return nil
		}
	}
	return lxerrors.New("could not find unikernel for app "+appName, nil)
}