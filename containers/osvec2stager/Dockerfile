FROM ubuntu

RUN sudo apt-get update -y && \
    sudo apt-get install build-essential software-properties-common -y && \
    sudo apt-add-repository ppa:awstools-dev/awstools -y && \
    sudo apt-get update -y && \
    sudo apt-get install ec2-api-tools -y && \
    sudo apt-get clean -y && \
    sudo rm -rf /var/lib/apt/lists/* /tmp/* /var/tmp/*

RUN mkdir /tmp/build/

COPY stage_ami.sh /tmp/build/
WORKDIR /tmp/build

#run this container with:
#docker run --rm --privileged -v /dev:/dev -e UNIKERNELFILE=PATH_TO_RAW_IMAGE -e UNIKERNEL_APP_NAME=Unikernel_Name osvec2stager

CMD bash stage_ami.sh
