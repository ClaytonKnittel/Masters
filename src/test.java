package src;

import src.processor.Processor;

public class test {

    public static void execute(String line) {}

    public static void main(String args[]) {
        String bin;
        if (args.length == 0) {
            bin = "bin/test";
        }
        else {
            bin = args[0];
        }

        Processor p = new Processor(bin);
        p.printState();
        while (p.execute());
        p.printState();
    }
}

