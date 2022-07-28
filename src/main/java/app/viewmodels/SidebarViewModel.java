package app.viewmodels;

import app.models.interfaces.SidebarItem;
import app.utils.ListChange;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;

import java.beans.PropertyChangeEvent;
import java.time.LocalDate;

public class SidebarViewModel {
    private SidebarItem model;
    private final SimpleStringProperty title;
    private final SimpleStringProperty borrowerEmail;
    private final SimpleStringProperty dueDateString;
    private final SimpleBooleanProperty isBorrowed;
    private final SimpleListProperty<String> reservationList;

    private boolean isEmpty;

    public SidebarViewModel (SidebarItem model){
        title = new SimpleStringProperty();
        borrowerEmail = new SimpleStringProperty();
        dueDateString = new SimpleStringProperty();
        isBorrowed = new SimpleBooleanProperty();
        reservationList = new SimpleListProperty<>();

        setModel(model);
    }

    public void setModel(SidebarItem model) {
        if (this.model != null) {
            this.model.removePropertyChangeListener("borrowerEmail", this::onBorrowerChange);
            this.model.removePropertyChangeListener("returnDate", this::onDueDateChange);
            this.model.removePropertyChangeListener("reservations", this::onReservationChange);
            reservationList.clear();
        }

        if (model == null) {
            isEmpty = true;
            title.setValue("None");
            borrowerEmail.setValue("None");
            dueDateString.setValue("None");
            return;
        }
        isEmpty = false;
        this.model = model;

        // Update properties
        title.setValue(model.getTitle());
        if (model.getBorrowerEmail() != null)
            borrowerEmail.setValue(this.model.getBorrowerEmail());
        else
            borrowerEmail.setValue("None");
        if (model.getReturnDate() != null)
            dueDateString.setValue(this.model.getReturnDate().toString());
        else
            dueDateString.setValue("None");
        isBorrowed.setValue(this.model.getBorrowerEmail() != null);
        if (this.model.getReservations() != null)
            reservationList.setValue(FXCollections.observableList(this.model.getReservations()));

        this.model.addPropertyChangeListener("borrowerEmail", this::onBorrowerChange);
        this.model.addPropertyChangeListener("returnDate", this::onDueDateChange);
        this.model.addPropertyChangeListener("reservations", this::onReservationChange);
    }

    public SimpleStringProperty titleProperty() {
        return title;
    }
    public SimpleStringProperty borrowerEmailProperty() {
        return borrowerEmail;
    }
    public SimpleStringProperty dueDateStringProperty() {
        return dueDateString;
    }
    public SimpleBooleanProperty isBorrowedProperty() {
        return isBorrowed;
    }
    public SimpleListProperty<String> reservationListProperty() {
        return reservationList;
    }

    public void onBorrowerChange (PropertyChangeEvent event) {
        String newValue = (String) event.getNewValue();

        if (newValue != null)
            borrowerEmail.setValue(newValue);
        else
            borrowerEmail.setValue("None");
        isBorrowed.setValue(newValue != null);
    }

    public void onDueDateChange (PropertyChangeEvent event) {
        LocalDate newValue = (LocalDate) event.getNewValue();
        if (newValue != null)
            dueDateString.setValue(newValue.toString());
        else
            dueDateString.setValue("None");
    }

    public void onReservationChange (PropertyChangeEvent event) {
        ListChange listChange = (ListChange) event.getNewValue();

        switch (listChange.getChangeType()) {
            case ADD -> {
                if (!reservationList.contains(listChange.getChangedObject()))
                    reservationList.add((String) listChange.getChangedObject());
            }
            case REMOVE -> reservationList.remove((String) listChange.getChangedObject());
        }
    }

    public void removeReservation (String email) {
        if (!isEmpty)
            model.removeReservation(email);
    }

    public SidebarItem getModel() {
        return model;
    }

    public void returnItem() {
        model.returnItem();
    }
}
