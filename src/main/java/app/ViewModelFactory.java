package app;

import app.models.CD;
import app.viewmodels.SidebarViewModel;

import java.time.LocalDate;
import java.util.ArrayList;

public class ViewModelFactory {
    ModelFactory modelFactory;
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
}
