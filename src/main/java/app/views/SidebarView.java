package app.views;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class SidebarView {
    @FXML
    private Label testLabel;

    public void text(String text) {
        testLabel.setText(text);
    }
}
