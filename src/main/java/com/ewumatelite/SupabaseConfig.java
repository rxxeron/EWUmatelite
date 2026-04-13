package com.ewumatelite;

public class SupabaseConfig {
    // We will use your explicit Dev environment variables here.
    // Replace THESE with your actual Dev Project URL & Anon Key from the dashboard!
    public static final String PROJECT_URL = "https://leofdzbobcekvembjuks.supabase.co"; 
    public static final String ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6Imxlb2ZkemJvYmNla3ZlbWJqdWtzIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzU5ODkwMzIsImV4cCI6MjA5MTU2NTAzMn0.N8bXAbwgGaOWzHNk0Wlks_KN13kXqlBpkwvpXnyBg6o"; 
    
    // Store the authenticated user's JWT
    public static String currentUserToken = null;
}
