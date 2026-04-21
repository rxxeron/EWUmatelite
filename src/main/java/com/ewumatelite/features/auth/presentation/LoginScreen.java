package com.ewumatelite.features.auth.presentation;
import com.ewumatelite.core.repositories.AuthRepository;
import com.ewumatelite.core.utils.PlatformUtils;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
public class LoginScreen {
    private final Stage stage;
    public LoginScreen(Stage stage) {
        this.stage = stage;
    }
    public void show() {
        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(40));
        root.getStyleClass().add("root");
        VBox glassCard = new VBox();
        glassCard.setAlignment(Pos.CENTER);
        glassCard.setMaxWidth(400);
        glassCard.setSpacing(20);
        glassCard.getStyleClass().addAll("glass-card", "glass-card-glow");
        Label sceneTitle = new Label("Welcome Back");
        sceneTitle.getStyleClass().add("title-label");
        Label subtitle = new Label("Login to continue");
        subtitle.getStyleClass().add("subtitle-label");
        VBox form = new VBox(15);
        form.setAlignment(Pos.CENTER_LEFT);
        form.setPadding(new Insets(20, 0, 0, 0));
        Label emailLabel = new Label("Email Address");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        Label pwLabel = new Label("Password");
        PasswordField pwBox = new PasswordField();
        pwBox.setPromptText("Enter your password");
        Button btn = new Button("SIGN IN");
        btn.setMaxWidth(Double.MAX_VALUE);
        Label actionTarget = new Label();
        actionTarget.setWrapText(true);
        Button btnRegister = new Button("Don't have an account? Sign Up");
        btnRegister.getStyleClass().add("text-button");
        btnRegister.setOnAction(e -> new com.ewumatelite.features.auth.presentation.RegistrationScreen(stage).show());
        form.getChildren().addAll(emailLabel, emailField, pwLabel, pwBox);
        glassCard.getChildren().addAll(sceneTitle, subtitle, form, btn, actionTarget, btnRegister);
        root.getChildren().add(glassCard);
        btn.setOnAction(e -> {
            com.ewumatelite.core.utils.LogExporter.log("ACTION: UI Event Triggered in " + this.getClass().getSimpleName());
            String email = emailField.getText();
            String password = pwBox.getText();
            if (email.isEmpty() || password.isEmpty()) {
                actionTarget.setText("Email & Password required");
                actionTarget.setTextFill(javafx.scene.paint.Color.rgb(244, 63, 94));
                return;
            }
            btn.setDisable(true);
            actionTarget.setTextFill(javafx.scene.paint.Color.WHITE);
            actionTarget.setText("Authenticating...");
            com.ewumatelite.core.utils.LogExporter.log("ACTION: Attempting to log in user with email: " + email);
            new Thread(() -> {
                try {
                    boolean success = new AuthRepository().login(email, password);
                    if (success) {
                        try {
                            String activeSem = new com.ewumatelite.core.repositories.AcademicRepository().getActiveSemester("tri_semester");
                            com.ewumatelite.core.utils.LogExporter.log("SUCESS: User " + email + " successfully logged in.");
                            Platform.runLater(() -> { 
                                actionTarget.setText("Login Successful!"); 
                                new com.ewumatelite.features.dashboard.presentation.DashboardScreen(stage, com.ewumatelite.core.config.SupabaseConfig.currentUserId, activeSem).show(); 
                            });
                        } catch (Exception semEx) {
                            com.ewumatelite.core.utils.LogExporter.log("ERROR: Failed to fetch active semester during login: " + semEx.getMessage());
                            Platform.runLater(() -> {
                                actionTarget.setTextFill(javafx.scene.paint.Color.rgb(244, 63, 94));
                                actionTarget.setText("Failed to get active semester: " + semEx.getMessage());
                                btn.setDisable(false);
                            });
                        }
                    } else {
                        com.ewumatelite.core.utils.LogExporter.log("WARNING: Failed login attempt for email: " + email);
                        Platform.runLater(() -> {
                            actionTarget.setTextFill(javafx.scene.paint.Color.rgb(244, 63, 94));
                            actionTarget.setText("Invalid credentials!");
                            btn.setDisable(false);
                        });
                    }
                } catch (Exception ex) {
                    com.ewumatelite.core.utils.LogExporter.log("ERROR: Exception during login execution: " + ex.getMessage());
                    Platform.runLater(() -> {
                        actionTarget.setTextFill(javafx.scene.paint.Color.rgb(244, 63, 94));
                        actionTarget.setText("Login Error: " + ex.getMessage());
                        btn.setDisable(false);
                    });
                }
            }).start();
        });
        Scene scene = new Scene(root, PlatformUtils.getScreenWidth(), PlatformUtils.getScreenHeight());
        scene.getStylesheets().add(getClass().getResource("/com/ewumatelite/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("EWUmate Lite - Secure Login");
        stage.show();
    }
}
