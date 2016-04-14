#	$NetBSD: Makefile,v 1.11 2015/11/16 23:27:08 pooka Exp $
#

.PATH:	${.CURDIR}/../../../../arch/arm/broadcom/

LIB=	rumpdev_obio
COMMENT=libliblib obio

IOCONF= OBIO.ioconf
RUMP_COMPONENT=ioconf

SRCS=	bcm2835_obio.c

#CPPFLAGS+= -DUHUB_DEBUG

.include <bsd.lib.mk>
.include <bsd.klinks.mk>
