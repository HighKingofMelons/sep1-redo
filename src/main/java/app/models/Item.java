package app.models;

import app.utils.ChangeType;
import app.utils.ItemType;
import app.utils.ListChange;
import app.utils.PropertyChangeSubject;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.time.LocalDate;


public abstract class Item implements SidebarItem
{
  private PropertyChangeSupport pcs;
  private String borrowerEmail;
  private LocalDate returnDate;
  private String title;
  private ArrayList<String> reservations;
  private LocalDate dateAddedToLibrary;
  private ItemType type;

  public Item(ItemType type, String title){
    pcs = new PropertyChangeSupport(this);
    this.type = type;
    this.title = title;
    dateAddedToLibrary = LocalDate.now();
  }
  public Item(String borrowerEmail, LocalDate returnDate, String title, ArrayList<String> reservations, ItemType type, LocalDate dateAddedToLibrary){
    pcs = new PropertyChangeSupport(this);
    this.borrowerEmail = borrowerEmail;
    this.returnDate = returnDate;
    this.title = title;
    this.reservations = reservations;
    this.type = type;
    this.dateAddedToLibrary = dateAddedToLibrary;
  }
  public void addResevation(String email) {
    if (!reservations.contains(email)) {
      reservations.add(email);
      firePropertyChange("reservations", null, new ListChange(ChangeType.ADD, email));
    }
  }

  public void borrow(String email, boolean isTeacher){
    if (isTeacher = true){
      borrowerEmail = email;
      LocalDate returnDate = LocalDate.now().plusMonths(5);
    }

  }
  public void removeReservation(String email){
    reservations.remove(email);
  }
  public boolean isReserving (String email){
    return reservations.contains(email);
  }

  public String getFirstReserver(){
    if (reservations.size() > 0){
      return reservations.get(0);
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

  public ArrayList<String> getReservations()
  {
    return reservations;
  }

  public LocalDate getDateAddedToLibrary()
  {
    return dateAddedToLibrary;
  }

  public LocalDate getReturnDate()
  {
    return returnDate;
  }

  public String getBorrowerEmail()
  {
    return borrowerEmail;
  }

  @Override
  public void returnItem() {
    //empty for now
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
}
