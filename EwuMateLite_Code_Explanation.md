# EwuMateLite Codebase Complete Breakdown

This document is a comprehensive guide to understanding the EwuMateLite JavaFX application line-by-line. You can read this on your phone to understand exactly how the application functions behind the scenes.

---

## 1. The Entry Points

### `Main.java`
This file is the absolute starting point of your visible JavaFX desktop application.

*   `package com.ewumatelite;` 
    Tells Java this file belongs to the main folder structure.
*   `public class Main extends Application` 
    Tells Java, "Make my `Main` class a specialized version of a JavaFX Application." This turns it into a windowed GUI app rather than just a terminal script.
*   `public void start(Stage primaryStage)` 
    When the app launches, JavaFX automatically calls this function to draw the first screen. `Stage` is the actual physical window on your computer screen.
*   `primaryStage.addEventFilter(...)` 
    This attaches a global event listener. It watches the entire window and intercepts any mouse clicks before they do anything else. It figures out what was clicked and logs it using `LogExporter`.
*   `if (SupabaseConfig.currentUserToken != null)` 
    Checks if a user token exists (meaning the user successfully logged in previously).
*   `new DashboardScreen(...).show();` 
    If a logged-in user is found, launch the Dashboard immediately.
*   `new LoginScreen(...).show();` 
    If no user is logged in, show the Login screen instead.
*   `public static void main(String[] args) { launch(args); }` 
    The very first method Java runs. It fires up the JavaFX engine and triggers the `start()` method.

### `Launcher.java`
A crucial but simple file used specifically for packaging JavaFX applications into `.jar` or `.exe` files.

*   `public class Launcher`
    A generic class that does not extend `Application`.
*   `public static void main(String[] args)`
    The starting point.
*   `Main.main(args);`
    Immediately calls the real `Main` class. Why do this? If your main class extends `Application`, Java expects JavaFX modules to be installed perfectly on the system. By using a standard `Launcher` class first, the app can handle missing dependencies gracefully before crashing, ensuring your packaged `.exe` works everywhere.

---

## 2. Core Architecture System

### `SupabaseConfig.java` (Database Connection)
This is the brain behind connecting to your remote database (Supabase).

*   `package com.ewumatelite.core.config;`
    Located in the "config" folder of the core system.
*   `public static final String PROJECT_URL / ANON_KEY / SERVICE_KEY`
    `public` (accessible everywhere), `static` (only one copy exists in memory), `final` (cannot be changed once set). These hold the exact URLs and passwords needed to talk to the remote server securely.
*   `public static String currentUserToken / currentUserId`
    Store the currently logged-in user's session tokens so other screens know who is using the app.
*   `static { java.util.prefs.Preferences prefs = ... }`
    A "static block" runs once when the app starts. It reaches into your operating system's internal registry (Windows Registry / Mac Preferences) and tries to extract a saved `SUPABASE_JWT`. This is exactly how the "Remember Me" function works across computer reboots.

---

## 3. How the App Actually Navigates and Works

The project follows a **Feature-based Architecture (MVC-like)**. Every feature (like Dashboard, Schedule, Auth) has:

1.  **Presentation (The UI)**
    *   `.java` files (like `LoginScreen.java`) setup the window.
    *   `.fxml` files (like `login.fxml`) handle the visual layout (colors, button positions) in an XML structure so you don't muddy your Java code with visual math.
    *   `Controllers` (like `LoginController.java`) act as the bridge. When you click a fake button in the `.fxml`, the Controller's Java function is triggered.

2.  **Repositories (The Database Bridge)**
    Controllers never talk to Supabase directly. They ask a Repository.
    *   For example: When you hit "Login", the Controller passes the email/password to `AuthRepository.login(email, password)`.
    *   The `AuthRepository` formats an HTTP network request, fires it at Supabase, waits for the result, parses the JSON response, and hands a clean Java result back to the Controller.

3.  **Models (The Data Blueprints)**
    Once `AcademicRepository` fetches courses from the database, it converts the messy JSON text into neat Java Objects called Models (like `CourseItem`). This way, your UI only deals with simple `course.getCourseName()` commands.

---

## 4. UI Rendering System (`LayoutFactory.java`)

