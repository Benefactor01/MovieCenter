package MovieCenter.Controller;

import MovieCenter.model.UserController;
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

public class ControllerOfListMovies implements Initializable {
    private double xOffset = 0;
    private double yOffset = 0;
    private int numberofmovies = 20; //A 20 a képek száma
    private List<Image> deck = new ArrayList<>();
    List<movies> moviesList;
    private List<Label> labelVoteAvg;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = numberofmovies -1; i >= 0; i--) {
            deck.add(new Image("/kepek/"+ i + ".jpg"));
        }
        gombok();
        moviesList = UserController.getmovies();
        gridInit();
    }

    @FXML private void final_bezaras(ActionEvent event){
        System.exit(0);
    }
    @FXML GridPane grid;
    @FXML private MenuButton menubutton1;
    @FXML private MenuButton menubutton2;

    //Belépett felhasználó nevének kiiírása
    public void transferMessage(String message) {
        menubutton2.setText(message);
    }

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

    private void gridInit() {
        List<String> Title = new ArrayList<>();
        List<String> Rendezo = new ArrayList<>();
        List<String> Date = new ArrayList<>();
        List<String> Category = new ArrayList<>();
        List<String> Votes = new ArrayList<>();

        List<moviesErtekeles> ertekeles;
        ertekeles = UserController.getallertekeles();

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
