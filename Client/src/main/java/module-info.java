module Client {
    requires javafx.controls;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.fxml;
    requires Server;
    requires java.sql;

    opens client to javafx.fxml;
    exports client;
    exports controllers;
    opens controllers to javafx.fxml;
    exports controllers.old;
    opens controllers.old to javafx.fxml;
}