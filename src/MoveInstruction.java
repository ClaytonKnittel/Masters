package src;

import org.aspectj.lang.annotation.Aspect;

import src.AbstractInstruction;

@Aspect
public class MoveInstruction extends AbstractInstruction {
    
    public MoveInstruction() {
        super("mov");
    }

    @Override
    public void apply() {

    }
    
}
