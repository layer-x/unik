# info
## links
- [notes on pi and rump](https://github.com/rumpkernel/wiki/wiki/Info%3A-Notes-on-hardware-platforms#raspberry-pi)
- [rump cross compile notes](https://github.com/rumpkernel/wiki/wiki/Howto:-Cross-compiling)
- [dave's repo with initial WIP implementation](https://github.com/dave-tucker/rumprun/tree/raspberrypi)
- [official RPi cross compilers](https://github.com/raspberrypi/tools)
- Links from mailing list:
 - https://www.freelists.org/post/rumpkernel-users/rumpkernel-on-RPi,10
 - https://www.freelists.org/post/rumpkernel-users/Rumprun-for-Raspberry-Pi,1
- can't emulate kernel: https://bugs.launchpad.net/qemu/+bug/1231093
-  jtag https://github.com/synthetos/PiOCD/wiki/Using-a-Raspberry-Pi-as-a-JTAG-Dongle
- http://sysprogs.com/VisualKernel/tutorials/raspberry/jtagsetup/
- https://wiki.netbsd.org/ports/evbarm/raspberry_pi/#index4h1
- https://github.com/dwelch67/raspberrypi/tree/master/armjtag
- https://sourceforge.net/p/openocd/mailman/message/32598366/ (rtck)
- http://openocd.org/doc/html/FAQ.html
- https://www.raspberrypi.org/documentation/hardware/raspberrypi/schematics/Raspberry-Pi-B-Plus-V1.2-Schematics.pdf

boot process:
- https://www.raspberrypi.org/forums/viewtopic.php?f=63&t=6685
- https://www.raspberrypi.org/documentation/hardware/raspberrypi/bcm2835/BCM2835-ARM-Peripherals.pdf

Arm
- http://www.peter-cockerell.net/aalp/html/frames.html

Linker
- https://access.redhat.com/documentation/en-US/Red_Hat_Enterprise_Linux/4/html/Using_ld_the_GNU_Linker/simple-example.html

# build env

Using raspberry pi tools cross compilers
clone outside container so you won't clone it again when containers die.. it's a lot of MB

    git clone https://github.com/raspberrypi/tools raspberrypi-tools

DONT forget to [move ssp headers](#fixing ssp headers). see here.

Start container:

(mount tools as read only as from some reason it gets deleted during rump run build. no idea why.)

    docker run --rm -t -i -v $PWD/shared-dir/:/opt/code -v $PWD/raspberrypi-tools/:/opt/raspberrypi-tools:ro --entrypoint=/bin/bash rumpcompiler-go-hw

In docker shell:

    cd /opt/rumprun/
    rm -rf ./obj
    git remote add  origin2 https://github.com/dave-tucker/rumprun.git
    git fetch origin2
    git checkout raspberrypi
    git submodule update
    export PATH=/opt/raspberrypi-tools/arm-bcm2708/gcc-linaro-arm-linux-gnueabihf-raspbian-x64/bin/:$PATH
    export CC=arm-linux-gnueabihf-gcc
    ./build-rr.sh -d $DESTDIR -b pi -o ./obj $PLATFORM  build -- -F ACFLAGS=-march=armv6k
    ./build-rr.sh -d $DESTDIR -o ./obj $PLATFORM install

# Test

After fixing all problems (see below) used the rumpcompiler-pi-hw container to build a hello world kernel:

hello.c file:

    #include<stdio.h>

    main()
    {
    printf("Hello World");

    }

build:

    /usr/local/bin/arm-rumprun-netbsdelf-eabihf-gcc hello.c -o program
    rumprun-bake hw_pi program.bin program

copied result program.bin to /boot and edited /boot/config.txt and added

    kernel=program.bin

Also tried via:

    qemu-system-arm -kernel program.bin -cpu arm1176 -m 256  -M versatilepb

and got a black screen. this is apparently expected, see https://bugs.launchpad.net/qemu/+bug/1231093

# obj copy

apparentally pi needs bin kernel and not elf:

    arm-rumprun-netbsdelf-eabihf-objcopy -S -O binary program.bin program.bin2

Testing with RPi connected via serial

    screen /dev/cu.PL2303-00001014 115200


(need the PL2303 drivers and a proper usb to serial cable.)

# kernel start address.
rump starts at address 0x100000 and not 0x8000 (expected by pi), need to fix rump linker script.

edit rumprun/platform/hw/arch/arm/rpi/kern.ldscript (or after build /usr/local/rumprun-arm/lib/hw.ldscript) and cahnge:

    . = 1m;

to

    . = 0x8000;

after baking, verify with:

    readelf --file-header --sections program.bin

# kernel crashes on boot
Global and function static variables are not initialized (not zeroed).
need to figure out why, might be cause:
## rodata start address is still 100000

from readelf:
    [Nr] Name              Type            Addr     Off    Size   ES Flg Lk Inf Al
    ...
    [ 2] .rodata           PROGBITS        001a6dc0 1a6dc0 033f00 00   A  0   0  8
    ...

# jtag setup

patch pi kernel to turn on alternate use for pins.

Use jtag connect , and connect the following:

| JTag  | Jtag Pi Pin | Wire Color | Target Pi |
|-------|-------------|------------|-----------|
| TCK   | 11          | Green      | 25        |
| TMS   | 25          | Blue       | 27        |
| TDI   | 10          | Purple     | 4         |
| TDO   | 9           | Grey       | 24        |
| RESET | 7           | White      | 22        |
| GND   | GND         | Black      | GND       |
| RTCK  | -           | -          | 23        |

debug:

    openocd  -s /usr/share/openocd/ -f  tcl/interface/sysfsgpio-raspberrypi.cfg -f tcl/target/raspberry.cfg

and then connect with gdb:

/opt/gdb-7.11/gdb/gdb /path/to/code -ex "target extended-remote 192.168.1.106:3333"

## error amd64
complication fails with:
/opt/rumprun/src-netbsd/sys/rump/librump/rumpkern/../../../arch/amd64/amd64/kobj_machdep.c:70:29: fatal error: machine/cpufunc.h: No such file or directory
 #include <machine/cpufunc.h>

commands to quickly build :

    /opt/rumprun/buildrump.sh/buildrump.sh -j4 -k -s /opt/rumprun/src-netbsd -T ./obj/rumptools -o ./obj/buildrump.sh -d ./obj/dest.stage -F ACFLAGS=-march=armv6k -j 1 build kernelheaders install

    /opt/rumprun/obj/rumptools/bin/brrumpmake -j 1 -f /opt/rumprun/obj/buildrump.sh/Makefile.all obj


    /opt/rumprun/buildrump.sh/buildrump.sh -j4 -k -s /opt/rumprun/src-netbsd -T ./obj/rumptools -o ./obj/buildrump.sh -d ./obj/dest.stage -F ACFLAGS=-march=armv6k -j 1 build


changing src-netbsd/sys/rump/librump/rumpkern/arch/x86_64/Makefile.inc to the arm version didnt help, the arch probs comes from somewhere else.

Solution: cleaning build env solves this.

## fixing ssp headers

as described in https://www.freelists.org/post/rumpkernel-users/rumpkernel-on-RPi,10

after cloning rpi tools, do this:

    cd raspberrypi-tools
    mv arm-bcm2708/gcc-linaro-arm-linux-gnueabihf-raspbian-x64/lib/gcc/arm-linux-gnueabihf/4.8.3/include arm-bcm2708/gcc-linaro-arm-linux-gnueabihf-raspbian-x64/lib/gcc/arm-linux-gnueabihf/4.8.3/not-include

## don't know how to make aeabi_idiv0.c.

command:

    cd /opt/rumprun/lib/libcompiler_rt && /opt/rumprun/./obj/rumptools/rumpmake MAKEOBJDIR=/opt/rumprun/./obj/lib/libcompiler_rt RUMPSRC=/opt/rumprun/src-netbsd obj && /opt/rumprun/./obj/rumptools/rumpmake MAKEOBJDIR=/opt/rumprun/./obj/lib/libcompiler_rt RUMPSRC=/opt/rumprun/src-netbsd includes && /opt/rumprun/./obj/rumptools/rumpmake BMKHEADERS=/opt/rumprun/./obj/include MAKEOBJDIR=/opt/rumprun/./obj/lib/libcompiler_rt RUMPSRC=/opt/rumprun/src-netbsd dependall


error seems to be a not found error as files are not in path (find out by adding -d A to make command) (nbmake= bsd make program)
the makefile for the compiler-rt doesn't include the libc path.

tried to do this:

(slash below is just for markdown syntax hight not to go crazy, and it doesn't effect bash so we're good)

    cp /opt/rumprun/src-netbsd/common/lib/libc/arch/arm/gen/\__aeabi*.c /opt/rumprun/src-netbsd/sys/external/bsd/compiler_rt/dist/lib/builtins/arm/


this cause mkdep to fail, as it couldnt find headers.


    /opt/rumprun/src-netbsd/sys/external/bsd/compiler_rt/dist/lib/builtins/arm/\__aeabi_idiv0.c:35:23: fatal error: sys/systm.h: No such file or directory
     #include <sys/systm.h>

To work around that, comment out the include and the call to panic.
edit:
- /opt/rumprun/src-netbsd/sys/external/bsd/compiler_rt/dist/lib/builtins/arm/\__aeabi_idiv0.c
- /opt/rumprun/src-netbsd/sys/external/bsd/compiler_rt/dist/lib/builtins/arm/\__aeabi_ldiv0.c

This will get rump compiling.


# to bake

Create a new target for baking without these drivers:

-lrumpdev_virtio_if_vioif
-lrumpdev_virtio_ld
-lrumpdev_virtio_viornd
-lrumpdev_pci_virtio
-lrumpdev_pci
-lrumpdev_audio_ac97
-lrumpdev_pci_auich
-lrumpdev_pci_eap
-lrumpdev_pci_hdaudio
-lrumpdev_hdaudio_hdafg
-lrumpdev_pci_if_wm
-lrumpdev_miiphy
-lrumpdev_pci_usbhc

see rumprun-bake.conf in rumpcompiler-pi-hw container.

# still not working
need to merge some of the netbsd pi code to rumprun.
