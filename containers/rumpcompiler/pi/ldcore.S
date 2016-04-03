
/*-
 * Copyright (c) 2015 Antti Kantee.  All Rights Reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS
 * OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE AUTHOR OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
 * LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY
 * OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 */

#include <hw/kernel.h>

.space 4096
bootstack:
.space 4096
undefstack:
.space 4096
irqstack:

.section .start,"ax",%progbits

.globl _start
_start:
  /* turn on debugging */
  bl enable_jtag

  /* make page table */
  /* thanks: http://www.embedded-bits.co.uk/2011/mmucode/ */
  ldr r0, =identity_page_table
  /* Set Translation Table Base 0 (TTB0) */
  mcr p15, 0, r0, c2, c0, 0

  mov     r0, #0
	mcr	p15, 0, r0, c8, c7, 0	/* Invalidate TLBs */


  /* disable access checks and set domain*/
  mov r0, #0x3
  mcr p15, 0, r0, c3, c0, 0

  /* turn on mmu */
  mrc p15, 0, r0, c1, c0, 0
  orr r0, r0, #0x1
  mcr p15, 0, r0, c1, c0, 0


	/* set undefined stack */
	mrs r0, cpsr
	bic r0, r0, #0x1f
	orr r0, r0, #0x1b
	msr cpsr, r0
	ldr sp, =undefstack

	bic r0, r0, #0x1f
	orr r0, r0, #0x12
	msr cpsr, r0
	ldr sp, =irqstack

	bic r0, r0, #0x1f
	orr r0, r0, #0x13
	msr cpsr, r0
	ldr sp, =bootstack
	bl arm_boot
	b haltme


.globl PUT32
PUT32:
    str r1,[r0]
    bx lr

.globl GET32
GET32:
    ldr r0,[r0]
    bx lr

.globl dummy
dummy:
    bx lr


haltme:
	b haltme

/*
 * "exception" vectors
 */
.globl vector_start, vector_end
vector_start:
	ldr pc, [pc, #24]
	ldr pc, [pc, #24]
	ldr pc, [pc, #24]
	ldr pc, [pc, #24]
	ldr pc, [pc, #24]
	nop
	ldr pc, [pc, #24]
	ldr pc, [pc, #24]

	.word	vector__start
	.word	vector_undefined
	.word	vector_softint
	.word	vector_prefetch_abort
	.word	vector_data_abort
	.word	0
	.word	vector_irq
	.word	vector_fiq
vector_end:

vector__start:
	b vector__start

vector_undefined:
	push {r0-r14}
	adds r0, sp, #(14*4)
	bl arm_undefined
	pop {r0-r14}
	movs pc, lr

vector_irq:
	push {r0-r14}
	adds r0, sp, #(14*4)
	bl arm_interrupt
	pop {r0-r14}
	subs pc, r14, #4

/*
 * The rest of the exceptions just loop.  Attach a debugger
 * to find out where you are currently.
 */

vector_softint:
	b vector_softint

vector_prefetch_abort:
	b vector_prefetch_abort

vector_data_abort:
	b vector_data_abort

vector_fiq:
	b vector_fiq


ENTRY(splhigh)
	mrs	r0, cpsr
        orr	r0, r0, #(1<<7)
        msr	cpsr_c, r0
	bx lr
END(splhigh)

ENTRY(spl0)
	mrs	r0, cpsr
        bic	r0, r0, #(1<<7)
        msr	cpsr_c, r0
	bx lr
END(spl0)
