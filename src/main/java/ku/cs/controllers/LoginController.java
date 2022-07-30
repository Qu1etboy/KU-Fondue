package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ku.cs.models.Router;

import java.io.IOException;

public class LoginController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField passwordTextField;

    @FXML
    public void initialize() {}

    @FXML
    public void handleRegisterButton(ActionEvent actionEvent) throws IOException {
        // go to register page
        Router.switchScene("register.fxml", actionEvent);
    }

    @FXML
    public void handleLoginButton(ActionEvent actionEvent) throws IOException{
        // go to main application if login successfully
        Router.switchScene("main-application.fxml", actionEvent);
    }
}
