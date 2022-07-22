package app.models;

import app.utils.ItemType;
import java.time.LocalDate;
import java.util.ArrayList;

public class Book extends Item
{
  private final String author;
  private final String ISBN;

  public Book(String title, String author, String ISBN){
    super(ItemType.Book, title);
    this.author = author;
    this.ISBN = ISBN;
  }

  public Book(String borrowerEmail, LocalDate returnDate, String title, ArrayList<String> reservations, LocalDate dateAddedToLibrary, String author, String ISBN){
    super(borrowerEmail, returnDate, title, reservations, ItemType.Book, dateAddedToLibrary);
    this.author = author;
    this.ISBN = ISBN;
  }

  public void borrow(String email, boolean isTeacher){
    if (super.getBorrowerEmail() != null)
      return;

    LocalDate returnDate = LocalDate.now();
    if (isTeacher) {
      super.setReturnDate(returnDate.plusMonths(6));
    } else {
      super.setReturnDate(returnDate.plusMonths(1));
    }
    super.setBorrowerEmail(email);
  }

  public String getAuthor()
  {
    return author;
  }

  public String getISBN()
  {
    return ISBN;
  }
}
