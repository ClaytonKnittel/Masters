.section .text
.global _start
_start:
    addi sp, sp, 0x400
    add gp, ra, sp
    add tp, sp, gp
    mv ra, gp
    mv gp, tp
    jal t0, L1
L1:


;    add x0, x1, x2
