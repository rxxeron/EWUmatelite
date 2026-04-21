package com.ewumatelite.core.utils;
import java.time.LocalDateTime;
public class LogExporter extends BaseFileExporter<String> {
    private static final String DEFAULT_LOG_PATH = System.getProperty("user.home") + "/Documents/EwuMateLite_activity.log";
    public static void log(String message) {
        new LogExporter(DEFAULT_LOG_PATH).writeToFile(message);
    }
    public LogExporter(String filename) {
        super(filename);
    }
    @Override
    public String extractData() {
        return "Log extraction mode";
    }
    @Override
    public String formatData(String data) {
        return "[LOG - " + LocalDateTime.now() + "] " + data;
    }
}