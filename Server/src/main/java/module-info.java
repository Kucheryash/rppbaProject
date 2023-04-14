module Server {
    requires javafx.controls;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;

    exports models;
    exports DB;
}