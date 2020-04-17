package src.processor;

public class Memory {
    
    private int[] m;

    public Memory(int n_instructions) {
        m = new int[n_instructions];
    }

    public int size() {
        return m.length;
    }

    public int fetch(int idx) {
        return m[idx];
    }

    public void set(int idx, int val) {
        m[idx] = val;
    }
}


