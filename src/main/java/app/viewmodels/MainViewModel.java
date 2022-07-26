package app.viewmodels;

import app.ViewModelFactory;
import app.models.Item;
import app.models.interfaces.Main;
import app.utils.ItemType;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.beans.PropertyChangeEvent;
import java.util.ArrayList;

/**
 * ViewModel class for MainView that is in between Model and View
 */
public class MainViewModel{
    private Main model;
    private ViewModelFactory vmf;
    //private final ObservableList<Item> items;
    private final SimpleListProperty<Item> items;
    // for filtering
    private ItemType curFilter; // current filter
    private String filterStr; // current query

    public MainViewModel(Main model, ViewModelFactory vmf) {
        this.model = model;
        this.vmf = vmf;
        items = new SimpleListProperty<>();
        curFilter = null;
        filterStr = "";

        // property changes
        this.model.addPropertyChangeListener("items", this::onItemsChange);

        // initial setup
        ArrayList<Item> allItems = this.model.getAllItems();
        //ObservableList<Item> itemz = FXCollections.observableList(allItems);
        items.setValue(FXCollections.observableList(allItems));
    }

    public SimpleListProperty<Item> getItems() {return items;}

    /**
     * Removes the item from the list
     * @param item The item to be removed
     */
    public void removeItem(Item item) {
        model.removeItem(item);
    }

    /**
     * Returns a list of items that match the given query
     * @param query The string query
     * @param filter By what to filter, null if everything
     */
    public void searchItems(String query, ItemType filter) {
        ArrayList<Item> filtered = model.searchItems(query, filter);
        curFilter = filter;
        filterStr = query;
        items.clear();
        items.setValue(FXCollections.observableList(filtered));
    }

    /**
     * Event for whenever items
     * @param event The item update event from property change
     */
    private void onItemsChange(PropertyChangeEvent event) {
        // we dont care about new value since its going to get filtered anyway
        searchItems(filterStr, curFilter); // the real update of list
    }
}
