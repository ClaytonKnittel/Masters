package src.intstructions;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import src.instructions.Instruction;

import src.processor.*;


// rename InstructionProcessor
@Aspect
public class rv32i implements Instruction {

    private static final int ILLEGAL_INSTRUCTION = 0x1;
    // thrown by jump instructions when destination address is not aligned
    // by 4-bytes
    private static final int MISALIGNED_INSTRUCTION_FETCH = 0x2;

    // Integer Register-Immediate instructions
    private int applyI(Processor p, int encoding) {
        int rdi  = (encoding >>  7) & 0x1f;
        int rs1i = (encoding >> 15) & 0x1f;

        Register rd  = p.getRegisterByIndex(rdi);
        Register rs1 = p.getRegisterByIndex(rs1i);

        // 12-bit immmediate
        // (sign extended)
        int imm_s = (encoding >> 20);
        int imm = imm_s & 0xfff;

        // shift amount (for shift instructions)
        int shamt = imm & 0x1f;
        // remaining bits after shamt bits for right shift instructions
        // (if 0, then logical, if 16, then arithmatic)
        int rem = (imm & 0xfe0) >> 5;

        switch (encoding & 0x7000) {
            case 0x0000:
                // addi (add (sign-extended) immediate to rs1)
                rd.set(rs1.get() + imm_s);
                System.out.printf("addi x%d, x%d, %d\n", rdi, rs1i, imm_s);
                break;
            case 0x1000:
                // slli (logical left shift immediate)
                rd.set(rs1.get() << shamt);
                System.out.printf("slli x%d, x%d, %d\n", rdi, rs1i, imm_s);
                break;
            case 0x2000:
                // slti (set less than immediate, places value 1 in register
                // rd if rs1 is less than imm (sign extended))
                rd.set((rs1.get() < imm_s) ? 1 : 0);
                System.out.printf("slti x%d, x%d, %d\n", rdi, rs1i, imm_s);
                break;
            case 0x3000:
                // sltiu (same as slti, but values are treated as unsigned)
                rd.set((rs1.getu() < imm) ? 1 : 0);
                System.out.printf("sltiu x%d, x%d, %d\n", rdi, rs1i, imm_s);
                break;
            case 0x4000:
                // xori (xor (sign-extended) immediate)
                rd.set(rs1.get() ^ imm_s);
                System.out.printf("xori x%d, x%d, %d\n", rdi, rs1i, imm_s);
                break;
            case 0x5000:
                if (rem == 0) {
                    // srli (logical right shift immediate)
                    rd.set(rs1.get() >>> shamt);
                    System.out.printf("srli x%d, x%d, %d\n", rdi, rs1i, shamt);
                }
                else if (rem == 16) {
                    // srai (arithmatic right shift immediate)
                    rd.set(rs1.get() >> shamt);
                    System.out.printf("srai x%d, x%d, %d\n", rdi, rs1i, shamt);
                }
                else {
                    return ILLEGAL_INSTRUCTION;
                }
                break;
            case 0x6000:
                // ori (or (sign-extended) immediate)
                rd.set(rs1.get() | imm_s);
                System.out.printf("ori x%d, x%d, %d\n", rdi, rs1i, imm_s);
                break;
            case 0x7000:
                // andi (and (sign-extended) immediate)
                rd.set(rs1.get() & imm_s);
                System.out.printf("andi x%d, x%d, %d\n", rdi, rs1i, imm_s);
                break;
        }

        // increment instruction pointer
        p.setPCRelative(4);

        return 0;
    }

