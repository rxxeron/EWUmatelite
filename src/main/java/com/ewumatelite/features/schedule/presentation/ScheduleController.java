package com.ewumatelite.features.schedule.presentation;

import com.ewumatelite.core.repositories.ExceptionRepository;
import com.ewumatelite.core.repositories.AcademicRepository;
import com.ewumatelite.core.utils.LogExporter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.layout.Region;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class ScheduleController {

    @FXML private VBox upcomingListContainer;
    @FXML private VBox pendingListContainer;
    
    @FXML private Label lblUpcoming;
    @FXML private Region indUpcoming;
    @FXML private Label lblPending;
    @FXML private Region indPending;

    private String uid;
    private String activeSem;
    private Stage stage;

    private final ExceptionRepository exRepo = new ExceptionRepository();
    private final AcademicRepository acRepo = new AcademicRepository();

    public void initData(Stage stage, String uid, String activeSem) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
        
        LogExporter.log("ScheduleController initialized for user " + uid + " in semester " + activeSem);
        loadScheduleData();
    }

    private void loadScheduleData() {
        Platform.runLater(() -> {
            upcomingListContainer.getChildren().clear();
            pendingListContainer.getChildren().clear();
            pendingListContainer.getChildren().add(new Label("Loading Data..."));
            switchToUpcoming();
        });

        new Thread(() -> {
            try {
                LocalDate now = LocalDate.now();
                String startStr = now.toString();
                String endStr = now.plusDays(14).toString();

                // Fetch weekly grid 
                JSONObject dbData = acRepo.getDashboardData(uid, activeSem, startStr);
                JSONObject weeklyGridRaw = dbData.optJSONObject("weekly_grid");
                final JSONObject weeklyGrid = (weeklyGridRaw == null) ? new JSONObject() : weeklyGridRaw;

                // Fetch Exceptions mapping
                final JSONArray allExceptions = exRepo.fetchExceptions(uid);
                
                // Fetch Holidays
                final JSONArray allHolidays = acRepo.fetchUpcomingHolidays(activeSem, startStr, endStr);

                Platform.runLater(() -> {
                    upcomingListContainer.getChildren().clear();
                    pendingListContainer.getChildren().clear();

                    buildTwoWeekSchedule(weeklyGrid, allExceptions, allHolidays);
                    buildPendingActions(allExceptions);
                });

            } catch (Exception e) {
                e.printStackTrace();
                Platform.runLater(() -> {
                    upcomingListContainer.getChildren().add(new Label("Error loading schedule."));
                    pendingListContainer.getChildren().clear();
                });
            }
        }).start();
    }

    @FXML
    private void switchToUpcoming() {
        upcomingListContainer.setVisible(true);
        upcomingListContainer.setManaged(true);
        pendingListContainer.setVisible(false);
        pendingListContainer.setManaged(false);
        
        lblUpcoming.setStyle("-fx-text-fill: #00E5FF; -fx-font-weight: bold; -fx-font-size: 14;");
        indUpcoming.setStyle("-fx-background-color: #00E5FF;");
        
        lblPending.setStyle("-fx-text-fill: #8A95A5; -fx-font-weight: bold; -fx-font-size: 14;");
        indPending.setStyle("-fx-background-color: transparent;");
    }

    @FXML
    private void switchToPending() {
        pendingListContainer.setVisible(true);
        pendingListContainer.setManaged(true);
        upcomingListContainer.setVisible(false);
        upcomingListContainer.setManaged(false);
        
        lblPending.setStyle("-fx-text-fill: #00E5FF; -fx-font-weight: bold; -fx-font-size: 14;");
        indPending.setStyle("-fx-background-color: #00E5FF;");
        
        lblUpcoming.setStyle("-fx-text-fill: #8A95A5; -fx-font-weight: bold; -fx-font-size: 14;");
        indUpcoming.setStyle("-fx-background-color: transparent;");
    }

    @FXML
    private void handleAddClass() {
        LogExporter.log("ACTION: Add Class FAB Clicked");
        new ManualEntryModal(uid, activeSem, this::loadScheduleData).show();
    }

    private void buildTwoWeekSchedule(JSONObject weeklyGrid, JSONArray allExceptions, JSONArray allHolidays) {
        LocalDate current = LocalDate.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("EEEE, MMM d");
        DateTimeFormatter matchFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        for (int i = 0; i < 14; i++) {
            LocalDate date = current.plusDays(i);
            String dateMatchingStr = date.format(matchFormat);
            String dayOfWeek = date.getDayOfWeek().toString();
            // Fallback match the casing to Flutter's map
            dayOfWeek = dayOfWeek.substring(0, 1).toUpperCase() + dayOfWeek.substring(1).toLowerCase();
            
            boolean processedHoliday = false;
            String holidayName = "";

            // Check if it's a holiday
            if (allHolidays != null) {
                for (int h = 0; h < allHolidays.length(); h++) {
                    JSONObject hol = allHolidays.getJSONObject(h);
                    if (dateMatchingStr.equals(hol.optString("event_date"))) {
                        String title = hol.optString("title", hol.optString("name", "")).toLowerCase();
                        String reason = hol.optString("name", "Holiday");
                        if (title.contains("swap") || title.contains("makeup")) {
                            String[] words = title.split(" ");
                            if (words.length > 0) {
                                String swapDayRaw = words[words.length - 1];
                                if (swapDayRaw.endsWith("s")) swapDayRaw = swapDayRaw.substring(0, swapDayRaw.length() - 1);
                                dayOfWeek = swapDayRaw.substring(0, 1).toUpperCase() + swapDayRaw.substring(1).toLowerCase();
                            }
                        } else if (!title.contains("makeup") && !title.contains("advising")) {
                            holidayName = reason;
                            processedHoliday = true;
                        }
                    }
                }
            }

            JSONArray dayClasses = weeklyGrid.optJSONArray(dayOfWeek);
            
            // Gather makeups for this date
            JSONArray dateExceptions = new JSONArray();
            if (allExceptions != null) {
                for (int exIdx = 0; exIdx < allExceptions.length(); exIdx++) {
                    JSONObject ex = allExceptions.getJSONObject(exIdx);
                    if (dateMatchingStr.equals(ex.optString("date"))) {
                        dateExceptions.put(ex);
                    }
                }
            }

            boolean hasContent = false;
            VBox dayContainer = new VBox(5);

            if (processedHoliday) {
                dayContainer.getChildren().add(createHolidayCard(holidayName));
                hasContent = true;
            } else if (dayClasses != null && dayClasses.length() > 0) {
                for (int j = 0; j < dayClasses.length(); j++) {
                    JSONObject classObj = dayClasses.getJSONObject(j);
                    String courseCode = classObj.optString("courseCode", classObj.optString("course_code", "Unknown"));
                    String timeStr = classObj.optString("startTime", "") + " - " + classObj.optString("endTime", "");
                    String room = classObj.optString("room", "");
                    
                    if (isCancelled(dateExceptions, dateMatchingStr, courseCode)) {
                        continue; // Skip cancelled entries from Upcoming timeline entirely as Flutter does
                    }
                    dayContainer.getChildren().add(createUpcomingCard(courseCode, timeStr, room, dateMatchingStr));
                    hasContent = true;
                }
            }

            // Append Exceptions (Makeup/Manual)
            for (int e = 0; e < dateExceptions.length(); e++) {
                JSONObject ex = dateExceptions.getJSONObject(e);
                String type = ex.optString("type");
                if ("makeup".equals(type) || "manual".equals(type)) {
                    String timeStr = ex.optString("start_time", "") + " - " + ex.optString("end_time", "");
                    String room = ex.optString("room", "");
                    String courseCode = ex.optString("course_code", "Unknown");
                    // Assuming createUpcomingCard takes courseCode, timeStr, room, dateStr
                    dayContainer.getChildren().add(createUpcomingCard(courseCode + " (Makeup)", timeStr, room, dateMatchingStr));
                    hasContent = true;
                }
            }

            if (hasContent) {
                Label dateHeader = new Label(date.format(dtf));
                dateHeader.setStyle("-fx-text-fill: #8A95A5; -fx-font-weight: bold; -fx-font-size: 14; -fx-padding: 10 0 5 0;");
                upcomingListContainer.getChildren().add(dateHeader);
                upcomingListContainer.getChildren().add(dayContainer);
            }
        }
        if (upcomingListContainer.getChildren().isEmpty()) {
            upcomingListContainer.getChildren().add(new Label("No upcoming classes scheduled."));
        }
    }

    private void buildPendingActions(JSONArray allExceptions) {
        for (int i = 0; i < allExceptions.length(); i++) {
            JSONObject ex = allExceptions.getJSONObject(i);
            String type = ex.optString("type");
            JSONObject meta = ex.optJSONObject("metadata");
            
            if ("cancel".equals(type) && meta != null && meta.optBoolean("pendingMakeup", false)) {
                String courseCode = ex.optString("course_code");
                String date = ex.optString("date");
                String exId = ex.optString("id");
                
                VBox card = createPendingCard(exId, courseCode, "Cancelled on " + date);
                pendingListContainer.getChildren().add(card);
            }
        }
        if (pendingListContainer.getChildren().isEmpty()) {
            pendingListContainer.getChildren().add(new Label("No pending makeups required."));
        }
    }

    private boolean isCancelled(JSONArray allExceptions, String date, String courseCode) {
        for (int i = 0; i < allExceptions.length(); i++) {
            JSONObject ex = allExceptions.getJSONObject(i);
            if ("cancel".equals(ex.optString("type")) 
                && courseCode.equals(ex.optString("course_code")) 
                && date.equals(ex.optString("date"))) {
                return true;
            }
        }
        return false;
    }

    private VBox createHolidayCard(String reason) {
        VBox card = new VBox(5);
        card.setStyle("-fx-background-color: #1F7883; -fx-padding: 15; -fx-background-radius: 8;");
        
        Label titleLabel = new Label("Holiday");
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");
        
        Label subLabel = new Label("It's a " + reason + "!\nNo classes scheduled.");
        subLabel.setStyle("-fx-text-fill: #E0F2F1;");
        
        card.getChildren().addAll(titleLabel, subLabel);
        return card;
    }

    private VBox createUpcomingCard(String title, String time, String location, String dateStr) {
        VBox card = new VBox(5);
        card.setStyle("-fx-background-color: #1E2836; -fx-padding: 15; -fx-background-radius: 8;");
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");
        
        Label timeLabel = new Label(time);
        timeLabel.setStyle("-fx-text-fill: #00e5ff;");
        
        HBox bottom = new HBox();
        Label locLabel = new Label(location);
        locLabel.setStyle("-fx-text-fill: #b0bec5;");
        
        Button cancelBtn = new Button("Cancel Session");
        cancelBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ff4444; -fx-cursor: hand;");
        cancelBtn.setOnAction(e -> showCancelDialog(title, dateStr));
        
        bottom.getChildren().addAll(locLabel, cancelBtn);
        HBox.setHgrow(locLabel, Priority.ALWAYS);
        locLabel.setMaxWidth(Double.MAX_VALUE);
        
        card.getChildren().addAll(titleLabel, timeLabel, bottom);
        return card;
    }

    private VBox createPendingCard(String exId, String title, String subtitle) {
        VBox card = new VBox(5);
        card.setStyle("-fx-background-color: #1E2836; -fx-padding: 15; -fx-background-radius: 8; -fx-border-color: #ffaa00; -fx-border-radius: 8; -fx-border-width: 1;");
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");
        
        Label subLabel = new Label(subtitle);
        subLabel.setStyle("-fx-text-fill: #b0bec5;");
        
        Button deleteBtn = new Button("Delete (Undo Cancel)");
        deleteBtn.setStyle("-fx-background-color: #aa0000; -fx-text-fill: white; -fx-cursor: hand;");
        deleteBtn.setOnAction(e -> {
            new Thread(() -> {
                try {
                    exRepo.removeException(exId);
                    loadScheduleData(); // Refresh UI
                } catch (Exception ex) { ex.printStackTrace(); }
            }).start();
        });

        card.getChildren().addAll(titleLabel, subLabel, deleteBtn);
        return card;
    }

    private void showCancelDialog(String courseCode, String dateStr) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Cancel Session");
        alert.setHeaderText("Cancel " + courseCode + " on " + dateStr + "?");
        alert.setContentText("You can schedule a makeup later from 'Pending Events'.");
        
        ButtonType btnSkip = new ButtonType("Confirm (Skip Makeup)", ButtonBar.ButtonData.OK_DONE);
        ButtonType btnMakeup = new ButtonType("Require Makeup", ButtonBar.ButtonData.OTHER);
        ButtonType btnBack = new ButtonType("Back", ButtonBar.ButtonData.CANCEL_CLOSE);

        alert.getButtonTypes().setAll(btnSkip, btnMakeup, btnBack);
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && (result.get() == btnSkip || result.get() == btnMakeup)) {
            boolean pendingMakeup = (result.get() == btnMakeup);
            new Thread(() -> {
                try {
                    exRepo.addCancellation(uid, dateStr, courseCode, pendingMakeup);
                    loadScheduleData(); // Refresh UI
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
        }
    }
}