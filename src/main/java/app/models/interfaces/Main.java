package app.models.interfaces;

import app.models.Item;
import app.utils.ItemType;
import app.utils.PropertyChangeSubject;
import java.util.ArrayList;

/**
 * Interface mainly for MainViewModel to interact with Inventory
 */
public interface Main extends PropertyChangeSubject {
    /**
     * Searches through all items in inventory and returns matching results
     * @param query The item's title to search for
     * @param filter Filter by specific item title, null if all items included
     * @return List of all matching item results
     */
    ArrayList<Item> searchItems(String query, ItemType filter);

    /**
     * Removes an item from inventory
     * @param item The item to remove
     */
    void removeItem(Item item);

    /**
     * Gets all items from the inventory
     * @return List of all items
     */
    ArrayList<Item> getAllItems();
}
