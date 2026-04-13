package com.ewumatelite.features.tasks.presentation;

import com.ewumatelite.core.repositories.AcademicRepository;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TasksController {

    @FXML private VBox upcomingTasksContainer;
    @FXML private VBox overdueTasksContainer;
    @FXML private VBox completedTasksContainer;
    @FXML private TabPane taskTabPane;

    private Stage stage;
    private String uid;
    private String activeSem;

    // Theme mimicking Flutter Tasks UI
    private final String CARD_BG = "#1E293B"; // Dark slate
    private final String TEXT_PRIMARY = "#FFFFFF";
    private final String TEXT_SECONDARY = "#8A95A5";
    private final String ACCENT_TEAL = "#22D3EE";
    private final String ACCENT_RED = "#F43F5E";

    public void initData(Stage stage, String uid, String activeSem) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
        
        // CSS to clean up tab pane background (since default JavaFX TabPane usually looks bad with dark themes)
        taskTabPane.setStyle("-fx-background-color: transparent; -fx-tab-min-width: 100;");
        
        loadTasks();
    }

    private void loadTasks() {
        upcomingTasksContainer.getChildren().clear();
        overdueTasksContainer.getChildren().clear();
        completedTasksContainer.getChildren().clear();

        upcomingTasksContainer.getChildren().add(new Label("Loading tasks..."));

        new Thread(() -> {
            try {
                JSONObject data = new AcademicRepository().getDashboardData(uid, activeSem, LocalDate.now().toString());
                Platform.runLater(() -> populateTasks(data.optJSONArray("tasks")));
            } catch (Exception ex) {
                Platform.runLater(() -> upcomingTasksContainer.getChildren().add(new Label("Error: " + ex.getMessage())));
            }
        }).start();
    }

    private void populateTasks(JSONArray tasks) {
        upcomingTasksContainer.getChildren().clear();
        overdueTasksContainer.getChildren().clear();
        completedTasksContainer.getChildren().clear();

        if (tasks == null || tasks.length() == 0) {
            upcomingTasksContainer.getChildren().add(createEmptyState("No upcoming tasks"));
            overdueTasksContainer.getChildren().add(createEmptyState("No overdue tasks"));
            completedTasksContainer.getChildren().add(createEmptyState("No completed tasks"));
            return;
        }

        LocalDate now = LocalDate.now();

        List<JSONObject> upcoming = new ArrayList<>();
        List<JSONObject> overdue = new ArrayList<>();
        List<JSONObject> completed = new ArrayList<>();

        for (int i = 0; i < tasks.length(); i++) {
            JSONObject t = tasks.getJSONObject(i);
            boolean isComp = t.optBoolean("is_completed", false);
            String dueStr = t.optString("due_date", "");
            
            if (isComp) {
                completed.add(t);
            } else {
                if (dueStr.isEmpty()) {
                    upcoming.add(t);
                } else {
                    try {
                        LocalDate due = LocalDate.parse(dueStr.substring(0, 10));
                        if (due.isBefore(now)) overdue.add(t);
                        else upcoming.add(t);
                    } catch (Exception e) {
                        upcoming.add(t);
                    }
                }
            }
        }

        if (upcoming.isEmpty()) upcomingTasksContainer.getChildren().add(createEmptyState("No upcoming tasks found"));
        else upcoming.forEach(t -> upcomingTasksContainer.getChildren().add(createTaskCard(t, false, false)));

        if (overdue.isEmpty()) overdueTasksContainer.getChildren().add(createEmptyState("No overdue tasks found"));
        else overdue.forEach(t -> overdueTasksContainer.getChildren().add(createTaskCard(t, true, false)));

        if (completed.isEmpty()) completedTasksContainer.getChildren().add(createEmptyState("No completed tasks found"));
        else completed.forEach(t -> completedTasksContainer.getChildren().add(createTaskCard(t, false, true)));
    }

    private HBox createTaskCard(JSONObject task, boolean isOverdue, boolean isCompleted) {
        HBox card = new HBox(15);
        card.setPadding(new Insets(16));
        
        card.setStyle("-fx-background-color: " + (isCompleted ? "#0F172A" : CARD_BG) + "; -fx-background-radius: 20; -fx-border-color: #334155; -fx-border-width: 1; -fx-border-radius: 20;");
        if (isCompleted) card.setOpacity(0.5);

        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(isCompleted);
        checkBox.setStyle("-fx-background-color: transparent;");

        VBox contentBox = new VBox(6);
        Label title = new Label(task.optString("title", "Unknown Task"));
        // Simulating strikethrough logic (JavaFX CSS workaround or direct style)
        String strike = isCompleted ? "-fx-strikethrough: true;" : "";
        title.setStyle("-fx-text-fill: " + TEXT_PRIMARY + "; -fx-font-size: 16px; -fx-font-weight: bold; " + strike);
        
        HBox badgesBox = new HBox(8);
        String cCode = task.optString("course_code", "");
        if (!cCode.isEmpty()) {
            Label codeLabel = new Label(cCode);
            codeLabel.setPadding(new Insets(2, 8, 2, 8));
            // Faking a tinted background with a border hack or web color
            codeLabel.setStyle("-fx-background-color: #164E63; -fx-text-fill: " + ACCENT_TEAL + "; -fx-font-size: 10px; -fx-font-weight: bold; -fx-background-radius: 6;");
            badgesBox.getChildren().add(codeLabel);
        }

        String dueStr = task.optString("due_date", "");
        String dateSub = "No due date";
        if (dueStr.length() >= 10) {
            dateSub = "Due: " + dueStr.substring(0, 10);
            if (dueStr.length() >= 16) {
                try {
                    String timePart = dueStr.substring(11, 16);
                    int h = Integer.parseInt(timePart.substring(0, 2));
                    String m = timePart.substring(3, 5);
                    String ampm = h >= 12 ? " PM" : " AM";
                    int displayHour = h % 12;
                    if (displayHour == 0) displayHour = 12;
                    dateSub += String.format(" at %02d:%s%s", displayHour, m, ampm);
                } catch (Exception e) {}
            }
        }
        
        Label dateLabel = new Label("📅 " + dateSub);
        dateLabel.setStyle("-fx-text-fill: " + (isOverdue ? ACCENT_RED : TEXT_SECONDARY) + "; -fx-font-size: 12px; -fx-font-weight: " + (isOverdue ? "bold" : "normal") + ";");
        badgesBox.getChildren().add(dateLabel);

        contentBox.getChildren().addAll(title, badgesBox);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Label moreIcon = new Label("✎"); // Edit icon instead of ellipsis
        moreIcon.setStyle("-fx-text-fill: " + TEXT_SECONDARY + "; -fx-font-size: 14px; -fx-font-weight: bold; -fx-cursor: hand;");
        moreIcon.setOnMouseClicked(e -> {
            e.consume();
            onEditTaskClicked(task);
        });

        card.setOnMouseClicked(e -> {
            if (!isCompleted) {
                onEditTaskClicked(task);
            }
        });
        card.setStyle("-fx-background-color: " + (isCompleted ? "#0F172A" : CARD_BG) + "; -fx-background-radius: 20; -fx-border-color: #334155; -fx-border-width: 1; -fx-border-radius: 20; -fx-cursor: " + (isCompleted ? "default" : "hand") + ";");

        card.getChildren().addAll(checkBox, contentBox, spacer, moreIcon);
        return card;
    }

    private void onEditTaskClicked(JSONObject task) {
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/fxml/add_task.fxml"));
            VBox page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Edit Task");
            dialogStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            
            javafx.scene.Scene scene = new javafx.scene.Scene(page);
            dialogStage.setScene(scene);

            AddTaskController controller = loader.getController();
            controller.initDataWithTask(dialogStage, uid, activeSem, task, () -> Platform.runLater(this::loadTasks));

            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VBox createEmptyState(String text) {
        VBox box = new VBox(15);
        box.setAlignment(Pos.CENTER);
        Label icon = new Label("📋");
        icon.setStyle("-fx-font-size: 40px; -fx-text-fill: #475569;");
        Label l = new Label(text);
        l.setStyle("-fx-text-fill: #475569; -fx-font-size: 14px;");
        box.getChildren().addAll(icon, l);
        box.setMinHeight(200);
        return box;
    }

    @FXML
    private void onAddTaskClicked() {
        com.ewumatelite.core.utils.LogExporter.log("ACTION: onAddTaskClicked Triggered in " + this.getClass().getSimpleName());
        try {
            javafx.fxml.FXMLLoader loader = new javafx.fxml.FXMLLoader(getClass().getResource("/fxml/add_task.fxml"));
            VBox page = loader.load();

            Stage dialogStage = new Stage();
            dialogStage.setTitle("Add New Task");
            dialogStage.initModality(javafx.stage.Modality.WINDOW_MODAL);
            dialogStage.initOwner(stage);
            
            javafx.scene.Scene scene = new javafx.scene.Scene(page);
            dialogStage.setScene(scene);

            AddTaskController controller = loader.getController();
            controller.initData(dialogStage, uid, activeSem, () -> Platform.runLater(this::loadTasks));

            dialogStage.showAndWait();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}