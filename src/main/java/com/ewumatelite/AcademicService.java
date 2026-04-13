package com.ewumatelite;

import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * Maps exactly to Flutter's Riverpod Providers and Repositories via PostgREST
 */
public class AcademicService {
    private final HttpClient httpClient = HttpClient.newHttpClient();

    // 0. Get Programs specifically like the Flutter ProgramSelectionScreen
    public JSONArray getPrograms() throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/programs?select=program_code,name,department_name,track";
        HttpRequest request = buildGetRequest(url);
        return executeGetArray(request);
    }

    // 1. Exactly mimics `activeSemesterRepository.getActiveSemester('tri_semester')` Let's fetch current active semester
    public String getActiveSemester(String trackType) throws Exception {
        String safeTrack = trackType == null ? "tri_semester" : trackType;
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/active_semester?track=eq." + safeTrack + "&select=current_semester_code&limit=1";
        
        HttpRequest request = buildGetRequest(url);

        JSONArray jsonArray = executeGetArray(request);
        
        if (jsonArray.length() > 0) {
            return jsonArray.getJSONObject(0).getString("current_semester_code"); // Example: "Fall 2026"
        }
        throw new RuntimeException("Active semester not found for " + safeTrack + " track.");
    }

    // 2. Exactly mimics CourseRepository fetching metadata
    public JSONArray getCourseMetadata() throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/course_metadata?select=code,name";
        HttpRequest request = buildGetRequest(url);
        return executeGetArray(request);
    }

    // 3. Exactly mimics the fallback dynamic table query `supabase.from(tableName).select()` for a specific course
    public JSONArray getSectionsForCourse(String currentSemesterCode, String targetCourseCode) throws Exception {
        String safeSem = currentSemesterCode.toLowerCase().replaceAll("[ _]", "");
        String tableName = "courses_" + safeSem; // Example: courses_spring2026
        
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/" + tableName + "?course_code=eq." + targetCourseCode.replaceAll(" ", "%20") + "&select=id,section_number,faculty_initials,schedule_data";
        HttpRequest request = buildGetRequest(url);
        
        return executeGetArray(request); // Returns a list of sections matching this specific course
    }

    // 4. Exactly mimics `scheduleRepositoryProvider.insert()` to place the final enrollment!
    public void pushEnrollment(String userId, String sanitizedSemesterCode, String courseCode, String sectionId, String sectionNumber) throws Exception {
        JSONObject payload = new JSONObject();
        payload.put("user_id", userId);
        payload.put("semester_code", sanitizedSemesterCode);
        payload.put("course_code", courseCode);
        payload.put("section_id", sectionId); // The UUID from dynamic courses table
        payload.put("section", sectionNumber); // The text section (e.g. "1")
        payload.put("status", "enrolled");

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SupabaseConfig.PROJECT_URL + "/rest/v1/enrollments"))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + getAuthToken())
                .header("Content-Type", "application/json")
                .header("Prefer", "return=representation") // Ask for full inserted object back, just like Flutter upsert
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();

        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new RuntimeException("Enrollment Failed: " + response.body());
        }
    }

    // Helper for clean syntax
    private String getAuthToken() {
        return SupabaseConfig.currentUserToken != null ? SupabaseConfig.currentUserToken : SupabaseConfig.ANON_KEY;
    }

    private HttpRequest buildGetRequest(String url) {
        return HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + getAuthToken())
                .header("Accept", "application/json")
                .GET()
                .build();
    }
    
    // Triggers the Edge Function to generate the schedule cache
    public void syncSchedule(String uid, String semesterCode) {
        try {
            String safeCode = semesterCode.replace(" ", "").replace("_", "");
            String url = SupabaseConfig.PROJECT_URL + "/functions/v1/sync-schedule";
            JSONObject body = new JSONObject();
            body.put("user_id", uid);
            body.put("semester_code", safeCode);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Content-Type", "application/json")
                    .header("apikey", SupabaseConfig.ANON_KEY)
                    .POST(HttpRequest.BodyPublishers.ofString(body.toString()))
                    .build();
                    
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() >= 400) {
                System.err.println("Sync Schedule Error: " + response.body());
            }
        } catch (Exception e) {
            System.err.println("Failed to call sync-schedule: " + e.getMessage());
        }
    }
    
    // Exactly maps the Flutter Dashboard logic fetching:
    // 1. Weekly Grid from user_semester_states
    // 2. Schedule Exceptions
    // 3. Top Tasks
    // 4. Enrollments (for the modal dropdown equivalent)
    public JSONObject getDashboardData(String uid, String semesterCode, String dateStr) throws Exception {
        JSONObject dashboardData = new JSONObject();
        
        // 1. User Semester States (Weekly Grid)
        String stateUrl = SupabaseConfig.PROJECT_URL + "/rest/v1/user_semester_states" +
                "?user_id=eq." + uid + "&semester_code=eq." + semesterCode + "&select=weekly_grid_cache&limit=1";
        try {
            JSONArray stateArr = executeGetArray(buildGetRequest(stateUrl));
            if (stateArr.length() > 0) {
                dashboardData.put("weekly_grid", stateArr.getJSONObject(0).optJSONObject("weekly_grid_cache"));
            }
        } catch (Exception e) {
            System.err.println("Dashboard states fetch skipped: " + e.getMessage());
        }

        // 2. Schedule Exceptions
        String exUrl = SupabaseConfig.PROJECT_URL + "/rest/v1/schedule_exceptions" +
                "?user_id=eq." + uid + "&date=eq." + dateStr;
        try {
            JSONArray exArr = executeGetArray(buildGetRequest(exUrl));
            dashboardData.put("exceptions", exArr);
        } catch (Exception e) {
             System.err.println("Dashboard exceptions fetch skipped: " + e.getMessage());
        }

        // 3. Active Tasks
        String tasksUrl = SupabaseConfig.PROJECT_URL + "/rest/v1/tasks" +
                "?user_id=eq." + uid + "&is_completed=eq.false&limit=5";
        try {
            JSONArray tasksArr = executeGetArray(buildGetRequest(tasksUrl));
            dashboardData.put("tasks", tasksArr);
        } catch (Exception e) {
             System.err.println("Dashboard tasks fetch skipped: " + e.getMessage());
        }
        
        // 4. Enrollments (For Modal Dropdown Mirror)
        String enrUrl = SupabaseConfig.PROJECT_URL + "/rest/v1/enrollments" +
                "?user_id=eq." + uid + "&semester_code=eq." + semesterCode + "&select=course_code";
        try {
            JSONArray enrArr = executeGetArray(buildGetRequest(enrUrl));
            dashboardData.put("enrollments", enrArr);
        } catch (Exception e) {
             System.err.println("Dashboard env fetch skipped: " + e.getMessage());
        }

        return dashboardData;
    }
    
    // --- Task Manager Methods (Copying Flutter's TaskRepository) ---
    
    public JSONArray getTasks(String uid) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/tasks?user_id=eq." + uid + "&order=due_date.asc";
        return executeGetArray(buildGetRequest(url));
    }

    public void createTask(String uid, String title, String courseCode, String dateStr, String type) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/tasks";
        JSONObject task = new JSONObject();
        task.put("user_id", uid);
        task.put("title", title);
        task.put("course_code", courseCode);
        if (dateStr != null && !dateStr.isEmpty()) {
            task.put("due_date", dateStr + "T23:59:59Z"); // Adding mock time
        }
        task.put("type", type);
        task.put("is_completed", false);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + getAuthToken())
                .POST(HttpRequest.BodyPublishers.ofString(task.toString()))
                .build();
                
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) throw new RuntimeException("Task Creation Failed: " + response.body());
    }

    public void updateTaskStatus(String taskId, boolean isCompleted) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/tasks?id=eq." + taskId;
        JSONObject update = new JSONObject();
        update.put("is_completed", isCompleted);

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + getAuthToken())
                .method("PATCH", HttpRequest.BodyPublishers.ofString(update.toString()))
                .build();
                
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) throw new RuntimeException("Task Update Failed: " + response.body());
    }

    public void deleteTask(String taskId) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/tasks?id=eq." + taskId;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + getAuthToken())
                .DELETE()
                .build();
                
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) throw new RuntimeException("Task Delete Failed: " + response.body());
    }
    
    private JSONArray executeGetArray(HttpRequest request) throws Exception {
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400 || !response.body().trim().startsWith("[")) {
            throw new RuntimeException("API GET Array Failed (" + response.statusCode() + "): " + response.body());
        }
        return new JSONArray(response.body());
    }
}
