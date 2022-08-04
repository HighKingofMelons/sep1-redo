package app.models;

import app.models.interfaces.AddItem;
import app.models.interfaces.Main;
import app.utils.ChangeType;
import app.utils.Exporter;
import app.utils.ItemType;
import app.utils.ListChange;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Inventory implements Main, AddItem {
    private PropertyChangeSupport pcs;
    private final ArrayList<Item> items;

    public Inventory() {
        pcs = new PropertyChangeSupport(this);
        items = new ArrayList<>(load());
        for (Item i: items ) {
            i.addPropertyChangeListener("reservations", this::onItemChange);
            i.addPropertyChangeListener("borrowerEmail", this::onItemChange);
        }
    }

    @Override
    public ArrayList<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    @Override
    public void removeItem(Item item) {
        if (items.remove(item)) {
            item.removePropertyChangeListener("reservations", this::onItemChange);
            item.removePropertyChangeListener("borrowerEmail", this::onItemChange);
            firePropertyChange("items", null, new ListChange(ChangeType.REMOVE, item));
            save();
        }
    }

    @Override
    public void addItem(Item item) {
        item.addPropertyChangeListener("reservations", this::onItemChange);
        item.addPropertyChangeListener("borrowerEmail", this::onItemChange);

        items.add(item);
        firePropertyChange("items", null, new ListChange(ChangeType.ADD, item));
        save();
    }

    @Override
    public ArrayList<Item> searchItems(String query, ItemType filter) {
        ArrayList<Item> matching = new ArrayList<>(); // 1 operation
        for (Item item: items) { // n operations (O(1) if n < 2)
            // checking does type match filter or filter is set null (aka all) and title contains query
            // if statement check: 11n operations
            if ((item.getType() == filter || filter == null) &&
                (query.length() <= 0 || item.getTitle().toLowerCase().contains(query))) {
                matching.add(item); // n operations
            }
        }

        return matching; // 1 operation

        // In Total:
        // Best case scenario: bigO(1)
        // Average case scenario: bigO(n)
        // Worst case scenario: bigO(n)
    }

    /**
     * Saves currently loaded items to XML file
     */
    private void save() {
        Exporter.saveItems(items);
    }

    /**
     * Loads saved items, if they exist
     * @return Saved items
     */
    private ArrayList<Item> load() {
        return Exporter.loadSave();
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
    private void firePropertyChange (String propertyName, Object oldValue, Object newValue) {
        pcs.firePropertyChange(propertyName, oldValue, newValue);
    }

    public void onItemChange(PropertyChangeEvent event) {
        save();
    }
}
