package com.example.project3.core;

public interface InstructionRoot {
    /* Execute the instruction and return the number of clock cycles used.
        */
    int execute(CPU cpu, Cursor[] operands);
}
