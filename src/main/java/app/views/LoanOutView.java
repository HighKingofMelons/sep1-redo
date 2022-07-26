package app.views;

import app.ViewHandler;
import app.viewmodels.LoanOutViewModel;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import javax.swing.*;


public class LoanOutView
{
  private ViewHandler viewHandler;
  private LoanOutViewModel loanOutViewModel;

  @FXML
  private TextField emailTextField;


  public void init(ViewHandler vh, LoanOutViewModel vm){
    viewHandler = vh;
    loanOutViewModel = vm;

  }


  public void onLoanOut(javafx.event.ActionEvent actionEvent)
  {
    loanOutViewModel.LoanOut(emailTextField.getText(), true);
  }
}
