package com.ewumatelite.features.schedule.presentation;

import com.ewumatelite.core.ui.LayoutFactory;
import com.ewumatelite.core.utils.PlatformUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ScheduleScreen {
    private final Stage stage;
    private final String uid;
    private final String activeSem;

    public ScheduleScreen(Stage stage, String uid, String activeSem) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
    }

    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/schedule.fxml"));
            Parent scheduleLayout = loader.load();
            
            ScheduleController controller = loader.getController();
            controller.initData(stage, uid, activeSem);

            Parent adaptiveLayout = LayoutFactory.create(stage, uid, activeSem, "Manage Schedule", scheduleLayout);
            
            Scene scene = new Scene(adaptiveLayout, PlatformUtils.getScreenWidth(), PlatformUtils.getScreenHeight());
            scene.getStylesheets().add(getClass().getResource("/com/ewumatelite/styles.css").toExternalForm());
            stage.setTitle("EWUmate Lite - Manage Schedule");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}