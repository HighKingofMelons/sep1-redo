package app.models;

import app.models.interfaces.LoanItem;

public class LoanOutViewModel
{
  private final LoanItem model;

  public LoanOutViewModel(LoanItem model){
    this.model = model;
  }

  public void LoanOut(String email, boolean isLecturer){
    model.borrow(email, isLecturer);
  }
}
