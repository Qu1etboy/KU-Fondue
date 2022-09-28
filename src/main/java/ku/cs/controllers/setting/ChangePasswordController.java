package ku.cs.controllers.setting;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserListDataSource;

import java.io.IOException;

public class ChangePasswordController {
    private User user;
    private UserList userList;
    private DataSource<UserList> data;
    @FXML
    private Label usernameLabel;
    @FXML private TextField userNameTextField;
    @FXML private TextField passwordPasswordField;
    @FXML private TextField passwordNewPasswordField;

    public void initData(User user) {
        this.user = user;
        data = new UserListDataSource("data", "user.csv");
        userList = data.readData();
    }

    @FXML private void handleOKButton(ActionEvent actionEvent) throws IOException {
        String username = userNameTextField.getText();
        String password = passwordPasswordField.getText();
        String passwordNew = passwordNewPasswordField.getText();
        // User user = login.checkUsername(username);

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please fill in all the field");
            alert.show();
            return;
        }
        if (user.getUsername().equals(username)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("User doesn't exist");
            alert.show();
            return;
        }
        if (user.getPassword().equals(password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Password is incorrect");
            alert.show();
            return;
        }

        user.setPassword(passwordNew);
        userList.updateUser(user);
        data.writeData(userList);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/setting.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane) ((Node) actionEvent.getSource()).getScene().getRoot()).getChildren().get(0);
        Parent pane = loader.load();

        // sent data to setting controller
        SettingDetailController settingDetailController = loader.getController();
        settingDetailController.initData(user);

        borderPane.setCenter(pane);
    }

    @FXML private void handleCancelButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/setting.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane) ((Node) actionEvent.getSource()).getScene().getRoot()).getChildren().get(0);
        Parent pane = loader.load();

        // sent data to setting controller
        SettingDetailController settingDetailController = loader.getController();
        settingDetailController.initData(user);

        borderPane.setCenter(pane);
    }
}