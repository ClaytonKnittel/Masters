.section .text
.global _start
_start:
    add gp, ra, sp
    lui sp, 0x100

;    add x0, x1, x2
