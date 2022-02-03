package hu.petrik.filmdb.controllers;

import hu.petrik.filmdb.data.FilmDb;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class AddViewController extends Controller {
    @FXML
    public Spinner<Integer> hosszSpinner;
    @FXML
    public ChoiceBox<Integer> ertekelesChoiceBox;
    @FXML
    public TextField cimTextField;
    @FXML
    public TextField kategoriaTextField;

    @FXML
    public void onHozzadasButtonClick(ActionEvent actionEvent) {
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
            boolean siker = db.addFilm(cim,kategoria,hossz, ertekeles);
            if(siker){
                smallAlert("Sikeres hozzáadás!");
                return;
            }
            smallAlert("Sikertelen hozzáadás!");
        }
        catch (NullPointerException e){
            smallError("A hossz megadása kötelező!");
        }
        catch (SQLException e){
            smallError("SQL Hibe: "+e.getMessage());
        }
        catch (Exception e){
            smallError("A hossznak egy 1 és 999 közötti egész számnak kell lennie!");
        }
    }

}
