package app;

import app.models.CD;
import app.models.interfaces.LoanItem;
import app.models.interfaces.ReserveItem;
import app.viewmodels.LoanOutViewModel;
import app.models.interfaces.Main;
import app.viewmodels.MainViewModel;
import app.viewmodels.ReserveViewModel;
import app.viewmodels.SidebarViewModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class ViewModelFactory {
    private ModelFactory modelFactory;
    public ViewModelFactory (ModelFactory mf) {
        modelFactory = mf;
    }

    public SidebarViewModel makeSidebarViewModel () {
        ArrayList<String> emails = new ArrayList<>();
        emails.add("test@email.seven");
        emails.add("test@email.six");
        emails.add("test@email.four");

        CD test = new CD(
                "testemail@what.no",
                LocalDate.now().plusMonths(3),
                "Test1",
                emails,
                LocalDate.now().minusMonths(1)
        );

        return new SidebarViewModel(test);
    }

    public ReserveViewModel reserveViewModel (ReserveItem item) {
        return new ReserveViewModel(item);
    }

    public LoanOutViewModel loanOutViewModel (LoanItem item) {
        return new LoanOutViewModel(item);
    }

    public MainViewModel makeMainViewModel() {
        return new MainViewModel(modelFactory.inventory, this);
    }
}
