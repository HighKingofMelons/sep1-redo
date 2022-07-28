package app.views;

import app.ViewHandler;
import app.viewmodels.LoanOutViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import javax.swing.*;


public class LoanOutView
{
  private ViewHandler viewHandler;
  private LoanOutViewModel loanOutViewModel;

  @FXML
  private TextField emailTextField;

  @FXML
  private CheckBox checkBoxTeacher;

  @FXML
  private Label labelFeedback;
  @FXML
  private Button addButton;

  public void init(ViewHandler vh, LoanOutViewModel vm){
    viewHandler = vh;
    loanOutViewModel = vm;

    onType(null);
  }

  public void onLoanOut(javafx.event.ActionEvent actionEvent)
  {
    loanOutViewModel.LoanOut(emailTextField.getText(), checkBoxTeacher.isSelected());
  }

  public void onType (KeyEvent event) {
    addButton.setDisable(emailTextField.getText().length() < 1);
  }
}
