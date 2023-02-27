package com.example.project3.core;

/* Gamepad interface */
public class Controller implements MemoryMappable {
    public enum Button {
        RIGHT(1),
        LEFT(2),
        UP(4),
        DOWN(8),
        A(16),
        B(32),
        SELECT(64),
        START(128);

        private final byte buttonCode;
        Button(int code) {
            buttonCode = (byte)code;
        }

        public byte getButtonCode() {
            return buttonCode;
        }
    }


    private byte state;
    private boolean readingDpad, readingButtons;
    private GameBoy gb;

    public Controller(GameBoy gb) {
        state = (byte)0xFF;  // No buttons pressed
        readingDpad = false;
        readingButtons = false;
        this.gb = gb;
    }

    public synchronized void updateButton(Button button, boolean pressed) {
        // 0 = pressed
        byte code = button.getButtonCode();
        byte combinedState = (byte)((state >>> 4) & (state & 0xF));
        state = pressed ? (byte)(state & ~code) : (byte)(state | code);

        /* Button transitioned from released -> pressed and game is checking for it
           Doesn't fire if the newly pressed button/direction's corresponding
           button/direction is already pressed (e.g., right/a) */
        if (((readingDpad && code <= 8) || (readingButtons && code > 8)) &&
                (combinedState & code) == 1 && pressed) {
            gb.cpu.raiseInterrupt(CPU.Interrupt.JOYPAD);
        }
    }

    @Override
    public synchronized byte read(char address) {

        if (address == 0xFF00) {
            if (readingDpad)
                return (byte)(state & 0xF);
            else if (readingButtons)
                return (byte)(state >>> 4);
            else
                return (byte)0xFF;
        }
        throw new IllegalArgumentException(String.format("Invalid controller I/O read address ($%04X)", (int)address));
    }

    @Override
    public synchronized void write(char address, byte value) {

        if (address == 0xFF00) {
            readingDpad = ((value & 0x10) == 0);
            readingButtons = ((value & 0x20) == 0);
        }
        else if (address > 0x7FFF)
            throw new IllegalArgumentException(String.format("Invalid controller I/O write address ($%04X)", (int)address));
    }
}