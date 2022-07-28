package app;

import app.models.Inventory;

public class ModelFactory {
    private final Inventory inventory; // public so the ViewModelHandler can access
    public ModelFactory() {
        inventory = new Inventory();
    }

    public Inventory getInventory() {
        return inventory;
    }
}
