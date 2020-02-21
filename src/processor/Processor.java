package src.processor;


import src.processor.Memory;

public class Processor {

    protected Memory mem;
    int rip;

    public Processor(int mem_size) {
        mem = new Memory(mem_size);
        rip = 0;
    }

    public static Processor make_simple() {
        Processor p = new Processor(3);
        p.mem.internal()[0] = "push %rbp";
        p.mem.internal()[1] = "mov %rsp, %rdi";
        p.mem.internal()[2] = "pop   %rbp";

        return p;
    }

    public static void execute(String instr) {}

    public boolean execute() {
        if (rip == mem.internal().length) {
            return false;
        }
        Processor.execute(mem.fetch(rip++));
        return true;
    }

}

