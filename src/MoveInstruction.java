package src;

import org.aspectj.lang.annotation.Aspect;

import src.AbstractInstruction;

@Aspect
public class MoveInstruction extends AbstractInstruction {
    
    public MoveInstruction() {
        super("mov");
    }

    @Override
    public void apply(String args[]) {
        System.out.println("Move instruction, write " + args[1] + " to " + args[2]);
    }
    
}
