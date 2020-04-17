package src.intstructions;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import src.instructions.Instruction;

import src.processor.*;


@Aspect
public class rv32i implements Instruction {

    private void applyI(Processor p, int encoding) {
        int rdi  = (encoding >>  7) & 0x1f;
        int rs1i = (encoding >> 15) & 0x1f;

        Register rd  = p.getRegisterByIndex(rdi);
        Register rs1 = p.getRegisterByIndex(rs1i);

        // 12-bit immmediate
        int imm = (encoding >> 20) & 0xfff;
        System.out.printf("%x\n", imm);

        System.out.println("Imm");
        switch (encoding & 0x7000) {
            case 0x0000:
                // addi
                rd.set(rs1.get() + imm);
                break;
        }
    }

    private void applyR(Processor p, int encoding) {
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
                break;
            case 0x40000000:
                // sub
                rd.set(rs1.get() - rs2.get());
                break;
            case 0x00001000:
                // sll (shift logical left)
                rd.set(rs1.get() << (rs2.get() & 0x1f));
                break;
            case 0x00002000:
                // slt (signed less than)
                rd.set(rs1.get() < rs2.get() ? 1 : 0);
                break;
            case 0x00003000:
                // sltu (unsigned less than)
                long v1 = ((long) rs1.get()) & 0xffffffffl;
                long v2 = ((long) rs2.get()) & 0xffffffffl;
                rd.set(v1 < v2 ? 1 : 0);
                break;
            case 0x00004000:
                // xor
                rd.set(rs1.get() ^ rs2.get());
                break;
            case 0x00005000:
                // srl (right logical shift)
                rd.set(rs1.get() >>> (rs2.get() & 0x1f));
                break;
            case 0x40005000:
                // sra (arithmatic right shift)
                rd.set(rs1.get() >> (rs2.get() & 0x1f));
                break;
            case 0x00006000:
                // or
                rd.set(rs1.get() | rs2.get());
                break;
            case 0x00007000:
                // and
                rd.set(rs1.get() & rs2.get());
                break;
        }
    }

    private void applyB(Processor p, int encoding) {

    }

    private void lui(Processor p, int encoding) {
        int rdi = (encoding >> 7) & 0x1f;

        Register rd = p.getRegisterByIndex(rdi);

        int imm = encoding & 0xfffff000;

        rd.set((imm & 0xfffff000) | (rd.get() & 0x00000fff));
    }


    public void execute(Processor p, int encoding) {
        // instruction is length 16
        switch (encoding & 0x7c) {
            case 0x10:
                // I instructions
                applyI(p, encoding);
                break;
            case 0x30:
                // R instructions
                applyR(p, encoding);
                break;
            case 0x34:
                // lui
                lui(p, encoding);
                break;
            case 0x60:
                // B instructions
                applyB(p, encoding);
            default:
                System.out.printf("got %x\n", encoding & 0x7c);
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
    }
}


