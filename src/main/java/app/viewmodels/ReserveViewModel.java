package app.viewmodels;

import app.models.interfaces.ReserveItem;

public class ReserveViewModel
{
  private ReserveItem model;

  public void ReserveViewModel(ReserveItem model){
    this.model = model;
  }


  public void addReservation(String reserveeEmail){
    model.addReservation(reserveeEmail);
  }

}
