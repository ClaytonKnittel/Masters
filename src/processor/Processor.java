package src.processor;


import src.processor.Memory;

public class Processor {

    public static final int N_REGS = 32;

    protected Register xregs[];
    protected Register pc;

    // flag to be set when an instruction match has been found in a cycle
    protected boolean foundInstruction;

    protected Memory mem;

    public Processor(String file_name) {
        xregs = new Register[N_REGS];

        // first register is 0 register
        xregs[0] = new ZeroRegister();
        for (int i = 1; i < xregs.length; i++) {
            xregs[i] = new Register(0);
        }
        pc = new Register();
        pc.set(0);

        foundInstruction = false;

        mem = new Memory(file_name);
    }

    public Memory getMem() {
        return mem;
    }

    public Register getRegisterByIndex(int idx) {
        return xregs[idx];
    }

    public int getPC() {
        return pc.get();
    }

    // absolute jump
    public void setPC(int val) {
        pc.set(val);
    }

    // relative jump
    public void setPCRelative(int offset) {
        pc.set(pc.get() + offset);
    }

    /**
     * to be called whenever an intruction has matched to an opcode
     */
    public void foundInstructionMatch() {
        foundInstruction = true;
    }

    public void execute(int instruction) {}

    public boolean execute() {
        if (pc.get() >= mem.size()) {
            return false;
        }
        int instr = mem.loadW(pc.get());

        foundInstruction = false;
        execute(instr);

        if (!foundInstruction) {
            // TODO no instruction matched (for now just ignore)
            pc.set(pc.get() + 4);
        }

        return pc.get() < mem.size();
    }

    public void printState() {
        for (int i = 0; i < xregs.length; i++) {
            System.out.printf("\tx%d:\t0x%08x\n", i, xregs[i].get());
        }
        System.out.println("pc:\t" + pc.get());
    }

}

