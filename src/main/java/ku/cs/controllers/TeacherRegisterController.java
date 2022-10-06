package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import ku.cs.models.*;
import ku.cs.services.AgencyListDataSource;
import ku.cs.services.DataSource;
import ku.cs.services.UserListDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class TeacherRegisterController extends RegisterController {
    private User user;
    @FXML private ComboBox<Agency> agencySelector;
    @FXML private Agency agency;

    public void initData(User user) {
        this.user = user;
    }
    public void initialize() {
        DataSource<AgencyList> agencyData = new AgencyListDataSource("data", "agency.csv");
        AgencyList agencyList = agencyData.readData();
        agencySelector.getItems().addAll(agencyList.getAgencyList());
        agencySelector.setOnAction(e -> handleSelectAgency());

        data = new UserListDataSource("data", "user.csv");
        userList = data.readData();
    }

    private void handleSelectAgency() {
        agency = agencySelector.getSelectionModel().getSelectedItem();
    }

    @Override
    @FXML
    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        // go back to login page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/dashboard.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);

        Parent pane = loader.load();
        DashboardDetailController dashboardDetailController = loader.getController();
        dashboardDetailController.initData(user);
        borderPane.setCenter(pane);
    }

    @Override
    @FXML
    public void handleRegisterButton(ActionEvent actionEvent) {
        username = usernameTextField.getText();
        name = nameTextField.getText();
        password = passwordTextField.getText();
        confirmPassword = confirmPasswordTextField.getText();

        if (!isValid()) {
            return;
        }

        if (agencySelector.getValue() == null) {
            errorMessage.setText("กรุณาเลือกหน่วยงาน");
            return;
        }

        // create new user and add it to user list
        User user = new User(username, name, password, Role.TEACHER, agency);

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

        System.out.println(user.getProfileImage().getUrl());
        userList.addUser(user);
        data.writeData(userList);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Register successfully");
        alert.show();

        clearTextField();

    }

    public void clearTextField() {
        usernameTextField.clear();
        nameTextField.clear();
        passwordTextField.clear();
        confirmPasswordTextField.clear();
        fileContent.getChildren().clear();
        errorMessage.setText("");
    }
}
