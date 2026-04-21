package com.ewumatelite.features.tasks.presentation;
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
import java.io.IOException;
public class TasksScreen {
    private final Stage stage;
    private final String uid;
    private final String activeSem;
    public TasksScreen(Stage stage, String uid, String activeSem) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
    }
    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/tasks.fxml"));
            VBox tasksLayout = loader.load();
            TasksController controller = loader.getController();
            controller.initData(stage, uid, activeSem);
            Parent adaptiveLayout = LayoutFactory.create(stage, uid, activeSem, "Tasks", tasksLayout);
            Scene scene = new Scene(adaptiveLayout, PlatformUtils.getScreenWidth(), PlatformUtils.getScreenHeight());
            scene.getStylesheets().add(getClass().getResource("/com/ewumatelite/styles.css").toExternalForm());
            stage.setTitle("EWUmate Lite - Tasks");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}