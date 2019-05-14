package MovieCenter.Controller;

import MovieCenter.model.dbControl;
import MovieCenter.model.tables.movies;
import MovieCenter.model.tables.moviesErtekeles;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/**
 * {@code Controller} of the {@code ListMovies} view.
 */
public class ControllerOfListMovies implements Initializable {

    /**
     * These 2 variables are for the window to be able to dragged, as it is an undecorated window.
     */
    private double xOffset = 0;
    private double yOffset = 0;

    /**The number of movies in the database.*/
    private int numberofmovies = 20;

    /**Hold all the images of the movies.*/
    private List<Image> deck = new ArrayList<>();

    /**The list of the films, directly from the database.*/
    private List<movies> moviesList;

    /**Contains the average rates on the films.*/
    private List<Label> labelVoteAvg;

    /**Loads all the pictures. These would be the movies' covers.
     * Also it initializes the buttons, and the whole grid, where all
     * the information is shown about the films.*/
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = numberofmovies -1; i >= 0; i--) {
            deck.add(new Image(getClass().getResource("/kepek/"+i+".jpg").toExternalForm()));
        }
        gombok();
        moviesList = dbControl.getmovies();
        gridInit();
    }

    /**Little red X in the top right corner to close the application.*/
    @FXML private void final_bezaras(ActionEvent event){
        ControllerOfLogin.guiStage.close();
    }

    /**{@link GridPane} contains all the information of the movies.*/
    @FXML GridPane grid;
    @FXML private MenuButton menubutton1;
    @FXML private MenuButton menubutton2;

    /**Writes the logged in username on the button in the top right corner.
     * @param message contains the username in string type */
    public void transferMessage(String message) {
        menubutton2.setText(message);
    }

    /**Initializes the main buttons, such as the logout button, the random movie button.*/
    private void gombok(){
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

        MenuItem m1 = new MenuItem("Filmek");
        MenuItem m2 = new MenuItem("Random film");
        m2.setOnAction(event -> {
            Random rand = new Random();
            ControllerOfFilm.movieID = rand.nextInt(numberofmovies);
            ControllerOfFilm.ertekeles = labelVoteAvg.get(ControllerOfFilm.movieID).getText();

            FXMLLoader belep = new FXMLLoader(getClass().getClassLoader().getResource("view/Film.fxml"));
            Parent root = null;
            try {
                root = belep.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ControllerOfFilm transfer3 = belep.getController();
            transfer3.transferMessage(menubutton2.getText());

            ControllerOfLogin.guiStage.setScene(new Scene(root));
        });
        menubutton1.setText("Movies");
        menubutton1.getItems().setAll(m1, m2);
    }

    /**The main part of this scene that consists of a lot of information.
     * It is a {@link GridPane} that holds all the information about the movies.
     * The {@code Title} is a List of buttons (it is converted at the end)
     * so if you click on a name of any films, it loads the information about it.
     */
    private void gridInit() {
        List<String> Title = new ArrayList<>();
        List<String> Rendezo = new ArrayList<>();
        List<String> Date = new ArrayList<>();
        List<String> Category = new ArrayList<>();
        List<String> Votes = new ArrayList<>();

        List<moviesErtekeles> ertekeles;
        ertekeles = dbControl.getallertekeles();

        Double[] ert = new Double[numberofmovies];
        Double[] count = new Double[numberofmovies];
        Double[] atlag = new Double[numberofmovies];

        for (int i = 0; i <= numberofmovies-1; i++) {
            ert[i] = 0.0;
            count[i] = 0.0;
            atlag[i] = 0.0;
        }

        for (int i = 0; i < ertekeles.size(); i++) {
            ert[ertekeles.get(i).getMovieID()] = ertekeles.get(i).getErtekeles() + ert[ertekeles.get(i).getMovieID()];
            count[ertekeles.get(i).getMovieID()]++;
        }

        for (int i = 0; i <= numberofmovies-1; i++) {
            if (ert[i] == 0.0) {
                atlag[i] = 0.0;
            } else {
                atlag[i] = ert[i] / count[i];
            }
        }

        for (int i = 0; i <= numberofmovies-1; i++) {
            Title.add(moviesList.get(i).getTitle());
            Rendezo.add(moviesList.get(i).getRendezo());
            Date.add(moviesList.get(i).getDate().toString());
            Category.add(moviesList.get(i).getCategory());
            Votes.add(atlag[i].toString());
        }

        List<Button> labelTitle = Title
                .stream()
                .map(Button::new)
                .collect(Collectors.toList());

        List<Label> labelRendezo = Rendezo
                .stream()
                .map(Label::new)
                .collect(Collectors.toList());

        List<Label> labelDate = Date
                .stream()
                .map(Label::new)
                .collect(Collectors.toList());

        List<Label> labelCategory = Category
                .stream()
                .map(Label::new)
                .collect(Collectors.toList());

        labelVoteAvg = Votes
                .stream()
                .map(Label::new)
                .collect(Collectors.toList());

        grid.setVgap(5);
        for (int i = 0; i <= numberofmovies-1; i++) {
            grid.add(new ImageView(deck.get(numberofmovies-1 - i)), 0, i);
            labelTitle.get(i).setStyle("-fx-background-color: transparent;-fx-text-fill: white;-fx-alignment: center;-fx-font-weight: bold;-fx-border-width: 50px;-fx-min-height: 175px");
            grid.add(labelTitle.get(i), 1, i);
            labelRendezo.get(i).setStyle("-fx-text-fill: white;-fx-alignment: center;-fx-font-weight: bold;-fx-border-width: 50px;-fx-min-height: 175px");
            grid.add(labelRendezo.get(i), 2, i);
            labelDate.get(i).setStyle("-fx-text-fill: white;-fx-alignment: center;-fx-font-weight: bold;-fx-border-width: 50px;-fx-min-height: 175px");
            grid.add(labelDate.get(i), 3, i);
            labelCategory.get(i).setStyle("-fx-text-fill: white;-fx-alignment: center;-fx-font-weight: bold;-fx-border-width: 50px;-fx-min-height: 175px");
            grid.add(labelCategory.get(i), 4, i);
            labelVoteAvg.get(i).setStyle("-fx-text-fill: white;-fx-alignment: center;-fx-font-weight: bold;-fx-border-width: 50px;-fx-min-height: 175px");
            grid.add(labelVoteAvg.get(i), 5, i);
        }



        Button[] buttons = new Button[numberofmovies];
        for(int i = 0; i <= numberofmovies-1; i++){
            final int buttonInd = i;
            buttons[i] = labelTitle.get(i);
            buttons[i].setOnAction(event -> {
                ControllerOfFilm.movieID = buttonInd;
                ControllerOfFilm.ertekeles = labelVoteAvg.get(buttonInd).getText();

                FXMLLoader belep = new FXMLLoader(getClass().getClassLoader().getResource("view/Film.fxml"));
                Parent root = null;
                try {
                    root = belep.load();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ControllerOfFilm transfer3 = belep.getController();
                transfer3.transferMessage(menubutton2.getText());
                ControllerOfLogin.guiStage.setScene(new Scene(root));

                ControllerOfLogin.guiStage.getScene().setOnMousePressed(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        xOffset = event.getSceneX();
                        yOffset = event.getSceneY();
                    }
                });
                ControllerOfLogin.guiStage.getScene().setOnMouseDragged(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        ControllerOfLogin.guiStage.setX(event.getScreenX() - xOffset);
                        ControllerOfLogin.guiStage.setY(event.getScreenY() - yOffset);
                    }
                });
            });
        }
    }
}
