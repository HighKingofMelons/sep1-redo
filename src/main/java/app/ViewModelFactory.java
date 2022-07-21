package app;

import app.models.CD;
import app.models.SidebarItem;
import app.viewmodels.SidebarViewModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class ViewModelFactory {
    ModelFactory modelFactory;
    public ViewModelFactory (ModelFactory mf) {
        modelFactory = mf;
    }

    public SidebarViewModel makeSidebarViewModel () {
        CD test = new CD(
                "testemail@what.no",
                LocalDate.now().plusMonths(3),
                "Test1",
                new ArrayList<String>(),
                LocalDate.now().minusMonths(1)
        );

        return new SidebarViewModel(test);
    }
}
