package com.ewumatelite.core.config;

public class SupabaseConfig {
    // We will use your explicit Dev environment variables here.
    // Replace THESE with your actual Dev Project URL & Anon Key from the dashboard!
    public static final String PROJECT_URL = "https://leofdzbobcekvembjuks.supabase.co"; 
    public static final String ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imxlb2ZkemJvYmNla3ZlbWJqdWtzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzU5ODkwMzIsImV4cCI6MjA5MTU2NTAzMn0.N8bXAbwgGaOWzHNk0Wlks_KN13kXqlBpkwvpXnyBg6o"; 
    public static final String SERVICE_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imxlb2ZkemJvYmNla3ZlbWJqdWtzIiwicm9sZSI6InNlcnZpY2Vfcm9sZSIsImlhdCI6MTc3NTk4OTAzMiwiZXhwIjoyMDkxNTY1MDMyfQ.AR04ATAop2l88Q5Bj1EsEHXPiYBE82v0H70piNGMzlQ"; 
    // Store the authenticated user's JWT
    public static String currentUserToken = null;
    public static String currentUserId = null;

    // Load persisted token on startup
    static {
        java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(SupabaseConfig.class);
        currentUserToken = prefs.get("SUPABASE_JWT", null);
        currentUserId = prefs.get("SUPABASE_UID", null);
    }
}


