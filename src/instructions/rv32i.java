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
        int imm = (encoding >> 20) & 0x3ff;

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

        switch (encoding & 0x7000) {
            case 0x0000:
                // add
                rd.set(rs1.get() + rs2.get());
                break;
        }
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
                // addi, slli, slti, sltiu, xori, srli, srai, ori, andi
                applyI(p, encoding);
                break;
            case 0x30:
                // add, sll, slt, sltu, xor, srl, sra, or, and
                applyR(p, encoding);
                break;
            case 0x34:
                // lui
                lui(p, encoding);
                break;
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


