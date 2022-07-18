package app.models;

import app.utils.Exporter;
import app.utils.ItemType;

import java.util.ArrayList;

public class Inventory implements Main, AddItem {
    private ArrayList<Item> items;

    public Inventory() {
        //TODO: add modelfactory
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

    }

    /**
     * Loads saved items, if they exist
     * @return Saved items
     */
    private ArrayList<Item> load() {
        return Exporter.loadSave();
    }
}
