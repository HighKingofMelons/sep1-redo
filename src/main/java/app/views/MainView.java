package app.views;

import app.ViewHandler;
import app.models.Item;
import app.utils.ItemType;
import app.viewmodels.MainViewModel;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

public class MainView {
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
    private TableView<Item> itemsTable;
    @FXML
    private TableColumn<Item, String> statusColumn;
    @FXML
    private TableColumn<Item, String> titleColumn;
    @FXML
    private TableColumn<Item, String> typeColumn;
    @FXML
    private TableColumn<Item, String> authorColumn;
    @FXML
    private TableColumn<Item, String> isbnColumn;
    @FXML
    private TableColumn<Item, String> magazineColumn;

    // sidebar
    @FXML
    private SubScene sidebarView;

    private ViewHandler viewHandler;
    private MainViewModel mainViewModel;

    public void init(ViewHandler vh, Parent sidebar, MainViewModel mvm) {
        viewHandler = vh;
        mainViewModel = mvm;
        sidebarView.setRoot(sidebar);

        // adding filter choice values
        filterDropdown.getItems().add("All");
        filterDropdown.getItems().add("Book");
        filterDropdown.getItems().add("Article");
        filterDropdown.getItems().add("CD");
        filterDropdown.getItems().add("DVD");
        filterDropdown.setValue("All"); // setting initial filter value

        // binding items updates
        itemsTable.itemsProperty().bind(mvm.getItems());

        // setting column values
        setColumnFactory(statusColumn, "status");
        setColumnFactory(titleColumn, "title");
        setColumnFactory(typeColumn, "type");
        setColumnFactory(authorColumn, "author");
        setColumnFactory(isbnColumn, "isbn");
        setColumnFactory(magazineColumn, "magazine");
    }

    public void setColumnFactory(TableColumn<Item, String> col, String field) {
        col.setCellValueFactory(p -> {
            // p.getValue() returns the Person instance for a particular TableView row
            return p.getValue().getField(field);
        });
    }

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
