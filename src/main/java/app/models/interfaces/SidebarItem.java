package app.models.interfaces;

import app.utils.PropertyChangeSubject;

import java.time.LocalDate;
import java.util.ArrayList;

public interface SidebarItem extends PropertyChangeSubject {
    /**
     * Return the Item's title
     * @return a String of the Item's title
     */
    String getTitle();
    /**
     * Return the email of the current borrower
     * @return a String of the email of the current borrower, returns None when not loaned out
     */
    String getBorrowerEmail();
    /**
     * Return the due date for return of the Item
     * @return a LocalDate of the Item's current due date, returns None when not loaned out
     */
    LocalDate getReturnDate();
    /**
     * Return the Item's current reservations
     * @return a ArrayList of Strings that contains the email of current reservees.
     */
    ArrayList<String> getReservations();
    /**
     * Remove a given reservation from an email
     * @param email a String of the email to be removed from Reservations
     */
    void removeReservation(String email);
    /**
     * Mark the item as no longer borrowed
     */
    void returnItem();
}
