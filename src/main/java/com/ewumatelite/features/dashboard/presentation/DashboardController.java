package com.ewumatelite.features.dashboard.presentation;

import com.ewumatelite.core.repositories.AcademicRepository;
import com.ewumatelite.features.tasks.presentation.TasksScreen;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import java.time.LocalTime;

public class DashboardController {
    
    @FXML private Label greetingLabel;
    @FXML private Label profileName;
    @FXML private Label dateLabel;
    @FXML private VBox scheduleContainer;
    @FXML private VBox tasksContainer;
    @FXML private Label loadingLabel;

    private Stage stage;
    private String uid;
    private String activeSem;

    // Theme constants
    private final String CARD_BG = "#1A2234";
    private final String TEXT_PRIMARY = "#FFFFFF";
    private final String TEXT_SECONDARY = "#8A95A5";
    private final String ACCENT_TEAL = "#00B4D8";
    private final String ACCENT_TEAL_DARK = "#123C46";
    private final String ACCENT_ORANGE = "#E67E22";
    private final String ACCENT_ORANGE_DARK = "#4C3821";

    public void initData(Stage stage, String uid, String activeSem) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
        
        // Set Time-based Greeting Placeholder
        int hour = LocalTime.now().getHour();
        String greeting = "Good Evening,";
        if (hour >= 5 && hour < 12) greeting = "Good Morning,";
        else if (hour >= 12 && hour < 17) greeting = "Good Afternoon,";
        
        greetingLabel.setText(greeting);

        // Placeholder Name
        profileName.setText("User");

        LocalDate now = LocalDate.now();
        String todayFormatted = now.format(DateTimeFormatter.ofPattern("EEEE, MMM d"));
        String todayDate = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        
        dateLabel.setText(todayFormatted);

        com.ewumatelite.core.utils.LogExporter.log("ACTION: User " + uid + " opened dashboard for semester " + activeSem);

