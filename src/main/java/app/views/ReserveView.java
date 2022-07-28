package app.views;

import app.ViewHandler;
import app.viewmodels.ReserveViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class ReserveView {
  private ReserveViewModel reserveViewModel;

  @FXML
  private TextField emailTextField;
  @FXML
  private Button addButton;

  public void init(ViewHandler vh, ReserveViewModel vm){
    reserveViewModel = vm;
    onType(null);
  }

  public void onType (KeyEvent event) {
    addButton.setDisable(emailTextField.getText().length() < 1);
  }

  public void onReserve(ActionEvent event)
  {
    reserveViewModel.addReservation(emailTextField.getText());
  }
}
