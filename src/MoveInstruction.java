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
        System.out.print("Move instruction, args ");
        for (int i = 1; i < args.length; i++) {
            System.out.print(args[i] + ", ");
        }
        System.out.println();
    }
    
}
