
test:     file format elf64-littleriscv


Disassembly of section .text:

0000000000000000 <_start>:
   0:	00110113          	addi	sp,sp,1
   4:	002081b3          	add	gp,ra,sp
   8:	00310233          	add	tp,sp,gp
   c:	00018093          	mv	ra,gp
  10:	00020193          	mv	gp,tp
  14:	004002ef          	jal	t0,18 <L1>

0000000000000018 <L1>:
  18:	00208033          	add	zero,ra,sp
