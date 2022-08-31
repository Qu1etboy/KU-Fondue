package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ku.cs.models.Register;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserListDataSource;

import java.io.IOException;

public class RegisterController {
    @FXML protected TextField usernameTextField;
    @FXML protected TextField nameTextField;
    @FXML protected TextField passwordTextField;
    @FXML protected TextField confirmPasswordTextField;

    protected Register register;
    protected UserList userList;
    protected DataSource<UserList> data;
    protected String username;
    protected String name;
    protected String password;
    protected String confirmPassword;

    public void initialize() {
        data = new UserListDataSource("data", "user.csv");
        userList = data.readData();
        register = new Register(userList);
    }

    @FXML
    public void handleUploadImage() {

    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        // go back to login page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/login.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleRegisterButton(ActionEvent actionEvent) throws IOException {
        username = usernameTextField.getText();
        name = nameTextField.getText();
        password = passwordTextField.getText();
        confirmPassword = confirmPasswordTextField.getText();

        if (!isValid()) {
            return;
        }

        // create new user and add it to user list
        userList.addUser(new User(username, name, password));
        data.writeData(userList);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Register successfully");
        alert.show();

        // go back to login page if register successfully
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/login.fxml"));
        Stage stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load());
        stage.setScene(scene);
        stage.show();

        System.out.println("register successfully");
    }

    /**
     *
     *  1. check if username is already exist in database or not
     *  2. check if password and confirm password is equal or not
     *  3. if everything pass then add user to the user's database
     *  4. otherwise alert to the user that something is wrong
     *
     * @return true if valid; false otherwise;
     */
    public boolean isValid() {
        if (username.isEmpty() || name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in all the field");
            alert.show();
            return false;
        }
        if (!register.validUsername(username)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username can contain only letter and digit with no more than 20 character");
            alert.show();
            return false;
        }
        if (!register.checkUsername(username)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Username already exist");
            alert.show();
            return false;
        }
        if (!register.checkPassword(password, confirmPassword)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Password and Confirm password don't match");
            alert.show();
            return false;
        }
        return true;
    }
}
