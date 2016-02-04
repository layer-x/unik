package ec2_metada_client

import "time"

type SecurityCredentials struct {
	Code string `json:"Code"`
	Lastupdated time.Time `json:"LastUpdated"`
	Type string `json:"Type"`
	Accesskeyid string `json:"AccessKeyId"`
	Secretaccesskey string `json:"SecretAccessKey"`
	Token string `json:"Token"`
	Expiration time.Time `json:"Expiration"`
}