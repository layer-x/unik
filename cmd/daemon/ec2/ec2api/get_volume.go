package ec2api

import (
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/layer-x/unik/types"
	"strings"
)

func GetVolumeByIdOrName(volumeIdOrName string) (*types.Volume, error) {
	volumes, err := ListVolumes()
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
