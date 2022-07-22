package app.models;

import java.time.LocalDate;

public class LoanOutViewModel
{
  private LoanItem model;

  public LoanOutViewModel(LoanItem model){
    this.model = model;
  }

  public void LoanOut(String email, boolean IsLecturer){
    if(IsLecturer = true){
      model.borrow(email, LocalDate.now().plusMonths(1));
    }
  }
}
