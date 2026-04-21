package com.ewumatelite.features.semester_progress.presentation;
import com.ewumatelite.core.repositories.AcademicRepository;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import org.json.JSONArray;
import org.json.JSONObject;
import java.util.*;
import java.util.stream.Collectors;
public class CourseProgressDetailController {
    @FXML private Label headerTitle;
    @FXML private VBox setupContainer;
    @FXML private VBox marksContainer;
    @FXML private Button setupBtn;
    @FXML private Button marksBtn;
    @FXML private Label totalExpectedLabel;
    @FXML private Label cgpaLabel;
    @FXML private Label remarksLabel;
    private final AcademicRepository repository = new AcademicRepository();
    private Stage stage;
    private String uid;
    private String activeSem;
    private JSONObject moduleData;
    private Runnable onBackCallback;
    private List<FieldDef> fields = new ArrayList<>();
    private TextField distQuizCtrl = new TextField();
    private ComboBox<String> quizStrategy = new ComboBox<>();
    private TextField quizNCtrl = new TextField("1");
    private List<TextField> obtQuizzesCtrls = new ArrayList<>();
    private TextField distShortQuizCtrl = new TextField();
    private ComboBox<String> shortQuizStrategy = new ComboBox<>();
    private TextField shortQuizNCtrl = new TextField("1");
    private List<TextField> obtShortQuizzesCtrls = new ArrayList<>();
    public void initData(Stage stage, String uid, String activeSem, JSONObject courseData, Runnable onBackCallback) {
        this.stage = stage;
        this.uid = uid;
        this.activeSem = activeSem;
        this.moduleData = courseData;
        this.onBackCallback = onBackCallback;
        Platform.runLater(() -> {
            if (moduleData.has("course_name") && !moduleData.isNull("course_name")) {
                headerTitle.setText(moduleData.getString("course_name"));
            } else if (moduleData.has("course_code") && !moduleData.isNull("course_code")) {
                headerTitle.setText(moduleData.getString("course_code"));
            }
            initStrategies();
            loadQuizData();
            buildFields();
            renderSetupTab();
            renderMarksTab();
            updateCalculations();
        });
    }
    private void initStrategies() {
        quizStrategy.getItems().addAll("best_one", "best_n", "average_n", "sum_all", "average_all");
        quizStrategy.setStyle("-fx-background-color: #1a2235; -fx-text-fill: white; -fx-background-radius: 5;");
        quizStrategy.setOnAction(e -> { updateCalculations(); renderSetupTab(); });
            com.ewumatelite.core.utils.LogExporter.log("ACTION: UI Event Triggered in " + this.getClass().getSimpleName());
        setupInput(distQuizCtrl); setupInput(quizNCtrl);
        quizNCtrl.textProperty().addListener((obs,o,n) -> updateCalculations());
        distQuizCtrl.textProperty().addListener((obs,o,n) -> updateCalculations());
        shortQuizStrategy.getItems().addAll("best_one", "best_n", "average_n", "sum_all", "average_all");
        shortQuizStrategy.setStyle("-fx-background-color: #1a2235; -fx-text-fill: white; -fx-background-radius: 5;");
        shortQuizStrategy.setOnAction(e -> { updateCalculations(); renderSetupTab(); });
            com.ewumatelite.core.utils.LogExporter.log("ACTION: UI Event Triggered in " + this.getClass().getSimpleName());
        setupInput(distShortQuizCtrl); setupInput(shortQuizNCtrl);
        shortQuizNCtrl.textProperty().addListener((obs,o,n) -> updateCalculations());
        distShortQuizCtrl.textProperty().addListener((obs,o,n) -> updateCalculations());
    }
    private void loadQuizData() {
        try {
            if (moduleData.has("dist_quiz") && !moduleData.isNull("dist_quiz")) try { distQuizCtrl.setText(String.valueOf(moduleData.getDouble("dist_quiz"))); } catch(Exception e) {}
            if (moduleData.has("dist_short_quiz") && !moduleData.isNull("dist_short_quiz")) try { distShortQuizCtrl.setText(String.valueOf(moduleData.getDouble("dist_short_quiz"))); } catch(Exception e) {}
            quizStrategy.setValue(moduleData.optString("quiz_strategy", "best_one"));
            quizNCtrl.setText(moduleData.optString("quiz_n", "1"));
            JSONObject extra = moduleData.optJSONObject("marks_data");
            if (extra != null) {
                shortQuizStrategy.setValue(extra.optString("short_quiz_strategy", "best_one"));
            } else {
                shortQuizStrategy.setValue("best_one");
            }
            shortQuizNCtrl.setText(moduleData.optString("short_quiz_n", "1"));
            if (moduleData.has("obt_quizzes") && !moduleData.isNull("obt_quizzes")) {
                try {
                    JSONArray arr = moduleData.getJSONArray("obt_quizzes");
                    for (int i = 0; i < arr.length(); i++) {
                        TextField tf = new TextField(String.valueOf(arr.getDouble(i)));
                        setupInput(tf);
                        tf.textProperty().addListener((obs,o,n)->updateCalculations());
                        obtQuizzesCtrls.add(tf);
                    }
                } catch(Exception e) { e.printStackTrace(); }
            }
            if (moduleData.has("obt_short_quizzes") && !moduleData.isNull("obt_short_quizzes")) {
                try {
                    JSONArray arr = moduleData.getJSONArray("obt_short_quizzes");
                    for(int i=0; i<arr.length(); i++) {
                        TextField tf = new TextField(String.valueOf(arr.getDouble(i)));
                        setupInput(tf);
                        tf.textProperty().addListener((obs,o,n)->updateCalculations());
                        obtShortQuizzesCtrls.add(tf);
                    }
                } catch(Exception e) { e.printStackTrace(); }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    private void buildFields() {
        fields.clear();
        fields.add(new FieldDef("Mid Term", "dist_mid", "obt_mid"));
        fields.add(new FieldDef("Final Term", "dist_final", "obt_final"));
        fields.add(new FieldDef("Assignment", "dist_assignment", "obt_assignment"));
        fields.add(new FieldDef("Project", "dist_project", "obt_project"));
        fields.add(new FieldDef("Presentation", "dist_presentation", "obt_presentation"));
        fields.add(new FieldDef("Viva", "dist_viva", "obt_viva"));
        fields.add(new FieldDef("Lab", "dist_lab", "obt_lab"));
        fields.add(new FieldDef("Attendance", "dist_attendance", "obt_attendance"));
        fields.add(new FieldDef("Term Paper", "dist_term_paper", "obt_term_paper"));
        fields.add(new FieldDef("Class Performance", "dist_class_performance", "obt_class_performance"));
        fields.add(new FieldDef("Optional 1", "dist_optional_1", "obt_optional_1"));
        fields.add(new FieldDef("Optional 2", "dist_optional_2", "obt_optional_2"));
        fields.add(new FieldDef("Optional 3", "dist_optional_3", "obt_optional_3"));
        for (FieldDef f : fields) { System.out.println("Adding field: " + f.name);
            if (moduleData.has(f.distKey) && !moduleData.isNull(f.distKey)) {
                try { f.distInput.setText(String.valueOf(moduleData.getDouble(f.distKey))); } catch(Exception e) {}
            }
            if (moduleData.has(f.obtKey) && !moduleData.isNull(f.obtKey)) {
                try { f.obtInput.setText(String.valueOf(moduleData.getDouble(f.obtKey))); } catch(Exception e) {}
            }
            f.distInput.textProperty().addListener((obs, old, nv) -> updateCalculations());
            f.obtInput.textProperty().addListener((obs, old, nv) -> updateCalculations());
        }
    }
    private void renderSetupTab() { System.out.println("renderSetupTab called, fields size: " + fields.size());
        setupContainer.getChildren().clear(); System.out.println("renderSetupTab fields size: " + fields.size());
        for (FieldDef f : fields) { System.out.println("Adding field: " + f.name);
            setupContainer.getChildren().add(f.buildSetupRow());
        }
        setupContainer.getChildren().add(createDivider());
        setupContainer.getChildren().add(createHeader("QUIZ STRATEGY"));
        setupContainer.getChildren().add(buildRow("Quiz Total Marks", distQuizCtrl));
        setupContainer.getChildren().add(buildRow("Short Quiz Total", distShortQuizCtrl));
        setupContainer.getChildren().add(buildRowCombo("Quiz Strategy", quizStrategy));
        String qs = quizStrategy.getValue();
        if ("best_n".equals(qs) || "average_n".equals(qs)) {
            setupContainer.getChildren().add(buildRow("N for Quiz", quizNCtrl));
        }
        setupContainer.getChildren().add(buildRowCombo("Short Quiz Strategy", shortQuizStrategy));
        String sqs = shortQuizStrategy.getValue();
        if ("best_n".equals(sqs) || "average_n".equals(sqs)) {
            setupContainer.getChildren().add(buildRow("N for Short Quiz", shortQuizNCtrl));
        }
    }
    private void renderMarksTab() {
        marksContainer.getChildren().clear();
        for (FieldDef f : fields) { System.out.println("Adding field: " + f.name);
            double dist = parseDouble(f.distInput.getText());
            if (dist > 0) {
                marksContainer.getChildren().add(f.buildMarksRow());
            }
        }
        double dq = parseDouble(distQuizCtrl.getText());
        if (dq > 0) {
            marksContainer.getChildren().add(buildQuizMarksSection("Quizzes", obtQuizzesCtrls, distQuizCtrl, quizStrategy, quizNCtrl, true));
        }
        double dsq = parseDouble(distShortQuizCtrl.getText());
        if (dsq > 0) {
            marksContainer.getChildren().add(buildQuizMarksSection("Short Quizzes", obtShortQuizzesCtrls, distShortQuizCtrl, shortQuizStrategy, shortQuizNCtrl, false));
        }
    }
    private VBox buildQuizMarksSection(String title, List<TextField> ctrls, TextField distCtrl, ComboBox<String> stratCombo, TextField nCtrl, boolean isMainQuiz) {
        VBox box = new VBox(10);
        box.setStyle("-fx-padding: 15; -fx-background-color: #131a2a; -fx-background-radius: 12;");
        Label l = new Label(title); l.setTextFill(Color.web("#22D3EE")); l.setFont(Font.font("System", FontWeight.BOLD, 14));
        double calc = calculateQuizMark(ctrls, stratCombo.getValue(), (int)parseDouble(nCtrl.getText()), parseDouble(distCtrl.getText()));
        Label subL = new Label("Calc: " + String.format("%.1f", calc) + " / " + distCtrl.getText() + " (" + stratCombo.getValue() + ")");
        subL.setTextFill(Color.web("#8091a7"));
        HBox header = new HBox(l, new Pane(), subL);
        HBox.setHgrow(header.getChildren().get(1), Priority.ALWAYS);
        box.getChildren().add(header);
        VBox list = new VBox(5);
        for (int i=0; i<ctrls.size(); i++) {
            TextField t = ctrls.get(i);
            HBox row = new HBox(10, new Label("Quiz " + (i+1)), new Pane(), t);
            row.setAlignment(Pos.CENTER_LEFT);
            ((Label)row.getChildren().get(0)).setTextFill(Color.WHITE);
            t.setPrefWidth(80);
            HBox.setHgrow(row.getChildren().get(1), Priority.ALWAYS);
            list.getChildren().add(row);
        }
        Button addBtn = new Button("+ Add");
        addBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #22D3EE; -fx-cursor: hand;");
        addBtn.setOnAction(e -> {
            com.ewumatelite.core.utils.LogExporter.log("ACTION: UI Event Triggered in " + this.getClass().getSimpleName());
            TextField tf = new TextField(); setupInput(tf); tf.textProperty().addListener((obs,o,n)->updateCalculations());
            ctrls.add(tf);
            updateCalculations();
        });
        Button remBtn = new Button("- Remove");
        remBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #ff4444; -fx-cursor: hand;");
        remBtn.setOnAction(e -> {
            com.ewumatelite.core.utils.LogExporter.log("ACTION: UI Event Triggered in " + this.getClass().getSimpleName());
            if(!ctrls.isEmpty()) ctrls.remove(ctrls.size()-1);
            updateCalculations();
        });
        HBox btns = new HBox(5, addBtn, remBtn);
        btns.setAlignment(Pos.CENTER_LEFT);
        box.getChildren().addAll(list, btns);
        return box;
    }
    private double calculateQuizMark(List<TextField> ctrls, String strategy, int n, double maxMark) {
        if (ctrls == null || ctrls.isEmpty()) return 0.0;
        List<Double> marks = ctrls.stream().map(c -> parseDouble(c.getText())).sorted((a,b)->b.compareTo(a)).collect(Collectors.toList());
        double total = 0.0;
        if ("best_one".equals(strategy)) {
            total = marks.get(0);
        } else if ("best_n".equals(strategy)) {
            for(int i=0; i<n && i<marks.size(); i++) total += marks.get(i);
        } else if ("average_n".equals(strategy) || "n_average".equals(strategy)) {
            double sum = 0; int count = 0;
            for(int i=0; i<n && i<marks.size(); i++) { sum += marks.get(i); count++; }
            if (count > 0) total = sum / count;
        } else if ("average_all".equals(strategy)) {
            double sum = 0; for(double m : marks) sum+=m;
            total = marks.size() > 0 ? sum / marks.size() : 0;
        } else if ("sum_all".equals(strategy)) {
            for(double m : marks) total+=m;
        }
        return total > maxMark ? maxMark : total;
    }
    private void updateCalculations() {
        double totalDist = parseDouble(distQuizCtrl.getText()) + parseDouble(distShortQuizCtrl.getText());
        double totalObt = calculateQuizMark(obtQuizzesCtrls, quizStrategy.getValue(), (int)parseDouble(quizNCtrl.getText()), parseDouble(distQuizCtrl.getText()))
                + calculateQuizMark(obtShortQuizzesCtrls, shortQuizStrategy.getValue(), (int)parseDouble(shortQuizNCtrl.getText()), parseDouble(distShortQuizCtrl.getText()));
        for (FieldDef f : fields) { System.out.println("Adding field: " + f.name);
            totalDist += parseDouble(f.distInput.getText());
            totalObt += parseDouble(f.obtInput.getText());
        }
        totalExpectedLabel.setText(String.format("Obtained: %.1f / %.1f", totalObt, totalDist));
        cgpaLabel.setText("Expected: " + getGrade(totalObt));
        renderSetupTab();
        renderMarksTab();
    }
    private double parseDouble(String val) {
        try { return val != null && !val.trim().isEmpty() ? Double.parseDouble(val) : 0; } catch(Exception e) { return 0; }
    }
    private void setupInput(TextField f) {
        f.setStyle("-fx-background-color: #1a2235; -fx-text-fill: white; -fx-background-radius: 5;");
        f.setPrefWidth(100);
    }
    private Label createHeader(String title) {
        Label l = new Label(title); l.setTextFill(Color.web("#22D3EE")); l.setFont(Font.font("System", FontWeight.BLACK, 14));
        return l;
    }
    private Separator createDivider() {
        Separator s = new Separator(); s.setStyle("-fx-opacity: 0.1;"); return s;
    }
    private HBox buildRow(String name, TextField f) {
        Label l = new Label(name); l.setTextFill(Color.WHITE); l.setFont(Font.font("System", FontWeight.BOLD, 14)); l.setPrefWidth(150);
        HBox right = new HBox(f); right.setAlignment(Pos.CENTER_RIGHT); HBox.setHgrow(right, Priority.ALWAYS);
        HBox row = new HBox(l, right); row.setAlignment(Pos.CENTER_LEFT); row.setStyle("-fx-padding: 10; -fx-background-color: #131a2a; -fx-background-radius: 10;");
        return row;
    }
    private HBox buildRowCombo(String name, ComboBox<String> c) {
        Label l = new Label(name); l.setTextFill(Color.WHITE); l.setFont(Font.font("System", FontWeight.BOLD, 14)); l.setPrefWidth(150);
        HBox right = new HBox(c); right.setAlignment(Pos.CENTER_RIGHT); HBox.setHgrow(right, Priority.ALWAYS);
        HBox row = new HBox(l, right); row.setAlignment(Pos.CENTER_LEFT); row.setStyle("-fx-padding: 10; -fx-background-color: #131a2a; -fx-background-radius: 10;");
        return row;
    }
    private String getGrade(double totalMark) {
        if (totalMark >= 80) return "A+"; if (totalMark >= 75) return "A"; if (totalMark >= 70) return "A-"; if (totalMark >= 65) return "B+";
        if (totalMark >= 60) return "B"; if (totalMark >= 55) return "B-"; if (totalMark >= 50) return "C+"; if (totalMark >= 45) return "C";
        if (totalMark >= 40) return "D"; return "F";
    }
    @FXML private void showSetup() {
        setupContainer.setVisible(true); setupContainer.setManaged(true);
        marksContainer.setVisible(false); marksContainer.setManaged(false);
        setupBtn.setStyle("-fx-background-color: #2ab6d4; -fx-text-fill: #0b111d; -fx-background-radius: 15; -fx-font-weight: bold;");
        marksBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #FFFFFF;");
    }
    @FXML private void showMarks() {
        setupContainer.setVisible(false); setupContainer.setManaged(false);
        marksContainer.setVisible(true); marksContainer.setManaged(true);
        marksBtn.setStyle("-fx-background-color: #2ab6d4; -fx-text-fill: #0b111d; -fx-background-radius: 15; -fx-font-weight: bold;");
        setupBtn.setStyle("-fx-background-color: transparent; -fx-text-fill: #FFFFFF;");
    }
    @FXML private void onBackClicked() {
        com.ewumatelite.core.utils.LogExporter.log("ACTION: FXML Action onBackClicked Triggered in " + this.getClass().getSimpleName());
        if (onBackCallback != null) onBackCallback.run();
    }
    @FXML private void onSaveClicked() {
        com.ewumatelite.core.utils.LogExporter.log("ACTION: FXML Action onSaveClicked Triggered in " + this.getClass().getSimpleName());
        for (FieldDef f : fields) { System.out.println("Adding field: " + f.name);
            String dStr = f.distInput.getText(); String oStr = f.obtInput.getText();
            if (!dStr.isEmpty()) moduleData.put(f.distKey, parseDouble(dStr)); else moduleData.remove(f.distKey);
            if (!oStr.isEmpty()) moduleData.put(f.obtKey, parseDouble(oStr)); else moduleData.remove(f.obtKey);
        }
        moduleData.put("user_id", uid);
        moduleData.put("semester_code", activeSem);
        String dQuiz = distQuizCtrl.getText(); if(!dQuiz.isEmpty()) moduleData.put("dist_quiz", parseDouble(dQuiz)); else moduleData.remove("dist_quiz");
        String dsQuiz = distShortQuizCtrl.getText(); if(!dsQuiz.isEmpty()) moduleData.put("dist_short_quiz", parseDouble(dsQuiz)); else moduleData.remove("dist_short_quiz");
        moduleData.put("quiz_strategy", quizStrategy.getValue());
        moduleData.put("quiz_n", parseDouble(quizNCtrl.getText()));
        JSONObject extra = moduleData.optJSONObject("marks_data");
        if(extra == null) extra = new JSONObject();
        extra.put("short_quiz_strategy", shortQuizStrategy.getValue());
        moduleData.put("marks_data", extra);
        moduleData.put("short_quiz_n", parseDouble(shortQuizNCtrl.getText()));
        JSONArray oQ = new JSONArray(); for(TextField t: obtQuizzesCtrls) { try{oQ.put(Double.parseDouble(t.getText()));}catch(Exception e){} }
        moduleData.put("obt_quizzes", oQ);
        JSONArray osQ = new JSONArray(); for(TextField t: obtShortQuizzesCtrls) { try{osQ.put(Double.parseDouble(t.getText()));}catch(Exception e){} }
        moduleData.put("obt_short_quizzes", osQ);
        try {
            repository.saveCourseMarks(uid, activeSem, moduleData);
            System.out.println("Marks saved successfully");
            Platform.runLater(this::onBackClicked);
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    class FieldDef {
        String name, distKey, obtKey;
        TextField distInput = new TextField();
        TextField obtInput = new TextField();
        HBox cachedSetupRow;
        VBox cachedMarksRow;
        FieldDef(String name, String dKey, String oKey) {
            this.name = name; this.distKey = dKey; this.obtKey = oKey;
            setupInput(distInput); setupInput(obtInput);
        }
        HBox buildSetupRow() {
            if (cachedSetupRow == null) cachedSetupRow = CourseProgressDetailController.this.buildRow(name, distInput);
            return cachedSetupRow;
        }
        VBox buildMarksRow() {
            if (cachedMarksRow != null) {
                Label maxL = (Label) ((HBox)cachedMarksRow.getChildren().get(0)).getChildren().get(2);
                maxL.setText("Max: " + distInput.getText());
                return cachedMarksRow;
            }
            Label l = new Label(name); l.setTextFill(Color.WHITE); l.setFont(Font.font("System", FontWeight.BOLD, 14));
            Label maxL = new Label("Max: " + distInput.getText()); maxL.setTextFill(Color.web("#8091a7"));
            HBox header = new HBox(l, new Pane(), maxL); header.setAlignment(Pos.CENTER_LEFT); HBox.setHgrow(header.getChildren().get(1), Priority.ALWAYS);
            obtInput.setPrefWidth(80);
            HBox inputRow = new HBox(new Pane(), obtInput); inputRow.setAlignment(Pos.CENTER_RIGHT); HBox.setHgrow(inputRow.getChildren().get(0), Priority.ALWAYS);
            VBox box = new VBox(10, header, inputRow); box.setStyle("-fx-padding: 15; -fx-background-color: #131a2a; -fx-background-radius: 12;");
            cachedMarksRow = box;
            return box;
        }
    }
}
