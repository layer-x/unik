# Links
- [PCI device driver guide](http://nairobi-embedded.org/linux_pci_device_driver.html)
- Patch for GDB [here](https://sourceware.org/bugzilla/attachment.cgi?id=8512&action=diff); [answer](http://stackoverflow.com/questions/8662468/remote-g-packet-reply-is-too-long) that led to it
- [netbsd kgdb](http://www.netbsd.org/docs/kernel/kgdb.html)
- [netbsd debug qemu](https://wiki.netbsd.org/kernel_debugging_with_qemu/)
- [netbsd on qemu](https://copyninja.info/blog/netbsd-on-qemu.html)
- [qemu if up](https://gist.github.com/EmbeddedAndroid/6572715#file-qemu-ifup) might be needed to bridge on mac. didn't work for me but whatever, also may need "sudo ifconfig tap0 10.0.1.12/24 up"
- GDB [cheatsheet](http://darkdust.net/files/GDB%20Cheat%20Sheet.pdf)
- Explanation on netbsd [drivers](http://cholla.mmto.org/computers/netbsd/driver.html)
- Similar [IDE Issue](https://github.com/rumpkernel/rumprun/issues/24#issuecomment-108382809)
# Modified go build container
change "go/src/runtime/string1.go" of modified rump-go

in findnull():
change for loop to this:

    for l < len(p) && p[l] != 0 {
            l++
    }
    if l == len(p) {
      return 0
    }

and a go builder using the modified-modified go version ([see issue here](https://github.com/deferpanic/gorump/issues/29)).
# Rump setup

get program.bin from /opt/code/program.bin from container;
to build a root.img fs:

    docker run --rm --privileged -v /dev:/dev -v /path/to/dir/where/program.bin/is/:/unikernel uvgroovy/rump-go-stager

How to run QEMU to reproduce issue:

    qemu-system-x86_64 -drive file=root.img,format=raw,if=virtio -device pci-bridge,chassis_nr=2  -device e1000,netdev=mynet0,mac=54:54:00:55:55:55,bus=pci.1,addr=1 -netdev user,id=mynet0,net=192.168.76.0/24,dhcpstart=192.168.76.9 -curses -s -S

Then to debug (on OSX):

    docker run --rm -ti --net="host" -v $PWD/prog/:/tmp/p rump-hw-debug
    /opt/gdb-7.11/gdb/gdb -ex 'target remote 192.168.99.1:1234' /opt/prog/program.bin

[Reference](https://github.com/rumpkernel/wiki/wiki/Howto%3A-Debugging-Rumprun-with-gdb)

## Convert image to vmdk:
from [here](http://stackoverflow.com/questions/454899/how-to-convert-flat-raw-disk-image-to-vmdk-for-virtualbox-or-vmplayer)

    qemu-img convert -O vmdk imagefile.dd vmdkname.vmdk

# NetBSD setup
to debug regular netbsd, need to figure out how to setup kernel debugging with netbsd and qemu network.

# Add ppb support to rump:

Start with a rump go builder, and add:
To src-netbsd/sys/rump/dev/lib/libpci/PCI.ioconf, append:

    pci*    at ppb? bus ?
    ppb*    at pci? dev ? function ?

To src-netbsd/sys/rump/dev/lib/libpci/Makefile add **"ppb.c"** to the list of source files. example:

    SRCS+=  ppb.c pci.c pci_map.c pci_quirks.c pci_subr.c pci_stub.c pci_usrreq.c

rebuild.

# WORK IN PROGRESS - Add scsi controller support to rump:
create src-netbsd/sys/rump/dev/lib/libpci_scsi and two files:

## Makefile:

    #	$NetBSD: Makefile,v 1.5 2015/11/16 23:27:08 pooka Exp $
    #

    RUMPTOP=${TOPRUMP}

    .PATH:	${RUMPTOP}/../dev/pci ${RUMPTOP}/../dev/ ${RUMPTOP}/../dev/ic

    LIB=	rumpdev_pci_scsi
    COMMENT=PCI SCSI controller drivers

    IOCONF=	PCI_SCSI.ioconf
    RUMP_COMPONENT=ioconf

    SRCS+= bio.c mpt_pci.c mpt_netbsd.c mpt.c

    CPPFLAGS+= -I${RUMPTOP}/librump/rumpkern -I${RUMPTOP}/../dev

    .include "${RUMPTOP}/Makefile.rump"
    .include <bsd.lib.mk>
    .include <bsd.klinks.mk>

## PCI_SCSI.ioconf
    #	$NetBSD: PCI_USBHC.ioconf,v 1.1 2015/05/20 12:21:38 pooka Exp $
    #

    ioconf pci_scsi

    include "conf/files"
    include "dev/pci/files.pci"


    pseudo-device   bio
    options         RAID_AUTOCONFIG

    pseudo-root pci*

    mpt*    at pci? dev ? function ?        # LSILogic 9x9 and 53c1030 (Fusion-MPT)

## Edit Bake
edit app-tools/rumprun-bake.conf change hw_generic to:

    conf hw_generic
            create          "generic targets, includes (almost) all drivers"
            assimilate      _miconf                 \
                            _virtio                 \
                            _virtio_scsi            \
                            _audio                  \
                            _pciether               \
                            _usb
            add     -lrumpdev_pci_scsi
    fnoc

## add component
edit:  src-netbsd/sys/rump/dev/Makefile.rumpdevcomp
also add "RUMPPCIDEVS+=   pci_scsi" to


# Step-by-step
1. build go builder (should have patch in it)
2. build patched
