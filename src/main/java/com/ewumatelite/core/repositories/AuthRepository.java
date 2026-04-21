package com.ewumatelite.core.repositories;
import com.ewumatelite.core.config.SupabaseConfig;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class AuthRepository {
    public boolean login(String email, String password) throws Exception {
        JSONObject payload = new JSONObject();
        payload.put("email", email);
        payload.put("password", password);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SupabaseConfig.PROJECT_URL + "/auth/v1/token?grant_type=password"))
                .header("Content-Type", "application/json")
                .header("apikey", SupabaseConfig.ANON_KEY)
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 200 && response.statusCode() < 300) {
            JSONObject resJson = new JSONObject(response.body());
            SupabaseConfig.currentUserToken = resJson.getString("access_token");
            SupabaseConfig.currentUserId = resJson.getJSONObject("user").getString("id");
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(SupabaseConfig.class);
            prefs.put("SUPABASE_JWT", SupabaseConfig.currentUserToken);
            prefs.put("SUPABASE_UID", SupabaseConfig.currentUserId);
            return true;
        } else {
            return false;
        }
    }
    private final HttpClient httpClient = HttpClient.newHttpClient();
    public String registerUser(String email, String password, String fullName, String nickname, String studentId, String programCode, String departmentName, String semType) throws Exception {
        JSONObject metaData = new JSONObject();
        metaData.put("fullName", fullName);
        metaData.put("displayName", nickname);
        JSONObject payload = new JSONObject();
        payload.put("email", email);
        payload.put("password", password);
        payload.put("data", metaData);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SupabaseConfig.PROJECT_URL + "/auth/v1/signup"))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new RuntimeException("Supabase Signup Failed: " + response.body());
        }
        JSONObject resJson = new JSONObject(response.body());
        String uid = null;
        if (resJson.has("access_token")) {
             SupabaseConfig.currentUserToken = resJson.getString("access_token");
             SupabaseConfig.currentUserId = resJson.getJSONObject("user").getString("id");
        }
        if (resJson.has("user")) {
            uid = resJson.getJSONObject("user").getString("id"); 
        } else if (resJson.has("id")) {
            uid = resJson.getString("id");
        }
        if (uid == null) {
            throw new RuntimeException("Wait, no User ID returned from Auth.");
        }
        JSONObject profilePayload = new JSONObject();
        profilePayload.put("id", uid);
        profilePayload.put("full_name", fullName);
        profilePayload.put("nickname", nickname);
        profilePayload.put("student_id", studentId);
        profilePayload.put("program_code", programCode);
        profilePayload.put("department_name", departmentName);
        profilePayload.put("semester_type", semType);
        profilePayload.put("track", semType); 
        profilePayload.put("onboarding_status", "completed"); 
        HttpRequest profileReq = HttpRequest.newBuilder()
                .uri(URI.create(SupabaseConfig.PROJECT_URL + "/rest/v1/profiles"))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + (SupabaseConfig.currentUserToken != null ? SupabaseConfig.currentUserToken : SupabaseConfig.ANON_KEY))
                .header("Content-Type", "application/json")
                .header("Prefer", "resolution=merge-duplicates") 
                .POST(HttpRequest.BodyPublishers.ofString(profilePayload.toString()))
                .build();
        HttpResponse<String> profileRes = httpClient.send(profileReq, HttpResponse.BodyHandlers.ofString());
        if (profileRes.statusCode() >= 400) {
            System.err.println("Warning: Profile Upsert Failed (Trigger might have already populated): " + profileRes.body());
        }
        return uid;
    }
}
