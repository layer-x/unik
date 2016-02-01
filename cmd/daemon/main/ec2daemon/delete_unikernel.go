package ec2daemon
import (
	"github.com/layer-x/unik/cmd/daemon/main/ec2_metada_client"
	"github.com/layer-x/layerx-commons/lxerrors"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/aws/aws-sdk-go/aws"
)

const UNIKERNEL_APP_NAME = "UNIKERNEL_APP_NAME"
const UNIKERNEL_ID = "UNIKERNEL_ID"

func deleteUnikernel(unikernelId string, force bool) error {
	allUnikInstances, err := listUnikInstances()
	if err != nil {
		return lxerrors.New("could not check to see running unik instances", err)
	}
	for _, instance := range allUnikInstances {
		if instance.UnikernelId == unikernelId {
			if force == true {
				err = deleteUnikInstance(instance.ID)
				if err != nil {
					return lxerrors.New("could not delete unik instance "+instance.ID, err)
				}
			} else {
				return lxerrors.New("attempted to delete unikernel "+ unikernelId +", however instance "+instance.ID+" is still running. override with force=true", nil)
			}
		}
	}
	ec2Client, err := ec2_metada_client.NewEC2Client()
	if err != nil {
		return lxerrors.New("could not start ec2 client session", err)
	}
	deregisterImageInput := &ec2.DeregisterImageInput{
		ImageId: aws.String(unikernelId),
	}
	_, err = ec2Client.DeregisterImage(deregisterImageInput)
	if err != nil {
		return lxerrors.New("could not deregister ami for unikernel "+ unikernelId, err)
	}
	err = deleteSnapshotAndVolume(unikernelId)
	if err != nil {
		return lxerrors.New("could not delete snapshot or volume for unikernel "+ unikernelId, err)
	}
	return nil
}

func deleteSnapshotAndVolume(unikernelId string) error {
	ec2Client, err := ec2_metada_client.NewEC2Client()
	if err != nil {
		return lxerrors.New("could not start ec2 client session", err)
	}
	describeSnapshotsOutput, err := ec2Client.DescribeSnapshots(&ec2.DescribeSnapshotsInput{})
	if err != nil {
		return lxerrors.New("could not get snapshot list from ec2", err)
	}
	for _, snapshot := range describeSnapshotsOutput.Snapshots {
		for _, tag := range snapshot.Tags {
			if *tag.Key == UNIKERNEL_ID && *tag.Value == unikernelId {
				snapshotId := *snapshot.SnapshotId
				volumeId := *snapshot.VolumeId
				deleteSnapshotInput := &ec2.DeleteSnapshotInput{
					SnapshotId: aws.String(snapshotId),
				}
				_, err = ec2Client.DeleteSnapshot(deleteSnapshotInput)
				if err != nil {
					return lxerrors.New("could not delete snapshot for unikernel "+ unikernelId, err)
				}
				deleteVolumeInput := &ec2.DeleteVolumeInput{
					VolumeId: aws.String(volumeId),
				}
				_, err = ec2Client.DeleteVolume(deleteVolumeInput)
				if err != nil {
					return lxerrors.New("could not delete volume for unikernel snapshot "+snapshotId, err)
				}
				return nil
			}
		}
	}
	return lxerrors.New("snapshot not found for unikernel "+ unikernelId, err)
}