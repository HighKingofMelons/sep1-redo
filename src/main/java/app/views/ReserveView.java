package app.views;

import app.ViewHandler;
import app.viewmodels.ReserveViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

import javax.swing.plaf.basic.BasicButtonUI;

public class ReserveView
{
  private ViewHandler viewHandler;
  private ReserveViewModel reserveViewModel;

  @FXML
  private TextField emailTextField;
  @FXML
  private Button addButton;

  public void init(ViewHandler vh, ReserveViewModel vm){
    viewHandler = vh;
    reserveViewModel = vm;
    onType(null);
  }

  public void onType (KeyEvent event) {
    addButton.setDisable(emailTextField.getText().length() < 1);
  }

  public void onReserve(ActionEvent actionEvent)
  {
    reserveViewModel.addReservation(emailTextField.getText());
  }
}
