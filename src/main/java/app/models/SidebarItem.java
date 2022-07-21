package app.models;

import java.time.LocalDate;
import java.util.ArrayList;

public interface SidebarItem {
    /**
     * Return the Item's title
     * @return a String of the Item's title
     */
    public String getTitle();
    /**
     * Return the email of the current borrower
     * @return a String of the email of the current borrower, returns None when not loaned out
     */
    public String getBorrowerEmail();
    /**
     * Return the due date for return of the Item
     * @return a LocalDate of the Item's current due date, returns None when not loaned out
     */
    public LocalDate getReturnDate();
    /**
     * Return the Item's current reservations
     * @return a ArrayList of Strings that contains the email of current reservees.
     */
    public ArrayList<String> getReservations();
    /**
     * Remove a given reservation from an email
     * @param email a String of the email to be removed from Reservations
     */
    public void removeReservation(String email);
    /**
     * Mark the item as no longer borrowed
     */
    public void returnItem();
}
