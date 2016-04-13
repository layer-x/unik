#! /usr/bin/env awk -f

# build page tables.  easier doing it like this than in assembly.
# we might actually want the page tables to reflect reality some
# day (e.g. mapping text r/o), but for now we don't care.

BEGIN {
  # how many megabytes of ram + gpio
	MEGS=1024
  # type for section
  # see: https://www.altera.com/content/dam/altera-www/global/en_US/pdfs/literature/third-party/archives/ddi0100e_arm_arm.pdf (B.3-7 3.3.3)
  SECTION=2
  CACHEBUFFER=0xc

  # entry type. 2 = section
  TYPE = SECTION
  # access bits. 3 == 0b11 is read-write
  ALL_ACESS = 3

	printf("/* AUTOMATICALLY GENERATED BY makepagetables.awk */\n\n");

	# map each physical section to the same virtual section.
  # as we are lazy.
  # align table to 16kb (per arm manual)
	printf(".align 14\n");
  printf(".globl identity_page_table\n");
  printf("identity_page_table:\n");
	for (i = 0; i < MEGS; i++) {
    addr = lshift(i, 20);

		printf("\t.word 0x%08x | 0x%x\n", addr, or(lshift(ALL_ACESS, 10), TYPE, CACHEBUFFER));
	}

}
