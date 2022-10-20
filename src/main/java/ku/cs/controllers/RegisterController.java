package ku.cs.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
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

import ku.cs.animatefx.animation.Shake;

public class RegisterController {
    @FXML protected TextField usernameTextField;
    @FXML protected TextField nameTextField;
    @FXML protected TextField passwordTextField;
    @FXML protected TextField confirmPasswordTextField;
    @FXML protected Label errorMessage;

    @FXML protected VBox fileContent;

    protected UserList userList;
    protected DataSource<UserList> userData;
    protected String username;
    protected String name;
    protected String password;
    protected String confirmPassword;
    protected Image image;

    public void initialize() {
        userData = new UserListDataSource("data", "user.csv");
        userList = userData.readData();
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

        String[] fileSplit = file.toURI().toString().split("/");

        fileContent.getChildren().clear();

        HBox box = new HBox();
        Label fileNameLabel = new Label(fileSplit[fileSplit.length - 1]);
        fileNameLabel.setMaxWidth(100);

        box.getChildren().add(new FontAwesomeIconView(FontAwesomeIcon.FILE_IMAGE_ALT));
        box.getChildren().add(fileNameLabel);
        box.setPrefWidth(Region.USE_COMPUTED_SIZE);
        box.setPrefHeight(50);
        box.setMaxWidth(200);
        box.setPadding(new Insets(3, 10, 3, 10));
        box.getStyleClass().add("border-box");
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);

        Button removeImage = new Button("X");
        removeImage.setOnAction(e -> handleRemoveImage(box));
        removeImage.getStyleClass().add("transparent-danger-button");
        box.getChildren().add(removeImage);

        fileContent.getChildren().add(box);
    }

    private void handleRemoveImage(HBox box) {
        image = null;
        fileContent.getChildren().remove(box);
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        // go back to login page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/login.fxml"));
        Scene scene = ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(loader.load());

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
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("หาไฟล์รูปภาพไม่เจอ (ชื่อไฟล์ห้ามมีช่องว่าง)");
                alert.show();
                return;
                // e.printStackTrace();
            }
        }

        userList.addUser(user);
        userData.writeData(userList);

        // go back to login page if register successfully
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/login.fxml"));
        Scene scene = ((Node) actionEvent.getSource()).getScene();
        scene.setRoot(loader.load());


        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Register successfully");
        alert.show();

//        System.out.println("register successfully");
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
        usernameTextField.getStyleClass().remove("error-field");
        nameTextField.getStyleClass().remove("error-field");
        passwordTextField.getStyleClass().remove("error-field");
        confirmPasswordTextField.getStyleClass().remove("error-field");

        if (username.isEmpty() || name.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            if (username.isEmpty()) {
                usernameTextField.getStyleClass().add("error-field");
                new Shake(usernameTextField).play();
            }
            if (name.isEmpty()) {
                nameTextField.getStyleClass().add("error-field");
                new Shake(nameTextField).play();
            }
            if (password.isEmpty()) {
                passwordTextField.getStyleClass().add("error-field");
                new Shake(passwordTextField).play();
            }
            if (confirmPassword.isEmpty()) {
                confirmPasswordTextField.getStyleClass().add("error-field");
                new Shake(confirmPasswordTextField).play();
            }
            errorMessage.setText("กรุณากรอกข้อมูลให้ครบ");
            return false;
        }
        if (!userList.validUsername(username)) {
            usernameTextField.getStyleClass().add("error-field");
            new Shake(usernameTextField).play();
            errorMessage.setText("ชื่อผู้ใช้ไม่สามารถยาวได้เกิน 20 ตัวและมีได้เฉพาะตัวเลขกับตัวอักษร");
            return false;
        }
        if (userList.findUserByUsername(username) != null) {
            usernameTextField.getStyleClass().add("error-field");
            new Shake(usernameTextField).play();
            errorMessage.setText("Username already exist");
            return false;
        }
        if (!password.equals(confirmPassword)) {
            passwordTextField.getStyleClass().add("error-field");
            confirmPasswordTextField.getStyleClass().add("error-field");
            new Shake(passwordTextField).play();
            new Shake(confirmPasswordTextField).play();
            errorMessage.setText("Password and Confirm password don't match");
            return false;
        }
        return true;
    }
}
