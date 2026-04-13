package com.ewumatelite.core.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

// Uses Abstract Class, Generics <T>, and File I/O mapping Association with Exportable
public abstract class BaseFileExporter<T> implements Exportable<T> {
    
    private String filename; // Encapsulation

    public BaseFileExporter(String filename) {
        this.filename = filename;
    }

    // Encapsulation - Getter & Setter
    public String getFilename() {
        return filename;
    }
    
    public void setFilename(String filename) {
        this.filename = filename;
    }

    // Abstract method forcing concrete child classes to determine formatting (Polymorphism)
    public abstract String formatData(T data);

    // Method bringing File I/O + proper Exception Handling
    public void writeToFile(T data) {
        File file = new File(getFilename());
        // try-with-resources to manage close automatically
        try (FileWriter writer = new FileWriter(file, true)) { // true = append mode
            writer.write(formatData(data) + "\n");
        } catch (IOException e) {
            System.err.println("Exception handling caught an error writing to file " + filename + " -> " + e.getMessage());
        }
    }
}