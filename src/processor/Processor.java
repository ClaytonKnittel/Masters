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
            xregs[i] = new Register(0);
        }
        pc = new Register();
        pc.set(0);
        mem = new Memory(mem_size);
    }

    public static Processor make_simple() {
        Processor p = new Processor(3);
        // lui x2, 0x1
        p.mem.set(0, 0x00100137);
        // add x3, x1, x2
        p.mem.set(1, 0x002081b3);
        p.mem.set(2, 0x002081b3);
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
            System.out.printf("\tx%d:\t0x%08x\n", i, xregs[i].get());
        }
        System.out.println("pc:\t" + pc.get());
    }

}

