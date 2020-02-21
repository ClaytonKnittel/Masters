package src.processor;

public class Memory {
    
    private String[] m;

    public Memory(int n_instructions) {
        m = new String[n_instructions];
    }

    public String fetch(int idx) {
        return m[idx];
    }

    public String[] internal() {
        return m;
    }
}


