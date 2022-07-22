package app.models;

import app.utils.ItemType;

import java.time.LocalDate;
import java.util.ArrayList;
import app.utils.ItemType;


public class Article extends Item
{
  private final String author;
  private final String magazine;


  public Article(String title, String author, String magazine){
    super(ItemType.Article, title);
    this.author = author;
    this.magazine = magazine;
  }
  public Article(String borrowerEmail, LocalDate returnDate, String title, ArrayList<String> reservations, LocalDate dateAddedToLibrary, String author, String magazine){
    super(borrowerEmail, returnDate, title, reservations, ItemType.Article,
        dateAddedToLibrary);
    this.author = author;
    this.magazine = magazine;
  }

  public String getAuthor()
  {
    return author;
  }

  public String getMagazine()
  {
    return magazine;
  }
}