Instead of rebuilding the left-panel Sidebar on every single screen, the app uses a clever trick.
*   The `LayoutFactory` takes a screen you've built (like the Settings page) and wraps it mathematically.
*   It grabs the `Sidebar` layout, slaps it on the left (20% screen width), takes your custom screen, slaps it on the right (80% screen width), and returns a unified layout to the `Stage`.

## Summary of the Flow

1.  User opens app. OS hits `Launcher.java` -> `Main.java`.
2.  `SupabaseConfig` checks OS Registry. Is a token saved? Yes.
3.  `Main` launches `DashboardScreen`.
4.  `DashboardScreen` loads `dashboard.fxml` and `LayoutFactory` adds the sidebar.
5.  `DashboardController` wakes up, creates an instance of `AcademicRepository`.
6.  `AcademicRepository` fires an HTTP request to Supabase to get the user's classes.
7.  Supabase returns JSON. Repository turns JSON into `CourseItem` lists.
8.  `DashboardController` loops through the list and draws visual blocks on the screen.
9.  The User can now see their grades and schedule!

---

## 5. Deep Dive: `AuthRepository.java` (Authentication Logic)
This file handles the messy process of talking to Supabase to log in or register users.

*   `public boolean login(String email, String password) throws Exception`
    The login function. Needs an email and password, and returns `true` or `false` based on success.
*   `JSONObject payload = new JSONObject();`
    Creates an empty JSON object (think of it like a digital envelope).
*   `payload.put("email", email);`
    Stuffs the user's email into the envelope.
*   `HttpRequest request = HttpRequest.newBuilder().uri(...).POST(...)`
    Builds a raw HTTP network request. It points directly to Supabase's `/auth/v1/token` endpoint and asks for a password grant (a JWT token).
*   `HttpResponse<String> response = httpClient.send(request...);`
    Fires the request over the internet and waits for Supabase to answer.
*   `if (response.statusCode() >= 200 && response.statusCode() < 300)`
    Checks if the request was successful (HTTP 200-class codes mean "OK").
*   `JSONObject resJson = new JSONObject(response.body());`
    Supabase answers with a massive string of text. This line converts that text back into an easily readable JSON object.
*   `SupabaseConfig.currentUserToken = resJson.getString("access_token");`
    Extracts the digital ID badge (the access token) from Supabase and saves it to the global configuration so the rest of the app can use it to fetch private data.
*   `java.util.prefs.Preferences prefs = ...`
    Saves that token permanently into your computer's Windows Registry/Mac Preferences. This is what makes the app remember you even if you close it!
*   `public String registerUser(...)`
    The enormous registration function. It takes 8 pieces of data (name, email, major, student ID, etc.).
*   *Part 1 of Register:* It stuffs the email/password into one JSON payload and hits `/auth/v1/signup`. If Supabase accepts it, a new user account is born.
*   *Part 2 of Register:* Because Supabase Auth only handles emails/passwords, the function extracts the new User ID (UID), creates a SECOND payload with your Student ID and Major, and fires it at the `profiles` table using a `Bearer` token to prove it has permission to write data.

---

## 6. Deep Dive: `MarksCalculator.java` (Business Logic)
This is a utility file. It doesn't draw UI and it doesn't talk to the database. It just does math very quickly for the Semester Progress screen.

*   `public static double calculateQuizValue(JSONArray qArr, String strategy, int n, double maxMark)`
    Computes a student's finalized quiz score based on strange syllabus rules (like "best 2 out of 3").
*   `List<Double> marks = new ArrayList<>();`
    Takes the JSON array of quiz marks from the database and turns them into a Java List.
*   `marks.sort(Collections.reverseOrder());`
    Crucial step: It sorts the quiz marks from highest to lowest. If you got a 10, 5, and 15, it becomes [15, 10, 5].
*   `if ("best_one".equals(strategy)) { total = marks.get(0); }`
    If the syllabus rule is "Best One", since the list is sorted highest-to-lowest, it just grabs the first item!
*   `else if ("best_n".equals(strategy))`
    If the rule is "Best N" (where `n` is, say, 2), it loops `n` times, adding up the top numbers.
*   `return total > maxMark ? maxMark : total;`
    A safety check. If the calculated total is somehow higher than the max allowed marks (due to bonus points or database typos), it caps the score at the maximum.
*   `public static String calculateGrade(double obtained, double distributed)`
    The grading scale algorithm. It takes the marks you earned (`obtained`) and divides by the total possible marks so far (`distributed`).
