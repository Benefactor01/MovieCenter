package MovieCenter.Controller;

import MovieCenter.model.dbControl;

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

/**
 * {@code Controller} of the {@code Login} view.
 */
public class ControllerOfLogin extends Application {

    /**
     * {@link Label} where the output of the login will be shown.
     */
    @FXML private Label labelsignin;

    /**
     * {@link TextField} where the {@code user} can write his/her username.
     */
    @FXML private TextField usernamesignin;

    /**
     * {@link PasswordField} where the {@code user} can write his/her password.
     */
    @FXML private PasswordField passwordsignin;

    /**
     * These 2 variables are for the window to be able to dragged, as it is an undecorated window.
     */
    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * This is the name of the only stage in the application
     * and it is set on public, so it can be handled from other Controllers too.
     */
    public static Stage guiStage;

    /**
     * It handles the click on button SignUp and makes the new fxml to be able to dragged.
     * Loads the {@code Register.fxml} file, and set the new scene to it.
     */
    @FXML
    private void signin_SignUp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/Register.fxml"));
        Parent root = loader.load();
        guiStage.setScene(new Scene(root));

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

    /**
     * Handles the button in the top right corner,
     * so if you click on the red X the program will exit.
     */
    @FXML
    private void signin_bezaras(ActionEvent event){
        System.exit(0);
    }

    /**
     * Handles the whole login process.
     * Checks if all the requirements fill and if so, the application will let you login.
     * You have to give the right {@code user}'s name and password.
     * Also there is a 2,5 seconds delay when you successfully logged in
     * so you have enough time to read the text on the label that says "Successfully logged in!"
     *
     * After login, you'll be directed to the {@code Belepve.fxml}
     * and it is also set to be able to dragged.
     * The {@link TextField} will be sent to the new scene, as it contains the username
     * and that has to be shown on all of the scenes.
     */
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
            if(dbControl.login(usernamesignin.getText(), passwordsignin.getText()) == -2){
                labelsignin.setTextFill(Color.DARKRED);
                labelsignin.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
                labelsignin.setText("Nincs ilyen felhasználó!");
            }
            if(dbControl.login(usernamesignin.getText(), passwordsignin.getText()) == -1){
                labelsignin.setTextFill(Color.DARKRED);
                labelsignin.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
                labelsignin.setText("Hibás jelszó!");
            }
            if(dbControl.login(usernamesignin.getText(), passwordsignin.getText()) == 1){
                labelsignin.setTextFill(Color.LIMEGREEN);
                labelsignin.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
                labelsignin.setText("Sikeres bejelentkezés!");

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
                        }));
                timeline.play();
            }
        }
    }

    /**
     * The {@code start} method is the first method to be loaded when the application starts.
     * It initializes the first scene, makes the windows undecorated, set its paramteres
     * and makes it draggable.
     */
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
