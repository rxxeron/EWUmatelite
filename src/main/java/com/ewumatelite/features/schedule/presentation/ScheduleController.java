package com.ewumatelite.features.schedule.presentation;

import com.ewumatelite.core.utils.LogExporter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ScheduleController {

    @FXML private VBox upcomingListContainer;
    @FXML private VBox pendingListContainer;

    private String uid;
    private String activeSem;
    private Stage stage;

    public void initData(Stage stage, String uid, String activeSem) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
        
        LogExporter.log("ScheduleController initialized for user " + uid + " in semester " + activeSem);
        loadScheduleData();
    }

    private void loadScheduleData() {
        // Run network tasks on background thread
        new Thread(() -> {
            // Simulate network delay
            try { Thread.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }

            Platform.runLater(() -> {
                // Clear existing
                upcomingListContainer.getChildren().clear();
                pendingListContainer.getChildren().clear();

                // Mock Upcoming Classes
                upcomingListContainer.getChildren().add(createCard("CSE325 - Operating Systems", "10:10 AM - 11:40 AM", "Room 402"));
                upcomingListContainer.getChildren().add(createCard("CSE326 - OS Lab", "01:20 PM - 02:50 PM", "Room 410"));
                
                // Mock Pending Actions
                pendingListContainer.getChildren().add(createCard("Meeting with Advising", "04:00 PM", "Room 718"));
            });
        }).start();
    }

    private VBox createCard(String title, String time, String location) {
        VBox card = new VBox(5);
        card.setStyle("-fx-background-color: #1E2836; -fx-padding: 15; -fx-background-radius: 8;");
        
        Label titleLabel = new Label(title);
        titleLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold; -fx-font-size: 14;");
        
        Label timeLabel = new Label(time);
        timeLabel.setStyle("-fx-text-fill: #00e5ff;");
        
        Label locLabel = new Label(location);
        locLabel.setStyle("-fx-text-fill: #b0bec5;");
        
        card.getChildren().addAll(titleLabel, timeLabel, locLabel);
        return card;
    }
}