package src.instructions;

import src.processor.Processor;

public interface Instruction {

    /*
     * called by aspects implementing this instruction to execute the
     * instruction, modifying processor state
     */
    void execute(Processor p, int encoding);

}

