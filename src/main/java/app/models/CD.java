package app.models;

import app.utils.ItemType;
import java.time.LocalDate;
import java.util.ArrayList;

public class CD extends Item
{
  public CD(String title){
    super(ItemType.CD, title);
  }
  public CD(String borrowerEmail, LocalDate returnDate, String title, ArrayList<String> reservations, LocalDate dateAddedToLibrary){
    super(borrowerEmail, returnDate, title, reservations, ItemType.CD,
        dateAddedToLibrary);
  }

  public void borrow(String email, boolean isTeacher){
    if (super.getBorrowerEmail() != null)
      return;

    LocalDate returnDate = LocalDate.now();
    if (super.getDateAddedToLibrary().isAfter(LocalDate.now().minusYears(1))) {
      super.setReturnDate(returnDate.plusWeeks(2));
    }
    else if (isTeacher) {
      super.setReturnDate(returnDate.plusMonths(6));
    } else {
      super.setReturnDate(returnDate.plusMonths(1));
    }
    super.setBorrowerEmail(email);
  }
}
