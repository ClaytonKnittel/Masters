.section .text
.global _start
_start:
    addi t0, t0, 30
    addi t2, t2, 0x1
L1:
    add t3, t1, t2
    mv t1, t2
    mv t2, t3
    addi t0, t0, -1
    bne t0, zero, L1


;    add x0, x1, x2
