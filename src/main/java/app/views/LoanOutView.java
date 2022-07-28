package app.views;

import app.ViewHandler;
import app.viewmodels.LoanOutViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


public class LoanOutView {
  private LoanOutViewModel loanOutViewModel;

  @FXML
  private TextField emailTextField;

  @FXML
  private CheckBox checkBoxTeacher;

  @FXML
  private Button addButton;

  public void init(ViewHandler vh, LoanOutViewModel vm){
    loanOutViewModel = vm;

    onType(null);
  }

  @FXML
  public void onLoanOut(ActionEvent event) {
    loanOutViewModel.LoanOut(emailTextField.getText(), checkBoxTeacher.isSelected());
  }

  @FXML
  public void onType (KeyEvent event) {
    addButton.setDisable(emailTextField.getText().length() < 1);
  }
}
