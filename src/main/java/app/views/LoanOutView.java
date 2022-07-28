package app.views;

import app.ViewHandler;
import app.viewmodels.LoanOutViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

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

  public void init(ViewHandler vh, LoanOutViewModel vm){
    viewHandler = vh;
    loanOutViewModel = vm;

  }


  public void onLoanOut(javafx.event.ActionEvent actionEvent)
  {
    if (checkBoxTeacher.isSelected()){
      loanOutViewModel.LoanOut(emailTextField.getText(), true);
      labelFeedback.setText("Succes");
    }
    else{
      labelFeedback.setText("Only teachers can loan out");
    }
  }
}
