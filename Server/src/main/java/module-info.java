module Server {
    requires javafx.controls;
    requires java.desktop;
    requires javafx.graphics;
    requires javafx.fxml;
    requires java.sql;

    exports models;
    exports DB;
    exports models.old;
    exports DB.old;
}