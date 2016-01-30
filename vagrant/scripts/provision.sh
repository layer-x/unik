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
export PATH=$PATH:/usr/local/go/bin
export GOPATH=/home/vagrant/go
mkdir -p $GOPATH/src/github.com/layer-x
pushd $GOPATH/src/github.com/layer-x/unik
go get golang.org/x/net/context
go get github.com/coreos/etcd/client
go get github.com/gogo/protobuf/proto
gp get github.com/aws/aws-sdk-go
go get ./...
pushd $GOPATH/src/github.com/layer-x/unik/cmd/daemon/main/
go build -o unik_daemon .
echo "STARTING UNIK!"
echo "(sudo -E unik_daemon -u $USERNAME -p $PASSWORD &) > /home/vagrant/unik.log 2>&1"
(sudo -E ./unik_daemon -u $USERNAME -p $PASSWORD &) > /home/vagrant/unik.log 2>&1
echo "STARTED UNIK!!"
