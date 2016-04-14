#	$NetBSD: Makefile,v 1.11 2015/11/16 23:27:08 pooka Exp $
#

.PATH:	${.CURDIR}/../../../../dev/usb

LIB=	rumpdev_usbarm
COMMENT=USB support for arm

IOCONF= USBARM.ioconf
RUMP_COMPONENT=ioconf

SRCS=	usb.c usbdi.c usbdi_util.c usb_mem.c usb_subr.c usb_quirks.c	\
	uhub.c usbroothub_subr.c usb_verbose.c

#CPPFLAGS+= -DUHUB_DEBUG

.include <bsd.lib.mk>
.include <bsd.klinks.mk>
