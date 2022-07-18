package app.models;

public class Article extends Item
{
  private String author;
  private String magazine;

  public Article(String title, String author, String magazine){
    author = this.author;
    magazine = this.magazine;
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
