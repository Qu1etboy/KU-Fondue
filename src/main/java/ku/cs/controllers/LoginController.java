package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ku.cs.models.SuspendUserList;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.SuspendUserListDataSource;
import ku.cs.services.UserListDataSource;

import java.io.IOException;

import ku.cs.animatefx.animation.Shake;

public class LoginController {
    @FXML private TextField usernameTextField;
    @FXML private TextField passwordTextField;
    @FXML private Label errorMessage;
    private UserList userList;
    private DataSource<UserList> data;
    private SuspendUserList suspendUserList;
    private DataSource<SuspendUserList> suspendUserData;
    @FXML
    public void initialize() {
        data = new UserListDataSource("data", "user.csv");
        userList = data.readData();
        suspendUserData = new SuspendUserListDataSource("data", "suspend_user.csv");
        suspendUserList = suspendUserData.readData();
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

        User user = userList.findUserByUsername(username);

        usernameTextField.getStyleClass().remove("error-field");
        passwordTextField.getStyleClass().remove("error-field");

        if (username.isEmpty() || password.isEmpty()) {
            if (username.isEmpty()) {
                usernameTextField.getStyleClass().add("error-field");
                new Shake(usernameTextField).play();
            }
            if (password.isEmpty()) {
                passwordTextField.getStyleClass().add("error-field");
                new Shake(passwordTextField).play();
            }
            errorMessage.setText("กรุณากรอกรายละเอียดให้ครบ");

            return;
        }
        if (user == null) {
            usernameTextField.getStyleClass().add("error-field");
            new Shake(usernameTextField).play();
            errorMessage.setText("ไม่พบผู้ใช้ในระบบ");
            return;
        }
        if (!user.getPassword().equals(password)) {
            passwordTextField.getStyleClass().add("error-field");
            new Shake(passwordTextField).play();
            errorMessage.setText("รหัสผ่านไม่ถูกต้อง");
            return;
        }
        if (user.isSuspend()) {
            usernameTextField.clear();
            passwordTextField.clear();

            // a dialog that tell the user that they got suspend and make a button to request unsuspend
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/suspendDialog.fxml"));
            Parent root = loader.load();

            SuspendDialogController suspendDialogController = loader.getController();
            suspendDialogController.initData(user);

            Scene scene = new Scene(root);
            Stage parentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Stage dialog = new Stage();
            dialog.setScene(scene);
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(parentStage);
            dialog.showAndWait();

            // update suspend user list from file
            suspendUserList = suspendUserData.readData();
            // increment number of tries user try to sign in when being suspended
            suspendUserList.incrementSuspendUserLoginCount(user);
            suspendUserData.writeData(suspendUserList);

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
