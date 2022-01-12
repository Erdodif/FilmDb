package hu.petrik.filmdb;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class MainController extends Controller{
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
    private FilmDb db;

    public void initialize(){
        colCim.setCellValueFactory(new PropertyValueFactory<>("cim"));
        colKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        colHossz.setCellValueFactory(new PropertyValueFactory<>("hossz"));
        colErtekeles.setCellValueFactory(new PropertyValueFactory<>("ertekeles"));
        try {
            this.db =new FilmDb();
            loadDB();
        }
        catch (Exception e){
            smallError(e.getMessage());
        }
    }

    private void loadDB(){
        try {
            List<Film> filmek = db.getFilmek();
            filmTable.getItems().clear();
            for (Film film: filmek){
                filmTable.getItems().add(film);
            }
        }
        catch (SQLException e){
            exceptionAllert(e);
        }
    }

    @FXML
    public void onHozzaadasButtonClick(ActionEvent actionEvent) {
        Scene scene = null;
        try {
            Stage stage = new Stage();
            FXMLLoader fxmlLoader = new FXMLLoader(FilmApp.class.getResource("add-view.fxml"));
            scene = new Scene(fxmlLoader.load(), 600, 200);
            stage.setTitle("FilmDB - Új elem");
            stage.setScene(scene);
            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    loadDB();
                }
            });
            stage.show();
        } catch (IOException e) {
            exceptionAllert(e);
        }
    }

    @FXML
    public void onModositasButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onTorlesButtonClick(ActionEvent actionEvent) {
        int selectedIndex = filmTable.getSelectionModel().getSelectedIndex();
        if(selectedIndex == -1){
            smallError("Nincs kiválasztva elem!");
            return;
        }
        Film torlendoFilm = filmTable.getSelectionModel().getSelectedItem();
        if (!confirm("Biztos, hogy törölni szeretné az alábbi elemet?","A film: "+torlendoFilm.hashCode())){
            return;
        }
        try {
            if(db.deleteFilm(torlendoFilm.getId())){
                smallAlert("Sikeres törlés");
                loadDB();
                return;
            }
            smallAlert("Sikertelen törlés");
        }
        catch (Exception e){
            exceptionAllert(e);
        }
    }

}