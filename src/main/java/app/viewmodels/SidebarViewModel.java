package app.viewmodels;

import app.models.SidebarItem;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.time.LocalDate;

public class SidebarViewModel {
    private SidebarItem model;
    private SimpleStringProperty title;
    private SimpleStringProperty borrowerEmail;
    private SimpleStringProperty dueDateString;
    private SimpleBooleanProperty isBorrowed;

    public SidebarViewModel (SidebarItem model){
        title = new SimpleStringProperty();
        borrowerEmail = new SimpleStringProperty();
        dueDateString = new SimpleStringProperty();
        isBorrowed = new SimpleBooleanProperty();

        setModel(model);
    }

    public void setModel(SidebarItem model) {
        if (this.model != null) {
            this.model.removePropertyChangeListener("borrowerEmail", this::onBorrowerChange);
            this.model.removePropertyChangeListener("returnDate", this::onDueDateChange);
        }
        this.model = model;

        // Update properties
        title.setValue(model.getTitle());
        if (model.getBorrowerEmail() != null)
            borrowerEmail.setValue(model.getBorrowerEmail());
        else
            borrowerEmail.setValue("None");
        if (model.getReturnDate() != null)
            dueDateString.setValue(model.getReturnDate().toString());
        else
            dueDateString.setValue("None");
        isBorrowed.setValue(model.getBorrowerEmail() != null);

        this.model.addPropertyChangeListener("borrowerEmail", this::onBorrowerChange);
        this.model.addPropertyChangeListener("returnDate", this::onDueDateChange);
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

}
