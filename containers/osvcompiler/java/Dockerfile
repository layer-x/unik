FROM ubuntu

ENV GOPATH=$HOME/go
ENV GOBIN=$GOPATH/bin
ENV PATH=$GOBIN:/usr/local/go/bin:$PATH
RUN apt-get install -y qemu maven wget git openjdk-7-jdk && \
    curl https://storage.googleapis.com/golang/go1.5.2.linux-amd64.tar.gz | sudo tar xz -C /usr/local && sudo mv /usr/local/go /usr/local/go1.5 && sudo ln -s /usr/local/go1.5 /usr/local/go && \
    mkdir -p $GOPATH/src/github.com/cloudius-systems && \
    cd $GOPATH/src/github.com/cloudius-systems && \
    git clone https://github.com/layer-x/capstan && \
    cd capstan && \
    ./install

#Run this container with -v path-to-root-src:/unikernel -e UNIKERNEL_NAME=<unikernel_name> --privileged (path must also contain the GENERATED capstanfile)

VOLUME /unikernel
WORKDIR /unikernel

CMD capstan build -p qemu program && \
    qemu-img create -f qcow2 -o backing_file=$HOME/.capstan/repository/program/program.qemu ./program.qcow2 && \
    qemu-img convert -O vmdk ./program.qcow2 ./program.vmdk && \
    qemu-img convert -O raw ./program.qcow2 ./program.raw && \
    rm program.qcow2
