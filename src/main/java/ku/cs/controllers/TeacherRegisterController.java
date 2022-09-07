package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import ku.cs.models.Agency;
import ku.cs.models.AgencyList;
import ku.cs.models.Register;
import ku.cs.models.User;
import ku.cs.services.AgencyListDataSource;
import ku.cs.services.DataSource;
import ku.cs.services.UserListDataSource;

import java.io.IOException;

public class TeacherRegisterController extends RegisterController {
    @FXML private User user;
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
        register = new Register(userList);
    }

    public void handleSelectAgency() {
        agency = agencySelector.getSelectionModel().getSelectedItem();
    }

    @Override
    @FXML
    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        // go back to login page
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/adminDashboard.fxml"));
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

        // create new user and add it to user list
        userList.addUser(new User(username, name, password, "teacher", agency));
        data.writeData(userList);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Register successfully");
        alert.show();

        clearTextField();

        System.out.println("register Teacher successfully");
    }

    public void clearTextField() {
        usernameTextField.clear();
        nameTextField.clear();
        passwordTextField.clear();
        confirmPasswordTextField.clear();
    }
}
