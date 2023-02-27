package com.example.project3.core;

/* Implementors support graphics output */
public interface RenderTarget {
    void frameReady(int[] frameBuffer);
}