FROM ubuntu

ENV GOPATH=$HOME/go
ENV GOBIN=$GOPATH/bin
ENV PATH=$GOBIN:/usr/local/go/bin:$PATH
RUN apt-get update -y
RUN apt-get install -y qemu maven wget git openjdk-7-jdk curl
RUN curl https://storage.googleapis.com/golang/go1.5.2.linux-amd64.tar.gz | sudo tar xz -C /usr/local && sudo mv /usr/local/go /usr/local/go1.5 && sudo ln -s /usr/local/go1.5 /usr/local/go
RUN mkdir -p $GOPATH/src/github.com/cloudius-systems
RUN cd $GOPATH/src/github.com/cloudius-systems && git clone https://github.com/layer-x/capstan
RUN cd $GOPATH/src/github.com/cloudius-systems/capstan && ./install

#Run this container with
#   docker run --rm --privileged \
#    -v path-to-src:/unikernel \
#    -v path-to-jar-wrapper:/jar-wrapper \ (java-wrapper must also contain the GENERATED capstanfile)
#       -e GROUP_ID=<groupid> \
#       -e ARTIFACT_ID=<artifactid> \
#       -e VERSION=<version> \
#   osvcompiler

VOLUME /jar-wrapper
VOLUME /unikernel

CMD cd /unikernel && \
    mvn package && \
    mvn install:install-file -Dfile=target/$ARTIFACT_ID-$VERSION-jar-with-dependencies.jar -DgroupId=$GROUP_ID \
        -DartifactId=$ARTIFACT_ID -Dversion=$VERSION -Dpackaging=jar && \
    cd /jar-wrapper && \
    mvn package && \
    capstan build -p qemu program && \
    qemu-img create -f qcow2 -o backing_file=$HOME/.capstan/repository/program/program.qemu ./program.qcow2 && \
    qemu-img convert -O vmdk ./program.qcow2 ./program.vmdk && \
    qemu-img convert -O raw ./program.qcow2 ./program.raw && \
    rm program.qcow2
