package app.models;

import app.utils.Exporter;
import app.utils.ItemType;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;

public class Inventory implements Main, AddItem {
    private PropertyChangeSupport pcs;
    private final ArrayList<Item> items;

    public Inventory() {
        //TODO: add modelfactory
        pcs = new PropertyChangeSupport(this);
        items = new ArrayList<>(load());
    }

    @Override
    public ArrayList<Item> getAllItems() {
        return new ArrayList<>(items);
    }

    @Override
    public void removeItem(Item item) {
        items.remove(item);
        save();
    }

    @Override
    public void addItem(Item item) {
        items.add(item);
        save();
    }

    @Override
    public ArrayList<Item> searchItems(String query, ItemType filter) {
        ArrayList<Item> matching = new ArrayList<>();
        for (Item item: items) {
            // checking does type match filter or filter is set null (aka all) and title contains query
            if ((item.getType() == filter || filter == null) &&
                (query.length() <= 0 || item.getTitle().toLowerCase().contains(query))) {
                matching.add(item);
            }
        }

        return matching;
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

    private void firePropertyChange (String propertyName, Object oldValue, Object newValue) {
        pcs.firePropertyChange(propertyName, oldValue, newValue);
    }
}
