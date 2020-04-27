
test:     file format elf64-littleriscv


Disassembly of section .text:

0000000000000000 <_start>:
   0:	01e28293          	addi	t0,t0,30
   4:	00138393          	addi	t2,t2,1

0000000000000008 <L1>:
   8:	00730e33          	add	t3,t1,t2
   c:	00038313          	mv	t1,t2
  10:	000e0393          	mv	t2,t3
  14:	0220000f          	fence	r,r
  18:	fff28293          	addi	t0,t0,-1
  1c:	fe0296e3          	bnez	t0,8 <L1>
  20:	00208033          	add	zero,ra,sp