*   `double percentage = (obtained / distributed) * 100;`
    Finds your percentage (e.g., 91.5%).
*   `if (percentage >= 97) return "A+"; ...`
    A giant cascade of if-statements that run down the scale. It returns the American letter grade mapped strictly to standard North American GPA cutoffs.

*(Keep this document as a quick reference when navigating the code folders!)*

---

## 7. Deep Dive: `AcademicRepository.java` (The Core API Hub)
This repository is the heavy-lifter for the entire app. Almost every screen that asks "What classes are there? What semester is it? Has the student enrolled?" goes through this file.

It functions by mapping Java HTTP calls strictly to **PostgREST** (the underlying tool Supabase uses to turn a database into a REST API).

*   `private final HttpClient httpClient = HttpClient.newHttpClient();`
    This creates an invisible Web Browser inside the app used to send and receive data.

### 7.1 Retrieving Active Semesters
*   `public String getUpcomingSemester(String trackType)` / `getActiveSemester(String trackType)`
    These functions contact the `active_semester` SQL table on Supabase.
    *   `String url = SupabaseConfig.PROJECT_URL + "/rest/v1/active_semester?track=eq." + safeTrack + "&select=..."`
        Notice the `?track=eq.tri_semester`? In PostgREST, this translates to the SQL query: `SELECT next_semester_code FROM active_semester WHERE track = 'tri_semester' LIMIT 1`.
    *   Returns `"Spring 2026"` or whatever the live database says.

### 7.2 Checking Course Data
*   `public JSONArray getCourseMetadata()`
    Hits the `course_metadata` table and grabs ONLY the `code` (CSE101) and `name` (Intro to CS). It explicitly filters using `?select=code,name` to save internet bandwidth.

*   `public JSONArray getSectionsForCourse(String currentSemesterCode, String targetCourseCode)`
    This handles Ewing's dynamic tables. If the semester is "Spring 2026", it converts it to `courses_spring2026` and pings that specific table to see what professors and times are teaching that course.

### 7.3 The Enrollment Process (`pushEnrollment()`)
When a user clicks "Enroll" in a class, this function executes a massive 3-step synchronization process to keep the database perfectly aligned.

*   **Step 1: The Enrollment Record**
    *   Creates a `JSONObject payload` containing `user_id`, `semester_code`, `course_code`, etc.
    *   Fires a `POST` request to the `enrollments` table with a `Bearer` token (proving the user is logged in).
*   **Step 2: Update the User Profile Array**
    *   It does a `GET` request to the `profiles` table to pull the user's current `enrolled_sections` array.
    *   It injects the new class into that array (`sections.put(sectionId)`).
    *   It fires a `PATCH` request (which means "update this existing row, don't create a new one") to save the array back to the profile.
*   **Step 3: Cache Invalidation**
    *   The mobile app and the desktop app both use a cached Weekly Grid to load the schedule instantly.
    *   This step fires a `PATCH` to the `user_semester_states` table setting `"weekly_grid_cache": null`. Why? Because the student just added a new class, so their old cached schedule is now wrong. This forces the app to recalculate the visual schedule chart on their next login!

### 7.4 Dropping a Class (`dropEnrollment()`)
*   Does the exact opposite of `pushEnrollment()`.
*   Uses the HTTP `DELETE` method pointing at the `enrollments` table.
*   (Though cut off in the snippet, it similarly removes the section from the profile array and clears the schedule cache).

---

## 8. Deep Dive: Core Models (`com.ewumatelite.core.models`)
Models are simple Java classes that act as "blueprints" for the data coming from the database. Instead of passing around confusing chunks of JSON text everywhere, the app converts that text into these clean Objects. 

All of these files use strict **Encapsulation** (variables are `private` and accessed only via `public` getter/setter methods) to prevent accidental data corruption.

### 8.1 `CourseItem.java`
Represents a single class (like "CSE101").
*   `private String code;` and `private String name;`
    Stores the course identifier (e.g., CSE101) and its human-readable title (e.g., Intro to CS).
*   `public CourseItem(String code, String name)`
    The constructor. Called when the repository first builds the object from database JSON.
*   `public String getCode() / getName()`
    Returns the hidden private variables. This is what the UI (like the Dashboard) uses to print text on the screen.
