package com.example.project3.core;

/* Implementors of this interface are cursors into either some general purpose
   memory location or register. */
public interface Cursor {
    char read();
    void write(char value);
}