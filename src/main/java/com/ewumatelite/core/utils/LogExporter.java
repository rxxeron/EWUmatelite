package com.ewumatelite.core.utils;

import java.time.LocalDateTime;

// Subclass demonstrating Inheritance and Polymorphism against Abstract Parent
public class LogExporter extends BaseFileExporter<String> {

    // Global static log helper for the entire application to use easily
    private static final String DEFAULT_LOG_PATH = System.getProperty("user.home") + "/Documents/EwuMateLite_activity.log";

    public static void log(String message) {
        new LogExporter(DEFAULT_LOG_PATH).writeToFile(message);
    }

    public LogExporter(String filename) {
        super(filename);
    }

    // Overridden methods mapping Polymorphism
    @Override
    public String extractData() {
        return "Log extraction mode";
    }

    @Override
    public String formatData(String data) {
        // Appends current timestamp string format internally into the data
        return "[LOG - " + LocalDateTime.now() + "] " + data;
    }
}