*   `@Override public String toString()`
    Java's default way of converting an Object to text. We override it so that if a Dropdown Menu asks for the object's name, it perfectly formats it as `"CSE101 - Intro to CS"`.

### 8.2 `ProgramItem.java`
Represents a degree major/program (Wait, code not explicitly shown, but identically structured to `CourseItem`).
*   Holds the `programCode` (e.g., "BS-CSE"), `name` (e.g., "Computer Science"), `department`, and `track`.

### 8.3 `SectionItem.java`
Represents a specific *time block/professor* for a Course (e.g., Section 2 of CSE101).
*   `private String cid;`
    The unique UUID from Supabase's dynamic `courses_fall...` tables identifying this exact section block.
*   `private String sectionNumber;`
    The human readable section (e.g., "1", "2").
*   `@Override public String toString()`
    Overridden to print `"Section 1"` cleanly in UI Dropdown menus.

---

## 9. Deep Dive: Core Utilities & the UI Factory (`com.ewumatelite.core.ui` & `.utils`)

### 9.1 `LayoutFactory.java`
A crucial UI component that follows the "Factory" design pattern. It dynamically wraps any screen in the app with the global navigation side-panel.
*   `public static Parent create(...)`
    The main factory method. It accepts standard variables (which user is logged in, what semester is it) *and* the `content` (which is the actual screen, like your Dashboard screen content).
*   `HBox desktopLayout = new HBox(new Sidebar(...).getView(), content);`
    This creates an `HBox` (Horizontal Box). It places a newly generated `Sidebar` on the left, and pushes your `content` screen to the right. 
*   `HBox.setHgrow(content, Priority.ALWAYS);`
    This tells the layout: "No matter how big the user resizes their window, keep the sidebar the exact same fixed size, and stretch the `content` screen to fill the rest of the empty space."
*   `desktopLayout.getStyleClass().add("content-area-root");`
    Attaches a CSS class so you can style this wrapper globally.

### 9.2 `LogExporter.java`
A utility class showing strong Object-Oriented principles. It handles writing debug logs to your computer so you can diagnose crashes or bugs without staring at a terminal.
*   `public class LogExporter extends BaseFileExporter<String>`
    This class **Inherits** from an abstract parent (`BaseFileExporter`). The parent likely handles the messy `FileWriter` code, so this class just needs to focus on formatting.
*   `private static final String DEFAULT_LOG_PATH = System.getProperty("user.home") + "/Documents/EwuMateLite_activity.log";`
    A brilliant line of code that asks the OS (Windows or Mac) for the path to the current user's "Documents" folder, and creates a log file there.
*   `public static void log(String message)`
    A static helper. Any class anywhere in the app can just type `LogExporter.log("Hello")` and it works instantly without creating new Objects.
*   `@Override public String formatData(String data)`
    An overridden method from the parent class (**Polymorphism**). It takes your message and automatically glues `[LOG - {Current Time}]` to the front of it before the parent class writes it to the file. This way, every log is perfectly timestamped!

### 9.3 `PlatformUtils.java` (Briefly)
Though not fully shown, this class contains functions (like `getScreenWidth()`) that ask the operating system for the physical display dimensions so the app opens at a clean default size suitable for the user's actual screen.

---

## 10. Deep Dive: Supporting Repositories (`ProfileRepository` & `ExceptionRepository`)

While `AcademicRepository` handles entire semesters, these two handle the user's personal details and schedule overrides.

### 10.1 `ProfileRepository.java`
Responsible for the user's identity, settings, and historical progress.
*   `public JSONObject getProfile(String uid)`
    Pings the `profiles` table and downloads everything about the user (Major, Student ID, Name) so the Sidebar and Profile screen can display it.
*   `public boolean updateProfileField(String uid, String field, String value)`
    A brilliant helper method. Instead of writing 10 different functions (like `updateName()`, `updatePhone()`), this single function takes the name of the column (`field`) and the new data (`value`), and sends a `PATCH` request. You can use it to instantly update any part of the user's profile.
*   `public int getCoursesDone(String uid)`
    A clever metric function. It reaches into `semester_summaries`, grabs all past semesters, loops through the JSON arrays of classes they took, and counts them up. The UI then displays "Total Courses Completed: X".
*   `public boolean updatePassword(String newPassword)`
    Unlike standard profile data, passwords live in a special, highly-secure `/auth/v1/user` endpoint. This fires a `PUT` request directly to Supabase Auth to cycle the user's password.

