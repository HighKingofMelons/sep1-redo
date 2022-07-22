package app.models.interfaces;

import java.time.LocalDate;
import java.util.Date;

public interface LoanItem
{
  void borrow(String email, boolean isTeacher);
}
