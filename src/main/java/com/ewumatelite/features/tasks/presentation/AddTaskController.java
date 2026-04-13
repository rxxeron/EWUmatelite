package com.ewumatelite.features.tasks.presentation;

import com.ewumatelite.core.repositories.AcademicRepository;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.time.LocalDate;

public class AddTaskController {

    @FXML private TextField titleField;
    @FXML private ComboBox<String> courseComboBox;
    @FXML private ComboBox<String> typeComboBox;
    @FXML private ComboBox<String> hourComboBox;
    @FXML private ComboBox<String> minuteComboBox;
    @FXML private ComboBox<String> ampmComboBox;
    @FXML private DatePicker datePicker;
    @FXML private Button saveBtn;
    @FXML private Button cancelBtn;

    private Stage dialogStage;
    private String uid;
    private String semesterCode;
    private String editingTaskId; // If null, it's a new task
    private Runnable onTaskAddedCallback;
    private boolean isSaving = false;

    @FXML
    public void initialize() {
        typeComboBox.setItems(FXCollections.observableArrayList(
            "Mid Exam", "Final Exam", "Quiz", "Short Quiz", "Term Paper",
            "Assignment", "Project", "Lab Report", "Others"
        ));
        typeComboBox.getSelectionModel().selectFirst();
        datePicker.setValue(LocalDate.now());

        // Initialize time dropdowns
        java.util.List<String> hours = new java.util.ArrayList<>();
        for (int i = 1; i <= 12; i++) hours.add(String.format("%02d", i));
        hourComboBox.setItems(FXCollections.observableArrayList(hours));
        hourComboBox.getSelectionModel().select("11");

        java.util.List<String> minutes = new java.util.ArrayList<>();
        for (int i = 0; i <= 59; i++) minutes.add(String.format("%02d", i));
        minuteComboBox.setItems(FXCollections.observableArrayList(minutes));
        minuteComboBox.getSelectionModel().select("59");

        ampmComboBox.setItems(FXCollections.observableArrayList("AM", "PM"));
        ampmComboBox.getSelectionModel().select("PM");
    }

    public void initData(Stage dialogStage, String uid, String semesterCode, Runnable onTaskAddedCallback) {
        initDataWithTask(dialogStage, uid, semesterCode, null, onTaskAddedCallback);
    }

    public void initDataWithTask(Stage dialogStage, String uid, String semesterCode, org.json.JSONObject taskToEdit, Runnable onTaskAddedCallback) {
        this.dialogStage = dialogStage;
        this.uid = uid;
        this.semesterCode = semesterCode;
        this.onTaskAddedCallback = onTaskAddedCallback;
        
        loadEnrolledCourses();

        if (taskToEdit != null) {
            this.editingTaskId = taskToEdit.optString("id");
            titleField.setText(taskToEdit.optString("title"));
            courseComboBox.setValue(taskToEdit.optString("course_code"));
            typeComboBox.setValue(taskToEdit.optString("type"));
            
            String dueDate = taskToEdit.optString("due_date", "");
            if (dueDate.length() >= 10) {
                datePicker.setValue(LocalDate.parse(dueDate.substring(0, 10)));
            }
            if (dueDate.length() >= 16) {
                try {
                    String timePart = dueDate.substring(11, 16); // HH:mm
                    int h = Integer.parseInt(timePart.substring(0, 2));
                    int m = Integer.parseInt(timePart.substring(3, 5));
                    
                    String ampm = h >= 12 ? "PM" : "AM";
                    int displayHour = h % 12;
                    if (displayHour == 0) displayHour = 12;
                    
                    hourComboBox.setValue(String.format("%02d", displayHour));
                    minuteComboBox.setValue(String.format("%02d", m));
                    ampmComboBox.setValue(ampm);
                } catch (Exception e) {}
            }
            saveBtn.setText("Update Task");
        }
    }
    
    private void loadEnrolledCourses() {
        new Thread(() -> {
            try {
                org.json.JSONArray enrollments = new AcademicRepository().getUserEnrollments(uid, semesterCode);
                java.util.List<String> courses = new java.util.ArrayList<>();
                for (int i = 0; i < enrollments.length(); i++) {
                    String courseCode = enrollments.getJSONObject(i).optString("course_code");
                    if (courseCode != null && !courseCode.isEmpty() && !courses.contains(courseCode)) {
                        courses.add(courseCode);
                    }
                }
                Platform.runLater(() -> {
                    courseComboBox.setItems(FXCollections.observableArrayList(courses));
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @FXML
    private void onSave(ActionEvent event) {
        com.ewumatelite.core.utils.LogExporter.log("ACTION: onSave Triggered in " + this.getClass().getSimpleName());
        if (isSaving) return;

        String title = titleField.getText().trim();
        if (title.isEmpty()) {
            com.ewumatelite.core.utils.LogExporter.log("ERROR: Task saving aborted (Empty Title)");
            showAlert("Validation Error", "Task title cannot be empty.");
            return;
        }

        String selectedCourse = courseComboBox.getValue();
        final String course = (selectedCourse == null) ? "" : selectedCourse;
        String type = typeComboBox.getValue();
        
        com.ewumatelite.core.utils.LogExporter.log("ACTION: Saving Task - Title: " + title + ", Course: " + course + ", Type: " + type);

        LocalDate date = datePicker.getValue();
        String dateStr = date != null ? date.toString() : "";
        
        if (!dateStr.isEmpty()) {
            try {
                int hh = Integer.parseInt(hourComboBox.getValue());
                String mm = minuteComboBox.getValue();
                String ampm = ampmComboBox.getValue();
                
                if (ampm.equals("PM") && hh < 12) hh += 12;
                if (ampm.equals("AM") && hh == 12) hh = 0;
                
                dateStr += String.format("T%02d:%s:00Z", hh, mm);
            } catch (Exception e) {}
        }

        final String finalDateStr = dateStr;
        isSaving = true;
        saveBtn.setText("Saving...");
        saveBtn.setDisable(true);

        new Thread(() -> {
            try {
                if (editingTaskId == null) {
                    new AcademicRepository().createTask(uid, title, course, finalDateStr, type, semesterCode);
                    com.ewumatelite.core.utils.LogExporter.log("SUCCESS: Task created successfully");
                } else {
                    new AcademicRepository().updateFullTask(editingTaskId, uid, title, course, finalDateStr, type, semesterCode);
                    com.ewumatelite.core.utils.LogExporter.log("SUCCESS: Task updated successfully");
                }
                Platform.runLater(() -> {
                    if (onTaskAddedCallback != null) {
                        onTaskAddedCallback.run();
                    }
                    dialogStage.close();
                });
            } catch (Exception e) {
                com.ewumatelite.core.utils.LogExporter.log("ERROR: Failed to save task: " + e.getMessage());
                Platform.runLater(() -> {
                    isSaving = false;
                    saveBtn.setText("Save Task");
                    saveBtn.setDisable(false);
                    showAlert("Error", "Failed to save task: " + e.getMessage());
                });
            }
        }).start();
    }

    @FXML
    private void onCancel(ActionEvent event) {
        com.ewumatelite.core.utils.LogExporter.log("ACTION: Cancelled Task creation/editing");
        if (!isSaving) {
            dialogStage.close();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}