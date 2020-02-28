package src.processor;

public class Memory {
    
    private int[] m;

    public Memory(int n_instructions) {
        m = new int[n_instructions];

        if (n_instructions == 3) {
            // add x3, x1, x2
            m[0] = 0x002081b3;
        }
    }

    public int size() {
        return m.length;
    }

    public int fetch(int idx) {
        return m[idx];
    }
}


