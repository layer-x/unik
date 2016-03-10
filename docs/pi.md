# info
## links
- [notes on pi and rump](https://github.com/rumpkernel/wiki/wiki/Info%3A-Notes-on-hardware-platforms#raspberry-pi)
- [rump cross compile notes](https://github.com/rumpkernel/wiki/wiki/Howto:-Cross-compiling)
- [dave's repo with initial WIP implementation](https://github.com/dave-tucker/rumprun/tree/raspberrypi)
- [official RPi cross compilers](https://github.com/raspberrypi/tools)
- Links from mailing list:
 - https://www.freelists.org/post/rumpkernel-users/rumpkernel-on-RPi,10

# build env

Using raspberry pi tools cross compilers
clone outside container so you won't clone it again when containers die.. it's a lot of MB

    git clone https://github.com/raspberrypi/tools raspberrypi-tools

Start container:

    docker run --rm -t -i -v $PWD/shared-dir/:/opt/code -v $PWD/raspberrypi-tools/:/opt/raspberrypi-tools --entrypoint=/bin/bash rumpcompiler-go-hw

In docker shell:

    cd /opt/rumprun/
    git remote add  origin2 https://github.com/dave-tucker/rumprun.git
    git fetch origin2
    git checkout raspberrypi
    git submodule update
    export CC=/opt/raspberrypi-tools/arm-bcm2708/gcc-linaro-arm-linux-gnueabihf-raspbian-x64/bin/arm-linux-gnueabihf-gcc
    ./build-rr.sh -d $DESTDIR -b pi -o ./obj $PLATFORM  build -- -F ACFLAGS=-march=armv6k
    export PATH=/opt/raspberrypi-tools/arm-bcm2708/gcc-linaro-arm-linux-gnueabihf-raspbian-x64/bin/:$PATH
    ./build-rr.sh -d $DESTDIR -b pi -o ./obj $PLATFORM  build -- -F ACFLAGS=-march=armv6k


watch it fail :(  
