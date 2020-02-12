package src;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

import java.lang.annotation.Annotation;

import src.Instruction;

@Aspect
public abstract class AbstractInstruction {

    String name;

    protected AbstractInstruction(String name) {
        this.name = name;
    }

    public abstract void apply(String args[]);

    @Before("execution (* *.execute(String))")
    public void apply(JoinPoint jp) {
        String arg = (String) (jp.getArgs()[0]);

        System.out.println("arg: " + arg);

        String args[] = arg.split("[\\s]+");

        if (args[0].equalsIgnoreCase(name)) {
            apply(args);
        }

    }
}

