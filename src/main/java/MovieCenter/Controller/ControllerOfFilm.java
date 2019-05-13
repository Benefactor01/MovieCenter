package MovieCenter.Controller;

import MovieCenter.model.UserController;
import MovieCenter.model.tables.movies;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static MovieCenter.model.UserController.*;

public class ControllerOfFilm implements Initializable {

    public static int movieID;
    public static String ertekeles;
    @FXML ImageView imageFilm;

    @FXML Label labelCim;
    @FXML Label labelRendezo;
    @FXML Label labelMegjelenes;
    @FXML Label labelKategoria;
    @FXML Label labelErtekeles;
    @FXML Label labelLeiras;
    @FXML Slider slider;
    @FXML MenuButton menubutton2;
    @FXML ListView list, list2;
    @FXML Label ertesites;

    //Belépett felhasználó nevének kiiírása
    public void transferMessage(String message) {
        menubutton2.setText(message);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        MenuItem f1 = new MenuItem("Kilépés");
        f1.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/Signin.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ControllerOfLogin.guiStage.setScene(new Scene(root));
        });
        menubutton2.getItems().setAll(f1);

        movies movie = UserController.getExactFilm(movieID);
        imageFilm.setImage(new Image("/kepek/"+ movieID + ".jpg"));
        labelCim.setText(movie.getTitle());
        labelRendezo.setText(movie.getRendezo());
        labelMegjelenes.setText(movie.getDate().toString());
        labelKategoria.setText(movie.getCategory());
        labelErtekeles.setText(ertekeles);
        labelLeiras.setText(movie.getLeiras());

        slider.setMin(1);
        slider.setMax(10);
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        slider.setSnapToTicks(true);
        listfeltoltes();
    }

    private void listfeltoltes(){
        List<Object[]> ertekelesek = getRateByUsername(movieID);
        if(!ertekelesek.isEmpty()){
            for (Object[] result : ertekelesek) {
                list.getItems().add(result[0]);
            }
            for (Object[] result : ertekelesek) {
                list2.getItems().add(result[1]);
            }
        }else{
            list.getItems().add("Még nincs értékelés!");
            list2.getItems().add("  ");
        }
    }

    @FXML private void final_bezaras(ActionEvent event){
        System.exit(0);
    }
    @FXML private void final_back(ActionEvent event){
        FXMLLoader belep = new FXMLLoader(getClass().getClassLoader().getResource("view/ListMovies.fxml"));
        Parent root = null;
        try {
            root = belep.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ControllerOfListMovies transfer2 = belep.getController();
        transfer2.transferMessage(menubutton2.getText());
        ControllerOfLogin.guiStage.setScene(new Scene(root));
    }
    @FXML private void ertekeles(ActionEvent event){
        if(ertekelesExists(getUserIDfromName(menubutton2.getText()),movieID) == 0)
            UserController.newRate(getUserIDfromName(menubutton2.getText()),movieID, (int)slider.getValue());
        else{
            ertesites.setStyle("-fx-text-fill: red");
            ertesites.setText("Már értékelted a filmet!");
        }
    }
}
