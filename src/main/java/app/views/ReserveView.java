package app.views;

import app.ViewHandler;
import app.viewmodels.ReserveViewModel;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ReserveView
{
  private ViewHandler viewHandler;
  private ReserveViewModel reserveViewModel;

  @FXML
  private TextField emailTextField;

  public void init(ViewHandler vh, ReserveViewModel vm){
    viewHandler = vh;
    reserveViewModel = vm;
  }

  public void onReserve(ActionEvent actionEvent)
  {
    reserveViewModel.addReservation(emailTextField.getText());
  }
}
