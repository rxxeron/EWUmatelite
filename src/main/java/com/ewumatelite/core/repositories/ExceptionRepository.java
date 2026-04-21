package com.ewumatelite.core.repositories;
import com.ewumatelite.core.config.SupabaseConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class ExceptionRepository {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    private HttpRequest.Builder buildAuthenticatedRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + SupabaseConfig.currentUserToken);
    }
    public JSONArray fetchExceptions(String uid) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/schedule_exceptions?user_id=eq." + uid;
        HttpRequest request = buildAuthenticatedRequest(url).GET().build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() == 200) {
            return new JSONArray(response.body());
        }
        return new JSONArray();
    }
    public void addCancellation(String uid, String date, String courseCode, boolean pendingMakeup) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/schedule_exceptions";
        JSONObject payload = new JSONObject();
        payload.put("user_id", uid);
        payload.put("type", "cancel");
        payload.put("date", date);
        payload.put("course_code", courseCode);
        JSONObject metadata = new JSONObject();
        metadata.put("pendingMakeup", pendingMakeup);
        payload.put("metadata", metadata);
        HttpRequest request = buildAuthenticatedRequest(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
    public void removeException(String exceptionId) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/schedule_exceptions?id=eq." + exceptionId;
        HttpRequest request = buildAuthenticatedRequest(url).DELETE().build();
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
    public void addManualClass(String uid, String date, String courseCode, String courseName, String startTime, String endTime, String room, String faculty) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/schedule_exceptions";
        JSONObject payload = new JSONObject();
        payload.put("user_id", uid);
        payload.put("type", "manual");
        payload.put("date", date);
        payload.put("course_code", courseCode);
        payload.put("course_name", courseName);
        payload.put("start_time", startTime);
        payload.put("end_time", endTime);
        payload.put("room", room);
        payload.put("faculty", faculty);
        HttpRequest request = buildAuthenticatedRequest(url)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
        httpClient.send(request, HttpResponse.BodyHandlers.ofString());
    }
}
