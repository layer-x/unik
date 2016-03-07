FROM ubuntu

COPY ./java $HOME/java

ENV GOPATH=$HOME/go
ENV GOBIN=$GOPATH/bin
ENV PATH=$GOBIN:/usr/local/go/bin:$PATH

RUN apt-get update
RUN apt-get install -y maven curl git openjdk-7-jdk
RUN curl https://storage.googleapis.com/golang/go1.5.2.linux-amd64.tar.gz | sudo tar xz -C /usr/local && sudo mv /usr/local/go /usr/local/go1.5 && sudo ln -s /usr/local/go1.5 /usr/local/go
RUN mkdir -p $GOPATH/src/github.com/vmware
RUN cd $GOPATH/src/github.com/vmware && \
    git clone https://github.com/layer-x/govmomi && \
    cd govmomi/govc && \
    go get ./... && \
    go install
RUN cd $HOME/java/vsphere-client && \
    mvn package && \
    cp target/vsphere-client-1.0-SNAPSHOT-jar-with-dependencies.jar /vsphere-client.jar

#run this container with govc command or java -jar
#e.g. `docker run vsphere-client govc datastore.rm -u "url" -k FOLDERNAME
# or
#`docker run vsphere-client java -jar /vsphere-client.jar https://vsphereip/sdk root pass vmname '[datastore1] unikernel-name/program.vmdk' 200

VOLUME /unikernel
