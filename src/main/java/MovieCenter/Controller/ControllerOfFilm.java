package MovieCenter.Controller;

import MovieCenter.model.dbControl;
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

import static MovieCenter.model.dbControl.*;

/**
 * {@code Controller} of the {@code Film} view.
 */
public class ControllerOfFilm implements Initializable {

    /**
     * The {@code movieID} is the key to select the chosen film's data.
     * It is set by other controllers mainly.
     */
    public static int movieID;

    /** This variable is also set from other controllers and is holding the rate of the chosen film. */
    public static String ertekeles;

    /**Place where the image of the movie will be shown.*/
    @FXML ImageView imageFilm;

    /**Labels are containing the attributes of the movies. Like it's title, director, etc..*/
    @FXML Label labelCim;
    @FXML Label labelRendezo;
    @FXML Label labelMegjelenes;
    @FXML Label labelKategoria;
    @FXML Label labelErtekeles;
    @FXML Label labelLeiras;

    /**With the help of this slider, the signed in user can rate the films.*/
    @FXML Slider slider;

    /**Sends the rate, and save it in the database.*/
    @FXML MenuButton menubutton2;

    /**Lists all the users who rated the film, and also the rate itself.*/
    @FXML ListView list, list2;

    /**If any errors occurred during the rate, this label will show the massage of the error.*/
    @FXML Label ertesites;

    /**Shows the user who is logged in the application.
     * @param message contains the username in string type*/
    public void transferMessage(String message) {
        menubutton2.setText(message);
    }

    /**The initialization of the whole scene.
     * Sets all the texts for the labels, initializes the users button
     * where the username is shown.
     * The {@code getExactFilm} gets the movie's attributes from the database
     * when you provide an existing ID to it.
     */
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

        movies movie = dbControl.getExactFilm(movieID);
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

    /**Gets all the rates from the database for the chosen film.
     * If there is none, it will tell you that there is no rates
     * on that film yet. If there is any, it list all of them.
     */
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

    /**Closes the program with the little red X on the top right as on any other scenes.*/
    @FXML private void final_bezaras(ActionEvent event){
        System.exit(0);
    }

    /**Switches back to the previous {@code ListMovies} view and lists all the movies.*/
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

    /**If the logged in user tries to rate the film, it sends the rate to the database
     * in case he/she hasn't rated the film already. If there is a rate on that name already,
     * the {@code ertesites} will tell it.
     */
    @FXML private void ertekeles(ActionEvent event){
        if(ertekelesExists(getUserIDfromName(menubutton2.getText()),movieID) == 0)
            dbControl.newRate(getUserIDfromName(menubutton2.getText()),movieID, (int)slider.getValue());
        else{
            ertesites.setStyle("-fx-text-fill: red");
            ertesites.setText("Már értékelted a filmet!");
        }
    }
}
