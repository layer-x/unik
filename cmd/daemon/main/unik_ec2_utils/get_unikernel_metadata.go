package unik_ec2_utils
import (
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/unik/cmd/types"
	"github.com/layer-x/docker/vendor/src/github.com/docker/go/canonical/json"
)

const UNIK_METADATA = "UNIK_METADATA"

func GetUnikernelMetadata(image *ec2.Instance) *types.Unikernel {
	for _, tag := range image.Tags {
		if *tag.Key == UNIK_METADATA {
			unikernelJson := *tag.Value
			var unikernel types.UnikInstance
			err := json.Unmarshal(unikernelJson, &unikernel)
			if err == nil {
				return &unikernel
			}
		}
	}
	return nil
}