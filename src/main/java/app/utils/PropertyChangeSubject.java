package app.utils;

import java.beans.PropertyChangeListener;

public interface PropertyChangeSubject {
    void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);
    void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
}
