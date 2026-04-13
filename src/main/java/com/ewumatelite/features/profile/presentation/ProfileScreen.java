package com.ewumatelite.features.profile.presentation;

import com.ewumatelite.core.ui.LayoutFactory;
import com.ewumatelite.core.utils.PlatformUtils;
import com.ewumatelite.core.repositories.ProfileRepository;
import com.ewumatelite.features.sidebar.presentation.Sidebar;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONObject;

import java.util.Optional;

public class ProfileScreen {
    private final Stage stage;
    private final String uid;
    private final String activeSem;
    private final ProfileRepository profileRepo = new ProfileRepository();

    public ProfileScreen(Stage stage, String uid, String activeSem) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.getStyleClass().add("content-area-root");

        root.setLeft(new Sidebar(stage, uid, activeSem, "Profile").getView());

        VBox contentWrap = new VBox(32);
        contentWrap.setPadding(new Insets(40, 60, 40, 60));
        contentWrap.setAlignment(Pos.TOP_LEFT);
        contentWrap.setMaxWidth(900);

        Label title = new Label("My Profile");
        title.getStyleClass().add("title-label");

        ScrollPane scrollPane = new ScrollPane(contentWrap);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("scroll-pane");

        Parent adaptiveLayout = LayoutFactory.create(stage, uid, activeSem, "Profile", scrollPane);

        Scene scene = new Scene(adaptiveLayout, PlatformUtils.getScreenWidth(), PlatformUtils.getScreenHeight());
        scene.getStylesheets().add(getClass().getResource("/com/ewumatelite/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("EWUmate Lite - My Profile");

        loadProfileData(contentWrap);
    }

    private void loadProfileData(VBox container) {
        Label loadingLabel = new Label("Loading Profile...");
        loadingLabel.setTextFill(Color.CYAN);
        container.getChildren().add(loadingLabel);

        new Thread(() -> {
            try {
                JSONObject profile = profileRepo.getProfile(uid);
                int coursesDone = profileRepo.getCoursesDone(uid);

                Platform.runLater(() -> {
                    container.getChildren().clear();
                    if (profile != null) {
                        buildProfileUI(container, profile, coursesDone);
                    } else {
                        Label errStatus = new Label("Profile not found.");
                        errStatus.setTextFill(Color.WHITE);
                        container.getChildren().add(errStatus);
                    }
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    container.getChildren().clear();
                    Label error = new Label("Failed to load profile: " + e.getMessage());
                    error.setTextFill(Color.RED);
                    container.getChildren().add(error);
                });
            }
        }).start();
    }

    private void buildProfileUI(VBox container, JSONObject profile, int coursesDone) {
        // Header
        VBox header = buildHeader(profile);

        // Personal Info
        VBox personalInfo = new VBox(16);
        personalInfo.getChildren().add(buildSectionTitle("Personal Info"));
        personalInfo.getChildren().add(buildInfoCard("Full Name", profile.optString("full_name", "Not Set"), () -> editField("full_name", "Full Name", profile, container)));
        personalInfo.getChildren().add(buildInfoCard("Nickname", profile.optString("nickname", "Not Set"), () -> editField("nickname", "Nickname", profile, container)));
        personalInfo.getChildren().add(buildInfoCard("Student ID", profile.optString("student_id", "Not Set"), () -> editField("student_id", "Student ID", profile, container)));

        // App Settings
        VBox appSettings = new VBox(16);
        appSettings.getChildren().add(buildSectionTitle("App Settings"));
        appSettings.getChildren().add(buildSettingsCard("Change Password", false, this::editPassword));
        appSettings.getChildren().add(buildSettingsCard("Sign Out", true, this::logout));

        container.getChildren().addAll(header, personalInfo, appSettings);
    }

    private VBox buildHeader(JSONObject profile) {
        VBox header = new VBox(12);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(20, 0, 20, 0));

        StackPane avatarPane = new StackPane();
        Circle avatarBg = new Circle(50, Color.rgb(255, 255, 255, 0.05));
        avatarBg.setStroke(Color.rgb(14, 165, 233, 0.3));
        avatarBg.setStrokeWidth(2);
        
        Text avatarInitial = new Text(profile.optString("nickname", "U").substring(0, 1).toUpperCase());
        avatarInitial.setFill(Color.WHITE);
        avatarInitial.setFont(Font.font("Segoe UI", FontWeight.BOLD, 40));
        avatarPane.getChildren().addAll(avatarBg, avatarInitial);

        Label name = new Label(profile.optString("full_name", "User"));
        name.getStyleClass().add("title-label");
        name.setStyle("-fx-font-size: 24px;");

        Label email = new Label("Student Account Verified");
        email.getStyleClass().add("label");
        email.setStyle("-fx-text-fill: #10B981; -fx-font-weight: bold;");

        String track = profile.optString("track", "tri_semester").replace("_", " ").toUpperCase();
        Label startedInfo = new Label("Started: " + formatSemester(profile.optString("admitted_semester")) + " • " + track);
        startedInfo.getStyleClass().add("label");
        startedInfo.setStyle("-fx-text-fill: #94A3B8;");

        header.getChildren().addAll(avatarPane, name, email, startedInfo);
        return header;
    }

