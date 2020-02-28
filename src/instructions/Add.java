package src.instructions;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import src.instructions.RInstruction;

import src.processor.*;

@Aspect
public class Add extends RInstruction {

    protected int instruction_mask() {
        return (int) (0xf8e00707fl);
    }

    protected int instruction_encoding() {
        return 0x00000033;
    }

    protected void op(Register rd, Register rs1, Register rs2) {
        rd.set(rs1.get() + rs2.get());
    }
}
