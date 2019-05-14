package MovieCenter.Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

/**
 * {@code Controller} of the {@code Belepve} view.
 */
public class ControllerOfInside implements Initializable {
    /**
     * It loads with the new fxml.
     * The {@code kepekbetoltese} loads the pictures for the image slider.
     * The {@code slideshow} initializes the slider.
     * The {@code gombok} sets the functions of the buttons.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kepekbetoltese();
        slideshow();
        gombok();
    }
    /**
     * These 2 variables are for the window to be able to dragged, as it is an undecorated window.
     */
    private double xOffset = 0;
    private double yOffset = 0;

    /**This {@link List} contains the images for the slideshow.*/
    private List<Image> deck = new ArrayList<>();

    /**Holds the dropdown menu for the Movies.*/
    @FXML private MenuButton menubutton1;

    /**Contains the {@code username} and as a submenu, you can log out of the application.*/
    @FXML private MenuButton menubutton2;

    /**There are 4 {@link ImageView} for the slider, it shows the images
     * like they are moving from left to right.
     */
    @FXML private ImageView img1;
    @FXML private ImageView img2;
    @FXML private ImageView img3;
    @FXML private ImageView img4;

    /**The number of movies in the database, also the number of pictures.*/
    private int numberofmovies = 20;

    /**With the help of this button you can exit the application.
     * It can be found in the top right corner.
     */
    @FXML
    private void belepve_bezaras(ActionEvent event){
        ControllerOfLogin.guiStage.close();
    }

    /**
     * Loads the pictures from the {@code kepek} folder.
     */
    private void kepekbetoltese(){
        for (int i = numberofmovies -1; i >= 0; i--) {
            deck.add(new Image(getClass().getResource("/kepek/"+i+".jpg").toExternalForm()));
        }
    }

    /**Variable defined for the for cycle,
     * as you should use final variable in a lambda expression.
     */
    int j = 0;

    /**
     * Handles the whole image slider. Every 2 seconds it moves all 4 pictures to the right.
     * It is set to {@code INDEFINITE} so it will run until the program is closed.
     */
    private void slideshow(){
        img1.setImage(deck.get(numberofmovies -3));
        img2.setImage(deck.get(numberofmovies -2));
        img3.setImage(deck.get(numberofmovies -1));
        img4.setImage(deck.get(0));

        Timeline timeline = new Timeline();
                timeline.getKeyFrames().add( new KeyFrame(Duration.seconds(2), e -> {
                    if(j >= 0 && j <= numberofmovies -4) {
                        img1.setImage(deck.get(numberofmovies -4-j));
                        img2.setImage(deck.get(numberofmovies -3-j));
                        img3.setImage(deck.get(numberofmovies -2-j));
                        img4.setImage(deck.get(numberofmovies -1-j));
                        j++;
                    }else
                    if(j == numberofmovies -3) {
                        img1.setImage(deck.get(numberofmovies -1));
                        img2.setImage(deck.get(0));
                        img3.setImage(deck.get(1));
                        img4.setImage(deck.get(2));
                        j++;
                    }else
                    if(j == numberofmovies -2) {
                        img1.setImage(deck.get(numberofmovies -2));
                        img2.setImage(deck.get(numberofmovies -1));
                        img3.setImage(deck.get(0));
                        img4.setImage(deck.get(1));
                        j++;
                    }else
                    if(j == numberofmovies -1) {
                        img1.setImage(deck.get(numberofmovies -3));
                        img2.setImage(deck.get(numberofmovies -2));
                        img3.setImage(deck.get(numberofmovies -1));
                        img4.setImage(deck.get(0));
                        j=0;
                    }
                }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    /**Write the username on the button in the top right corner.
     * This method is called from the Login view.
     * @param message contains the username in string type */
    public void transferMessage(String message) {
        menubutton2.setText(message);
    }

    /**Initializes the buttons of this scene.
     * The first {@link MenuButton} is responsible to show the dropdown menu
     * with the only element "Filmek". When you click on this submenu, it will
     * load the new scene with the {@code ListMovies} view and makes it draggable.
     * The second {@link MenuButton} is for the username, and as a submenu
     * you can logout from the application.
     */
    private void gombok(){
        MenuItem m1 = new MenuItem("Filmek");
        m1.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ListMovies.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ControllerOfListMovies transfer2 = loader.getController();
            transfer2.transferMessage(menubutton2.getText());

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

        menubutton1.setText("Movies");
        menubutton1.getItems().setAll(m1);

        MenuItem f1 = new MenuItem("Kilépés");
        f1.setOnAction(event -> {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/Login.fxml"));
            Parent root = null;
            try {
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            ControllerOfLogin.guiStage.setScene(new Scene(root));
        });
        menubutton2.getItems().setAll(f1);
    }
}
