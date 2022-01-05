module hu.petrik.filmdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens hu.petrik.filmdb to javafx.fxml;
    exports hu.petrik.filmdb;
}