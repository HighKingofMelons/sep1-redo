package app.models;

import app.utils.ItemType;
import java.time.LocalDate;
import java.util.ArrayList;

public class DVD extends Item
{
  public DVD(String title){
    super(ItemType.CD, title);
  }
  public DVD(String borrowerEmail, LocalDate returnDate, String title, ArrayList<String> reservations, LocalDate dateAddedToLibrary){
    super(borrowerEmail, returnDate, title, reservations, ItemType.CD,
        dateAddedToLibrary);
  }
}
