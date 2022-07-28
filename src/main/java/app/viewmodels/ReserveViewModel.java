package app.viewmodels;

import app.models.interfaces.ReserveItem;

public class ReserveViewModel {
  private ReserveItem model;

  public ReserveViewModel(ReserveItem model){
    this.model = model;
  }


  public void addReservation(String email){
    model.addReservation(email);
  }

}
