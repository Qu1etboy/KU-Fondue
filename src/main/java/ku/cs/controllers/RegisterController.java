package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import ku.cs.models.Router;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField usernameTextField;
    @FXML
    private TextField nameTextField;
    @FXML
    private TextField passwordTextField;
    @FXML
    private TextField confirmPasswordTextField;

    @FXML
    public void handleUploadImage() {

    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        // go back to login page
        Router.switchScene("login.fxml", actionEvent);
    }

    @FXML
    public void handleRegisterButton() {
        /*
         *  1. check if username is correct or not for example no more than 20 character
         *  2. check if username is already exist in database or not
         *  3. check if password and confirm password is equal or not
         *  4. if everything pass then add user to the user's database
         *  5. otherwise alert to the user that something is wrong
         * */
    }
}
