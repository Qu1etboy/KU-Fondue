package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.models.Login;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserListDataSource;

import java.io.IOException;

public class LoginController {
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    private Login login;

    @FXML
    public void initialize() {
        DataSource<UserList> data = new UserListDataSource("data", "user.csv");
        UserList userList = data.readData();
        login = new Login(userList);
    }

    @FXML
    public void handleRegisterButton(ActionEvent actionEvent) throws IOException {
        // go to register page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/register.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load(), 800, 600);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handleLoginButton(ActionEvent actionEvent) throws IOException {
        String username = usernameTextField.getText();
        String password = passwordTextField.getText();

        User user = login.checkUsername(username);

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in all the field");
            alert.show();
            return;
        }
        if (user == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User doesn't exist");
            alert.show();
            return;
        }
        if (!login.checkPassword(user.getPassword(), password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Password is incorrect");
            alert.show();
            return;
        }
        if (user.isSuspend()) {
            // a dialog that tell the user that they got suspend and make a button to request unsuspend
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/suspendDialog.fxml"));
            Scene scene = new Scene(loader.load());
            Stage parentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(parentStage);
            dialog.showAndWait();

            // TODO: increment number of tries user try to sign in when being suspended

            System.out.println("You have been suspended from the app");
            return;
        }

        // go to main application
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/main-application.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(loader.load(), 900, 600);

        MainApplicationController mainApplicationController = loader.getController();
        mainApplicationController.initData(user);

        stage.setScene(scene);
        stage.setMinWidth(900);
        stage.show();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Login successfully");
        alert.show();
    }
}
