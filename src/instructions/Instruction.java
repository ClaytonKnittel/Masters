package src.instruction;

import src.processor.Processor;

interface Instruction {
    
    void execute(Processor p);
}

