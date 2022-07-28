package app.views;

import app.ViewHandler;
import app.viewmodels.AddItemViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class AddItemView {
    // type, title & add button
    @FXML
    ChoiceBox<String> typeChoice;
    @FXML
    Button addButton;
    @FXML
    TextField titleField;

    // input field1
    @FXML
    HBox field1Box;
    @FXML
    Text field1Title;
    @FXML
    TextField field1Input;

    // input field2
    @FXML
    HBox field2Box;
    @FXML
    Text field2Title;
    @FXML
    TextField field2Input;

    private AddItemViewModel vm;

    public void init(ViewHandler vh, AddItemViewModel vm) {
        this.vm = vm;

        // adding type choices
        typeChoice.getItems().add("Book");
        typeChoice.getItems().add("Article");
        typeChoice.getItems().add("CD");
        typeChoice.getItems().add("DVD");
        typeChoice.setValue("Book"); // setting initial type value

        // updating visuals
        updateVisuals();
        updateAddButton();
    }

    /**
     * Updates visuals (disables/enables inputs based on type)
     */
    private void updateVisuals() {
        switch (typeChoice.getValue()) {
            case "Book" -> {
                field1Box.setDisable(false);
                field1Title.setText("Author:");
                field2Box.setDisable(false);
                field2Title.setText("ISBN:");
            }
            case "Article" -> {
                field1Box.setDisable(false);
                field1Title.setText("Author:");
                field2Box.setDisable(false);
                field2Title.setText("Magazine:");
            }
            case "CD", "DVD" -> {
                field1Box.setDisable(true);
                field2Box.setDisable(true);
            }
        }
    }

    /**
     * Disables/enables add button based on choice and provided inputs
     */
    private void updateAddButton() {
        if (titleField.getText().length() < 1) {
            // no title provided
            addButton.setDisable(true);
            return;
        }

        // checking necessary inputs based off type
        switch (typeChoice.getValue()) {
            case "Book", "Article" -> {
                if (field1Input.getText().length() < 1 || field2Input.getText().length() < 1) {
                    // fields not provided
                    addButton.setDisable(true);
                    return;
                }
            }
        }

        // everything satisfied, enabling add button
        addButton.setDisable(false);
    }

    /**
     * Clears all inputs except type choice
     */
    private void clearInputs() {
        titleField.setText("");
        field1Input.setText("");
        field2Input.setText("");
        updateAddButton();
    }

    @FXML
    public void onAddButton(MouseEvent event) {
        if (!addButton.isDisabled()) { // only if button is allowed to press
            switch (typeChoice.getValue()) {
                case "Book" -> vm.AddBook(titleField.getText(), field1Input.getText(), field2Input.getText());
                case "Article" -> vm.AddArticle(titleField.getText(), field1Input.getText(), field2Input.getText());
                case "CD" -> vm.AddCD(titleField.getText());
                case "DVD" -> vm.AddDVD(titleField.getText());
            }
        }
        clearInputs();
    }

    @FXML
    public void onClearButton(MouseEvent event) {
        clearInputs();
    }

    @FXML
    public void field1Action(KeyEvent event) {
        updateAddButton();
    }

    @FXML
    public void field2Action(KeyEvent event) {
        updateAddButton();
    }

    @FXML
    public void titleAction(KeyEvent event) {
        updateAddButton();
    }

    @FXML
    public void onChoice(ActionEvent event) {
        updateVisuals();
        updateAddButton();
    }
}
