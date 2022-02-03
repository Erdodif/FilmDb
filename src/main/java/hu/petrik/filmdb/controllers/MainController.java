package hu.petrik.filmdb.controllers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import hu.petrik.filmdb.data.Film;
import hu.petrik.filmdb.data.FilmDb;
import hu.petrik.filmdb.http.RequestHandler;
import hu.petrik.filmdb.http.Response;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.*;

public class MainController extends Controller {
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

    public void initialize() {
        colCim.setCellValueFactory(new PropertyValueFactory<>("cim"));
        colKategoria.setCellValueFactory(new PropertyValueFactory<>("kategoria"));
        colHossz.setCellValueFactory(new PropertyValueFactory<>("hossz"));
        colErtekeles.setCellValueFactory(new PropertyValueFactory<>("ertekeles"));
        try {
            this.db = new FilmDb();
            loadDB();
        } catch (Exception e) {
            smallError(e.getMessage());
        }
    }

    private void loadDB() {
        try {
            Response response = RequestHandler.get("localhost:8080/filmapp/filmek");
            String content = response.getContent();
            if(response.getResponseCode() > 399){
                smallError(content);
                return;
            }
            Gson gaySon = new Gson();
            gaySon.fromJson(content, (new TypeToken<List<Film>>(){}).getType());
            List<Film> filmek = new ArrayList<>();
            filmTable.getItems().clear();
            for (Film film : filmek) {
                filmTable.getItems().add(film);
            }
        } catch (IOException e) {
            exceptionAllert(e);
        }
    }

    @FXML
    public void onHozzaadasButtonClick(ActionEvent actionEvent) {
        try {
            Controller newWindow = newWindow("hozzaad-view.fxml", "Új film", 600, 200);
            newWindow.getStage().setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    loadDB();
                }
            });
            newWindow.getStage().show();
        } catch (IOException e) {
            exceptionAllert(e);
        }
    }

    @FXML
    public void onModositasButtonClick(ActionEvent actionEvent) {
        int selectedIndex = filmTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            smallError("Nincs kiválasztva elem!");
            return;
        }
        Film modositando = filmTable.getSelectionModel().getSelectedItem();
        try {
            EditController newWindow = (EditController) newWindow(
                            "edit-view.fxml", modositando.getCim() + " Módosítása", 600, 200);

            newWindow.getStage().setOnHiding(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent windowEvent) {
                    loadDB();
                }
            });
            newWindow.setModositando(modositando);
            newWindow.getStage().show();
        } catch (IOException e) {
            e.printStackTrace();
            exceptionAllert(e);
        }
    }

    @FXML
    public void onTorlesButtonClick(ActionEvent actionEvent) {
        int selectedIndex = filmTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1) {
            smallError("Nincs kiválasztva elem!");
            return;
        }
        Film torlendoFilm = filmTable.getSelectionModel().getSelectedItem();
        if (!confirm("Biztos, hogy törölni szeretné az alábbi elemet?", "A film: " + torlendoFilm.hashCode())) {
            return;
        }
        try {
            if (db.deleteFilm(torlendoFilm.getId())) {
                smallAlert("Sikeres törlés");
                loadDB();
                return;
            }
            smallAlert("Sikertelen törlés");
        } catch (Exception e) {
            exceptionAllert(e);
        }
    }

}