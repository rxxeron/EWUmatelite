# EwuMateLite Complete Function Dictionary

This document explicitly lists **every single function** inside the major classes of the project, completely breaking down what each function specifically does.

---

## 1. Application Entry (`com.ewumatelite`)

### `Launcher.java`
*   **`public static void main(String[] args)`**
    *   **Explanation:** The absolute starting point of the packaged application. It has only one job: call `Main.main(args)`. It exists purely to prevent JavaFX from crashing when the app is bundled into a standalone `.exe` or `.jar` file.

### `Main.java`
*   **`public void start(Stage primaryStage)`**
    *   **Explanation:** This is the JavaFX lifecycle method. When the UI engine boots, it calls this function. This function: 1. Sets up a global mouse-click tracker for logging. 2. Checks `SupabaseConfig.currentUserToken`. 3. If a token exists, it launches `DashboardScreen`. If not, it launches `LoginScreen`.
*   **`public static void main(String[] args)`**
    *   **Explanation:** The standard Java entry point. It calls the built-in JavaFX `launch(args)` method to start the UI engine.

---

## 2. Core Config & Utils (`com.ewumatelite.core.*`)

### `SupabaseConfig.java`
*(This class has no functions, only `public static final` variables and a `static {}` block).*
*   **`static { ... }` (Static Initialization Block)**
    *   **Explanation:** Runs automatically once when the app starts. It reaches into the computer's OS Registry (`java.util.prefs`) to see if a session token (`SUPABASE_JWT`) was saved from a previous login, allowing the "Remember Me" functionality.

### `MarksCalculator.java`
*   **`public static double calculateQuizValue(JSONArray qArr, String strategy, int n, double maxMark)`**
    *   **Explanation:** Takes an array of student quiz marks, sorts them from highest to lowest, and applies a syllabus rule (like "best_n" or "average_all") to calculate the final quiz score. Caps the total at `maxMark` to prevent overflow errors.
*   **`public static String calculateGrade(double obtained, double distributed)`**
    *   **Explanation:** Takes the student's earned marks, divides by the total possible marks to get a percentage, and runs through an `if/else` ladder to return an American Letter Grade (e.g., A+, B-, F).

### `LogExporter.java`
*   **`public static void log(String message)`**
    *   **Explanation:** A global helper function. Any class can call this to instantly write debug text to a log file on the user's computer without needing complex setup.
*   **`public LogExporter(String filename)`**
    *   **Explanation:** The constructor. Connects the exporter to a specific file on the hard drive.
*   **`public String extractData()`**
    *   **Explanation:** Overrides a parent method to return a dummy string indicating "Log extraction mode".
*   **`public String formatData(String data)`**
    *   **Explanation:** Overrides a parent method to prepend the current system Date and Time (`[LOG - 2026-04-18...]`) before the message is saved to the file.

### `LayoutFactory.java`
*   **`public static Parent create(Stage stage, String uid, String activeSem, String screenTitle, Node content)`**
    *   **Explanation:** A UI generator function. It takes any raw screen view (like the Dashboard UI) and dynamically wraps the global generic Navigation `Sidebar` component to the left of it, returning the combined layout.

---

## 3. Repositories (Database Layer) (`com.ewumatelite.core.repositories`)

### `AuthRepository.java`
*   **`public boolean login(String email, String password)`**
    *   **Explanation:** Wraps the credentials in a JSON object, sends an HTTP POST request to Supabase's `/auth/v1/token` endpoint. If successful, extracts the JWT `access_token` and saves it to the Windows/Mac registry for persistence. Returns `true` on success.
*   **`public String registerUser(String email, String password, String fullName, String nickname, String studentId, String programCode, String departmentName, String semType)`**
    *   **Explanation:** A massive two-stage function. First, POSTs to `/auth/v1/signup` to create the secure user credential. Second, takes the newly generated User ID and fires a PATCH request to the `profiles` SQL table to save the student's personal details (Name, Major, ID). Returns the `UID` string.

### `AcademicRepository.java`
*(Contains many endpoint wrappers. Summarizing the core ones read)*
*   **`public JSONArray getPrograms()`**
    *   **Explanation:** HTTP GET to the `programs` table to fetch the list of all valid EWU university majors and departments for the registration screen.
*   **`public String getActiveSemester(String trackType)`**
    *   **Explanation:** HTTP GET to the `active_semester` table to find the string code (e.g. "Spring 2026") for the currently ongoing semester.
*   **`public JSONArray getSectionsForCourse(String currentSemesterCode, String targetCourseCode)`**
    *   **Explanation:** Reaches into dynamic semester tables (like `courses_spring2026`) and fetches the available times/professors specifically for `targetCourseCode`.
