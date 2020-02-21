package src;

import src.processor.Processor;

public class test {

    public static void execute(String line) {}

    public static void main(String args[]) {
        Processor p = Processor.make_simple();

        while (p.execute());
    }
}