### 10.2 `ExceptionRepository.java`
This solves the biggest problem with rigid college schedules: **Cancellations and Makeup Classes**. Normal classes repeat weekly, but what if a class is canceled on a Tuesday?
*   `public JSONArray fetchExceptions(String uid)`
    Downloads a list of every single date where a student's schedule is "abnormal".
*   `public void addCancellation(String uid, String date, String courseCode, boolean pendingMakeup)`
    If a student clicks "Cancel Class" on their dashboard, this function creates a JSON payload with `"type": "cancel"`. When the schedule screen renders that specific `date`, it sees this flag and visually crosses the class out! It also attaches `pendingMakeup` so the app knows to remind the student later.
*   `public void addManualClass(...)`
    If a professor schedules a makeup class at a bizarre time (e.g., Friday on a weekend), the student inputs it. This creates a record with `"type": "manual"` containing custom times and rooms. The schedule screen will artificially inject this block into the UI for that single date only.

---

## 11. Deep Dive: Authentication Features (`com.ewumatelite.features.auth`)

These screens are the first thing a user sees. They combine JavaFX UI rendering with multithreaded database calls.

### 11.1 `LoginScreen.java`
Renders the login UI and handles the login process.
*   `public void show()`
    This method builds the entire UI purely in Java code (without an `.fxml` file). It creates a massive centered `VBox` (Vertical layout) containing TextFields for email and password.
*   `btn.setOnAction(e -> { ... })`
    This is the click-handler for the "SIGN IN" button. When clicked, it grabs the typed email and password and immediately disables the button (`btn.setDisable(true)`) so the user doesn't spam click it.
*   `new Thread(() -> { ... }).start();`
    **Crucial Concept (Multithreading):** Trying to talk over the internet is slow. If we did it on the main UI thread, the entire app window would freeze and say "Not Responding" for 3 seconds. By creating a new `Thread`, the app talks to Supabase in the background while the UI remains perfectly smooth and displays "Authenticating...".
*   `boolean success = new AuthRepository().login(email, password);`
    It defers to the Repository (detailed in Section 5) to actually do the hard work. 
*   `Platform.runLater(() -> { new DashboardScreen(...).show(); });`
    Once the background thread is done, it *must* use `Platform.runLater()` to hand control back to the main UI thread to safely draw the Dashboard and destroy the Login screen.

### 11.2 `RegistrationScreen.java`
A massive form where users create their account and set up their university profile.
*   `ComboBox<String> deptComboBox = new ComboBox<>();`
    This creates a dropdown menu for Departments. But wait, where do the departments come from? 
*   `new Thread(() -> { JSONArray progs = academicRepository.getPrograms(); ... })`
    As soon as the Registration screen opens, a background thread instantly fires off to the database to download all valid EWU degrees and departments.
*   `deptComboBox.getItems().addAll(programsByDept.keySet());`
    Once downloaded, it fills the first dropdown with Department names (e.g., "Computer Science").
*   `deptComboBox.setOnAction(...)`
    When the user clicks a Department, this listener fires and instantly populates the *second* dropdown (`programComboBox`) with only the specific degrees in that department (e.g., "B.Sc in CSE").
*   Finally, when the user clicks "REGISTER & SYNC", it gathers all the text fields, fires them to `AuthRepository.registerUser()`, and simultaneously provisions their Supabase Auth AND their row in the `profiles` SQL table.

---

## 12. Deep Dive: Dashboard Feature (`com.ewumatelite.features.dashboard`)
The Dashboard is the central hub of EwuMateLite. It shows today's schedule, immediate tasks, and upcoming holidays.

### 12.1 `DashboardScreen.java`
This file acts as the router to load the visual UI.
*   `FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/dashboard.fxml"));`
    Instead of building the UI in raw Java (like the Login screen), this loads a visual XML file (`dashboard.fxml`). This allows for cleaner, separated code.
*   `VBox dashboardLayout = loader.load();`
    Extracts the layout block from the XML.
*   `DashboardController controller = loader.getController();`
    Automatically finds the Java class attached to the XML file so we can pass data to it.
*   `Parent adaptiveLayout = LayoutFactory.create(stage, uid, activeSem, "Dashboard", dashboardLayout);`
    Uses the `LayoutFactory` (discussed in Section 9.1) to wrap the Dashboard content inside the global Navigation Sidebar.

