# patch /opt/rumprun/lib/librumprun_base/config.c < /tmp/patch

cd  /opt/rumprun/

DESTDIR=/usr/local
BUILDRUMP_EXTRA=

if [ "$PLATFORM" = "" ]; then
  echo PLATFORM should be xen or hw
  exit 1
fi

./build-rr.sh -d $DESTDIR -o ./obj $PLATFORM build -- $BUILDRUMP_EXTRA && \
./build-rr.sh -d $DESTDIR -o ./obj $PLATFORM install
