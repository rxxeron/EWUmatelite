package com.ewumatelite.features.schedule.presentation;
import com.ewumatelite.core.repositories.ExceptionRepository;
import com.ewumatelite.core.repositories.AcademicRepository;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.json.JSONArray;
import org.json.JSONObject;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
public class ManualEntryModal {
    private final ExceptionRepository exRepo = new ExceptionRepository();
    private final AcademicRepository acRepo = new AcademicRepository();
    private final String uid;
    private final String activeSem;
    private final Runnable onComplete;
    private ComboBox<String> courseComboBox;
    private ToggleGroup sessionTypeGroup;
    private DatePicker datePicker;
    private TextField timeField;
    private TextField roomField;
    private JSONArray enrollments = new JSONArray();
    public ManualEntryModal(String uid, String activeSem, Runnable onComplete) {
        this.uid = uid;
        this.activeSem = activeSem;
        this.onComplete = onComplete;
    }
    public void show() {
        Stage modalStage = new Stage();
        modalStage.initModality(Modality.APPLICATION_MODAL);
        modalStage.setTitle("Manual Entry");
        modalStage.initStyle(StageStyle.UTILITY);
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #1A1A2E; -fx-border-color: #00E5FF; -fx-border-width: 1;");
        layout.setAlignment(Pos.TOP_LEFT);
        Label titleLabel = new Label("Manual Entry");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 18;");
        Label courseLabel = new Label("Course");
        courseLabel.setStyle("-fx-text-fill: #8A95A5;");
        courseComboBox = new ComboBox<>();
        courseComboBox.setPromptText("Select Course");
        courseComboBox.setPrefWidth(Double.MAX_VALUE);
        courseComboBox.setStyle("-fx-background-color: rgba(255,255,255,0.05); -fx-text-fill: white;");
        new Thread(() -> {
            try {
                enrollments = acRepo.getUserEnrollments(uid, activeSem);
                List<String> courses = new ArrayList<>();
                for (int i = 0; i < enrollments.length(); i++) {
                    courses.add(enrollments.getJSONObject(i).optString("course_code"));
                }
                Platform.runLater(() -> courseComboBox.getItems().addAll(courses));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
        Label sessionTypeLabel = new Label("Session Type");
        sessionTypeLabel.setStyle("-fx-text-fill: #8A95A5;");
        sessionTypeGroup = new ToggleGroup();
        ToggleButton theoryBtn = new ToggleButton("Theory (90 min)");
        theoryBtn.setToggleGroup(sessionTypeGroup);
        theoryBtn.setSelected(true);
        theoryBtn.setMaxWidth(Double.MAX_VALUE);
        theoryBtn.setStyle("-fx-text-fill: white;");
        HBox.setHgrow(theoryBtn, javafx.scene.layout.Priority.ALWAYS);
        ToggleButton labBtn = new ToggleButton("Lab (3 hrs)");
        labBtn.setToggleGroup(sessionTypeGroup);
        labBtn.setMaxWidth(Double.MAX_VALUE);
        labBtn.setStyle("-fx-text-fill: white;");
        HBox.setHgrow(labBtn, javafx.scene.layout.Priority.ALWAYS);
        HBox sessionBox = new HBox(10, theoryBtn, labBtn);
        sessionBox.setAlignment(Pos.CENTER);
        Label dateLabel = new Label("Date");
        dateLabel.setStyle("-fx-text-fill: #8A95A5;");
        datePicker = new DatePicker(LocalDate.now());
        datePicker.setPrefWidth(Double.MAX_VALUE);
        Label timeLabel = new Label("Start Time (e.g., 09:00 AM)");
        timeLabel.setStyle("-fx-text-fill: #8A95A5;");
        timeField = new TextField();
        timeField.setPromptText("Tap to pick start time");
        timeField.setStyle("-fx-background-color: rgba(255,255,255,0.05); -fx-text-fill: white;");
        Label roomLabel = new Label("Room / Venue");
        roomLabel.setStyle("-fx-text-fill: #8A95A5;");
        roomField = new TextField();
        roomField.setStyle("-fx-background-color: rgba(255,255,255,0.05); -fx-text-fill: white;");
        Button saveBtn = new Button("SAVE ENTRY");
        saveBtn.setPrefWidth(Double.MAX_VALUE);
        saveBtn.setStyle("-fx-background-color: #00E5FF; -fx-text-fill: black; -fx-font-weight: bold;");
        saveBtn.setOnAction(e -> saveEntry(modalStage));
        Button cancelBtn = new Button("Cancel");
        cancelBtn.setPrefWidth(Double.MAX_VALUE);
        cancelBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: white;");
        cancelBtn.setOnAction(e -> modalStage.close());
        layout.getChildren().addAll(
            titleLabel,
            courseLabel, courseComboBox,
            sessionTypeLabel, sessionBox,
            dateLabel, datePicker,
            timeLabel, timeField,
            roomLabel, roomField,
            saveBtn, cancelBtn
        );
        Scene scene = new Scene(layout, 350, 600);
        modalStage.setScene(scene);
        modalStage.showAndWait();
    }
    private void saveEntry(Stage stage) {
        String course = courseComboBox.getValue();
        LocalDate date = datePicker.getValue();
        String time = timeField.getText();
        String room = roomField.getText();
        if (course == null || date == null || time.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Please fill in course, date, and start time.");
            alert.show();
            return;
        }
        ToggleButton selectedMode = (ToggleButton) sessionTypeGroup.getSelectedToggle();
        boolean isLab = selectedMode.getText().contains("Lab");
        new Thread(() -> {
            try {
                String courseName = "Manual Entry";
                String faculty = "N/A";
                String dateStr = date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                String endTime = isLab ? "End (+3 hrs)" : "End (+90m)"; 
                exRepo.addManualClass(uid, dateStr, course, courseName, time, endTime, room, faculty);
                Platform.runLater(() -> {
                    onComplete.run();
                    stage.close();
                });
            } catch (Exception ex) {
                ex.printStackTrace();
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to save entry: " + ex.getMessage());
                    alert.show();
                });
            }
        }).start();
    }
}