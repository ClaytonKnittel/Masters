package src;

import org.aspectj.lang.annotation.Aspect;

import src.AbstractInstruction;

@Aspect
public class PopInstruction extends AbstractInstruction {
    
    public PopInstruction() {
        super("pop");
    }

    @Override
    public void apply(String args[]) {
        System.out.println("Pop instruction, " + args[1]);
    }
    
}
