package com.ewumatelite.core.utils;

public class PlatformUtils {
    
    /**
     * Checks if the application is running in forced mobile mode.
     * Deprecated in favor of fixed desktop sizing.
     */
    public static boolean isMobileMode() {
        return false;
    }

    /**
     * Helper to get the recommended screen width.
     */
    public static double getScreenWidth() {
        return 1100;
    }

    /**
     * Helper to get the recommended screen height.
     */
    public static double getScreenHeight() {
        return 800;
    }
}
