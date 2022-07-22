package app.models;

import java.time.LocalDate;

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
