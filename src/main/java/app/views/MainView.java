package app.views;

import app.ViewHandler;
import app.models.Item;
import app.utils.ItemType;
import app.viewmodels.MainViewModel;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.SubScene;
import javafx.scene.control.*;

import javafx.scene.input.MouseEvent;

public class MainView {
    // topbar
    @FXML
    private TextField queryField;
    @FXML
    private ChoiceBox<String> filterDropdown;

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
    private MainViewModel vm;

    public void init(ViewHandler vh, Parent sidebar, MainViewModel mvm) {
        viewHandler = vh;
        vm = mvm;
        sidebarView.setRoot(sidebar);

        // adding filter choice values
        filterDropdown.getItems().add("All");
        filterDropdown.getItems().add("Book");
        filterDropdown.getItems().add("Article");
        filterDropdown.getItems().add("CD");
        filterDropdown.getItems().add("DVD");
        filterDropdown.setValue("All"); // setting initial filter value

        // setting column values
        setColumnFactory(statusColumn, "status");
        setColumnFactory(titleColumn, "title");
        setColumnFactory(typeColumn, "type");
        setColumnFactory(authorColumn, "author");
        setColumnFactory(isbnColumn, "isbn");
        setColumnFactory(magazineColumn, "magazine");

        // binding items updates
        itemsTable.itemsProperty().bind(mvm.getItems());
        tableSelectionUpdate();
    }

    private void setColumnFactory(TableColumn<Item, String> col, String field) {
        col.setCellValueFactory(itemCell -> {
            // itemCell.getValue() returns the Item instance for a particular TableView row
            return itemCell.getValue().getField(field);
        });
    }

    private void tableSelectionUpdate() {
        //https://stackoverflow.com/a/22036817
        itemsTable.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {
            @Override
            public void changed(ObservableValue observableValue, Object oldValue, Object newValue) {
                //Check whether item is selected and set value of selected item to Label
                if (itemsTable.getSelectionModel().getSelectedItem() != null) {
                    TableView.TableViewSelectionModel selectionModel = itemsTable.getSelectionModel();
                    // updating the sidebar
                    viewHandler.switchSidebarItem((Item) selectionModel.getSelectedItem());
                } else {
                    // updating the sidebar
                    viewHandler.switchSidebarItem(null);
                }
            }
        });
    }

    @FXML
    private void onAddButton(MouseEvent event) {
        viewHandler.openAddItemView();
    }

    @FXML
    private void onRemoveButton(MouseEvent event) {
        Item selected = itemsTable.getSelectionModel().getSelectedItem();
        vm.removeItem(selected);
        viewHandler.switchSidebarItem(null);
    }

    @FXML
    private void onSearchButton(MouseEvent event) {
        // setting filter
        ItemType filterType = null; // null represents all
        switch (filterDropdown.getValue()) {
            case "Book" -> filterType = ItemType.Book;
            case "Article" -> filterType = ItemType.Article;
            case "CD" -> filterType = ItemType.CD;
            case "DVD" -> filterType = ItemType.DVD;
        }

        vm.searchItems(queryField.getText(), filterType);
    }
}
