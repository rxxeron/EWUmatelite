package com.ewumatelite.features.sidebar.presentation;

import com.ewumatelite.features.dashboard.presentation.DashboardScreen;
import com.ewumatelite.features.enrollment.presentation.EnrollmentScreen;
import com.ewumatelite.features.tasks.presentation.TasksScreen;
import com.ewumatelite.core.repositories.AcademicRepository;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import javafx.geometry.Insets;

public class Sidebar {
    private final Stage stage;
    private final String uid;
    private final String activeSem;
    private final String activeScreen;

    public Sidebar(Stage stage, String uid, String activeSem, String activeScreen) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
        this.activeScreen = activeScreen;
    }

    public VBox getView() {
        VBox sidebar = new VBox(20);
        sidebar.setPrefWidth(240);
        sidebar.getStyleClass().add("sidebar");
        sidebar.setAlignment(Pos.TOP_CENTER);

        Text appTitle = new Text("EWU Mate");
        appTitle.getStyleClass().add("sidebar-title");
        
        Text semText = new Text(activeSem);
        semText.getStyleClass().add("sidebar-subtitle");
        
        VBox header = new VBox(5, appTitle, semText);
        header.setAlignment(Pos.CENTER);
        header.getStyleClass().add("sidebar-header");

        Button btnDashboard = createTabButton("Dashboard", activeScreen.equals("Dashboard"));
        btnDashboard.setOnAction(e -> {
            com.ewumatelite.core.utils.LogExporter.log("ACTION: User Navigated to Dashboard");
            new DashboardScreen(stage, uid, activeSem).show();
        });

        Button btnEnrollment = createTabButton("Course Browser", activeScreen.equals("Enrollment"));
        btnEnrollment.setOnAction(e -> {
            com.ewumatelite.core.utils.LogExporter.log("ACTION: User Navigated to Course Browser");
            btnEnrollment.setText("Loading...");
            new Thread(() -> {
                try {
                    JSONArray courses = new AcademicRepository().getCourseMetadata();
                    Platform.runLater(() -> new EnrollmentScreen(stage, uid, activeSem, courses).show());
                } catch (Exception ex) {
                    com.ewumatelite.core.utils.LogExporter.log("ERROR: Course Browser Load failed: " + ex.getMessage());
                    ex.printStackTrace();
                    Platform.runLater(() -> btnEnrollment.setText("Error"));
                }
            }).start();
        });

        Button btnTasks = createTabButton("Tasks", activeScreen.equals("Tasks"));
        btnTasks.setOnAction(e -> {
            com.ewumatelite.core.utils.LogExporter.log("ACTION: User Navigated to Tasks");
            new TasksScreen(stage, uid, activeSem).show();
        });

        Button btnSemesterProgress = createTabButton("Semester Progress", activeScreen.equals("Semester Progress"));
        btnSemesterProgress.setOnAction(e -> {
            com.ewumatelite.core.utils.LogExporter.log("ACTION: User Navigated to Semester Progress");
            new com.ewumatelite.features.semester_progress.presentation.SemesterProgressScreen(stage, uid, activeSem).show();
        });
        
        Button btnProfile = createTabButton("Profile", activeScreen.equals("Profile"));
        btnProfile.setOnAction(e -> {
            com.ewumatelite.core.utils.LogExporter.log("ACTION: User Navigated to Profile");
            new com.ewumatelite.features.profile.presentation.ProfileScreen(stage, uid, activeSem).show();
        });

        Button btnLogout = new Button("Logout");
        btnLogout.getStyleClass().add("logout-button");
        btnLogout.setMaxWidth(Double.MAX_VALUE);
        btnLogout.setOnAction(e -> {
            com.ewumatelite.core.utils.LogExporter.log("ACTION: User Logged Out");
            // Clear persistent auth state
            com.ewumatelite.core.config.SupabaseConfig.currentUserToken = null;
            com.ewumatelite.core.config.SupabaseConfig.currentUserId = null;
            java.util.prefs.Preferences prefs = java.util.prefs.Preferences.userNodeForPackage(com.ewumatelite.core.config.SupabaseConfig.class);
            prefs.remove("SUPABASE_JWT");
            prefs.remove("SUPABASE_UID");
            
            new com.ewumatelite.features.auth.presentation.LoginScreen(stage).show();
        });

        sidebar.getChildren().addAll(header, btnDashboard, btnEnrollment, btnTasks, btnSemesterProgress, btnProfile);
        
        javafx.scene.layout.Region spacer = new javafx.scene.layout.Region();
        VBox.setVgrow(spacer, javafx.scene.layout.Priority.ALWAYS);
        sidebar.getChildren().addAll(spacer, btnLogout);

        return sidebar;
    }
    
    private Button createTabButton(String text, boolean isActive) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.getStyleClass().add("sidebar-button");
        if (isActive) {
            btn.getStyleClass().add("sidebar-button-active");
        }
        return btn;
    }
}
