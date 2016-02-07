package ec2_metada_client

import (
	"encoding/json"
	"github.com/aws/aws-sdk-go/aws"
	"github.com/aws/aws-sdk-go/aws/session"
	"github.com/aws/aws-sdk-go/service/ec2"
	"github.com/layer-x/layerx-commons/lxerrors"
	"os/exec"
	"strings"
)

func getKeys() (string, string, error) {
	curlCommand := exec.Command("curl", "http://169.254.169.254/latest/meta-data/iam/security-credentials/UNIK_BACKEND/")
	credentialsJson, err := curlCommand.Output()
	if err != nil {
		return "", "", lxerrors.New("could not run \"curl http://169.254.169.254/latest/meta-data/iam/security-credentials/UNIK_BACKEND/\"", err)
	}
	var credentials SecurityCredentials
	err = json.Unmarshal(credentialsJson, &credentials)
	if err != nil {
		return "", "", lxerrors.New("unmarshalling json to ec2 security credentials", err)
	}
	return credentials.Accesskeyid, credentials.Secretaccesskey, nil
}

func getRegion() (string, error) {
	curlCommand := exec.Command("curl", "http://169.254.169.254/latest/meta-data/placement/availability-zone")
	azBytes, err := curlCommand.Output()
	if err != nil {
		return "", lxerrors.New("could not run \"curl http://169.254.169.254/latest/meta-data/placement/availability-zone\"", err)
	}
	region := string(azBytes)
	for _, r := range "abcde" {
		region = strings.TrimSuffix(region, string(r))
	}
	return region, nil
}

func NewEC2Client() (*ec2.EC2, error) {
	region, err := getRegion()
	if err != nil {
		return nil, lxerrors.New("getting region from ec2 metadata server", err)
	}
	return ec2.New(session.New(), &aws.Config{
		Region: aws.String(region),
	}), nil
}
