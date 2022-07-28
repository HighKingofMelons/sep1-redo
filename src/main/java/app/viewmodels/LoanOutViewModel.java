package app.viewmodels;

import app.models.interfaces.LoanItem;

public class LoanOutViewModel {
  private LoanItem model;

  public LoanOutViewModel(LoanItem model){
    this.model = model;
  }

  public void LoanOut(String email, boolean isTeacher){
    model.borrow(email, isTeacher);
  }
}
