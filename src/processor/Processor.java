package src.processor;


import src.processor.Memory;

public class Processor {

    public static final int N_REGS = 32;

    protected Register xregs[];
    protected Register pc;

    protected Memory mem;

    public Processor(int mem_size) {
        xregs = new Register[N_REGS];
        for (int i = 0; i < xregs.length; i++) {
            xregs[i] = new Register(i == 0 ? 0 : 1);
        }
        pc = new Register();
        pc.set(0);
        mem = new Memory(mem_size);
    }

    public static Processor make_simple() {
        Processor p = new Processor(3);
        return p;
    }

    public Register getRegisterByIndex(int idx) {
        return xregs[idx];
    }

    public void execute(int instruction) {}

    public boolean execute() {
        if (pc.get() == mem.size()) {
            return false;
        }
        execute(mem.fetch(pc.get()));
        pc.set(pc.get() + 1);
        return true;
    }

    public void printState() {
        for (int i = 0; i < xregs.length; i++) {
            System.out.println("\tx" + i + ":\t" + xregs[i].get());
        }
        System.out.println("pc:\t" + pc.get());
    }

}

