package app.views;

import app.ViewHandler;
import app.models.CD;
import app.models.Item;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.SplitPane;

import javafx.event.ActionEvent;

public class MainView {
    @FXML
    private SplitPane splitPane;

    ViewHandler viewHandler;

    public void init(ViewHandler vh, Scene scene) {
        viewHandler = vh;
        splitPane.getItems().set(1, scene.getRoot());
    }

    @FXML
    private void onButton (ActionEvent event) {
        viewHandler.switchSidebarItem(new CD("Test2"));
        viewHandler.openAddItemView();
    }
}
