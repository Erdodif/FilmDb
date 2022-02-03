module hu.petrik.filmdb {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.gson;

    exports hu.petrik.filmdb;
    exports hu.petrik.filmdb.controllers;
    opens hu.petrik.filmdb.controllers to javafx.fxml;
    exports hu.petrik.filmdb.http;
    opens hu.petrik.filmdb.http to javafx.fxml;
    exports hu.petrik.filmdb.data;
    opens hu.petrik.filmdb.data to javafx.fxml, com.google.gson;
}