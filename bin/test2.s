
test2:     file format elf64-littleriscv


Disassembly of section .text:

0000000000000000 <main>:
   0:	fe010113          	addi	sp,sp,-32
   4:	00813c23          	sd	s0,24(sp)
   8:	02010413          	addi	s0,sp,32
   c:	fe042623          	sw	zero,-20(s0)
  10:	fec42783          	lw	a5,-20(s0)
  14:	0017879b          	addiw	a5,a5,1
  18:	fef42623          	sw	a5,-20(s0)
  1c:	00000793          	li	a5,0
  20:	00078513          	mv	a0,a5
  24:	01813403          	ld	s0,24(sp)
  28:	02010113          	addi	sp,sp,32
  2c:	00008067          	ret
