package MovieCenter.Controller;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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

public class ControllerOfInside implements Initializable {
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        kepekbetoltese();
        slideshow();
        gombok();
    }

    private double xOffset = 0;
    private double yOffset = 0;

    private List<Image> deck = new ArrayList<>();

    @FXML private MenuButton menubutton1;
    @FXML private MenuButton menubutton2;
    @FXML private ImageView img1;
    @FXML private ImageView img2;
    @FXML private ImageView img3;
    @FXML private ImageView img4;

    private int numberofpictures = 20;

    @FXML
    private void belepve_bezaras(ActionEvent event){
        System.exit(0);
    }

    private void kepekbetoltese(){
        for (int i = numberofpictures-1; i >= 0; i--) {
            deck.add(new Image("/kepek/"+ i + ".jpg"));
        }
    }
    int j = 0;
    private void slideshow(){
        img1.setImage(deck.get(numberofpictures-3));
        img2.setImage(deck.get(numberofpictures-2));
        img3.setImage(deck.get(numberofpictures-1));
        img4.setImage(deck.get(0));

        Timeline timeline = new Timeline();
                timeline.getKeyFrames().add( new KeyFrame(Duration.seconds(2), e -> {
                    if(j >= 0 && j <= numberofpictures-4) {
                        img1.setImage(deck.get(numberofpictures-4-j));
                        img2.setImage(deck.get(numberofpictures-3-j));
                        img3.setImage(deck.get(numberofpictures-2-j));
                        img4.setImage(deck.get(numberofpictures-1-j));
                        j++;
                    }else
                    if(j == numberofpictures-3) {
                        img1.setImage(deck.get(numberofpictures-1));
                        img2.setImage(deck.get(0));
                        img3.setImage(deck.get(1));
                        img4.setImage(deck.get(2));
                        j++;
                    }else
                    if(j == numberofpictures-2) {
                        img1.setImage(deck.get(numberofpictures-2));
                        img2.setImage(deck.get(numberofpictures-1));
                        img3.setImage(deck.get(0));
                        img4.setImage(deck.get(1));
                        j++;
                    }else
                    if(j == numberofpictures-1) {
                        img1.setImage(deck.get(numberofpictures-3));
                        img2.setImage(deck.get(numberofpictures-2));
                        img3.setImage(deck.get(numberofpictures-1));
                        img4.setImage(deck.get(0));
                        j=0;
                    }
                }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    //Belépett felhasználó nevének kiiírása
    public void transferMessage(String message) {
        menubutton2.setText(message);
    }
    private void gombok(){
        //Movies gomb
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

        //Felhasználónév
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
