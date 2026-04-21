package com.ewumatelite;
import javafx.application.Application;
import javafx.stage.Stage;
import com.ewumatelite.features.auth.presentation.RegistrationScreen;
public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        com.ewumatelite.core.utils.LogExporter.log("Application Session started explicitly by the User.");
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
        if (com.ewumatelite.core.config.SupabaseConfig.currentUserToken != null) {
            new com.ewumatelite.features.dashboard.presentation.DashboardScreen(primaryStage, com.ewumatelite.core.config.SupabaseConfig.currentUserId, "Spring 2026").show();
        } else {
            new com.ewumatelite.features.auth.presentation.LoginScreen(primaryStage).show();
        }
    }
    public static void main(String[] args) {
        launch(args);
    }
}
