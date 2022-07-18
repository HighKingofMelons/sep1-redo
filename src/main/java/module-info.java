module com.example.redoagaon {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens app to javafx.fxml;
    opens app.models to javafx.fxml;
    opens app.viewmodels to javafx.fxml;
    opens app.views to javafx.fxml;
    exports app;
}