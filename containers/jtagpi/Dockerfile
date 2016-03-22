FROM armhfbuild/debian

RUN apt-get update && apt-get install -y autoconf libtool libftdi-dev

RUN cd /tmp/ && wget https://gist.github.com/giseburt/e832ed40e3c77fcf7533/raw/e8c71233970e4d42eed7c3bf4b13390cdcf2a1fd/raspberrypi-due.tcl

RUN apt-get install -y git

RUN cd /opt/ && \
    git clone --recursive git://git.code.sf.net/p/openocd/code openocd-git

RUN apt-get install -y gcc pkg-config libusb-1.0-0-dev

RUN apt-get install -y make

RUN cd /opt/openocd-git && \
    ./bootstrap && \
    ./configure --enable-sysfsgpio \
         --enable-maintainer-mode \
         --disable-werror \
          --enable-ftdi \
         --enable-ep93xx \
         --enable-at91rm9200 \
         --enable-usbprog \
         --enable-presto_libftdi \
         --enable-jlink \
         --enable-vsllink \
         --enable-rlink \
         --enable-arm-jtag-ew \
         --enable-dummy \
         --enable-buspirate \
         --enable-ulink \
         --enable-presto_libftdi \
         --enable-usb_blaster_libftdi \
         --prefix=/usr \
    && \
    make && make install

RUN  cd /opt/openocd-git && \
     cp -r tcl/ /usr/share/openocd/ && \
     cp /tmp/raspberrypi-due.tcl /usr/share/openocd/tcl/board/

COPY board/raspberrypi-raspberrypi.tcl /usr/share/openocd/tcl/board/
COPY target/raspberry.cfg /usr/share/openocd/tcl/target/

# run with

EXPOSE 3333
