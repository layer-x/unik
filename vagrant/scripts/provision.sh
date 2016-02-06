#!/usr/bin/env bash
set -x
USERNAME=$1
PASSWORD=$2
sudo apt-key adv --keyserver hkp://p80.pool.sks-keyservers.net:80 --recv-keys 58118E89F3A912897C070ADBF76221572C52609D
echo "deb https://apt.dockerproject.org/repo ubuntu-trusty main" | sudo tee /etc/apt/sources.list.d/docker.list
sudo apt-get update
sudo apt-get install docker-engine -y
sudo service docker start
sudo apt-get install -y git wget curl build-essential
wget https://storage.googleapis.com/golang/go1.5.1.linux-amd64.tar.gz
sudo tar -C /usr/local -xzf go1.5.1.linux-amd64.tar.gz
rm go1.5.1.linux-amd64.tar.gz
echo "export PATH=$PATH:/usr/local/go/bin" | tee --append /home/vagrant/.bashrc
echo "export GOPATH=/home/vagrant/go" | tee --append /home/vagrant/.bashrc
export GOPATH=/home/vagrant/go
export GOBIN=$GOPATH/bin
export PATH=$GOBIN:/usr/local/go/bin:$PATH
go get github.com/tools/godep
pushd $GOPATH/src/github.com/layer-x/unik/cmd/daemon/
godep go build -o unikd .
echo "STARTING UNIK!"
echo "(sudo -E unikd -u $USERNAME -p $PASSWORD &) > /home/vagrant/unik.log 2>&1"
(sudo -E ./unikd &) > /home/vagrant/unik.log 2>&1
echo "STARTED UNIK!!"
public_ip_address=`curl -s http://169.254.169.254/latest/meta-data/public-ipv4`
echo "The public IP for this instance is $public_ip_address"