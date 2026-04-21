package com.ewumatelite.core.repositories;
import com.ewumatelite.core.config.SupabaseConfig;
import org.json.JSONArray;
import org.json.JSONObject;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
public class AcademicRepository {
    private final HttpClient httpClient = HttpClient.newHttpClient();
    public JSONArray getPrograms() throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/programs?select=program_code,name,department_name,track";
        HttpRequest request = buildGetRequest(url);
        return executeGetArray(request);
    }
    public String getUpcomingSemester(String trackType) throws Exception {
        String safeTrack = trackType == null ? "tri_semester" : trackType;
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/active_semester?track=eq." + safeTrack + "&select=next_semester_code&limit=1";
        HttpRequest request = buildGetRequest(url);
        JSONArray jsonArray = executeGetArray(request);
        if (jsonArray.length() > 0) {
            return jsonArray.getJSONObject(0).getString("next_semester_code");
        }
        return null;
    }
    public String getActiveSemester(String trackType) throws Exception {
        String safeTrack = trackType == null ? "tri_semester" : trackType;
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/active_semester?track=eq." + safeTrack + "&select=current_semester_code&limit=1";
        HttpRequest request = buildGetRequest(url);
        JSONArray jsonArray = executeGetArray(request);
        if (jsonArray.length() > 0) {
            return jsonArray.getJSONObject(0).getString("current_semester_code"); 
        }
        throw new RuntimeException("Active semester not found for " + safeTrack + " track.");
    }
    public JSONObject getScheduleGeneration(String genId) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/schedule_generations?id=eq." + genId + "&limit=1";
        HttpRequest request = buildGetRequest(url);
        JSONArray jsonArray = executeGetArray(request);
        if (jsonArray.length() > 0) {
            return jsonArray.getJSONObject(0);
        }
        return null;
    }
    public JSONArray getCourseMetadata() throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/course_metadata?select=code,name";
        HttpRequest request = buildGetRequest(url);
        return executeGetArray(request);
    }
    public JSONArray getSectionsForCourse(String currentSemesterCode, String targetCourseCode) throws Exception {
        String safeSem = currentSemesterCode.toLowerCase().replaceAll("[ _]", "");
        String tableName = "courses_" + safeSem; 
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/" + tableName + "?course_code=eq." + targetCourseCode.replaceAll(" ", "%20") + "&select=id,section_number,faculty_initials,schedule_data";
        HttpRequest request = buildGetRequest(url);
        return executeGetArray(request); 
    }
    public void pushEnrollment(String userId, String sanitizedSemesterCode, String courseCode, String sectionId, String sectionNumber) throws Exception {
        JSONObject payload = new JSONObject();
        payload.put("user_id", userId);
        payload.put("semester_code", sanitizedSemesterCode);
        payload.put("course_code", courseCode);
        payload.put("section_id", sectionId); 
        payload.put("section", sectionNumber); 
        payload.put("status", "enrolled");
        System.out.println("JSON PAYLOAD: " + payload.toString());
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(SupabaseConfig.PROJECT_URL + "/rest/v1/enrollments"))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + getAuthToken())
                .header("Content-Type", "application/json")
                .header("Prefer", "return=representation") 
                .POST(HttpRequest.BodyPublishers.ofString(payload.toString()))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new RuntimeException("Enrollment Failed: " + response.body());
        }
        String pUrl = SupabaseConfig.PROJECT_URL + "/rest/v1/profiles?id=eq." + userId + "&select=enrolled_sections";
        JSONArray pArray = executeGetArray(buildGetRequest(pUrl));
        if (pArray.length() > 0) {
            JSONObject p = pArray.getJSONObject(0);
            JSONArray sections = p.optJSONArray("enrolled_sections");
            if (sections == null) sections = new JSONArray();
            sections.put(sectionId);
            JSONObject upLoad = new JSONObject();
            upLoad.put("enrolled_sections", sections);
            HttpRequest upReq = HttpRequest.newBuilder()
                .uri(URI.create(SupabaseConfig.PROJECT_URL + "/rest/v1/profiles?id=eq." + userId))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + getAuthToken())
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(upLoad.toString()))
                .build();
            httpClient.send(upReq, HttpResponse.BodyHandlers.ofString());
        }
        JSONObject cacheClear = new JSONObject();
        cacheClear.put("weekly_grid_cache", JSONObject.NULL);
        HttpRequest cacheReq = HttpRequest.newBuilder()
            .uri(URI.create(SupabaseConfig.PROJECT_URL + "/rest/v1/user_semester_states?user_id=eq." + userId + "&semester_code=eq." + sanitizedSemesterCode))
            .header("apikey", SupabaseConfig.ANON_KEY)
            .header("Authorization", "Bearer " + getAuthToken())
            .header("Content-Type", "application/json")
            .method("PATCH", HttpRequest.BodyPublishers.ofString(cacheClear.toString()))
            .build();
        httpClient.send(cacheReq, HttpResponse.BodyHandlers.ofString());
    }
    public JSONArray getUserEnrollments(String userId, String semesterCode) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/enrollments?user_id=eq." + userId + "&semester_code=eq." + semesterCode + "&select=section_id,course_code";
        HttpRequest request = buildGetRequest(url);
        return executeGetArray(request);
    }
    public void dropEnrollment(String userId, String semesterCode, String sectionId) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/enrollments?user_id=eq." + userId + "&semester_code=eq." + semesterCode + "&section_id=eq." + sectionId;
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + getAuthToken())
                .DELETE()
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) {
            throw new RuntimeException("Drop Failed: " + response.body());
        }
        String pUrl = SupabaseConfig.PROJECT_URL + "/rest/v1/profiles?id=eq." + userId + "&select=enrolled_sections";
        JSONArray pArray = executeGetArray(buildGetRequest(pUrl));
        if (pArray.length() > 0) {
            JSONObject p = pArray.getJSONObject(0);
            JSONArray sections = p.optJSONArray("enrolled_sections");
            if (sections != null) {
                JSONArray newSections = new JSONArray();
                for (int i=0; i<sections.length(); i++) {
                    if (!sections.getString(i).equals(sectionId)) {
                        newSections.put(sections.getString(i));
                    }
                }
                JSONObject upLoad = new JSONObject();
                upLoad.put("enrolled_sections", newSections);
                HttpRequest upReq = HttpRequest.newBuilder()
                    .uri(URI.create(SupabaseConfig.PROJECT_URL + "/rest/v1/profiles?id=eq." + userId))
                    .header("apikey", SupabaseConfig.ANON_KEY)
                    .header("Authorization", "Bearer " + getAuthToken())
                    .header("Content-Type", "application/json")
                    .method("PATCH", HttpRequest.BodyPublishers.ofString(upLoad.toString()))
                    .build();
                httpClient.send(upReq, HttpResponse.BodyHandlers.ofString());
            }
        }
        JSONObject cacheClear = new JSONObject();
        cacheClear.put("weekly_grid_cache", JSONObject.NULL);
        HttpRequest cacheReq = HttpRequest.newBuilder()
            .uri(URI.create(SupabaseConfig.PROJECT_URL + "/rest/v1/user_semester_states?user_id=eq." + userId + "&semester_code=eq." + semesterCode.replace(" ", "%20")))
            .header("apikey", SupabaseConfig.ANON_KEY)
            .header("Authorization", "Bearer " + getAuthToken())
            .header("Content-Type", "application/json")
            .method("PATCH", HttpRequest.BodyPublishers.ofString(cacheClear.toString()))
            .build();
        httpClient.send(cacheReq, HttpResponse.BodyHandlers.ofString());
    }
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
    public JSONArray fetchUpcomingHolidays(String semesterCode, String startDate, String endDate) throws Exception {
        String safeSemesterCode = semesterCode.toLowerCase().replace(" ", "");
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/calendar_" + safeSemesterCode +
                "?event_date=gte." + startDate + "&event_date=lte." + endDate;
        try {
            return executeGetArray(buildGetRequest(url));
        } catch (Exception e) {
            System.err.println("Holiday fetch skipped: " + e.getMessage());
            return new JSONArray();
        }
    }
    public JSONObject getDashboardData(String uid, String semesterCode, String dateStr) throws Exception {
        JSONObject dashboardData = new JSONObject();
        String safeSemesterCode = semesterCode.replace(" ", "%20");
        String stateUrl = SupabaseConfig.PROJECT_URL + "/rest/v1/user_semester_states" +
                "?user_id=eq." + uid + "&semester_code=eq." + safeSemesterCode + "&select=weekly_grid_cache&limit=1";
        try {
            JSONArray stateArr = executeGetArray(buildGetRequest(stateUrl));
            if (stateArr.length() > 0) {
                dashboardData.put("weekly_grid", stateArr.getJSONObject(0).optJSONObject("weekly_grid_cache"));
            }
        } catch (Exception e) {
            System.err.println("Dashboard states fetch skipped: " + e.getMessage());
        }
        String exUrl = SupabaseConfig.PROJECT_URL + "/rest/v1/schedule_exceptions" +
                "?user_id=eq." + uid + "&date=eq." + dateStr;
        try {
            JSONArray exArr = executeGetArray(buildGetRequest(exUrl));
            dashboardData.put("exceptions", exArr);
        } catch (Exception e) {
             System.err.println("Dashboard exceptions fetch skipped: " + e.getMessage());
        }
        String holidayUrl = SupabaseConfig.PROJECT_URL + "/rest/v1/calendar_" + semesterCode.toLowerCase().replace(" ", "") +
                "?event_date=eq." + dateStr + "&limit=1";
        try {
            JSONArray holidayArr = executeGetArray(buildGetRequest(holidayUrl));
            if (holidayArr.length() > 0) {
                dashboardData.put("holiday", holidayArr.getJSONObject(0));
            }
        } catch (Exception e) {
            System.err.println("Holiday fetch skipped (table might not exist): " + e.getMessage());
        }
        String tasksUrl = SupabaseConfig.PROJECT_URL + "/rest/v1/tasks" +
                "?user_id=eq." + uid + "&is_completed=eq.false&limit=5";
        try {
            JSONArray tasksArr = executeGetArray(buildGetRequest(tasksUrl));
            dashboardData.put("tasks", tasksArr);
        } catch (Exception e) {
             System.err.println("Dashboard tasks fetch skipped: " + e.getMessage());
        }
        String pUrl = SupabaseConfig.PROJECT_URL + "/rest/v1/profiles?id=eq." + uid + "&select=nickname&limit=1";
        try {
            JSONArray pArr = executeGetArray(buildGetRequest(pUrl));
            if (pArr.length() > 0) {
                dashboardData.put("nickname", pArr.getJSONObject(0).optString("nickname"));
            }
        } catch (Exception e) {}
        return dashboardData;
    }
    public JSONArray getTasks(String uid) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/tasks?user_id=eq." + uid + "&order=due_date.asc";
        return executeGetArray(buildGetRequest(url));
    }
    public void createTask(String uid, String title, String courseCode, String dateStr, String type, String semesterCode) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/tasks";
        JSONObject task = new JSONObject();
        task.put("user_id", uid);
        task.put("title", title);
        task.put("course_code", courseCode);
        if (dateStr != null && !dateStr.isEmpty()) {
            if (!dateStr.contains("T")) {
                dateStr += "T23:59:59Z";
            }
            task.put("due_date", dateStr);
        }
        task.put("type", type);
        task.put("is_completed", false);
        if (semesterCode != null && !semesterCode.isEmpty()) {
            task.put("semester_code", semesterCode);
        }
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
    public void updateFullTask(String taskId, String uid, String title, String courseCode, String dateStr, String type, String semesterCode) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/tasks?id=eq." + taskId;
        JSONObject task = new JSONObject();
        task.put("title", title);
        task.put("course_code", courseCode);
        if (dateStr != null && !dateStr.isEmpty()) {
            if (!dateStr.contains("T")) {
                dateStr += "T23:59:59Z";
            }
            task.put("due_date", dateStr);
        } else {
            task.put("due_date", JSONObject.NULL);
        }
        task.put("type", type);
        if (semesterCode != null && !semesterCode.isEmpty()) {
            task.put("semester_code", semesterCode);
        }
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + getAuthToken())
                .method("PATCH", HttpRequest.BodyPublishers.ofString(task.toString()))
                .build();
        HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        if (response.statusCode() >= 400) throw new RuntimeException("Task Update Failed: " + response.body());
    }
    public JSONArray getSemesterProgressData(String userId, String semesterCode) throws Exception {
        String url = SupabaseConfig.PROJECT_URL + "/rest/v1/v_semester_progress?user_id=eq." + userId + "&semester_code=eq." + semesterCode;
        return executeGetArray(buildGetRequest(url));
    }
    public void saveCourseMarks(String userId, String semesterCode, JSONObject data) throws Exception {
        boolean isNew = data.optBoolean("is_new", false);
        data.remove("is_new");
        data.remove("total_obtained");
        data.remove("total_distributed");
        data.remove("id");
        data.remove("course_name"); 
        data.remove("credits");
        data.remove("status");
        data.put("user_id", userId);
        data.put("semester_code", semesterCode);
        data.put("updated_at", java.time.Instant.now().toString());
        if (isNew) {
            String url = SupabaseConfig.PROJECT_URL + "/rest/v1/semester_course_marks";
            HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + getAuthToken())
                .POST(HttpRequest.BodyPublishers.ofString(data.toString()))
                .build();
            HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() >= 400) throw new RuntimeException("Save Marks Failed: " + res.body());
        } else {
            String encodedCourseCode = data.getString("course_code").replace(" ", "%20");
            String url = SupabaseConfig.PROJECT_URL + "/rest/v1/semester_course_marks?user_id=eq." + userId + "&semester_code=eq." + semesterCode + "&course_code=eq." + encodedCourseCode;
            HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .header("apikey", SupabaseConfig.ANON_KEY)
                .header("Authorization", "Bearer " + getAuthToken())
                .method("PATCH", HttpRequest.BodyPublishers.ofString(data.toString()))
                .build();
            HttpResponse<String> res = httpClient.send(req, HttpResponse.BodyHandlers.ofString());
            if (res.statusCode() >= 400) throw new RuntimeException("Update Marks Failed: " + res.body());
        }
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
