package app;

import app.models.interfaces.LoanItem;
import app.models.interfaces.ReserveItem;
import app.models.interfaces.SidebarItem;
import app.viewmodels.LoanOutViewModel;
import app.viewmodels.ReserveViewModel;
import app.views.LoanOutView;
import app.views.MainView;
import app.views.ReserveView;
import app.views.SidebarView;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
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

        URL mainfxml = getClass().getResource("/app/mainView.fxml");
        URL sidebarfxml = getClass().getResource("/app/sidebar-view.fxml");

        FXMLLoader mainLoader = new FXMLLoader(mainfxml);
        FXMLLoader sidebarLoader = new FXMLLoader(sidebarfxml);

        Scene mainScene;
        Parent sidebarParent;
        try {
            mainScene = mainLoader.load();
            sidebarParent = sidebarLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        mainView = mainLoader.getController();
        sidebarView = sidebarLoader.getController();
        mainView.init(this, sidebarParent, viewModelFactory.makeMainViewModel());
        sidebarView.init(this, viewModelFactory.makeSidebarViewModel());

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

    public void openLoanOutView(LoanItem item) {
        URL fxmlLocation = getClass().getResource("/app/loan-out-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

        Scene addItemScene;
        try {
            addItemScene = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        LoanOutView loanOutView = fxmlLoader.getController();
        loanOutView.init(this, viewModelFactory.loanOutViewModel(item));
        Stage addItemStage = new Stage();
        addItemStage.initOwner(mainStage);
        addItemStage.setScene(addItemScene);
        addItemStage.show();
    }

    public void openReservationView(ReserveItem item) {
        URL fxmlLocation = getClass().getResource("/app/reserve-view.fxml");
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);

        Scene addItemScene;
        try {
            addItemScene = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ReserveView reserveView = fxmlLoader.getController();
        reserveView.init(this, viewModelFactory.reserveViewModel(item));
        Stage addItemStage = new Stage();
        addItemStage.initOwner(mainStage);
        addItemStage.setScene(addItemScene);
        addItemStage.show();
    }

    public void switchSidebarItem(SidebarItem item) {
        sidebarView.setModel(item);
    }
}
