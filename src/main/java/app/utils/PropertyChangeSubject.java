package app.utils;

import java.beans.PropertyChangeListener;

public interface PropertyChangeSubject {
    /**
     * Add a given Listener to be notified of a given property's changes
     * @param propertyName a String of the name of the property to receive changes from
     * @param propertyChangeListener the Object to be notified
     */
    void addPropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener);
    /**
     * Stop a given Listener from being notified of a given property's changes
     * @param propertyName a String of the name of the property changes are received from
     * @param propertyChangeListener the Object to no longer be notified
     */
    void removePropertyChangeListener(String propertyName, PropertyChangeListener propertyChangeListener);
}
