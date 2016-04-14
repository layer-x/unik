#	$NetBSD: Makefile,v 1.4 2015/11/16 23:27:08 pooka Exp $
#

RUMPTOP=${TOPRUMP}

.PATH:	${RUMPTOP}/../dev/usb

LIB=	rumpdev_usb_if_usmsc
COMMENT=SMSC LAN95xx 10/100 USB Ethernet device

IOCONF=	USB_IF_USMSC.ioconf
RUMP_COMPONENT=ioconf

SRCS+=	if_smsc.c

CPPFLAGS+= -I${RUMPTOP}/librump/rumpkern

.include "${RUMPTOP}/Makefile.rump"
.include <bsd.lib.mk>
.include <bsd.klinks.mk>