### 12.2 `DashboardController.java`
This file contains the intense logic to calculate what the student needs to see *today*.
*   `@FXML private Label greetingLabel;`
    The `@FXML` tag connects this Java variable directly to a UI Label defined in the `dashboard.fxml` file.
*   `int hour = LocalTime.now().getHour();`
    The "Greeting Logic". If it's 5 AM it says "Good Morning," if 1 PM "Good Afternoon," etc.
*   **The 8 PM Rule:**
    *   `if (hour >= 20) { targetDate = targetDate.plusDays(1); }`
    *   A core feature from the EWUmate mobile app! If it is past 8:00 PM, the student doesn't care about the classes they already finished today. The Dashboard automatically shifts forward 1 day to show *Tomorrow's* schedule.
*   `new Thread(() -> { JSONObject data = new AcademicRepository().getDashboardData(...); })`
    Fires off a background thread to Supabase to fetch the mega-JSON object containing the user's weekly grid, tasks, and holiday calendar.
*   `private void populateUI(JSONObject data, LocalDate targetDate)`
    Once the data arrives, this function takes over to draw the schedule cards.
*   **The Holiday & Makeup Logic:**
    *   `if (title.contains("swap") || title.contains("makeup"))`
    *   The app checks the university academic calendar. If today is a Tuesday, but the academic calendar says "Tuesday replaced by Sunday's Schedule", the controller runs a string-split algorithm (`title.split(" ")`), extracts the word "Sunday", and forces the UI to draw Sunday's classes instead of Tuesday's!
*   **The Cancellation Filter:**
    *   `if ("cancel".equals(ex.optString("type")) && courseCode.equals(ex.optString("course_code"))) { isCancelled = true; }`
    *   Before drawing a class card, it checks the user's `schedule_exceptions`. If the student manually marked this specific class as "Canceled" for today's date, it sets a flag so the UI renders the class crossed-out and grayed-out.

---

## 13. Deep Dive: Schedule Feature (`com.ewumatelite.features.schedule`)
Unlike the Dashboard which only shows *today*, the Schedule screen projects 14 days into the future and allows students to manipulate their calendar by adding makeup classes or canceling sick days.

### 13.1 `ScheduleScreen.java`
Standard router file. It loads `schedule.fxml`, connects the `ScheduleController`, and wraps it in the global `LayoutFactory` sidebar exactly like the Dashboard.

### 13.2 `ScheduleController.java`
This file is a masterclass in combining multiple database tables into a single unified timeline.
*   **The Two Tabs (Upcoming vs Pending):**
    *   `@FXML private VBox upcomingListContainer;` / `pendingListContainer;`
    *   The UI has two tabs. `switchToUpcoming()` and `switchToPending()` hide and show these containers dynamically, updating the blue indicator lines underneath the active tab so the user knows where they are.
*   `private void loadScheduleData()`
    *   This is the heavy data-fetcher. It fires a new background Thread.
    *   It grabs *three* separate pieces of data:
        1. The user's `weekly_grid` (their standard repeating classes).
        2. `allExceptions` (Cancellations and Makeups).
        3. `allHolidays` (University-wide closed days).
*   `private void buildTwoWeekSchedule(...)`
    *   It loops exactly 14 times (`for (int i = 0; i < 14; i++)`) to build a 14-day timeline starting from `LocalDate.now()`.
    *   For each day, it checks the `allHolidays` list. If today is a holiday, it draws a "Holiday Banner" instead of classes!
    *   If it's not a holiday, it looks at the `weekly_grid` for that specific Day of the Week (e.g., "Tuesday"), reads the classes, cross-references them against `allExceptions` to see if any are canceled, and draws the UI cards.
*   `@FXML private void handleAddClass()`
    *   Fires when the user clicks the floating Action Button (+). It opens a `ManualEntryModal` popup window where the student can type in the details of a makeup class scheduled by their professor at an unusual time.

---

## 14. Deep Dive: Semester Progress Feature (`com.ewumatelite.features.semester_progress`)
This feature acts as the student's gradebook. It pulls their current marks from the database, calculates their grades locally, and draws progress cards.

### 14.1 `SemesterProgressScreen.java`
Standard router file. Loads `/fxml/semester_progress.fxml`, attaches the controller, passes the `uid` and `activeSem`, and wraps the whole thing in the `LayoutFactory` sidebar layout.

