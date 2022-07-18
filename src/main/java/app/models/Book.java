package app.models;

public class Book extends Item
{
  private String author;
  private String ISBN;

  public Book(String title, String author, String ISBN)
  {
    author = this.author;
    ISBN = this.ISBN;
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
