# patch /opt/rumprun/lib/librumprun_base/config.c < /tmp/patch

cd  /opt/rumprun/

DESTDIR=/usr/local
BUILDRUMP_EXTRA=

if [ "$PLATFORM" = "" ]; then
  echo PLATFORM should be xen or hw
  exit 1
fi


if [ "$PLATFORM" = "hw" ]; then
# ppb patch
cat >>  /opt/rumprun/src-netbsd/sys/rump/dev/lib/libpci/PCI.ioconf <<EOF

    pci*    at ppb? bus ?
    ppb*    at pci? dev ? function ?
EOF

sed -i -e 's/SRCS+=	pci.c/SRCS+=	ppb.c ci.c' /opt/rumprun/src-netbsd/sys/rump/dev/lib/libpci/Makefile


./build-rr.sh -d $DESTDIR -o ./obj $PLATFORM build -- $BUILDRUMP_EXTRA && \
./build-rr.sh -d $DESTDIR -o ./obj $PLATFORM install

fi