    // Integer Register-Register operations
    private int applyR(Processor p, int encoding) {
        int rdi  = (encoding >>  7) & 0x1f;
        int rs1i = (encoding >> 15) & 0x1f;
        int rs2i = (encoding >> 20) & 0x1f;

        Register rd  = p.getRegisterByIndex(rdi);
        Register rs1 = p.getRegisterByIndex(rs1i);
        Register rs2 = p.getRegisterByIndex(rs2i);

        switch (encoding & 0xfe007000) {
            case 0x00000000:
                // add
                rd.set(rs1.get() + rs2.get());
                System.out.printf("add x%d, x%d, x%d\n", rdi, rs1i, rs2i);
                break;
            case 0x40000000:
                // sub
                rd.set(rs1.get() - rs2.get());
                System.out.printf("sub x%d, x%d, x%d\n", rdi, rs1i, rs2i);
                break;
            case 0x00001000:
                // sll (shift logical left)
                rd.set(rs1.get() << (rs2.get() & 0x1f));
                System.out.printf("sll x%d, x%d, x%d\n", rdi, rs1i, rs2i);
                break;
            case 0x00002000:
                // slt (signed less than)
                rd.set(rs1.get() < rs2.get() ? 1 : 0);
                System.out.printf("slt x%d, x%d, x%d\n", rdi, rs1i, rs2i);
                break;
            case 0x00003000:
                // sltu (unsigned less than)
                rd.set(rs1.getu() < rs2.getu() ? 1 : 0);
                System.out.printf("sltu x%d, x%d, x%d\n", rdi, rs1i, rs2i);
                break;
            case 0x00004000:
                // xor
                rd.set(rs1.get() ^ rs2.get());
                System.out.printf("xor x%d, x%d, x%d\n", rdi, rs1i, rs2i);
                break;
            case 0x00005000:
                // srl (right logical shift)
                rd.set(rs1.get() >>> (rs2.get() & 0x1f));
                System.out.printf("srl x%d, x%d, x%d\n", rdi, rs1i, rs2i);
                break;
            case 0x40005000:
                // sra (arithmatic right shift)
                rd.set(rs1.get() >> (rs2.get() & 0x1f));
                System.out.printf("sra x%d, x%d, x%d\n", rdi, rs1i, rs2i);
                break;
            case 0x00006000:
                // or
                rd.set(rs1.get() | rs2.get());
                System.out.printf("or x%d, x%d, x%d\n", rdi, rs1i, rs2i);
                break;
            case 0x00007000:
                // and
                rd.set(rs1.get() & rs2.get());
                System.out.printf("and x%d, x%d, x%d\n", rdi, rs1i, rs2i);
                break;
            default:
                return ILLEGAL_INSTRUCTION;
        }

        // increment instruction pointer
        p.setPCRelative(4);

        return 0;
    }

    // Unconditional Jump (jump and link)
    private int jal(Processor p, int encoding) {
        int rdi  = (encoding >>  7) & 0x1f;
        Register rd  = p.getRegisterByIndex(rdi);

        // offset address in multiple of 2 bytes
        int jimm20 = (encoding & 0x000ff000) |
                    ((encoding & 0x00100000) >>> 10) |
                    ((encoding & 0x7fe00000) >>> 21) |
                    ((encoding & 0x80000000) >>  11);

        if ((jimm20 & 0x1) != 0) {
            return MISALIGNED_INSTRUCTION_FETCH;
        }
        System.out.printf("jal x%d, %d\n", rdi, jimm20 << 1);

        // store instruction following jal in rd
        rd.set(p.getPC() + 4);

        // add 2 * jimm20 to pc
        p.setPCRelative(jimm20 << 1);

        return 0;
    }

    // Unconditional Jump (indirect jump)
    private int jalr(Processor p, int encoding) {
        int rdi  = (encoding >>  7) & 0x1f;
        int rs1i = (encoding >> 15) & 0x1f;

        Register rd  = p.getRegisterByIndex(rdi);
        Register rs1 = p.getRegisterByIndex(rs1i);

        if ((encoding & 0x7000) != 0) {
            // jal requires these bits to be 0
            return ILLEGAL_INSTRUCTION;
        }

        // 12-bit immmediate (sign-extended)
        int imm12 = (encoding >> 20);

        // destination address is obtained by adding sign-extended immediate
        // to rs1 and setting the least-significant bit to 0
        int dst = rs1.get() + imm12;
        dst &= 0xfffffffe;

        if ((dst & 0x3) != 0) {
            return MISALIGNED_INSTRUCTION_FETCH;
        }

        System.out.printf("jalr x%d, x%d, %d\n", rdi, rs1i, imm12);

        // store instruction following jal in rd
        rd.set(p.getPC() + 4);

        // perform the jump
        p.setPC(dst);

        return 0;
    }

