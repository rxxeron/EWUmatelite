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
        int hour = LocalTime.now().getHour();
        String greeting = "Good Evening,";
        if (hour >= 5 && hour < 12) greeting = "Good Morning,";
        else if (hour >= 12 && hour < 17) greeting = "Good Afternoon,";
        greetingLabel.setText(greeting);
        profileName.setText("User");
        LocalDate targetDate = LocalDate.now();
        if (hour >= 20) {
            targetDate = targetDate.plusDays(1);
        }
        String todayFormatted = targetDate.format(DateTimeFormatter.ofPattern("EEEE, MMM d"));
        String todayDate = targetDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        dateLabel.setText(todayFormatted);
        com.ewumatelite.core.utils.LogExporter.log("ACTION: User " + uid + " opened dashboard for semester " + activeSem);
        final LocalDate finalTargetDate = targetDate;
        new Thread(() -> {
            try {
                JSONObject data = new AcademicRepository().getDashboardData(uid, activeSem, todayDate);
                com.ewumatelite.core.utils.LogExporter.log("SUCCESS: Dashboard data fetched successfully.");
                Platform.runLater(() -> populateUI(data, finalTargetDate));
            } catch (Exception ex) {
                com.ewumatelite.core.utils.LogExporter.log("ERROR: Failed to fetch dashboard data: " + ex.getMessage());
                Platform.runLater(() -> {
                    scheduleContainer.getChildren().clear();
                    scheduleContainer.getChildren().add(createErrorLabel(ex.getMessage()));
                });
            }
        }).start();
    }
    private void populateUI(JSONObject data, LocalDate targetDate) {
        String nick = data.optString("nickname", "User");
        profileName.setText(nick);
        scheduleContainer.getChildren().clear();
        tasksContainer.getChildren().clear();
        String dayStr = targetDate.getDayOfWeek().name();
        dayStr = dayStr.substring(0, 1).toUpperCase() + dayStr.substring(1).toLowerCase();
        boolean processedHoliday = false;
        JSONObject holidayData = data.optJSONObject("holiday");
        if (holidayData != null) {
            String title = holidayData.optString("title", holidayData.optString("name", "")).toLowerCase();
            String reason = holidayData.optString("name", "Holiday");
            if (title.contains("swap") || title.contains("makeup")) {
                String[] words = title.split(" ");
                if (words.length > 0) {
                    String swapDayRaw = words[words.length - 1];
                    if (swapDayRaw.endsWith("s")) swapDayRaw = swapDayRaw.substring(0, swapDayRaw.length() - 1);
                    dayStr = swapDayRaw.substring(0, 1).toUpperCase() + swapDayRaw.substring(1).toLowerCase();
                }
            } else if (!title.contains("makeup") && !title.contains("advising")) {
                scheduleContainer.getChildren().add(createHolidayBanner(reason));
                processedHoliday = true;
            }
        }
        if (!processedHoliday) {
            JSONObject grid = data.optJSONObject("weekly_grid");
            JSONArray classesObj = grid != null ? grid.optJSONArray(dayStr) : null;
            JSONArray exceptions = data.optJSONArray("exceptions");
            boolean hasClasses = false;
            if (classesObj != null && classesObj.length() > 0) {
                for (int i = 0; i < classesObj.length(); i++) {
                    JSONObject c = classesObj.getJSONObject(i);
                    String courseCode = c.optString("courseCode", c.optString("course_code"));
                    boolean isCancelled = false;
                    if (exceptions != null) {
                        for (int j = 0; j < exceptions.length(); j++) {
                            JSONObject ex = exceptions.getJSONObject(j);
                            if ("cancel".equals(ex.optString("type")) && courseCode.equals(ex.optString("course_code"))) {
                                isCancelled = true;
                                break;
                            }
                        }
                    }
                    if (!isCancelled) {
                        scheduleContainer.getChildren().add(createClassCard(c));
                        hasClasses = true;
                    }
                }
            }
            if (exceptions != null) {
                for (int i = 0; i < exceptions.length(); i++) {
                    JSONObject ex = exceptions.getJSONObject(i);
                    String type = ex.optString("type");
                    if ("makeup".equals(type) || "manual".equals(type)) {
                        JSONObject mapped = new JSONObject();
                        mapped.put("courseCode", ex.optString("course_code"));
                        mapped.put("courseName", ex.optString("course_name"));
                        mapped.put("room", ex.optString("room"));
                        mapped.put("startTime", ex.optString("start_time"));
                        mapped.put("endTime", ex.optString("end_time"));
                        mapped.put("type", ex.optString("faculty")); 
                        scheduleContainer.getChildren().add(createClassCard(mapped));
                        hasClasses = true;
                    }
                }
            }
            if (!hasClasses) {
                scheduleContainer.getChildren().add(createEmptyLabel("No classes scheduled for today."));
            }
        }
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
    private VBox createHolidayBanner(String reason) {
        VBox box = new VBox(10);
        box.setAlignment(Pos.CENTER);
        box.setPadding(new Insets(20));
        box.setStyle("-fx-background-color: " + ACCENT_TEAL_DARK + "; -fx-background-radius: 12;");
        Label title = new Label("Chill!");
        title.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18px;");
        Label subtitle = new Label("It's a " + reason + "!\nNo classes scheduled today.");
        subtitle.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-text-alignment: center;");
        subtitle.setAlignment(Pos.CENTER);
        box.getChildren().addAll(title, subtitle);
        return box;
    }
    private HBox createClassCard(JSONObject c) {
        String type = c.optString("type", "Theory");
        boolean isLab = type.equalsIgnoreCase("Lab");
        String borderColor = isLab ? ACCENT_ORANGE_DARK : ACCENT_TEAL_DARK;
        String badgeColor = isLab ? "#9E6023" : "#1F7883";
        String lineFill = isLab ? ACCENT_ORANGE : ACCENT_TEAL;
        HBox card = new HBox(15);
        card.setPadding(new Insets(15));
        card.setStyle("-fx-background-color: " + CARD_BG + "; -fx-background-radius: 12; -fx-border-color: " + borderColor + "; -fx-border-width: 1.5; -fx-border-radius: 12;");
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