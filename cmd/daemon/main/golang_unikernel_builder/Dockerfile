FROM ubuntu

RUN sudo apt-get update -y && \
    sudo apt-get install libxen-dev curl git build-essential wget software-properties-common -y && \
    sudo apt-add-repository ppa:awstools-dev/awstools -y && \
    sudo apt-get update -y && \
    sudo apt-get install ec2-api-tools -y && \
    sudo apt-get clean -y && \
    sudo rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*


ENV GOROOT=/usr/local/go
ENV PATH=$PATH:$GOROOT/bin
RUN curl https://storage.googleapis.com/golang/go1.5.2.linux-amd64.tar.gz | sudo tar xz -C /usr/local && sudo mv /usr/local/go /usr/local/go1.5 && sudo ln -s /usr/local/go1.5 /usr/local/go &&\
    cd /tmp && git clone https://github.com/deferpanic/gorump && cd gorump/go/src && GOROOT_BOOTSTRAP=/usr/local/go GOOS=rumprun GOARCH=amd64 ./make.bash && cd /tmp && sudo mv gorump/go /usr/local/go1.5-patched && sudo rm /usr/local/go && sudo rm -rf /usr/local/go1.5  && sudo ln -s /usr/local/go1.5-patched /usr/local/go && rm -rf gorump

RUN DESTDIR=/usr/local && \
    BUILDRUMP_EXTRA= && \
    PLATFORMS="xen"  && \
    cd /tmp && \
    git clone https://github.com/rumpkernel/rumprun && \
    cd rumprun && \
    git submodule update --init && \
    for PLATFORM in $PLATFORMS; do \
    ./build-rr.sh -d $DESTDIR -o ./obj $PLATFORM build -- $BUILDRUMP_EXTRA && \
    ./build-rr.sh -d $DESTDIR -o ./obj $PLATFORM install ;\
    done && \
    rm -rf /tmp/rumprun

RUN mkdir /tmp/build/

COPY mainstub.c gomaincaller.go create_ami.sh /tmp/build/

VOLUME /opt/code
WORKDIR /opt/code

CMD sudo cp /tmp/build/* . && \
    sudo -E CC=x86_64-rumprun-netbsd-gcc CGO_ENABLED=1 GOOS=rumprun /usr/local/go/bin/go build -buildmode=c-archive -v -a -x $(find . -name "*.go") && \
    sudo -E RUMPRUN_STUBLINK=succeed x86_64-rumprun-netbsd-gcc -g -o program mainstub.c $(find . -name "*.a") && sudo -E rm -f /opt/code/mainstub.c /opt/code/gomaincaller.go && \
    sudo -E rumprun-bake xen_pv program_xen.bin program && \
    sudo -E rumprun ec2 -I 'qnet0,xenif' -W 'qnet0,inet,dhcp' program_xen.bin && \
    sudo -E bash create_ami.sh rumprun-program.bin.ec2dir
