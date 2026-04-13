package com.ewumatelite;

import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Maps exactly to Flutter's AuthRepository (client.auth.signUp)
 */
public class AuthService {
    private final HttpClient httpClient = HttpClient.newHttpClient();

    public String registerUser(String email, String password, String fullName, String nickname, String studentId, String programCode, String departmentName, String semType) throws Exception {
        // 1. Prepare User Metadata for Auth
        JSONObject metaData = new JSONObject();
        metaData.put("fullName", fullName);
        metaData.put("displayName", nickname);

        JSONObject payload = new JSONObject();
        payload.put("email", email);
        payload.put("password", password);
        payload.put("data", metaData);

        // 2. HTTP request to Supabase Auth Endpoint
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

        // 3. Extract the newly generated Auth UUID
        JSONObject resJson = new JSONObject(response.body());
        String uid = null;
        if (resJson.has("access_token")) {
             SupabaseConfig.currentUserToken = resJson.getString("access_token");
        }
        if (resJson.has("user")) {
            uid = resJson.getJSONObject("user").getString("id"); 
        } else if (resJson.has("id")) {
            uid = resJson.getString("id");
        }

        if (uid == null) {
            throw new RuntimeException("Wait, no User ID returned from Auth.");
        }

        // 4. Update the Profiles table directly EXACTLY like Flutter does 
        // Flutter separates it into register_screen (nickname, student_id) and program_selection (program_code, dept_name).
        // For the Lite version, we'll do it in one atomic push!
        JSONObject profilePayload = new JSONObject();
        profilePayload.put("id", uid);
        profilePayload.put("full_name", fullName);
        profilePayload.put("nickname", nickname);
        profilePayload.put("student_id", studentId);
        profilePayload.put("program_code", programCode);
        profilePayload.put("department_name", departmentName);
        profilePayload.put("semester_type", semType);
        profilePayload.put("track", semType); // Flutter explicitly saves this as track too
        profilePayload.put("onboarding_status", "completed"); // Skips the onboarding loop!

        HttpRequest profileReq = HttpRequest.newBuilder()
                .uri(URI.create(SupabaseConfig.PROJECT_URL + "/rest/v1/profiles"))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + (SupabaseConfig.currentUserToken != null ? SupabaseConfig.currentUserToken : SupabaseConfig.ANON_KEY))
                .header("Content-Type", "application/json")
                .header("Prefer", "resolution=merge-duplicates") // Upsert equivalent
                .POST(HttpRequest.BodyPublishers.ofString(profilePayload.toString()))
                .build();

        HttpResponse<String> profileRes = httpClient.send(profileReq, HttpResponse.BodyHandlers.ofString());
        if (profileRes.statusCode() >= 400) {
            System.err.println("Warning: Profile Upsert Failed (Trigger might have already populated): " + profileRes.body());
        }

        return uid;
    }
}
