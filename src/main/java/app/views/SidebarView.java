package app.views;

import app.ViewHandler;
import app.models.interfaces.LoanItem;
import app.models.interfaces.ReserveItem;
import app.models.interfaces.SidebarItem;
import app.viewmodels.SidebarViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class SidebarView {
    @FXML
    private Label titleLabel;
    @FXML
    private Label borrowerEmailLabel;
    @FXML
    private Label dueDateLabel;
    @FXML
    private Button loanButton;
    @FXML
    private Button returnButton;
    @FXML
    private Button addReservationButton;
    @FXML
    private Button removeReservationButton;
    @FXML
    private ListView<String> reservationListView;

    private ViewHandler viewHandler;
    private SidebarViewModel viewModel;

    public void init (ViewHandler vh, SidebarViewModel vm) {
        viewHandler = vh;
        viewModel = vm;

        titleLabel.textProperty().bind(viewModel.titleProperty());
        borrowerEmailLabel.textProperty().bind(viewModel.borrowerEmailProperty());
        dueDateLabel.textProperty().bind(viewModel.dueDateStringProperty());

        reservationListView.itemsProperty().bind(viewModel.reservationListProperty());
        removeReservationButton.disableProperty().bind(
                reservationListView.getSelectionModel().selectedItemProperty().isNull()
        );

        updateButtons();
    }

    public void setModel (SidebarItem model) {
        viewModel.setModel(model);
        updateButtons();
    }

    public void onLoanButton (ActionEvent event) {
        viewHandler.openLoanOutView((LoanItem) viewModel.getModel());
        updateButtons();
    }

    public void onAddReservationButton (ActionEvent event) {
        viewHandler.openReservationView((ReserveItem) viewModel.getModel());
    }

    public void onReturnButton (ActionEvent event) {
        viewModel.returnItem();
        updateButtons();
    }

    public void onRemoveReservationButton (ActionEvent event) {
        String selectedEmail = reservationListView.getSelectionModel().getSelectedItem();
        viewModel.removeReservation(selectedEmail);
    }

    public void updateButtons () {
        if (viewModel.getModel() != null) {
            if (viewModel.isBorrowedProperty().getValue()) {
                returnButton.setDisable(false);
                loanButton.setDisable(true);
            }
            else {
                returnButton.setDisable(true);
                loanButton.setDisable(false);
            }
            addReservationButton.setDisable(false);
        }
        else {
            returnButton.setDisable(true);
            loanButton.setDisable(true);
            addReservationButton.setDisable(true);
        }
    }
}