### 14.2 `SemesterProgressController.java`
The brains of the grade calculation UI.
*   `@FXML private FlowPane courseContainer;`
    A `FlowPane` is unique in JavaFX. Instead of stacking items in a strict list (like a `VBox`), a FlowPane wraps items to a new line when it runs out of horizontal space. This automatically builds a responsive grid of course cards!
*   `private void loadProgressData()`
    Fires a background thread calling `AcademicRepository().getSemesterProgressData(...)` to download the student's grades for all enrolled classes for the current semester.
*   `private void renderData(JSONArray data)`
    Once the raw JSON data arrives, it loops through every single enrolled class and hands the data to `createCourseCard(JSONObject courseData)`.
*   `private VBox createCourseCard(JSONObject courseData)`
    This function dynamically builds a visual "Card" for one course.
    *   **Parsing the Numbers:** It reads dozens of fields (`obt_mid`, `obt_final`, `obt_attendance`, etc.) pulling the exact scores the student entered for that class.
    *   **Quiz Strategy Parsing:**
        `String qStrategy = courseData.optString("quiz_strategy", "best_one");`
        `total += MarksCalculator.calculateQuizValue(...);`
        It takes the array of quiz scores and the professor's strict syllabus rules, passes them into our `MarksCalculator` utility (from Section 6), and gets the finalized quiz score back.
    *   **Color Coding the Grades:**
        It runs a cascading `if/else` block. If the total is >= 80, the course card gets an "A+" badge and glowing teal accents (`#00e0ff`). If the total is < 50, it gets an orange warning color (`#FFAB40`).
    *   **Building the Elements:** It structures these numbers into JavaFX `HBox` (horizontal rows) containing Labels, badges, and progress bars, then lumps them into a final `VBox` card to return to the `FlowPane`.

---

## 15. Deep Dive: Tasks Feature (`com.ewumatelite.features.tasks`)
The Tasks section manages everything from homework deadlines to study goals. This feature categorizes items based on exactly when they are due.

### 15.1 `TasksScreen.java`
Like the other router files, this launches `/fxml/tasks.fxml`, initializes `TasksController.java`, and wraps the whole thing in `LayoutFactory`.

### 15.2 `TasksController.java`
This file parses a giant dump of tasks, checks today's date, and sorts them into different UI buckets.
*   `@FXML private VBox upcomingTasksContainer; overdueTasksContainer; completedTasksContainer;`
    These link directly to the `TabPane` tabs defined in the `.fxml` file. Each tab holds a scrolling list of tasks.
*   `private void loadTasks()`
    Throws all three containers into a clean "Loading tasks..." state and spawns a background thread.
    Instead of making a brand new database call, it smartly hooks into `getDashboardData()` which fetches the student's *entire dashboard payload* (including tasks) for the current active semester in one big JSON shot, saving API bandwidth.
*   `private void populateTasks(JSONArray tasks)`
    The Sorting Algorithm.
    *   It creates three empty Java Lists: `upcoming`, `overdue`, and `completed`.
    *   It loops through the JSON tasks (`for (int i = 0; i < tasks.length(); i++)`).
    *   **Logic 1:** If `is_completed` is true, immediately shove it in the `completed` list.
    *   **Logic 2:** If it has no due date (`dueStr.isEmpty()`), it defaults to `upcoming`.
    *   **Logic 3:** If it *does* have a due date, it extracts the `YYYY-MM-DD` part, converts it to a `LocalDate`, and compares it to `LocalDate.now()`. If it's before today, it's `overdue`, else `upcoming`.
    *   Finally, it loops through each newly sorted list, generating UI cards for each and throwing them into their respective tabs.
*   `private HBox createTaskCard(JSONObject task, boolean isOverdue, boolean isCompleted)`
    This builds the physical card you see.
    *   `card.setOpacity(0.5);` - If a task is completed, it dims the whole box to 50% transparency so your eye ignores it.
    *   `if (!cCode.isEmpty())` - If the task is linked to a specific class (like `CSE303`), it generates a tiny teal badge and sticks it next to the title.
    *   **Time parsing:** Supabase sends ugly UTC timestamps (like `2024-05-18T14:00:00Z`). This block contains manual substring math to extract the hours, figure out AM/PM, and display a pretty string like `"Due: 2024-05-18 at 02:00 PM"`.
    *   `card.setOnMouseClicked(...)` - Clicking a non-completed card spawns `add_task.fxml` as a popup dialog so the user can quickly edit the details.

