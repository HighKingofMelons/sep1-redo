package app;

import app.models.Inventory;

public class ModelFactory {
    public final Inventory inventory; // public so the ViewModelHandler can access
    public ModelFactory() {
        inventory = new Inventory();
    }
}
