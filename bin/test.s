
test:     file format elf64-littleriscv


Disassembly of section .text:

0000000000000000 <_start>:
   0:	40010113          	addi	sp,sp,1024
   4:	002081b3          	add	gp,ra,sp
   8:	00310233          	add	tp,sp,gp
   c:	808e                	mv	ra,gp
   e:	8192                	mv	gp,tp
  10:	004002ef          	jal	t0,14 <L1>

0000000000000014 <L1>:
  14:	00208033          	add	zero,ra,sp