        new Thread(() -> {
            try {
                JSONObject data = new AcademicRepository().getDashboardData(uid, activeSem, todayDate);
                com.ewumatelite.core.utils.LogExporter.log("SUCCESS: Dashboard data fetched successfully.");
                Platform.runLater(() -> populateUI(data, now));
            } catch (Exception ex) {
                com.ewumatelite.core.utils.LogExporter.log("ERROR: Failed to fetch dashboard data: " + ex.getMessage());
                Platform.runLater(() -> {
                    scheduleContainer.getChildren().clear();
                    scheduleContainer.getChildren().add(createErrorLabel(ex.getMessage()));
                });
            }
        }).start();
    }

    private void populateUI(JSONObject data, LocalDate now) {
        // Update user nickname if fetched
        String nick = data.optString("nickname", "User");
        profileName.setText(nick);

        scheduleContainer.getChildren().clear();
        tasksContainer.getChildren().clear();

        // 1. Build Schedule
        String dayStr = now.getDayOfWeek().name();
        dayStr = dayStr.substring(0, 1).toUpperCase() + dayStr.substring(1).toLowerCase();

        JSONObject grid = data.optJSONObject("weekly_grid");
        if (grid != null && grid.has(dayStr)) {
            JSONArray classesObj = grid.optJSONArray(dayStr);
            if (classesObj != null && classesObj.length() > 0) {
                for (int i = 0; i < classesObj.length(); i++) {
                    scheduleContainer.getChildren().add(createClassCard(classesObj.getJSONObject(i)));
                }
            } else {
                scheduleContainer.getChildren().add(createEmptyLabel("No classes scheduled for today."));
            }
        } else {
            scheduleContainer.getChildren().add(createEmptyLabel("No classes scheduled for today."));
        }

        // 2. Build Tasks
        JSONArray tasks = data.optJSONArray("tasks");
        boolean hasTasks = false;
        if (tasks != null) {
            for (int i = 0; i < tasks.length(); i++) {
                JSONObject t = tasks.getJSONObject(i);
                if (t.optBoolean("is_completed", false) || t.optBoolean("is_missed", false)) continue;
                tasksContainer.getChildren().add(createTaskCard(t));
                hasTasks = true;
            }
        }
        if (!hasTasks) {
            tasksContainer.getChildren().add(createEmptyLabel("No upcoming tasks."));
        }
    }

    @FXML
    private void onSeeAllTasksClicked() {
        com.ewumatelite.core.utils.LogExporter.log("ACTION: onSeeAllTasksClicked Triggered in " + this.getClass().getSimpleName());
        new TasksScreen(stage, uid, activeSem).show();
    }

    // --- Dynamic Card Builders (Mimicking the visual style in native JavaFX) ---

    private HBox createClassCard(JSONObject c) {
        String type = c.optString("type", "Theory");
        boolean isLab = type.equalsIgnoreCase("Lab");
        
        String borderColor = isLab ? ACCENT_ORANGE_DARK : ACCENT_TEAL_DARK;
        String badgeColor = isLab ? "#9E6023" : "#1F7883";
        String lineFill = isLab ? ACCENT_ORANGE : ACCENT_TEAL;

        HBox card = new HBox(15);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: " + CARD_BG + "; -fx-background-radius: 12; -fx-border-color: " + borderColor + "; -fx-border-width: 1.5; -fx-border-radius: 12;");

        // Time Block
        VBox timeBox = new VBox(4);
        timeBox.setAlignment(Pos.CENTER);
        timeBox.setMinWidth(60);
        
        String st = c.optString("startTime", "00:00 AM");
        String et = c.optString("endTime", "00:00 AM");
        
        Label stL = new Label(st.split(" ")[0]);
        stL.setStyle("-fx-text-fill: " + TEXT_PRIMARY + "; -fx-font-weight: bold; -fx-font-size: 14px;");
        Label stAm = new Label(st.contains(" ") ? st.split(" ")[1] : "");
        stAm.setStyle("-fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-size: 10px;");
        
        Line divider = new Line(0, 0, 0, 20);
        divider.setStrokeWidth(2.5);
        divider.setStroke(Color.web(lineFill));

        Label etL = new Label(et.split(" ")[0]);
        etL.setStyle("-fx-text-fill: " + TEXT_PRIMARY + "; -fx-font-weight: bold; -fx-font-size: 14px;");

        timeBox.getChildren().addAll(stL, stAm, divider, etL);

        // Content Block
        VBox contentBox = new VBox(6);
        HBox.setHgrow(contentBox, Priority.ALWAYS);

        HBox topRow = new HBox(10);
        Label title = new Label(c.optString("courseName", "Unknown Course"));
        title.setStyle("-fx-text-fill: " + TEXT_PRIMARY + "; -fx-font-size: 17px; -fx-font-weight: bold;");
        Pane fillPane = new Pane();
        HBox.setHgrow(fillPane, Priority.ALWAYS);
        
        Label typeBadge = new Label(type);
        typeBadge.setPadding(new Insets(2, 8, 2, 8));
        typeBadge.setStyle("-fx-background-color: " + badgeColor + "; -fx-text-fill: white; -fx-font-size: 11px; -fx-background-radius: 15; -fx-font-weight: bold;");
        topRow.getChildren().addAll(title, fillPane, typeBadge);

        HBox midRow = new HBox(10);
        Label code = new Label(c.optString("courseCode", "UNK"));
        code.setStyle("-fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-size: 13px; -fx-font-weight: bold;");
        Pane fillPane2 = new Pane();
        HBox.setHgrow(fillPane2, Priority.ALWAYS);
        Label faculty = new Label("👤 " + c.optString("faculty", "TBA"));
        faculty.setStyle("-fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-size: 13px;");
        midRow.getChildren().addAll(code, fillPane2, faculty);

        HBox botRow = new HBox(5);
        Label room = new Label("📍 Room " + c.optString("room", "TBA"));
        room.setStyle("-fx-text-fill: " + lineFill + "; -fx-font-size: 13px; -fx-font-weight: bold;");
        botRow.getChildren().add(room);

        contentBox.getChildren().addAll(topRow, midRow, botRow);
        card.getChildren().addAll(timeBox, contentBox);

        return card;
    }

    private HBox createTaskCard(JSONObject t) {
        HBox card = new HBox(15);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: " + CARD_BG + "; -fx-background-radius: 12;");
        card.setAlignment(Pos.CENTER_LEFT);

        Circle iconBg = new Circle(20);
        iconBg.setFill(Color.web("#182A3A"));

        VBox contentBox = new VBox(4);
        Label title = new Label(t.optString("title", "Task"));
        title.setStyle("-fx-text-fill: " + TEXT_PRIMARY + "; -fx-font-size: 15px; -fx-font-weight: bold;");
        
        String cCode = t.optString("course_code", "GEN");
        String dateStr = t.optString("due_date", "Date TBA");
        if (dateStr.length() > 10) dateStr = dateStr.substring(0, 10);
        Label sub = new Label(cCode + " • " + dateStr);
        sub.setStyle("-fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-size: 12px;");
        
        contentBox.getChildren().addAll(title, sub);

        Pane fillPane = new Pane();
        HBox.setHgrow(fillPane, Priority.ALWAYS);

        Label arrow = new Label(">");
        arrow.setStyle("-fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-size: 16px; -fx-font-weight: bold;");

        card.getChildren().addAll(iconBg, contentBox, fillPane, arrow);
        return card;
    }

    private Label createEmptyLabel(String text) {
        Label l = new Label(text);
        l.setStyle("-fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-size: 14px; -fx-font-style: italic;");
        return l;
    }

    private Label createErrorLabel(String err) {
        Label l = new Label("Failed to load: " + err);
        l.setStyle("-fx-text-fill: #E74C3C; -fx-wrap-text: true;");
        return l;
    }
}