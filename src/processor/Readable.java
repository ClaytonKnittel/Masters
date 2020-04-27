package src.processor;

public interface Readable {

    /**
     * reads the state of the object and returns its value
     */
    int get();

    /**
     * reads the state of the object and returns its value unsigned
     */
    long getu();
}
