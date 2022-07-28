package app;

import app.models.CD;
import app.models.interfaces.LoanItem;
import app.models.interfaces.ReserveItem;
import app.viewmodels.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class ViewModelFactory {
    private ModelFactory modelFactory;
    public ViewModelFactory (ModelFactory mf) {
        modelFactory = mf;
    }

    public SidebarViewModel makeSidebarViewModel () {
        return new SidebarViewModel(null);
    }

    public ReserveViewModel reserveViewModel (ReserveItem item) {
        return new ReserveViewModel(item);
    }

    public LoanOutViewModel loanOutViewModel (LoanItem item) {
        return new LoanOutViewModel(item);
    }

    public MainViewModel makeMainViewModel() {
        return new MainViewModel(modelFactory.getInventory(), this);
    }

    public AddItemViewModel makeAddItemViewModel() {
        return new AddItemViewModel(modelFactory.getInventory());
    }
}
