package ec2api

import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/pkg/types"
	"strings"
"github.com/layer-x/layerx-commons/lxlog"
)

func GetVolumeByIdOrName(logger lxlog.Logger, volumeIdOrName string) (*types.Volume, error) {
	volumes, err := ListVolumes(logger)
	if err != nil {
		return nil, lxerrors.New("failed to retrieve known volumes", err)
	}
	for _, volume := range volumes {
		if strings.HasPrefix(volume.Name, volumeIdOrName) || strings.HasPrefix(volume.VolumeId, volumeIdOrName) {
			return volume, nil
		}
	}
	return nil, lxerrors.New("volume with prefix "+ volumeIdOrName +" not found", nil)
}
