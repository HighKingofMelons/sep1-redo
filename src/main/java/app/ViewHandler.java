package app;

import app.views.MainView;
import app.views.SidebarView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class ViewHandler {
    ViewModelFactory viewModelFactory;
    Stage mainStage;

    MainView mainView;
    SidebarView sidebarView;

    public ViewHandler (ViewModelFactory vmf, Stage stage) {
        viewModelFactory = vmf;
        mainStage = stage;

        URL mainfxml = getClass().getResource("/app/main-view.fxml");
        URL sidebarfxml = getClass().getResource("/app/sidebar-view.fxml");

        FXMLLoader mainLoader = new FXMLLoader(mainfxml);
        FXMLLoader sidebarLoader = new FXMLLoader(sidebarfxml);

        Scene mainScene;
        Scene sidebarScene;
        try {
            mainScene = mainLoader.load();
            sidebarScene = sidebarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mainView = mainLoader.getController();
        sidebarView = sidebarLoader.getController();
        mainView.init(this, sidebarScene);

        mainStage.setScene(mainScene);
        mainStage.show();
    }

    public void openAddItemView() {
        URL fxmlLocation = getClass().getResource("/app/add-item-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

        Scene addItemScene;
        try {
            addItemScene = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Stage addItemStage = new Stage();
        addItemStage.initOwner(mainStage);
        addItemStage.setScene(addItemScene);
        addItemStage.show();
    }

    public void switchText() {
        sidebarView.text("Wow i did it!");
    }
}
