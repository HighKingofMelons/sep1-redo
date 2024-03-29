package app.models;

import app.models.interfaces.LoanItem;
import app.models.interfaces.ReserveItem;
import app.models.interfaces.SidebarItem;
import app.utils.ChangeType;
import app.utils.ItemType;
import app.utils.ListChange;
import javafx.beans.property.SimpleStringProperty;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.time.LocalDate;


public abstract class Item implements SidebarItem, LoanItem, ReserveItem {
  private final PropertyChangeSupport pcs;
  private String borrowerEmail;
  private LocalDate returnDate;
  private final String title;
  private final ArrayList<String> reservations;
  private final LocalDate dateAddedToLibrary;
  private final ItemType type;
  private SimpleStringProperty status;

  public Item(ItemType type, String title){
    pcs = new PropertyChangeSupport(this);
    this.type = type;
    this.title = title;
    dateAddedToLibrary = LocalDate.now();
    reservations = new ArrayList<>();
    status = new SimpleStringProperty("OK");
    if (returnDate != null) {
      if (LocalDate.now().plusDays(2).isAfter(returnDate)) {
        status = new SimpleStringProperty("LATE");
      } else if (LocalDate.now().plusDays(-4).isAfter(returnDate)) {
        status = new SimpleStringProperty("SOON");
      }
    }
  }

  public Item(String borrowerEmail, LocalDate returnDate, String title, ArrayList<String> reservations, ItemType type, LocalDate dateAddedToLibrary){
    pcs = new PropertyChangeSupport(this);
    this.borrowerEmail = borrowerEmail;
    this.returnDate = returnDate;
    this.title = title;
    this.reservations = reservations;
    this.type = type;
    this.dateAddedToLibrary = dateAddedToLibrary;
    status = new SimpleStringProperty("OK");
    if (returnDate != null) {
      if (LocalDate.now().plusDays(2).isAfter(returnDate)) {
        status = new SimpleStringProperty("LATE");
      } else if (LocalDate.now().plusDays(-4).isAfter(returnDate)) {
        status = new SimpleStringProperty("SOON");
      }
    }
  }

  public void addReservation(String email) {
    if (!reservations.contains(email)) {
      reservations.add(email);
      firePropertyChange("reservations", null, new ListChange(ChangeType.ADD, email));
    }
  }

  public void removeReservation(String email){
    if (reservations.remove(email))
      firePropertyChange("reservations", null, new ListChange(ChangeType.REMOVE, email));
  }

  public ItemType getType() {
    return type;
  }

  public String getTitle() {
    return title;
  }

  public ArrayList<String> getReservations() {
    if (reservations != null)
      return (ArrayList<String>) reservations.clone();
    else
      return null;
  }

  public LocalDate getDateAddedToLibrary() {
    return dateAddedToLibrary;
  }

  public LocalDate getReturnDate() {
    return returnDate;
  }

  public String getBorrowerEmail() {
    return borrowerEmail;
  }

  protected void setBorrowerEmail(String email) {
    String oldEmail = borrowerEmail;
    borrowerEmail = email;
    removeReservation(email);
    firePropertyChange("borrowerEmail", oldEmail, borrowerEmail);
  }

  protected void setReturnDate(LocalDate date) {
    LocalDate oldDate = returnDate;
    returnDate = date;
    firePropertyChange("returnDate", oldDate, returnDate);
  }

  @Override
  public void returnItem() {
    String oldEmail = borrowerEmail;
    LocalDate oldDate = returnDate;
    borrowerEmail = null;
    returnDate = null;
    status.setValue("OK");
    firePropertyChange("borrowerEmail", oldEmail, null);
    firePropertyChange("returnDate", oldDate, null);
  }

  @Override
  public void addPropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener) {
    pcs.addPropertyChangeListener(propertyName, propertyChangeListener);
  }

  @Override
  public void removePropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener) {
    pcs.removePropertyChangeListener(propertyName, propertyChangeListener);
  }

  /**
   * Notifies relevant listeners to changes for a given property
   * @param propertyName a String of the changed variable/property's name
   * @param oldValue the previous value, if the change is to a list this should be set to null
   * @param newValue the new value, if the change is to a list pass a ListChange object here
   */
  protected void firePropertyChange (String propertyName, Object oldValue, Object newValue) {
    pcs.firePropertyChange(propertyName, oldValue, newValue);
  }

  public SimpleStringProperty getField(String field) {
    switch (field) {
      case "title" -> {
        return new SimpleStringProperty(title);
      }
      case "type" -> {
        return switch (type) {
          case Book -> new SimpleStringProperty("Book");
          case Article -> new SimpleStringProperty("Article");
          case CD -> new SimpleStringProperty("CD");
          case DVD -> new SimpleStringProperty("DVD");
        };
      }
      case "status" -> {
        return status;
      }
      case "author" -> {
        if (type == ItemType.Book) {
          return new SimpleStringProperty(
                  ((Book) this).getAuthor()
          );
        } else if (type == ItemType.Article) {
          return new SimpleStringProperty(
                  ((Article) this).getAuthor()
          );
        }
        return new SimpleStringProperty("---");
      }
      case "isbn" -> {
        if (type == ItemType.Book) {
          return new SimpleStringProperty(
                  ((Book) this).getISBN()
          );
        }
        return new SimpleStringProperty("---");
      }
      case "magazine" -> {
        if (type == ItemType.Article) {
          return new SimpleStringProperty(
                  ((Article) this).getMagazine()
          );
        }
        return new SimpleStringProperty("---");
      }
    }

    return new SimpleStringProperty("BROKEN!");
  }
}
