package src.processor;

import src.processor.Readable;

/**
 * Implements the zero register (x0), which is hard-wired to zero and ignores
 * writes to it
 */
public class ZeroRegister extends Register {

    public ZeroRegister() {
        super(0);
    }

    @Override
    public void set(Readable r) {
    }

    @Override
    public void set(int val) {
    }

}
