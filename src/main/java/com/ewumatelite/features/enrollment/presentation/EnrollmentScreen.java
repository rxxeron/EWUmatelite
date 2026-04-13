package com.ewumatelite.features.enrollment.presentation;
import com.ewumatelite.features.sidebar.presentation.Sidebar;
import com.ewumatelite.core.ui.LayoutFactory;
import com.ewumatelite.core.utils.PlatformUtils;

import com.ewumatelite.core.repositories.AcademicRepository;
import com.ewumatelite.features.dashboard.presentation.DashboardScreen;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;

public class EnrollmentScreen {
    private final Stage stage;
    private final String uid;
    private final String activeSem;
    private final JSONArray courses;

    public EnrollmentScreen(Stage stage, String uid, String activeSem, JSONArray courses) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
        this.courses = courses;
    }

    public void show() {
        VBox root = new VBox(20);
        root.setPadding(new Insets(30));
        root.setAlignment(Pos.TOP_LEFT);
        root.getStyleClass().add("content-area-root");
        
        Label title = new Label("Course Selection");
        title.getStyleClass().add("title-label");
        
        Label semesterLabel = new Label(activeSem);
        semesterLabel.setStyle("-fx-text-fill: #0EA5E9; -fx-font-weight: bold; -fx-font-size: 16px;");

        Label subtitle = new Label("Select your courses for the current track.");
        subtitle.getStyleClass().add("subtitle-label");

        final Label actionTarget = new Label();
        actionTarget.setWrapText(true);
        
        TextField searchBar = new TextField();
        searchBar.setPromptText("Search by Course Code or Name");
        searchBar.getStyleClass().add("text-field");
        searchBar.setMaxWidth(500);
        
        VBox coursesContainer = new VBox(15);
        coursesContainer.setStyle("-fx-background-color: transparent;");

        Runnable applyFilter = () -> {
            coursesContainer.getChildren().clear();
            String query = searchBar.getText().trim().toLowerCase();
            
            for (int i = 0; i < courses.length(); i++) {
                JSONObject c = courses.getJSONObject(i);
                String courseCode = c.getString("code");
                String courseName = c.getString("name");
                
                if (!query.isEmpty() && !courseCode.toLowerCase().contains(query) && !courseName.toLowerCase().contains(query)) {
                    continue; 
                }
                
                VBox card = new VBox();
                card.getStyleClass().add("card-container");
                card.setPadding(Insets.EMPTY); // Use card-container padding or customize
            
                HBox header = new HBox(15);
                header.setPadding(new Insets(20));
                header.setAlignment(Pos.CENTER_LEFT);
                header.setStyle("-fx-cursor: hand;");
                
                VBox headerText = new VBox(5);
                Label codeLabel = new Label(courseCode);
                codeLabel.getStyleClass().add("title-label");
                codeLabel.setStyle("-fx-font-size: 18px;");
                
                Label nameLabel = new Label(courseName);
                nameLabel.getStyleClass().add("label");
                headerText.getChildren().addAll(codeLabel, nameLabel);
                
                Region headerSpacer = new Region();
                HBox.setHgrow(headerSpacer, Priority.ALWAYS);
                
                Label expandIcon = new Label("▶");
                expandIcon.getStyleClass().add("label");
                expandIcon.setStyle("-fx-font-size: 14px;");
                
                header.getChildren().addAll(headerText, headerSpacer, expandIcon);
                
                VBox sectionContainer = new VBox(12);
                sectionContainer.setPadding(new Insets(0, 20, 20, 20));
                sectionContainer.setVisible(false);
                sectionContainer.setManaged(false);
                
                Label loadingLabel = new Label("Fetching sections...");
                loadingLabel.getStyleClass().add("label");
                loadingLabel.setStyle("-fx-font-style: italic;");
                sectionContainer.getChildren().add(loadingLabel);
                
                final boolean[] isExpanded = {false};
                final boolean[] isLoaded = {false};
                
                header.setOnMouseClicked(event -> {
                    isExpanded[0] = !isExpanded[0];
                    sectionContainer.setVisible(isExpanded[0]);
                    sectionContainer.setManaged(isExpanded[0]);
                    expandIcon.setText(isExpanded[0] ? "▼" : "▶");
                    
                    if (isExpanded[0] && !isLoaded[0]) {
                        new Thread(() -> {
                            try {
                                AcademicRepository academicRepository = new AcademicRepository();
                                JSONArray sections = academicRepository.getSectionsForCourse(activeSem, courseCode);
                                JSONArray enrolledData = academicRepository.getUserEnrollments(uid, activeSem);
                                java.util.Set<String> enrolledSectionIds = new java.util.HashSet<>();
                                for(int k=0; k<enrolledData.length(); k++) { enrolledSectionIds.add(enrolledData.getJSONObject(k).getString("section_id")); }

                                Platform.runLater(() -> {
                                    sectionContainer.getChildren().clear();
                                    if (sections.length() == 0) {
                                        Label noSec = new Label("No sections available.");
                                        noSec.setTextFill(javafx.scene.paint.Color.rgb(244, 63, 94));
                                        sectionContainer.getChildren().add(noSec);
                                    } else {
                                        for (int j = 0; j < sections.length(); j++) {
                                            JSONObject sec = sections.getJSONObject(j);
                                            String secId = sec.getString("id");
                                            String secNum = sec.optString("section_number", "N/A");
                                            
                                            // [Schedule Logic Remains Same]
                                            String day1 = "";
                                            String time1 = "";
                                            if (sec.has("schedule_data") && !sec.isNull("schedule_data")) {
                                                JSONArray schedArr = sec.optJSONArray("schedule_data");
                                                if (schedArr != null && schedArr.length() > 0) {
                                                    String rawDay = schedArr.getJSONObject(0).optString("day", "");
                                                    String dayExpanded = "";
                                                    for (char ch : rawDay.toCharArray()) {
                                                        if(ch == 'S') dayExpanded += "Sun, ";
                                                        else if(ch == 'M') dayExpanded += "Mon, ";
                                                        else if(ch == 'T') dayExpanded += "Tue, ";
                                                        else if(ch == 'W') dayExpanded += "Wed, ";
                                                        else if(ch == 'R') dayExpanded += "Thu, ";
                                                        else if(ch == 'F') dayExpanded += "Fri, ";
                                                        else if(ch == 'A') dayExpanded += "Sat, ";
                                                    }
                                                    if (dayExpanded.endsWith(", ")) dayExpanded = dayExpanded.substring(0, dayExpanded.length() - 2);
                                                    day1 = dayExpanded.isEmpty() ? rawDay : dayExpanded;
                                                    String st = schedArr.getJSONObject(0).optString("startTime", "");
                                                    String et = schedArr.getJSONObject(0).optString("endTime", "");
                                                    time1 = (!st.isEmpty() && !et.isEmpty()) ? (st + " - " + et) : st;
                                                }
                                            }
                                            String faculty = sec.optString("faculty_initials", "TBA");
                                            
                                            HBox secRow = new HBox(15); 
                                            secRow.setAlignment(Pos.CENTER_LEFT); 
                                            secRow.setPadding(new Insets(12)); 
                                            secRow.setStyle("-fx-background-color: rgba(255,255,255,0.03); -fx-background-radius: 8;");
                                            
                                            VBox detailsBox = new VBox(3); 
                                            Label secLabel = new Label("SEC " + secNum); 
                                            secLabel.getStyleClass().add("title-label");
                                            secLabel.setStyle("-fx-font-size: 14px;");
                                            
                                            Label timeLabel = new Label(day1 + " | " + time1); 
                                            timeLabel.getStyleClass().add("label");
                                            timeLabel.setStyle("-fx-font-size: 12px;");
                                            
                                            Label facLabel = new Label("Faculty: " + faculty); 
                                            facLabel.getStyleClass().add("label");
                                            facLabel.setStyle("-fx-font-size: 11px;");
                                            
                                            detailsBox.getChildren().addAll(secLabel, timeLabel, facLabel);
                                            Region spacer = new Region(); HBox.setHgrow(spacer, Priority.ALWAYS);
                                            
                                            boolean isEnrolled = enrolledSectionIds.contains(secId);
                                            Button btnAction = new Button(isEnrolled ? "DROP" : "ENROLL");
                                            btnAction.getStyleClass().add("button");
                                            if (isEnrolled) btnAction.setStyle("-fx-background-color: #F43F5E;");
                                            
                                            btnAction.setOnAction(e -> { 
            com.ewumatelite.core.utils.LogExporter.log("ACTION: UI Event Triggered in " + this.getClass().getSimpleName());
                                                btnAction.setDisable(true); 
                                                actionTarget.setText("");
                                                new Thread(() -> {
                                                    try {
                                                        if (btnAction.getStyle().contains("#F43F5E")) {
                                                            academicRepository.dropEnrollment(uid, activeSem, secId);
                                                            Platform.runLater(() -> { 
                                                                btnAction.setText("ENROLL"); 
                                                                btnAction.setStyle(""); 
                                                                actionTarget.setText("Dropped!"); 
                                                                actionTarget.setTextFill(javafx.scene.paint.Color.WHITE); 
                                                            });
                                                        } else {
                                                            academicRepository.pushEnrollment(uid, activeSem, courseCode, secId, secNum);
                                                            Platform.runLater(() -> { 
                                                                btnAction.setText("DROP"); 
                                                                btnAction.setStyle("-fx-background-color: #F43F5E;"); 
                                                                actionTarget.setText("Enrolled!"); 
                                                                actionTarget.setTextFill(javafx.scene.paint.Color.rgb(16, 185, 129)); 
                                                            });
                                                        }
                                                        academicRepository.syncSchedule(uid, activeSem);
                                                    } catch (Exception ex) { 
                                                        Platform.runLater(() -> { 
                                                            actionTarget.setText("Error: " + ex.getMessage()); 
                                                            actionTarget.setTextFill(javafx.scene.paint.Color.rgb(244, 63, 94)); 
                                                        }); 
                                                    }
                                                    Platform.runLater(() -> btnAction.setDisable(false));
                                                }).start();
                                            });
                                            secRow.getChildren().addAll(detailsBox, spacer, btnAction); 
                                            sectionContainer.getChildren().add(secRow);
                                        }
                                    }
                                });
                            } catch (Exception ex) { Platform.runLater(() -> { sectionContainer.getChildren().clear(); Label err = new Label("Error: "+ex.getMessage()); err.setTextFill(javafx.scene.paint.Color.RED); sectionContainer.getChildren().add(err); }); }
                        }).start();
                        isLoaded[0] = true;
                    }
                });
                
                card.getChildren().addAll(header, sectionContainer);
                coursesContainer.getChildren().add(card);
            }
        };

        applyFilter.run();
        searchBar.textProperty().addListener((observable, oldValue, newValue) -> applyFilter.run());

        ScrollPane scrollPane = new ScrollPane(coursesContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.getStyleClass().add("scroll-pane");

        root.getChildren().addAll(title, semesterLabel, subtitle, searchBar, scrollPane, actionTarget);
        VBox.setVgrow(scrollPane, Priority.ALWAYS);

        Parent adaptiveLayout = LayoutFactory.create(stage, uid, activeSem, "Enrollment", root);

        Scene scene = new Scene(adaptiveLayout, PlatformUtils.getScreenWidth(), PlatformUtils.getScreenHeight());
        scene.getStylesheets().add(getClass().getResource("/com/ewumatelite/styles.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
    }
}