    // Conditional Branches
    private int applyB(Processor p, int encoding) {
        int rs1i = (encoding >> 15) & 0x1f;
        int rs2i = (encoding >> 20) & 0x1f;

        Register rs1 = p.getRegisterByIndex(rs1i);
        Register rs2 = p.getRegisterByIndex(rs2i);

        int bimm12hi = ((encoding & 0x00000080) <<   3) |
                       ((encoding & 0x00000f00) >>>  8) |
                       ((encoding & 0x7e000000) >>> 21) |
                       ((encoding & 0x80000000) >>  20);

        // bimm12hi is jump offset in multiple of 2 bytes
        if ((bimm12hi & 0x1) != 0) {
            return MISALIGNED_INSTRUCTION_FETCH;
        }

        boolean condition;

        switch (encoding & 0x7000) {
            case 0x0000:
                // beq (branch if equal)
                condition = (rs1.get() == rs2.get());
                System.out.printf("beq x%d, x%d, %d\n", rs1i, rs2i, bimm12hi << 1);
                break;
            case 0x1000:
                // bne (branch if not equal)
                condition = (rs1.get() != rs2.get());
                System.out.printf("bne x%d, x%d, %d\n", rs1i, rs2i, bimm12hi << 1);
                break;
            case 0x4000:
                // blt (branch if less than (signed))
                condition = (rs1.get() < rs2.get());
                System.out.printf("blt x%d, x%d, %d\n", rs1i, rs2i, bimm12hi << 1);
                break;
            case 0x5000:
                // bge (branch if greater or equal (signed))
                condition = (rs1.get() >= rs2.get());
                System.out.printf("bge x%d, x%d, %d\n", rs1i, rs2i, bimm12hi << 1);
                break;
            case 0x6000:
                // bltu (branch if less than (unsigned))
                condition = (rs1.getu() < rs2.getu());
                System.out.printf("bltu x%d, x%d, %d\n", rs1i, rs2i, bimm12hi << 1);
                break;
            case 0x7000:
                // bgeu (branch if greater or equal (unsigned))
                condition = (rs1.getu() >= rs2.getu());
                System.out.printf("bgeu x%d, x%d, %d\n", rs1i, rs2i, bimm12hi << 1);
                break;
            default:
                return ILLEGAL_INSTRUCTION;
        }

        // jump by 2 * bimm12hi if condition succeeded, else go to next instruction
        p.setPCRelative(condition ? (bimm12hi << 1) : 4);

        return 0;
    }

    private int lui(Processor p, int encoding) {
        int rdi = (encoding >> 7) & 0x1f;

        Register rd = p.getRegisterByIndex(rdi);

        int imm = encoding & 0xfffff000;

        System.out.printf("lui x%d, %d\n", rd, imm);

        // set highest 20 bits of rd
        rd.set((imm & 0xfffff000) | (rd.get() & 0x00000fff));

        // increment instruction pointer
        p.setPCRelative(4);

        return 0;
    }

    // Load Instructions
    public int applyL(Processor p, int encoding) {
        int rdi  = (encoding >>  7) & 0x1f;
        int rs1i = (encoding >> 15) & 0x1f;

        Register rd  = p.getRegisterByIndex(rdi);
        Register rs1 = p.getRegisterByIndex(rs1i);

        Memory mem = p.getMem();

        // 12-bit immmediate (sign-extended)
        int imm12 = (encoding >> 20);

        int dst = rs1.get() + imm12;

        // memory fetched from memory is to be stored here
        int res;
        switch (encoding & 0x7000) {
            case 0x0000:
                // lb (load byte (signed))
                System.out.printf("lb x%d, %d(x%d)\n", rdi, imm12, rs1i);
                res = (int) mem.loadB(dst);
                break;
            case 0x1000:
                // lh (load short (signed))
                System.out.printf("lh x%d, %d(x%d)\n", rdi, imm12, rs1i);
                res = (int) mem.loadH(dst);
                break;
            case 0x2000:
                // lw (load word)
                System.out.printf("lw x%d, %d(x%d)\n", rdi, imm12, rs1i);
                res = mem.loadW(dst);
                break;
            case 0x4000:
                // lbu (load byte (unsigned))
                System.out.printf("lbu x%d, %d(x%d)\n", rdi, imm12, rs1i);
                res = ((int) mem.loadB(dst)) & 0xff;
                break;
            case 0x5000:
                // lhu (load short (unsigned))
                System.out.printf("lhu x%d, %d(x%d)\n", rdi, imm12, rs1i);
                res = ((int) mem.loadH(dst)) & 0xffff;
                break;
            default:
                return ILLEGAL_INSTRUCTION;
        }

        rd.set(res);

        // increment instruction pointer
        p.setPCRelative(4);

        return 0;
    }

