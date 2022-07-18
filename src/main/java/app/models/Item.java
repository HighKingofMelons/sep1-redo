package app.models;

import app.utils.ItemType;

import java.util.ArrayList;
import java.util.Date;
import java.time.LocalDate;


public abstract class Item
{
  private String borrowerEmail;
  private Date returnDate;
  private String title;
  private ArrayList<String> reservation;
  private Date dateAddedToLibrary;
  private ItemType type;


  public void addResevation(String email){
    if (!reservation.contains(email))
      reservation.add(email);
  }

  public void borrow(String email, boolean isTeacher){
    if (isTeacher = true){
      borrowerEmail = email;
      LocalDate returnDate = LocalDate.now().plusMonths(5);
    }

  }
  public void removeReservation(String email){
    reservation.remove(email);

  }
  public boolean isReserving (String email){
    return reservation.contains(email);
  }

  public String getFirstReserver(){
    if (reservation.size() > 0){
      return reservation.get(0);
    }
    return null;
  }

  public ItemType getType()
  {
    return type;
  }

  public String getTitle() {
    return title;
  }

  public ArrayList<String> getReservation()
  {
    return reservation;
  }

  public Date getDateAddedToLibrary()
  {
    return dateAddedToLibrary;
  }

  public Date getReturnDate()
  {
    return returnDate;
  }

  public String getBorrowerEmail()
  {
    return borrowerEmail;
  }
}
