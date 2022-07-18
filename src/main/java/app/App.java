package app;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        ModelFactory mf = new ModelFactory();
        ViewModelFactory vmf = new ViewModelFactory(mf);
        ViewHandler vh = new ViewHandler(vmf, stage);
    }

    public static void main(String[] args) {
        App.launch();
    }
}
