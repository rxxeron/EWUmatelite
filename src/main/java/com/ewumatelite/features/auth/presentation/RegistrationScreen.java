package com.ewumatelite.features.auth.presentation;

import com.ewumatelite.core.models.ProgramItem;
import com.ewumatelite.core.repositories.AuthRepository;
import com.ewumatelite.core.repositories.AcademicRepository;
import com.ewumatelite.core.utils.PlatformUtils;
import com.ewumatelite.features.enrollment.presentation.EnrollmentScreen;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RegistrationScreen {
    private final Stage stage;

    public RegistrationScreen(Stage stage) {
        this.stage = stage;
    }

    public void show() {
        stage.setTitle("EWUmate Lite - Account Registration");

        VBox root = new VBox();
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
        root.getStyleClass().add("root");

        VBox glassCard = new VBox();
        glassCard.setAlignment(Pos.CENTER);
        glassCard.setMaxWidth(500);
        glassCard.setSpacing(10);
        glassCard.getStyleClass().addAll("glass-card", "glass-card-glow");

        Label sceneTitle = new Label("Create Account");
        sceneTitle.getStyleClass().add("title-label");

        Label subtitle = new Label("Join the elite EWU community");
        subtitle.getStyleClass().add("subtitle-label");

        VBox form = new VBox(8);
        form.setAlignment(Pos.CENTER_LEFT);

        Label emailLabel = new Label("Official Email");
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");

        Label pwLabel = new Label("Password");
        PasswordField pwBox = new PasswordField();
        pwBox.setPromptText("Secure password");

        Label nameLabel = new Label("Full Name");
        TextField nameField = new TextField();
        nameField.setPromptText("Enter full name");

        Label studentIdLabel = new Label("Student ID");
        TextField studentIdField = new TextField();
        studentIdField.setPromptText("20XXXXXXX");

        Label nicknameLabel = new Label("Nickname");
        TextField nicknameField = new TextField();
        nicknameField.setPromptText("Cool user name");

        Label deptLabel = new Label("Department");
        ComboBox<String> deptComboBox = new ComboBox<>();
        deptComboBox.setPromptText("Loading Depts...");
        deptComboBox.setMaxWidth(Double.MAX_VALUE);

        Label programLabel = new Label("Academic Program");
        ComboBox<ProgramItem> programComboBox = new ComboBox<>();
        programComboBox.setPromptText("Select Department First");
        programComboBox.setMaxWidth(Double.MAX_VALUE);

        Button btn = new Button("REGISTER & SYNC");
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setPadding(new Insets(15));

        Label actionTarget = new Label();
        actionTarget.setWrapText(true);

        Button btnBack = new Button("Already have an account? Login");
        btnBack.getStyleClass().add("text-button");
        btnBack.setOnAction(e -> new com.ewumatelite.features.auth.presentation.LoginScreen(stage).show());

        form.getChildren().addAll(
            emailLabel, emailField, 
            pwLabel, pwBox, 
            nameLabel, nameField, 
            studentIdLabel, studentIdField, 
            nicknameLabel, nicknameField,
            deptLabel, deptComboBox,
            programLabel, programComboBox
        );

        glassCard.getChildren().addAll(sceneTitle, subtitle, form, btn, actionTarget, btnBack);
        root.getChildren().add(glassCard);

        Map<String, List<ProgramItem>> programsByDept = new HashMap<>();
        
        new Thread(() -> {
            try {
                AcademicRepository academicRepository = new AcademicRepository();
                JSONArray progs = academicRepository.getPrograms(); 
                
                for (int i = 0; i < progs.length(); i++) {
                    JSONObject p = progs.getJSONObject(i);
                    String dept = p.optString("department_name", "Unknown");
                    String code = p.optString("program_code", "");
                    String name = p.optString("name", "");
                    String track = p.optString("track", "tri_semester");
                    
                    programsByDept.putIfAbsent(dept, new ArrayList<>());
                    programsByDept.get(dept).add(new ProgramItem(code, name, track));
                }

                Platform.runLater(() -> {
                    deptComboBox.getItems().addAll(programsByDept.keySet());
                    deptComboBox.setPromptText("Select Department");
                });
            } catch (Exception ex) {
                Platform.runLater(() -> {
                    deptComboBox.setPromptText("Failed to load");
                });
            }
        }).start();

        deptComboBox.setOnAction(e -> {
            com.ewumatelite.core.utils.LogExporter.log("ACTION: UI Event Triggered in " + this.getClass().getSimpleName());
            String selectedDept = deptComboBox.getValue();
            if (selectedDept != null && programsByDept.containsKey(selectedDept)) {
                programComboBox.getItems().clear();
                programComboBox.getItems().addAll(programsByDept.get(selectedDept));
                programComboBox.getSelectionModel().selectFirst();
            }
        });

        btn.setOnAction(e -> {
            com.ewumatelite.core.utils.LogExporter.log("ACTION: UI Event Triggered in " + this.getClass().getSimpleName());
            String emailAttempt = emailField.getText().trim();
            com.ewumatelite.core.utils.LogExporter.log("ACTION: Attempting Registration for email: " + emailAttempt);
            if (deptComboBox.getValue() == null || programComboBox.getValue() == null) {
                com.ewumatelite.core.utils.LogExporter.log("ERROR: Registration failed - missing Dept/Program");
                actionTarget.setText("Please select Dept & Program!");
                actionTarget.setTextFill(javafx.scene.paint.Color.rgb(244, 63, 94));
                return;
            }

            actionTarget.setText("Registration starting...");
            actionTarget.setTextFill(javafx.scene.paint.Color.WHITE);
            btn.setDisable(true);
            
            new Thread(() -> {
                try {
                    AuthRepository authRepository = new AuthRepository();
                    AcademicRepository academicRepository = new AcademicRepository();
                    ProgramItem selectedProgram = programComboBox.getValue();

                    String uid = authRepository.registerUser(
                            emailAttempt,
                            pwBox.getText(),
                            nameField.getText().trim(),
                            nicknameField.getText().trim(),
                            studentIdField.getText().trim(),
                            selectedProgram.getCode(),
                            deptComboBox.getValue(),
                            selectedProgram.getTrack()
                    );
                    
                    com.ewumatelite.core.utils.LogExporter.log("SUCCESS: User Registered successfully. Generated UID: " + uid);

                    Platform.runLater(() -> actionTarget.setText("User Registered! Synchronizing..."));

                    String activeSem = academicRepository.getActiveSemester(selectedProgram.getTrack());
                    JSONArray courses = academicRepository.getCourseMetadata();

                    Platform.runLater(() -> {
                        actionTarget.setText("SUCCESS!");
                        new EnrollmentScreen(stage, uid, activeSem, courses).show();
                    });

                } catch (Exception ex) {
                    com.ewumatelite.core.utils.LogExporter.log("ERROR: Registration Exception: " + ex.getMessage());
                    Platform.runLater(() -> {
                        actionTarget.setText("Error: " + ex.getMessage());
                        actionTarget.setTextFill(javafx.scene.paint.Color.rgb(244, 63, 94));
                    });
                } finally {
                    Platform.runLater(() -> btn.setDisable(false));
                }
            }).start();
        });

        Scene scene = new Scene(root, PlatformUtils.getScreenWidth(), PlatformUtils.getScreenHeight());
        scene.getStylesheets().add(getClass().getResource("/com/ewumatelite/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}

