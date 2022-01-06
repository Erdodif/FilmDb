package hu.petrik.filmdb;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class MainController {
    @FXML
    private TableView<Film> filmTable;
    @FXML
    private TableColumn<Film, String> colCim;
    @FXML
    private TableColumn<Film, String> colKategoria;
    @FXML
    private TableColumn<Film, Integer> colHossz;
    @FXML
    private TableColumn<Film, Integer> colErtekeles;

    public void initialize(){
        colCim.setCellValueFactory(new PropertyValueFactory<>("cim"));
        colKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        colHossz.setCellValueFactory(new PropertyValueFactory<>("hossz"));
        colErtekeles.setCellValueFactory(new PropertyValueFactory<>("ertekeles"));
        try {
            FilmDb db = new FilmDb();
            List<Film> filmek = db.getFilmek();
            for (Film film: filmek){
                filmTable.getItems().add(film);
            }
        }
        catch (SQLException e){
            errorOutput(e);
        }
    }

    private void errorOutput(Exception e) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Hiba");
        alert.setHeaderText(e.getClass().toString());
        alert.setContentText(e.getMessage());
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Platform.runLater(alert::show);
            }
        },500);
    }

    @FXML
    public void onHozzaadasButtonClick(ActionEvent actionEvent) {
        Scene scene = null;
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(FilmApp.class.getResource("add-view.fxml"));
            scene = new Scene(fxmlLoader.load(), 300, 800);
            stage.setTitle("FilmDB - Új elem");
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            errorOutput(e);
        }
    }

    @FXML
    public void onModositasButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onTorlesButtonClick(ActionEvent actionEvent) {
    }
}