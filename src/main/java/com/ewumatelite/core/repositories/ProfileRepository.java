package com.ewumatelite.core.repositories;
import com.ewumatelite.core.config.SupabaseConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class ProfileRepository {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private String getAuthToken() {
        return SupabaseConfig.currentUserToken != null ? SupabaseConfig.currentUserToken : SupabaseConfig.ANON_KEY;
    }
    public JSONObject getProfile(String uid) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/profiles?id=eq." + uid + "&select=*&limit=1";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + getAuthToken())
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400 || !response.body().trim().startsWith("[")) {
            throw new RuntimeException("API GET Profile Failed: " + response.body());
        }
        JSONArray jsonArray = new JSONArray(response.body());
        if (jsonArray.length() > 0) {
            return jsonArray.getJSONObject(0);
        }
        return null;
    }
    public boolean updateProfileField(String uid, String field, String value) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/profiles?id=eq." + uid;
        JSONObject payload = new JSONObject();
        payload.put(field, value);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + getAuthToken())
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() >= 200 && response.statusCode() < 300;
    }
    public int getCoursesDone(String uid) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/semester_summaries?user_id=eq." + uid + "&select=courses";
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + getAuthToken())
                .header("Accept", "application/json")
                .GET()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) return 0;
        JSONArray arr = new JSONArray(response.body());
        int count = 0;
        for (int i = 0; i < arr.length(); i++) {
            JSONArray courses = arr.getJSONObject(i).optJSONArray("courses");
            if (courses != null) {
                count += courses.length();
            }
        }
        return count;
    }
    public boolean updatePassword(String newPassword) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/auth/v1/user";
        JSONObject payload = new JSONObject();
        payload.put("password", newPassword);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + getAuthToken())
                .header("Content-Type", "application/json")
                .method("PUT", HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        return response.statusCode() >= 200 && response.statusCode() < 300;
    }
}
