package MovieCenter.Controller;

import MovieCenter.model.UserController;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class ControllerOfLogin extends Application {

    @FXML private Label labelsignin;
    @FXML private TextField usernamesignin;
    @FXML private PasswordField passwordsignin;

    private double xOffset = 0;
    private double yOffset = 0;

    public static Stage guiStage;

    @FXML
    private void signin_SignUp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/Register.fxml"));
        Parent root = loader.load();
        guiStage.setScene(new Scene(root));

        //Az új ablakot (register.fxml) mozgathatóvá teszi

        guiStage.getScene().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        guiStage.getScene().setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                guiStage.setX(event.getScreenX() - xOffset);
                guiStage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    //Jobb felső X
    @FXML
    private void signin_bezaras(ActionEvent event){
        System.exit(0);
    }

    @FXML
    private void signin_SendSignIn() {

        if(usernamesignin.getLength() > 15){
            labelsignin.setTextFill(Color.DARKRED);
            labelsignin.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
            labelsignin.setText("A felhasználónév túl hosszú!");
        }
        if(passwordsignin.getLength() > 20){
            labelsignin.setTextFill(Color.DARKRED);
            labelsignin.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
            labelsignin.setText("A jelszó túl hosszú!");
        }

        if(passwordsignin.getText().trim().isEmpty()){
            labelsignin.setTextFill(Color.DARKRED);
            labelsignin.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
            labelsignin.setText("A jelszó nem maradhat üres!");
        }
        if(usernamesignin.getText().trim().isEmpty()){
            labelsignin.setTextFill(Color.DARKRED);
            labelsignin.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
            labelsignin.setText("Üres felhasználónév!");
        }
        if(!usernamesignin.getText().trim().isEmpty() && !passwordsignin.getText().trim().isEmpty() && usernamesignin.getLength() <= 15 && passwordsignin.getLength() <= 20){
            if(UserController.login(usernamesignin.getText(), passwordsignin.getText()) == -2){
                labelsignin.setTextFill(Color.DARKRED);
                labelsignin.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
                labelsignin.setText("Nincs ilyen felhasználó!");
            }
            if(UserController.login(usernamesignin.getText(), passwordsignin.getText()) == -1){
                labelsignin.setTextFill(Color.DARKRED);
                labelsignin.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
                labelsignin.setText("Hibás jelszó!");
            }
            if(UserController.login(usernamesignin.getText(), passwordsignin.getText()) == 1){
                labelsignin.setTextFill(Color.LIMEGREEN);
                labelsignin.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
                labelsignin.setText("Sikeres bejelentkezés!");

                //Időzítő 2.5 másodpercig hogy látható legyen a bejelentkezés sikeres felirat!

                Timeline timeline = new Timeline(new KeyFrame(
                        Duration.millis(2500),
                        ae -> {
                            FXMLLoader belep = new FXMLLoader(getClass().getClassLoader().getResource("view/Belepve.fxml"));
                            Parent root = null;
                            try {
                                root = belep.load();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                            //Belépett felhasználó továbbküldése ControllerOfInside-nek
                            ControllerOfInside transfer1 = belep.getController();
                            transfer1.transferMessage(usernamesignin.getText());

                            guiStage.close();
                            guiStage.setScene(new Scene(root, 800, 600));
                            guiStage.show();



                            guiStage.getScene().setOnMousePressed(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    xOffset = event.getSceneX();
                                    yOffset = event.getSceneY();
                                }
                            });
                            guiStage.getScene().setOnMouseDragged(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent event) {
                                    guiStage.setX(event.getScreenX() - xOffset);
                                    guiStage.setY(event.getScreenY() - yOffset);
                                }
                            });
                            // Main.getStage().setMaximized(true);
                        }));
                timeline.play();
            }
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        guiStage = stage;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        Parent root = loader.load();
        guiStage.setTitle("LogIn or Register");
        guiStage.initStyle(StageStyle.UNDECORATED);
        guiStage.setScene(new Scene(root, 800, 600));
        guiStage.show();

        guiStage.getScene().setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        guiStage.getScene().setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() - xOffset);
                stage.setY(event.getScreenY() - yOffset);
            }
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