    private Label buildSectionTitle(String title) {
        Label t = new Label(title);
        t.getStyleClass().add("title-label");
        t.setStyle("-fx-text-fill: #0EA5E9; -fx-font-size: 16px;");
        return t;
    }

    private HBox buildInfoCard(String label, String value, Runnable onTap) {
        HBox card = new HBox(16);
        card.getStyleClass().add("card-container");
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle(card.getStyle() + "; -fx-cursor: hand;");

        VBox textCol = new VBox(4);
        Label lbl = new Label(label);
        lbl.getStyleClass().add("label");
        lbl.setStyle("-fx-font-size: 12px; -fx-text-fill: #64748B;");
        
        Label val = new Label(value);
        val.getStyleClass().add("title-label");
        val.setStyle("-fx-font-size: 16px;");
        textCol.getChildren().addAll(lbl, val);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button editBtn = new Button("Edit");
        editBtn.getStyleClass().add("text-button");

        card.getChildren().addAll(textCol, spacer, editBtn);
        card.setOnMouseClicked(e -> onTap.run());

        return card;
    }

    private HBox buildSettingsCard(String title, boolean isDestructive, Runnable onTap) {
        HBox card = new HBox(16);
        card.getStyleClass().add("card-container");
        card.setPadding(new Insets(16, 20, 16, 20));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle(card.getStyle() + "; -fx-cursor: hand;");

        Label lbl = new Label(title);
        lbl.getStyleClass().add("title-label");
        lbl.setStyle("-fx-font-size: 16px;");
        if (isDestructive) lbl.setStyle(lbl.getStyle() + "; -fx-text-fill: #F43F5E;");

        card.getChildren().add(lbl);
        card.setOnMouseClicked(e -> onTap.run());

        return card;
    }

    private void editField(String dbKey, String uiName, JSONObject profile, VBox container) {
        TextInputDialog dialog = new TextInputDialog(profile.optString(dbKey, ""));
        dialog.setTitle("Edit " + uiName);
        dialog.setHeaderText("Update your " + uiName);
        dialog.getDialogPane().setStyle("-fx-base: #1E2836; -fx-control-inner-background: #16202A; -fx-text-fill: white;");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && !result.get().trim().isEmpty() && !result.get().trim().equals(profile.optString(dbKey))) {
            new Thread(() -> {
                try {
                    boolean success = profileRepo.updateProfileField(uid, dbKey, result.get().trim());
                    if (success) {
                        Platform.runLater(() -> loadProfileData(container)); // Refresh
                    }
                } catch (Exception e) {}
            }).start();
        }
    }

    private void editPassword() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Change Password");
        dialog.setHeaderText("Enter new password (min 6 characters)");
        dialog.getDialogPane().setStyle("-fx-base: #1E2836; -fx-control-inner-background: #16202A; -fx-text-fill: white;");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent() && result.get().length() >= 6) {
            new Thread(() -> {
                try {
                    profileRepo.updatePassword(result.get());
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Password updated successfully");
                        alert.show();
                    });
                } catch (Exception e) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR, "Failed: " + e.getMessage());
                        alert.show();
                    });
                }
            }).start();
        }
    }

    private void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to log out?", ButtonType.YES, ButtonType.NO);
        alert.showAndWait().ifPresent(res -> {
            if (res == ButtonType.YES) {
                new com.ewumatelite.features.auth.presentation.LoginScreen(stage).show();
            }
        });
    }

    private String formatSemester(String semester) {
        if (semester == null || semester.isEmpty()) return "Unknown";
        String[] parts = semester.replace("_", " ").split(" ");
        StringBuilder sb = new StringBuilder();
        for (String p : parts) {
            if (!p.isEmpty()) sb.append(p.substring(0, 1).toUpperCase()).append(p.substring(1).toLowerCase()).append(" ");
        }
        return sb.toString().trim();
    }
}