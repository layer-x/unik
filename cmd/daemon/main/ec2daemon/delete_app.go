package ec2daemon
import "github.com/layer-x/layerx-commons/lxerrors"

func DeleteApp(unikernelName string, force bool) error {
	unikernels, err := ListUnikernels()
	if err != nil {
		return lxerrors.New("could not get unikernel list", err)
	}
	for _, unikernel := range unikernels {
		if unikernel.UnikernelName == unikernelName {
			err = deleteUnikernel(unikernel.AMI, force)
			if err != nil {
				return lxerrors.New("could not delete unikernel "+unikernel.AMI, err)
			}
			return nil
		}
	}
	return lxerrors.New("could not find unikernel for app "+unikernelName, nil)
}