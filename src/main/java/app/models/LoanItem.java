package app.models;

import java.time.LocalDate;
import java.util.Date;

public interface LoanItem
{
  public void borrow(String email, LocalDate returntime);
}
