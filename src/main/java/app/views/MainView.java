package app.views;

import app.ViewHandler;
import app.models.CD;
import app.viewmodels.MainViewModel;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

public class MainView {
    /*@FXML
    private SplitPane splitPane;
     */
    // topbar
    @FXML
    private Button addButton;
    @FXML
    private Button removeButton;
    @FXML
    private TextField queryField;
    @FXML
    private ChoiceBox<String> filterDropdown;
    @FXML
    private Button searchButton;

    // items list
    @FXML
    private TableView<String> itemsTable;
    @FXML
    private TableColumn<String, String> statusColumn;
    @FXML
    private TableColumn<String, String> titleColumn;
    @FXML
    private TableColumn<String, String> typeColumn;
    @FXML
    private TableColumn<String, String> authorColumn;
    @FXML
    private TableColumn<String, String> isbnColumn;
    @FXML
    private TableColumn<String, String> magazineColumn;

    // sidebar
    @FXML
    private SubScene sidebarView;

    private ViewHandler viewHandler;
    private MainViewModel mainViewModel;

    public void init(ViewHandler vh, Scene scene) {
        // TODO: Add mainViewModel as parameter
        viewHandler = vh;
        //mainViewModel = mm;
        //splitPane.getItems().set(1, scene.getRoot()); // what is this supposed to do?

        // adding filter choice values
        filterDropdown.getItems().add("All");
        filterDropdown.getItems().add("Book");
        filterDropdown.getItems().add("Article");
        filterDropdown.getItems().add("CD");
        filterDropdown.getItems().add("DVD");
        filterDropdown.setValue("All"); // setting initial filter value
    }

    /*
    @FXML
    private void onButton (ActionEvent event) {
        viewHandler.switchSidebarItem(new CD("Test2"));
        viewHandler.openAddItemView();
    }
     */

    @FXML
    private void onAddButton(MouseEvent event) {
        System.out.println("Main Add button action!");
        viewHandler.openAddItemView();
    }

    @FXML
    private void onRemoveButton(MouseEvent event) {
        System.out.println("Main Remove button action!");
    }

    @FXML
    private void onSearchButton(MouseEvent event) {
        System.out.println("Main Search button action!");
    }
}
