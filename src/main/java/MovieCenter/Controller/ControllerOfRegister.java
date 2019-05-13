package MovieCenter.Controller;

import MovieCenter.model.UserController;

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

public class ControllerOfRegister {

    private double xOffset = 0;
    private double yOffset = 0;

    @FXML private TextField usernameregister;
    @FXML private PasswordField passwordregister;
    @FXML private TextField emailregister;
    @FXML private Label labelregister;

    //Átvált a bejelentkezés scene-re (signin.fxml)
    @FXML
    private void register_LogIn() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
        Parent root = loader.load();
        ControllerOfLogin.guiStage.setScene(new Scene(root));

        //mozgathatóvá teszi az ablakot

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
            UserController.createUser(usernameregister.getText(), passwordregister.getText(), emailregister.getText());
            labelregister.setTextFill(Color.LIMEGREEN);
            labelregister.setStyle("-fx-font-weight: bold; -fx-font-size: 24;");
            labelregister.setText("Sikeres regisztráció!");
        }
    }

    @FXML
    private void register_bezaras(ActionEvent event){
        System.exit(0);
    }
}
