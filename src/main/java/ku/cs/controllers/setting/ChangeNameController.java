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
import ku.cs.animatefx.animation.Shake;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserListDataSource;

import java.io.IOException;

public class ChangeNameController {
    private User user;
    private UserList userList;
    private DataSource<UserList> data;
    @FXML
    private Label usernameLabel;
    @FXML private TextField userNameTextField;
    @FXML private TextField passwordPasswordField;
    @FXML private TextField userNameNewTextField;
    @FXML private Label errorMessage;

    public void initData(User user) {
        this.user = user;
        data = new UserListDataSource("data", "user.csv");
        userList = data.readData();
    }

    @FXML private void handleOKButton(ActionEvent actionEvent) throws IOException {
        String username = userNameTextField.getText();
        String password = passwordPasswordField.getText();
        String usernameNew = userNameNewTextField.getText();
        // User user = login.checkUsername(username);

        userNameTextField.getStyleClass().remove("error-field");
        passwordPasswordField.getStyleClass().remove("error-field");
        userNameNewTextField.getStyleClass().remove("error-field");

        if (username.isEmpty() || password.isEmpty() || usernameNew.isEmpty()) {
            errorMessage.setText("กรุณากรอกรายละเอียดให้ครบ");
            if (username.isEmpty()) {
                userNameTextField.getStyleClass().add("error-field");
                new Shake(userNameTextField).play();
            }
            if (password.isEmpty()) {
                passwordPasswordField.getStyleClass().add("error-field");
                new Shake(passwordPasswordField).play();
            }
            if (usernameNew.isEmpty()) {
                userNameNewTextField.getStyleClass().add("error-field");
                new Shake(userNameNewTextField).play();
            }
            return;
        }
        if (!user.checkUsername(username)) {
            errorMessage.setText("ชื่อผู้ใช้ไม่ถูกต้อง");
            userNameTextField.getStyleClass().add("error-field");
            new Shake(userNameTextField).play();
            return;
        }
        if (!user.checkPassword(password)) {
            errorMessage.setText("รหัสผ่านไม่ถูกต้อง");
            passwordPasswordField.getStyleClass().add("error-field");
            new Shake(passwordPasswordField).play();
            return;
        }

        user.setName(usernameNew);
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
