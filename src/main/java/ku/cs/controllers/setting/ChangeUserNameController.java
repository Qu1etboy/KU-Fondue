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
import ku.cs.animatefx.animation.FadeOut;
import ku.cs.animatefx.animation.Shake;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserListDataSource;

import java.io.IOException;

public class ChangeUserNameController {
    private User user;
    private UserList userList;
    private DataSource<UserList> data;
    @FXML
    private Label usernameLabel;
    @FXML private TextField userNameTextField;
    @FXML private TextField passwordField;
    @FXML private TextField userNameNewTextField;
    @FXML protected Label errorMessage;
    public void initData(User user) {
        this.user = user;
        data = new UserListDataSource("data", "user.csv");
        userList = data.readData();
    }

    @FXML private void handleOKButton(ActionEvent actionEvent) throws IOException {
        String username = userNameTextField.getText();
        String password = passwordField.getText();
        String userNew = userNameNewTextField.getText();
        // User user = login.checkUsername(username);

        if (username.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("กรุณากรอกรายละเอียดให้ครบ");
            alert.show();
            return;
        }
        if (!user.getUsername().equals(username)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("ชื่อผู้ใช้ไม่ถูกต้อง");
            alert.show();
            return;
        }
        if (!user.getPassword().equals(password)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("รหัสผ่านไม่ถูกต้อง");
            alert.show();
            return;
        }
        if (!isValid()) {
            return;
        }

        user.setUsername(userNew);
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

    public boolean isValid() {
        String username = userNameTextField.getText();
        String password = passwordField.getText();
        String userNew = userNameNewTextField.getText();

        if (!userList.validUsername(userNew)) {
            userNameNewTextField.getStyleClass().add("error-field");
            new Shake(userNameNewTextField).play();
            errorMessage.setText("ชื่อผู้ใช้ไม่สามารถยาวได้เกิน 20 ตัวและมีได้เฉพาะตัวเลขกับตัวอักษร");
            return false;
        }
        if (userList.findUserByUsername(userNew) != null) {
            userNameNewTextField.getStyleClass().add("error-field");
            new Shake(userNameNewTextField).play();
            errorMessage.setText("Username already exist");
            return false;
        }
        return true;
    }
}
