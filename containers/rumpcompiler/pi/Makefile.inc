
ASMS=	arch/arm/rpi/locore.S arch/arm/rpi/pagetable.S


SRCS+=	arch/arm/rpi/machdep.c
SRCS+=	arch/arm/rpi/serialcons.c

CPPFLAGS+=	-Iarch/arm
AFLAGS+= 	-meabi=gnu

.PHONY: archdirs
archdirs:
	mkdir -p ${RROBJ}/platform/arch/arm/rpi