---

## 16. Deep Dive: Enrollment Feature (`com.ewumatelite.features.enrollment`)
This is where the user registers for their specific classes after successfully completing signup or prior to starting a new semester. Unlike other features, this one builds its UI completely in Java without an `.fxml` file.

### 16.1 `EnrollmentScreen.java`
This single file manages both the Course Search UI and the Enrollment logic.
*   `public void show()`
    Builds the main container with a `TextField searchBar` and a vertical `coursesContainer`.
*   `Runnable applyFilter = () -> { ... }`
    A custom search algorithm. Every time you type in the search bar, it loops through every university course (`courses.length()`) and checks if the name or code matches your search query. If it doesn't match, it immediately skips rendering it `continue;`.
*   **The Lazy-Loading Accordion Pattern:**
    When it prints out the list of classes matching your search, it only prints the Title ("Intro to CS"). It does *not* display the sections (times/professors) yet.
    *   `VBox sectionContainer = new VBox(12);`
        It creates a hidden container right below the class title.
    *   `header.setOnMouseClicked(event -> { ... }`
        This attaches a click listener to the class title. When clicked, it expands the hidden `sectionContainer` and *then* fires a background thread: `academicRepository.getSectionsForCourse(activeSem, courseCode);`
    *   **Why is this brilliant?** There are thousands of sections. If it downloaded and rendered every single section for every single class on load, the app would crash. By waiting until the user explicitly clicks a course to download its specific sections, the UI remains perfectly fast. 
*   **Checking Existing Enrollments:**
    `academicRepository.getUserEnrollments(uid, activeSem);`
    Before drawing the "Enroll" buttons, it asks the database what the student is *already* enrolled in. If the student is already in a section, the button says "Drop", otherwise it says "Enroll".
---

## 17. Deep Dive: Sidebar & Profile (`com.ewumatelite.features.sidebar` & `.profile`)

These two features are inherently linked. The sidebar navigates everywhere, and the profile screen manages the user's core identity.

### 17.1 `Sidebar.java`
This isn't a "Screen" that loads on its own, it's a UI Component that gets attached to the left side of every single screen via `LayoutFactory`.
*   `public VBox getView()`
    Returns the vertical block containing all the standard navigation buttons.
*   **Dynamic Highlighting:**
    `createTabButton("Dashboard", activeScreen.equals("Dashboard"));`
    When a screen asks `LayoutFactory` for a Sidebar, it passes its own name (e.g., "Dashboard"). The sidebar compares that name to all its buttons, and applies the `sidebar-button-active` CSS class to the matching one, turning it bright blue.
*   **The Logout Routine:**
    `btnLogout.setOnAction(e -> { ... })`
    When clicked, this button performs a critical security wipe. It sets the session objects (`currentUserToken`, `currentUserId`) to `null`, and accesses the computer's OS Registry (`java.util.prefs.Preferences`) to completely delete the saved `SUPABASE_JWT`. This permanently logs the user out. Finally, it launches `LoginScreen`.

### 17.2 `ProfileScreen.java`
This screen mirrors the "Settings" tab of the mobile app.
*   `public void show()`
    Unlike other screens that use `VBox` as their root, this uses a `BorderPane` with a `ScrollPane` inside. This ensures that if the user's profile gets too long, a scrollbar automatically appears without breaking the layout.
*   `loadProfileData(VBox container)`
    Runs a background thread firing `profileRepo.getProfile(uid)` and crucially `profileRepo.getCoursesDone(uid)` (which loops through all historical semesters counting the student's completed classes).
*   `buildProfileUI(...)`
    Dynamically draws the user's avatar. Since users don't upload images, it uses JavaFX shapes: `Circle avatarBg = new Circle(...)` and overlays the first letter of their nickname via `substring(0, 1).toUpperCase()`.
*   **Action Handlers:**
    It attaches Java Lambda expressions `() -> editField(...)` and `this::editPassword` to the profile buttons. When clicked, these trigger popup dialogs (likely `Alert` or custom stages) allowing the student to change their password or nickname instantly updating the database via `ProfileRepository`.