*   **`public void pushEnrollment(String userId, String sanitizedSemesterCode, String courseCode, String sectionId, String sectionNumber)`**
    *   **Explanation:** A 3-part transaction function. 1. Inserts the course into the `enrollments` table. 2. Adds the course ID to the JSON Array in the student's `profile`. 3. Sets the `weekly_grid_cache` to null so the app is forced to recalculate their visual schedule.
*   **`public void dropEnrollment(String userId, String semesterCode, String sectionId)`**
    *   **Explanation:** Fires an HTTP DELETE to remove the course from the `enrollments` table.

### `ProfileRepository.java`
*   **`public JSONObject getProfile(String uid)`**
    *   **Explanation:** Fetches all personal columns (Name, Major, Verification status) directly from the `profiles` table for the current user.
*   **`public boolean updateProfileField(String uid, String field, String value)`**
    *   **Explanation:** A reusable HTTP PATCH request that allows the app to update *any* specific column in a user's profile dynamically (e.g., changing their nickname).
*   **`public int getCoursesDone(String uid)`**
    *   **Explanation:** Fetches all `semester_summaries` for the user, loops through them, and mathematically counts how many courses the student has completed across their entire degree.

### `ExceptionRepository.java`
*   **`public JSONArray fetchExceptions(String uid)`**
    *   **Explanation:** Downloads all canceled classes or makeup classes for the student so the UI knows to cross them out.
*   **`public void addCancellation(String uid, String date, String courseCode, boolean pendingMakeup)`**
    *   **Explanation:** HTTP POSTs a new row to `schedule_exceptions` with the type "cancel", hiding this class instance from the student's schedule.

---

## 4. UI Controllers (`com.ewumatelite.features.*`)

### `LoginScreen.java` / `RegistrationScreen.java`
*   **`public void show()` (Inside both classes)**
    *   **Explanation:** JavaFX UI builders. They programmatically create text boxes, buttons, apply CSS styling, and attach `.setOnAction()` click listeners to the buttons to trigger the Repositories.

### `DashboardController.java`
*   **`public void initData(Stage stage, String uid, String activeSem)`**
    *   **Explanation:** The setup function. Calculates the "Good Morning/Evening" string. Checks if the current time is past 8:00 PM, and if so, shifts the target date forward by 1 day. Spawns a background thread to fetch dashboard JSON from `AcademicRepository`.
*   **`private void populateUI(JSONObject data, LocalDate targetDate)`**
    *   **Explanation:** Parses the JSON. Checks if `targetDate` is a university holiday; if so, draws a Holiday Banner. If not a holiday, loops through the `weekly_grid` checking against `schedule_exceptions` to draw the specific schedule cards for today.

### `SemesterProgressController.java`
*   **`public void initData(Stage stage, String uid, String activeSem)`**
    *   **Explanation:** Sets the initial screen labels and calls `loadProgressData()`.
*   **`private void loadProgressData()`**
    *   **Explanation:** Initiates an async network thread to fetch the student's entire raw grade dump for the active semester.
*   **`private void renderData(JSONArray data)`**
    *   **Explanation:** Clears the UI grid. Loops over every enrolled course in the data JSON, passing each item to `createCourseCard`.
*   **`private VBox createCourseCard(JSONObject courseData)`**
    *   **Explanation:** Extracts the scores (midterm, final, attendance), adds them up, passes quizzes to `MarksCalculator`, evaluates the American grade (A, B, C) using if/else logic, and returns a fully styled graphical JavaFX `VBox` card to be rendered.

### `TasksController.java`
*   **`public void initData(Stage stage, String uid, String activeSem)`**
    *   **Explanation:** Configures UI tabs and fires the `loadTasks()` database fetcher.
*   **`private void populateTasks(JSONArray tasks)`**
    *   **Explanation:** Loops through the raw task JSON. Reads the `due_date` strings, converts them to Java Time relative to `now()`, and places the JSON into one of three memory lists: `upcoming`, `overdue`, or `completed`. Finally, loops through those 3 lists and draws their visual cards into the UI tabs.

### `Sidebar.java`
*   **`public VBox getView()`**
    *   **Explanation:** Creates the vertical Navigation strip. Instantiates every navigation button (Dashboard, Tasks, Profile). Attaches click listeners that destroy the current screen and instantiate the requested Screen.
*   **`private Button createTabButton(String text, boolean isActive)`**
    *   **Explanation:** A graphical helper that generates a UI button. If `isActive` is true, it applies a specific CSS class (`sidebar-button-active`) to turn the button bright blue so the user knows what page they are on.