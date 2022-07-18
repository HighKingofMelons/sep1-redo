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
}
