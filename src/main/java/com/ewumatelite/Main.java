package com.ewumatelite;

import javafx.application.Application;
import javafx.stage.Stage;
import com.ewumatelite.features.auth.presentation.RegistrationScreen;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        
        // Save the log file to the User's Documents folder
        com.ewumatelite.core.utils.LogExporter.log("Application Session started explicitly by the User.");

        // Global Event Listener to capture EVERY UI Action and Mouse Click across all scenes
        primaryStage.addEventFilter(javafx.scene.input.MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getTarget() instanceof javafx.scene.Node) {
                javafx.scene.Node target = (javafx.scene.Node) event.getTarget();
                String targetInfo = target.getClass().getSimpleName();
                if (target instanceof javafx.scene.control.Labeled) {
                    targetInfo += " ['" + ((javafx.scene.control.Labeled) target).getText() + "']";
                } else if (target.getId() != null) {
                    targetInfo += " [id=" + target.getId() + "]";
                }
                com.ewumatelite.core.utils.LogExporter.log("GLOBAL UI ACTION: User clicked " + targetInfo);
            }
        });

        new com.ewumatelite.features.auth.presentation.LoginScreen(primaryStage).show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

