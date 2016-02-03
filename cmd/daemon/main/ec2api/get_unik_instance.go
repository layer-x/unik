package ec2api
import (
	"github.com/layer-x/unik/cmd/types"
	"github.com/layer-x/layerx-commons/lxerrors"
"strings"
)

func GetUnikInstanceByPrefix(unikInstanceIdPrefix string) (*types.UnikInstance, error) {
	unikInstances, err := ListUnikInstances()
	if err != nil {
		return nil, lxerrors.New("failed to retrieve known instances", err)
	}
	for _, unikInstance := range unikInstances {
		if strings.HasPrefix(unikInstance.UnikInstanceID, unikInstanceIdPrefix) {
			return unikInstance, nil
		}
	}
	return nil, lxerrors.New("unik instance with prefix "+unikInstanceIdPrefix+" not found", nil)
}