    // Store Instructions
    public int applyS(Processor p, int encoding) {
        int rs1i = (encoding >> 15) & 0x1f;
        int rs2i = (encoding >> 20) & 0x1f;

        Register rs1 = p.getRegisterByIndex(rs1i);
        Register rs2 = p.getRegisterByIndex(rs2i);

        Memory mem = p.getMem();

        int imm12 = (encoding >> 20) |
                   ((encoding >> 7) & 0x7f);

        int dst = rs1.get() + imm12;
        int val = rs2.get();

        switch (encoding & 0x7000) {
            case 0x0000:
                // sb (store byte)
                System.out.printf("sb x%d, %d(x%d)\n", rs2i, imm12, rs1i);
                mem.storeB(dst, (byte) val);
                break;
            case 0x1000:
                // sh (store short)
                System.out.printf("sh x%d, %d(x%d)\n", rs2i, imm12, rs1i);
                mem.storeH(dst, (short) val);
                break;
            case 0x2000:
                // sw (store word)
                System.out.printf("sw x%d, %d(x%d)\n", rs2i, imm12, rs1i);
                mem.storeW(dst, val);
                break;
            default:
                return ILLEGAL_INSTRUCTION;
        }

        // increment instruction pointer
        p.setPCRelative(4);

        return 0;
    }

    private int applyF(Processor p, int encoding) {
        final String rwio[] = {
            "", "w", "r", "rw",
            "o", "wo", "ro", "rwo",
            "i", "wi", "ri", "rwi",
            "io", "wio", "rio", "rwio"
        };
        
        switch (encoding & 0x7000) {
            case 0x0000:
                // fence instruction
                if ((encoding & 0xf00f8f80) != 0) {
                    return ILLEGAL_INSTRUCTION;
                }
                int pred_bitmask = (encoding & 0x0f000000) >>> 24;
                int succ_bitmask = (encoding & 0x00f00000) >>> 20;
                System.out.printf("fence %s, %s\n", rwio[pred_bitmask],
                        rwio[succ_bitmask]);
                break;
            case 0x1000:
                // fence.i instruction
                if ((encoding & 0xffff8f80) != 0) {
                    return ILLEGAL_INSTRUCTION;
                }
                System.out.printf("fence.i\n");
                break;
            default:
                return ILLEGAL_INSTRUCTION;
        }

        // increment instruction pointer
        p.setPCRelative(4);

        return 0;
    }


    public void execute(Processor p, int encoding) {
        int res;
        // instruction is length 16
        switch (encoding & 0x7c) {
            case 0x00:
                // L instructions
                res = applyL(p, encoding);
                break;
            case 0x0c:
                // fence instructions
                res = applyF(p, encoding);
                break;
            case 0x10:
                // I instructions
                res = applyI(p, encoding);
                break;
            case 0x20:
                // S instruction
                res = applyS(p, encoding);
                break;
            case 0x30:
                // R instructions
                res = applyR(p, encoding);
                break;
            case 0x34:
                // lui
                res = lui(p, encoding);
                break;
            case 0x60:
                // B instructions
                res = applyB(p, encoding);
                break;
            case 0x64:
                // jalr
                res = jalr(p, encoding);
                break;
            case 0x6c:
                // jal
                res = jal(p, encoding);
                break;
            default:
                res = ILLEGAL_INSTRUCTION;
        }
        switch (res) {
            case ILLEGAL_INSTRUCTION:
                System.out.printf("illegal instruction 0x%08x\n", encoding);
                break;
            case MISALIGNED_INSTRUCTION_FETCH:
                System.out.printf("Misaligned instruction fetch\n");
                break;
            default:
                p.foundInstructionMatch();
        }
    }

    
    @Before("execution (* *.execute(int))")
    public void apply(JoinPoint jp) {
        Processor p = (Processor) jp.getThis();
        Integer encoding = (Integer) jp.getArgs()[0];
        
        if ((encoding & 0x3) == 0x3 &&
                (encoding & 0x1c) != 0x1c) {
            execute(p, encoding);
        }
        else {
            System.out.printf("Not 0x%08x\n", encoding);
        }
    }
}


