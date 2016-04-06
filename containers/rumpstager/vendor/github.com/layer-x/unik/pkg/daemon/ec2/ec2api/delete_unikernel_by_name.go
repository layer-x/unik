package ec2api

import (
"github.com/layer-x/layerx-commons/lxerrors"
"github.com/layer-x/layerx-commons/lxlog"
)

func DeleteUnikernelByName(logger *lxlog.LxLogger, unikernelName string, force bool) error {
	unikernels, err := ListUnikernels(logger)
	if err != nil {
		return lxerrors.New("could not get unikernel list", err)
	}
	for _, unikernel := range unikernels {
		if unikernel.UnikernelName == unikernelName {
			err = DeleteUnikernel(logger, unikernel.Id, force)
			if err != nil {
				return lxerrors.New("could not delete unikernel "+unikernel.Id, err)
			}
			return nil
		}
	}
	return lxerrors.New("could not find unikernel "+unikernelName, nil)
}
