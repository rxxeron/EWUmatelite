package com.ewumatelite.features.semester_progress.presentation;

import com.ewumatelite.core.ui.LayoutFactory;
import com.ewumatelite.core.utils.PlatformUtils;
import com.ewumatelite.features.sidebar.presentation.Sidebar;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.io.IOException;

public class CourseProgressDetailScreen {
    private final Stage stage;
    private final String uid;
    private final String activeSem;
    private final JSONObject courseData;
    private final Runnable onBackCallback;

    public CourseProgressDetailScreen(Stage stage, String uid, String activeSem, JSONObject courseData, Runnable onBackCallback) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
        this.courseData = courseData;
        this.onBackCallback = onBackCallback;
    }

    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/course_progress_detail.fxml"));
            VBox detailLayout = loader.load();
            
            CourseProgressDetailController controller = loader.getController();
            controller.initData(stage, uid, activeSem, courseData, onBackCallback);
            
            Parent adaptiveLayout = LayoutFactory.create(stage, uid, activeSem, "Semester Progress", detailLayout);

            Scene scene = new Scene(adaptiveLayout, PlatformUtils.getScreenWidth(), PlatformUtils.getScreenHeight());
            scene.getStylesheets().add(getClass().getResource("/com/ewumatelite/styles.css").toExternalForm());
            stage.setTitle("Edit " + courseData.optString("course_code"));
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
