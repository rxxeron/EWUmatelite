package com.ewumatelite.core.utils;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
public abstract class BaseFileExporter<T> implements Exportable<T> {
    private String filename; 
    public BaseFileExporter(String filename) {
        this.filename = filename;
    }
    public String getFilename() {
        return filename;
    }
    public void setFilename(String filename) {
        this.filename = filename;
    }
    public abstract String formatData(T data);
    public void writeToFile(T data) {
        File file = new File(getFilename());
        try (FileWriter writer = new FileWriter(file, true)) { 
            writer.write(formatData(data) + "\n");
        } catch (IOException e) {
            System.err.println("Exception handling caught an error writing to file " + filename + " -> " + e.getMessage());
        }
    }
}