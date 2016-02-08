FROM ubuntu

RUN apt-get install -y software-properties-common && \
    add-apt-repository -y ppa:webupd8team/java && \
    apt-add-repository ppa:awstools-dev/awstools && \
    apt-get update && \
    echo oracle-java8-installer shared/accepted-oracle-license-v1-1 select true | /usr/bin/debconf-set-selections && \
    apt-get install -y oracle-java8-installer && apt-get install -y oracle-java8-set-default && \
    apt-add-repository ppa:awstools-dev/awstools && \
    apt-get install -y ec2-api-tools python unzip curl && \
    curl "https://s3.amazonaws.com/aws-cli/awscli-bundle.zip" -o "awscli-bundle.zip" && unzip awscli-bundle.zip && sudo ./awscli-bundle/install -i /usr/local/aws -b /usr/local/bin/aws && \
    rm -rf awscli-bundle  awscli-bundle.zip && \
    sudo apt-get clean -y && sudo rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

RUN apt-get update
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y --force-yes libtool autoconf automake autopoint bc gperf texinfo uuid-dev check

RUN DEBIAN_FRONTEND=noninteractive apt-get update  && apt-get install -y --force-yes git make gcc g++ libdevmapper-dev

RUN DEBIAN_FRONTEND=noninteractive apt-get install -y --force-yes build-essential fuse libfuse-dev autoconf automake libtool git libbsd-dev e2fslibs-dev
RUN DEBIAN_FRONTEND=noninteractive apt-get install -y --force-yes fuse libfuse-dev
RUN wget http://launchpadlibrarian.net/87312045/ufsutils_8.2-3_amd64.deb && dpkg -i ufsutils_8.2-3_amd64.deb
RUN git clone https://github.com/DanielO/fuse-ufs2 && \
    cd fuse-ufs2 && \
    echo AM_PROG_AR >> ./configure.ac && \
    ./autogen.sh && \
    ./configure && \
    sed '/__st_ino/d' -i ./fuse-ufs/do_fillstatbuf.c && \
    sed '/__st_ino/d' -i ./fuse-ufs/op_readdir.c && \
    make && make install


    RUN DEBIAN_FRONTEND=noninteractive apt-get install -y --force-yes parted grub kpartx


# stager takes a xen binary and volums, and creates a AWS ami
COPY stage.sh    /stager/stage.sh

VOLUME /unikernel

# RUN LIKE THIS: docker run --rm  -v /dev:/dev --privileged  -v /path/to/unikernel/partitions:/unikernel uvgroovy/aws-rump-stager
# build and run: sudo docker build -t uvgroovy/aws-rump-stager ./stager/ &&  sudo docker run --rm  -v /dev:/dev --privileged  -v $PWD/testapp/rumprun-program_xen.bin.ec2dir:/unikernel/root  -ti uvgroovy/aws-rump-stager
ENTRYPOINT ["/bin/bash", "/stager/stage.sh"]
