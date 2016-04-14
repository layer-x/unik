#	$NetBSD: Makefile,v 1.11 2015/11/16 23:27:08 pooka Exp $
#

.PATH:	${.CURDIR}/../../../../arch/arm/broadcom/

LIB=	rumpdev_dwctwo
COMMENT=USB DesignWare HS Controller driver

IOCONF= DWCTWO.ioconf
RUMP_COMPONENT=ioconf

SRCS=	bcm2835_dwctwo.c

#CPPFLAGS+= -DUHUB_DEBUG

.include <bsd.lib.mk>
.include <bsd.klinks.mk>
