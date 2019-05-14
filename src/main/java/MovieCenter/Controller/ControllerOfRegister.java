package MovieCenter.Controller;

import MovieCenter.model.dbControl;

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

import java.io.IOException;

/**
 * {@code Controller} of the {@code Register} view.
 */
public class ControllerOfRegister {

    /**
     * These 2 variables are for the window to be able to dragged, as it is an undecorated window.
     */
    private double xOffset = 0;
    private double yOffset = 0;

    /**
     * {@link TextField} where the {@code user} can write his/her username and email.
     * {@link PasswordField} where the {@code user} can write his/her password.
     * {@link Label} where the output of the register will be shown.
     */
    @FXML private TextField usernameregister;
    @FXML private PasswordField passwordregister;
    @FXML private TextField emailregister;
    @FXML private Label labelregister;

    /**
     * It handles the click on button LogIn and makes the new fxml to be able to dragged.
     * Loads the {@code Login.fxml} file, and set the new scene to it.
     */
    @FXML
    private void register_LogIn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        Parent root = loader.load();
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
    }

    /**
     * Handles the whole registration process,
     * checks if all the requirements are filled, and if so
     * than it creates a new user in the database.
     * The {@link Label}'s value is changing depending on success or not.
     */
    @FXML
    public void register_SendRegister () {
        if(!emailregister.getText().trim().contains("@") || !emailregister.getText().trim().contains(".")){
            labelregister.setTextFill(Color.DARKRED);
            labelregister.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
            labelregister.setText("Hibás email formátum!");
        }
        if(emailregister.getText().trim().isEmpty()){
            labelregister.setTextFill(Color.DARKRED);
            labelregister.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
            labelregister.setText("Üres email!");
        }
        if(passwordregister.getText().trim().isEmpty()){
            labelregister.setTextFill(Color.DARKRED);
            labelregister.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
            labelregister.setText("A jelszó nem maradhat üresen!");
        }
        if(usernameregister.getText().isEmpty()){
            labelregister.setTextFill(Color.DARKRED);
            labelregister.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
            labelregister.setText("Üres felhasználónév!");
        }
        if(!emailregister.getText().trim().isEmpty() && !usernameregister.getText().trim().isEmpty() && !passwordregister.getText().trim().isEmpty() && emailregister.getText().trim().contains("@") && emailregister.getText().trim().contains(".")){
            dbControl.createUser(usernameregister.getText(), passwordregister.getText(), emailregister.getText());
            labelregister.setTextFill(Color.LIMEGREEN);
            labelregister.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
            labelregister.setText("Sikeres regisztráció!");
        }
    }

    /**
     * Makes the little X button in the top right corner to exit the application.
     */
    @FXML
    private void register_bezaras(ActionEvent event){
        System.exit(0);
    }
}
