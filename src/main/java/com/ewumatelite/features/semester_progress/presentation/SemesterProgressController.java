package com.ewumatelite.features.semester_progress.presentation;
import com.ewumatelite.core.repositories.AcademicRepository;
import com.ewumatelite.core.utils.MarksCalculator;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.FlowPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.geometry.Insets;
public class SemesterProgressController {
    @FXML private FlowPane courseContainer;
    @FXML private Label semesterLabel;
    @FXML private Label loadingLabel;
    private Stage stage;
    private String uid;
    private String activeSem;
    public void initData(Stage stage, String uid, String activeSem) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
        semesterLabel.setText(activeSem);
        loadProgressData();
    }
    private void loadProgressData() {
        courseContainer.getChildren().clear();
        courseContainer.getChildren().add(loadingLabel);
        new Thread(() -> {
            try {
                JSONArray data = new AcademicRepository().getSemesterProgressData(uid, activeSem);
                Platform.runLater(() -> renderData(data));
            } catch (Exception e) {
                Platform.runLater(() -> {
                    loadingLabel.setText("Failed to load. " + e.getMessage());
                });
            }
        }).start();
    }
    private void renderData(JSONArray data) {
        courseContainer.getChildren().clear();
        if (data.length() == 0) {
            Label noData = new Label("No enrollment or marks found.");
            noData.setStyle("-fx-text-fill: #475569;");
            courseContainer.getChildren().add(noData);
            return;
        }
        for (int i = 0; i < data.length(); i++) {
            JSONObject course = data.getJSONObject(i);
            courseContainer.getChildren().add(createCourseCard(course));
        }
    }
    private VBox createCourseCard(JSONObject courseData) {
        String code = courseData.optString("course_code", "Course");
        double total = 0.0;
        total += courseData.optDouble("obt_mid", 0.0);
        total += courseData.optDouble("obt_final", 0.0);
        total += courseData.optDouble("obt_attendance", 0.0);
        total += courseData.optDouble("obt_project", 0.0);
        total += courseData.optDouble("obt_presentation", 0.0);
        total += courseData.optDouble("obt_viva", 0.0);
        total += courseData.optDouble("obt_assignment", 0.0);
        total += courseData.optDouble("obt_term_paper", 0.0);
        total += courseData.optDouble("obt_lab", 0.0);
        total += courseData.optDouble("obt_class_performance", 0.0);
        String qStrategy = courseData.optString("quiz_strategy", "best_one");
        int qN = courseData.optInt("quiz_n", 1);
        double qMax = courseData.optDouble("dist_quiz", 0.0);
        JSONArray qArr = courseData.optJSONArray("obt_quizzes");
        total += MarksCalculator.calculateQuizValue(qArr, qStrategy, qN, qMax);
        JSONObject extra = courseData.optJSONObject("marks_data");
        if (extra == null) extra = new JSONObject();
        String sqStrategy = extra.optString("short_quiz_strategy", "best_one");
        int sqN = courseData.optInt("short_quiz_n", 1);
        double sqMax = courseData.optDouble("dist_short_quiz", 0.0);
        JSONArray sqArr = courseData.optJSONArray("obt_short_quizzes");
        total += MarksCalculator.calculateQuizValue(sqArr, sqStrategy, sqN, sqMax);
        String percentageText = String.format("%.1f%%", total);
        String grade = "F";
        String gradeColor = "#F43F5E";
        if (total >= 80) { grade = "A+"; gradeColor = "#00e0ff"; }
        else if (total >= 75) { grade = "A"; gradeColor = "#00e0ff"; }
        else if (total >= 70) { grade = "A-"; gradeColor = "#00e0ff"; }
        else if (total >= 65) { grade = "B+"; gradeColor = "#00e0ff"; }
        else if (total >= 60) { grade = "B"; gradeColor = "#00e0ff"; }
        else if (total >= 55) { grade = "B-"; gradeColor = "#00e0ff"; }
        else if (total >= 50) { grade = "C+"; gradeColor = "#FFAB40"; }
        else if (total >= 45) { grade = "C"; gradeColor = "#FFAB40"; }
        else if (total >= 40) { grade = "D"; gradeColor = "#FFAB40"; }
        VBox card = new VBox(15);
        card.setPadding(new Insets(20));
        card.setPrefWidth(320);
        card.setMinWidth(320);
        card.setStyle("-fx-background-color: #172033; -fx-background-radius: 20; -fx-cursor: hand;");
        HBox row1 = new HBox();
        row1.setAlignment(Pos.CENTER_LEFT);
        Label codeLabel = new Label(code);
        codeLabel.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 24px; -fx-font-weight: bold;");
        Region spacer1 = new Region();
        HBox.setHgrow(spacer1, Priority.ALWAYS);
        Label percentLabel = new Label(percentageText);
        percentLabel.setStyle("-fx-text-fill: " + gradeColor + "; -fx-font-size: 20px; -fx-font-weight: bold;");
        row1.getChildren().addAll(codeLabel, spacer1, percentLabel);
        HBox row2 = new HBox();
        row2.setAlignment(Pos.CENTER_LEFT);
        Label titleLabel = new Label("Course Details Here"); 
        titleLabel.setStyle("-fx-text-fill: #b3b9c5; -fx-font-size: 13px; -fx-font-weight: bold;");
        Region spacer2 = new Region();
        HBox.setHgrow(spacer2, Priority.ALWAYS);
        Label gradeBadge = new Label(grade);
        gradeBadge.setAlignment(Pos.CENTER);
        gradeBadge.setMinWidth(25);
        gradeBadge.setPadding(new Insets(1, 8, 1, 8));
        gradeBadge.setStyle("-fx-border-color: " + gradeColor + "55; -fx-border-width: 1.5; -fx-border-radius: 6; -fx-text-fill: " + gradeColor + "; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 6; -fx-background-color: " + gradeColor + "15;");
        row2.getChildren().addAll(titleLabel, spacer2, gradeBadge);
        VBox progContainer = new VBox(5);
        Pane barBg = new Pane();
        barBg.setPrefHeight(6);
        barBg.setStyle("-fx-background-color: " + gradeColor + "20; -fx-background-radius: 3;");
        Pane barFill = new Pane();
        barFill.setPrefHeight(6);
        double pc = Math.min(total, 100) / 100.0;
        barFill.setPrefWidth(280 * pc);
        barFill.setStyle("-fx-background-color: " + gradeColor + "; -fx-background-radius: 3;");
        barBg.getChildren().add(barFill);
        HBox maxHbox = new HBox();
        maxHbox.setAlignment(Pos.CENTER_RIGHT);
        Label maxLabel = new Label("100 Max");
        maxLabel.setStyle("-fx-text-fill: #617188; -fx-font-size: 10px; -fx-font-weight: bold;");
        maxHbox.getChildren().add(maxLabel);
        progContainer.getChildren().addAll(barBg, maxHbox);
        VBox marksList = new VBox(12);
        marksList.setPadding(new Insets(10, 0, 0, 0));
        int rowCount = 0;
        if (qArr != null && rowCount < 4) {
            for (int j = 0; j < qArr.length() && rowCount < 4; j++) {
                double val = qArr.optDouble(j, 0);
                if (val > 0) {
                    marksList.getChildren().add(createMarkRow("Quiz " + (j+1), val));
                    rowCount++;
                }
            }
        }
        if (courseData.has("obt_mid") && courseData.optDouble("obt_mid", 0) > 0 && rowCount < 4) {
            marksList.getChildren().add(createMarkRow("Mid", courseData.optDouble("obt_mid", 0)));
            rowCount++;
        }
        if (courseData.has("obt_attendance") && courseData.optDouble("obt_attendance", 0) > 0 && rowCount < 4) {
            marksList.getChildren().add(createMarkRow("Attendance", courseData.optDouble("obt_attendance", 0)));
            rowCount++;
        }
        card.getChildren().addAll(row1, row2, progContainer, marksList);
        card.setOnMouseClicked(e -> {
            new CourseProgressDetailScreen(stage, uid, activeSem, courseData, () -> {
                new SemesterProgressScreen(stage, uid, activeSem).show();
            }).show();
        });
        card.setOnMouseEntered(e -> card.setStyle("-fx-background-color: #1f2b45; -fx-background-radius: 20; -fx-cursor: hand;"));
        card.setOnMouseExited(e -> card.setStyle("-fx-background-color: #172033; -fx-background-radius: 20; -fx-cursor: hand;"));
        return card;
    }
    private HBox createMarkRow(String name, double val) {
        HBox row = new HBox();
        row.setAlignment(Pos.CENTER_LEFT);
        Label nl = new Label(name);
        nl.setStyle("-fx-text-fill: #a6b2c2; -fx-font-size: 13px; -fx-font-weight: bold;");
        Region sp = new Region();
        HBox.setHgrow(sp, Priority.ALWAYS);
        Label vl = new Label(String.valueOf(val).replace(".0", ""));
        vl.setStyle("-fx-text-fill: #FFFFFF; -fx-font-size: 14px; -fx-font-weight: bold;");
        row.getChildren().addAll(nl, sp, vl);
        return row;
    }
}
