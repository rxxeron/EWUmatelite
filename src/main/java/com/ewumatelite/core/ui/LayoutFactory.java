package com.ewumatelite.core.ui;
import com.ewumatelite.core.utils.PlatformUtils;
import com.ewumatelite.features.sidebar.presentation.Sidebar;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;
public class LayoutFactory {
    public static Parent create(Stage stage, String uid, String activeSem, String screenTitle, Node content) {
        HBox desktopLayout = new HBox(new Sidebar(stage, uid, activeSem, screenTitle).getView(), content);
        HBox.setHgrow(content, Priority.ALWAYS);
        desktopLayout.getStyleClass().add("content-area-root");
        return desktopLayout;
    }
}
