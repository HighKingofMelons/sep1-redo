package app.models;

import app.models.interfaces.LoanItem;
import app.models.interfaces.ReserveItem;
import app.models.interfaces.SidebarItem;
import app.utils.ChangeType;
import app.utils.ItemType;
import app.utils.ListChange;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.time.LocalDate;


public abstract class Item implements SidebarItem, LoanItem, ReserveItem
{
  private final PropertyChangeSupport pcs;
  private String borrowerEmail;
  private LocalDate returnDate;
  private final String title;
  private ArrayList<String> reservations;
  private final LocalDate dateAddedToLibrary;
  private final ItemType type;

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
    if (reservations != null)
      return (ArrayList<String>) reservations.clone();
    else
      return null;
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

  protected void setBorrowerEmail(String email) {
    String oldEmail = borrowerEmail;
    borrowerEmail = email;
    firePropertyChange("borrowerEmail", oldEmail, borrowerEmail);
  }

  protected void setReturnDate(LocalDate date) {
    LocalDate oldDate = returnDate;
    returnDate = date;
    firePropertyChange("oldDate", oldDate, returnDate);
  }

  @Override
  public void returnItem() {
    String oldEmail = borrowerEmail;
    LocalDate oldDate = returnDate;
    borrowerEmail = null;
    returnDate = null;
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
}
