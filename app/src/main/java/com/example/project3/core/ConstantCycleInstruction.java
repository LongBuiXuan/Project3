package com.example.project3.core;

/* Used to override the cycle value returned for an instruction */
public class ConstantCycleInstruction extends InstructionForm {
    private int cycles;
    public ConstantCycleInstruction(InstructionRoot root, Cursor[] operandTemplate, int cycles) {
        super(root, operandTemplate);
        this.cycles = cycles;
    }

    @Override
    public int execute(CPU cpu) {
        super.execute(cpu);
        return cycles;
    }
}