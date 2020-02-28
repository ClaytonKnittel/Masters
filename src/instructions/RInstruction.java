package src.instructions;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import src.instructions.Instruction;

import src.processor.*;

/**
 * R instruction, i.e. instruction using three register inputs
 *
 * layout:
 *  
 *  31 ---- 25 24 - 20 19 - 15 14 ----- 12 11  7 6 ---- 0
 *    funct7     rs2     rs1      funct3     rd   opcode
 *
 *
 * with
 *  opcode - encoding of operation to use
 *  rd - destination register
 *  rs1 - first input register
 *  rs2 - second input register
 *
 */
@Aspect
public abstract class RInstruction implements Instruction {

    public static final int opcode_mask = 0xfc00707f;

    private static final int REGISTER_ENCODING_MASK = 0x1f;
    private static final int RD_OFFSET = 7;
    private static final int RS1_OFFSET = 15;
    private static final int RS2_OFFSET = 20;

    /**
     * to be overridden by concrete instructions, which should do some
     * operation on rs1 and rs2, and store the result in rd
     */
    protected abstract void op(Register rd, Register rs1, Register rs2);

    /**
     * gives back the mask corresponding to this instruction, which is to
     * be applied to an instruction before comparing to this instruction's
     * encoding when determining if this is a match
     */
    protected abstract int instruction_mask();

    /**
     * gives the opcode of this instruction, which must have only values
     * in funct7, funct3, and opcode set
     */
    protected abstract int instruction_encoding();

    /**
     * returns true if the given encoding is an instance of this instruction,
     * false otherwise
     */
    public boolean match(int encoding) {
        if ((encoding & 0x3) != 0x3) {
            return false;
        }
        return (encoding & instruction_mask()) == instruction_encoding();
    }

    public void execute(Processor p, int encoding) {
        int rde = REGISTER_ENCODING_MASK & (encoding >> RD_OFFSET);
        int rs1e = REGISTER_ENCODING_MASK & (encoding >> RS1_OFFSET);
        int rs2e = REGISTER_ENCODING_MASK & (encoding >> RS2_OFFSET);

        Register rd = p.getRegisterByIndex(rde); 
        Register rs1 = p.getRegisterByIndex(rs1e); 
        Register rs2 = p.getRegisterByIndex(rs2e); 

        op(rd, rs1, rs2);
    }
    
    @Before("execution (* *.execute(int))")
    public void apply(JoinPoint jp) {
        Processor p = (Processor) jp.getThis();
        Integer encoding = (Integer) jp.getArgs()[0];
        System.out.println(encoding);
        if (match(encoding)) {
            execute(p, encoding);
        }
    }
}

