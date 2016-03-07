package ec2api

import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/pkg/types"
	"strings"
)

func GetUnikInstanceByPrefixOrName(unikInstanceIdPrefixOrName string) (*types.UnikInstance, error) {
	unikInstances, err := ListUnikInstances()
	if err != nil {
		return nil, lxerrors.New("failed to retrieve known instances", err)
	}
	for _, unikInstance := range unikInstances {
		if strings.HasPrefix(unikInstance.UnikInstanceID, unikInstanceIdPrefixOrName) || strings.HasPrefix(unikInstance.UnikInstanceName, unikInstanceIdPrefixOrName) {
			return unikInstance, nil
		}
	}
	return nil, lxerrors.New("unik instance with prefix "+ unikInstanceIdPrefixOrName +" not found", nil)
}
