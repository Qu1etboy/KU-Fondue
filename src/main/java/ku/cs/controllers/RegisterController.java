package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.models.Register;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserListDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;

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
    protected Image image;

    public void initialize() {
        data = new UserListDataSource("data", "user.csv");
        userList = data.readData();
        register = new Register(userList);
    }

    @FXML
    public void handleUploadImage(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        image = new Image(file.toURI().toString());
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
        User user = new User(username, name, password);
//        System.out.println(image);
        File file = null;
        if (image != null) {
            file = new File(image.getUrl().substring(5));
        }
        if (file != null) {
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("images");
                if (!destDir.exists()) destDir.mkdirs();
                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");
                String filename = user.getId() + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath() + System.getProperty("file.separator") + filename
                );
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                // SET NEW FILE PATH TO IMAGE
                user.setProfileImage(new Image(target.toUri().toString()));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println(user.getProfileImage());
        userList.addUser(user);
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
