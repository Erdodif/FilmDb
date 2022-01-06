package hu.petrik.filmdb;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.paint.ImagePattern;
import javafx.stage.Stage;

import java.io.Console;
import java.io.IOException;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.regex.Pattern;

public class AddViewController {
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
        Integer ertekelesIndex = ertekelesChoiceBox.getSelectionModel().getSelectedIndex();
        if(cim.isEmpty()){
            smallAlert("A cím megadása kötelező!");
            return;
        }
        if(kategoria.isEmpty()){
            smallAlert("A kategória megadása kötelező!");
            return;
        }
        try {
            Integer hossz = hosszSpinner.getValue();
        }
        catch (NullPointerException e){
            smallAlert("A hossz megadása kötelező!");
            return;
        }
        catch (Exception e){
            smallAlert("A hossznak egy 1 és 999 közötti egész számnak kell lennie!");
            return;
        }
        if(ertekelesIndex == -1){
            smallAlert("Értékelés megadása kötelező!");
            return;
        }
        System.err.println("K Cs");
    }

    private void smallAlert(String s) {
        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setContentText(s);
        alert.getButtonTypes().add(ButtonType.OK);
        alert.show();
    }
}
