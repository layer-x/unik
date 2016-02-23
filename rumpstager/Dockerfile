FROM ubuntu

RUN DEBIAN_FRONTEND=noninteractive apt-get update -y && \
    apt-get install -y --force-yes parted grub kpartx curl && \
    apt-get clean -y && rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

VOLUME /unikernel

ENV GOPATH=/opt/go
RUN curl https://storage.googleapis.com/golang/go1.6.linux-amd64.tar.gz | tar xz -C /usr/local && \
    mkdir -p $GOPATH

ENV PATH=/usr/local/go/bin:$PATH

# stager takes a xen binary and volums, and creates a AWS ami
RUN mkdir -p $GOPATH/src/github.com/layer-x/unik/rumpstager/
COPY .  $GOPATH/src/github.com/layer-x/unik/rumpstager/
WORKDIR $GOPATH/src/github.com/layer-x/unik/rumpstager/

RUN ["go", "build"]

# RUN LIKE THIS: docker run --rm  -v /dev:/dev --privileged  -v /path/to/unikernel/partitions:/unikernel uvgroovy/uvgroovy/rump-go-stager
# build and run: sudo docker build -t uvgroovy/aws-rump-stager ./stager/ &&  sudo docker run --rm  -v /dev:/dev --privileged  -v $PWD/testapp/rumprun-program_xen.bin.ec2dir:/unikernel/root  -ti uvgroovy/aws-rump-stager
ENTRYPOINT ["/opt/go/src/github.com/layer-x/unik/rumpstager/rumpstager"]
