package hu.petrik.filmdb.controllers;

import hu.petrik.filmdb.Controller;
import hu.petrik.filmdb.Film;
import hu.petrik.filmdb.FilmDb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class EditController extends Controller {
    @FXML
    public Spinner<Integer> hosszSpinner;
    @FXML
    public ChoiceBox<Integer> ertekelesChoiceBox;
    @FXML
    public TextField cimTextField;
    @FXML
    public TextField kategoriaTextField;

    private Film modositando;

    @FXML
    public void onSaveButtonClick(ActionEvent actionEvent) {
        String cim = cimTextField.getText();
        String kategoria = kategoriaTextField.getText();
        //Pattern pattern = Pattern.compile("\\p{javaAlphabetic}");
        //boolean rendben = Pattern.matches(String.valueOf(pattern),cim);
        int ertekelesIndex = ertekelesChoiceBox.getSelectionModel().getSelectedIndex();
        if(cim.isEmpty()){
            smallError("A cím megadása kötelező!");
            return;
        }
        if(kategoria.isEmpty()){
            smallError("A kategória megadása kötelező!");
            return;
        }
        try {
            Integer hossz = hosszSpinner.getValue();
            FilmDb db = new FilmDb();
            if(ertekelesIndex == -1){
                smallAlert("Értékelés megadása kötelező!");
                return;
            }
            int ertekeles = ertekelesChoiceBox.getValue();
            modositando.setCim(cim);
            modositando.setKategoria(kategoria);
            modositando.setHossz(hossz);
            modositando.setErtekeles(ertekeles);
            boolean siker = db.editFilm(modositando);
            if(siker){
                smallAlert("Sikeres módosítás!");
                this.stage.close();
                return;
            }
            smallAlert("Sikertelen módosítás!");
        }
        catch (NullPointerException e){
            smallError("A hossz megadása kötelező!");
        }
        catch (SQLException e){
            smallError("SQL Hiba: "+e.getMessage());
            e.printStackTrace();
        }
        catch (Exception e){
            smallError("A hossznak egy 1 és 999 közötti egész számnak kell lennie!");
        }
    }

    public void setModositando(Film modositando) {
        this.modositando = modositando;
        cimTextField.setText(modositando.getCim());
        kategoriaTextField.setText(modositando.getKategoria());
        hosszSpinner.getEditor().setText(Integer.toString(modositando.getHossz()));
        ertekelesChoiceBox.setValue(modositando.getErtekeles());
    }
    public Film getModositando() {
        return this.modositando;
    }
}
