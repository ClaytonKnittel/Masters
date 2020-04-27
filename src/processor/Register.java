package src.processor;

import src.processor.Readable;

public class Register implements Readable {
    
    int state;


    public Register() {
        state = 0;
    }

    public Register(int val) {
        state = val;
    }

    public void set(Readable r) {
        state = r.get();
    }

    public void set(int val) {
        state = val;
    }

    public int get() {
        return state;
    }

    public long getu() {
        return ((long) state) & 0xffffffffL;
    }
}

