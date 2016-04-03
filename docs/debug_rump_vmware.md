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

SCSI mpt
- http://netbsd.gw.com/cgi-bin/man-cgi?mpt+4+NetBSD-6.0

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

    docker run --rm -ti --net="host" -v $PWD/:/opt/prog rumpdebugger-gdb-hw
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
How to run QEMU to reproduce issue:

    qemu-system-x86_64 -drive file=root.img,format=raw,if=scsi -device pci-bridge,chassis_nr=2  -device e1000,netdev=mynet0,mac=54:54:00:55:55:55,bus=pci.1,addr=1 -netdev user,id=mynet0,net=192.168.76.0/24,dhcpstart=192.168.76.9 -curses -s -S

from vmware you need the mpt driver. to find that out you need to compile pcictl from source (the one in netbsd7 doesn't show the drivers) on netbsd. and then run
(assuming the newly compiled binary is in /usr/src/usr.sbin/pcictl/pcictl):

    /usr/src/usr.sbin/pcictl/pcictl /dev/pci0 list -N | grep SCSI

create src-netbsd/sys/rump/dev/lib/libpci_scsi and two files:

## Makefile:

    #    $NetBSD: Makefile,v 1.5 2015/11/16 23:27:08 pooka Exp $
    #

    RUMPTOP=${TOPRUMP}

    .PATH:    ${RUMPTOP}/../dev/pci ${RUMPTOP}/../dev/ ${RUMPTOP}/../dev/ic

    LIB=    rumpdev_pci_scsi
    COMMENT=PCI SCSI controller drivers

    IOCONF=    PCI_SCSI.ioconf
    RUMP_COMPONENT=ioconf

    SRCS+= mpt_pci.c  mpt_netbsd.c mpt.c mpt_debug.c

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
    include "dev/files.dev"


    pseudo-device   bio
    options         RAID_AUTOCONFIG

    pseudo-root pci*

    mpt*    at pci? dev ? function ?        # LSILogic 9x9 and 53c1030 (Fusion-MPT)
    scsibus* at mpt?


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


## failures:
when baking this yields:

/usr/local/rumprun-x86_64/lib/rumprun-hw/librumpdev_pci_scsi.a(mpt_pci.o): In function `mpt_pci_attach':
/opt/rumprun/src-netbsd/sys/rump/../dev/pci/mpt_pci.c:207: undefined reference to `rumpns_mpt_intr'
/opt/rumprun/src-netbsd/sys/rump/../dev/pci/mpt_pci.c:219: undefined reference to `rumpns_mpt_disable_ints'
/opt/rumprun/src-netbsd/sys/rump/../dev/pci/mpt_pci.c:222: undefined reference to `rumpns_mpt_dma_mem_alloc'
/opt/rumprun/src-netbsd/sys/rump/../dev/pci/mpt_pci.c:228: undefined reference to `rumpns_mpt_init'
/opt/rumprun/src-netbsd/sys/rump/../dev/pci/mpt_pci.c:234: undefined reference to `rumpns_mpt_scsipi_attach'

rumpns can be ignored as it is added by rump tool chain.

mpt_intr, mpt_dma_mem_alloc, mpt_scsipi_attach are in ./dev/ic/mpt_netbsd.c
mpt_init,mpt_disable_ints are in ./dev/ic/mpt.c

so I added "mpt_netbsd.c mpt.c" to the SRC line in the makefile

this gives this error:

/opt/rumprun/src-netbsd/sys/rump/../dev/ic/mpt_netbsd.c:82:17: fatal error: bio.h: No such file or directory
 #include "bio.h"

 bio.h seems to be autogenerated. since NBIO is not defined i tried just to created an empty file; assuming nbio functions won't be used anyway.. :

    touch /opt/rumprun/src-netbsd/sys/rump/../dev/ic/bio.h


then it failed on a bunch of other not defined functions:
    /opt/rumprun/src-netbsd/sys/rump/../dev/ic/mpt.c:382: undefined reference to `rumpns_mpt_print_db'
    /opt/rumprun/src-netbsd/sys/rump/../dev/ic/mpt.c:494: undefined reference to `rumpns_mpt_print_reply'
    /opt/rumprun/src-netbsd/sys/rump/../dev/ic/mpt.c:1143: undefined reference to `rumpns_mpt_ioc_diag'
    /opt/rumprun/src-netbsd/sys/rump/../dev/ic/mpt.c:174: undefined reference to `rumpns_mpt_print_db'
    /opt/rumprun/src-netbsd/sys/rump/../dev/ic/mpt_netbsd.c:504: undefined reference to `rumpns_mpt_print_reply'
    /opt/rumprun/src-netbsd/sys/rump/../dev/ic/mpt_netbsd.c:619: undefined reference to `rumpns_mpt_req_state'
    /opt/rumprun/src-netbsd/sys/rump/../dev/ic/mpt_netbsd.c:621: undefined reference to `rumpns_mpt_print_scsi_io_request'
    /opt/rumprun/src-netbsd/sys/rump/../dev/ic/mpt_netbsd.c:625: undefined reference to `rumpns_mpt_print_reply'
    /opt/rumprun/src-netbsd/sys/rump/../dev/ic/mpt_netbsd.c:427: undefined reference to `rumpns_mpt_req_state'
    /opt/rumprun/src-netbsd/sys/rump/../dev/ic/mpt_netbsd.c:429: undefined reference to `rumpns_mpt_print_scsi_io_request'
    /opt/rumprun/src-netbsd/sys/rump/../dev/ic/mpt_netbsd.c:1094: undefined reference to `rumpns_mpt_print_scsi_io_request'

some of those are in ./dev/ic/mpt_debug.c so tried to add this file to makefile.

which seemed to finally bake!


VMware uses scsi with vendor id 0x1000 and device id 0x0030
which qemu doesn't emulate, so can't test with qemu, as the driver for the qemu scsi is a different one.

gdb server in fusion:
http://wiki.osdev.org/VMware
