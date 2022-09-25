package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import ku.cs.controllers.agency.AgencyController;
import ku.cs.controllers.complaintcategory.ComplaintCategoryDetailController;
import ku.cs.models.Role;
import ku.cs.models.User;

import java.io.IOException;

public class DashboardDetailController {
    private User user;

    @FXML private Button complaintListButton;
    @FXML private Button myComplaintListButton;
    @FXML private Button userListButton;
    @FXML private Button manageCategoryButton;
    @FXML private Button manageComplaintButton;
    @FXML private Button registerTeacherButton;
    @FXML private Button addAgencyButton;
    @FXML private Button reportButton;


    public void initData(User user) {
        this.user = user;

        // display the content corresponding to user's role
        if (user.getRole() == Role.STUDENT || user.getRole() == Role.TEACHER) {
            userListButton.setVisible(false);
            manageCategoryButton.setVisible(false);
            registerTeacherButton.setVisible(false);
            addAgencyButton.setVisible(false);
            reportButton.setVisible(false);
        }
        if (user.getRole() == Role.STUDENT) {
            manageComplaintButton.setVisible(false);
        }
    }

    @FXML
    private void handleComplaint(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/complaint.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        Parent pane = loader.load();
        ComplaintDetailController controller = loader.getController();
        controller.initData(user);
        borderPane.setCenter(pane);
    }

    @FXML
    private void handleMyComplaint(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/myComplaint.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        Parent pane = loader.load();
        ComplaintDetailController controller = loader.getController();
        controller.initData(user);
        borderPane.setCenter(pane);
    }

    @FXML
    private void handleUserList(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/showUser.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        Parent pane = loader.load();
        ShowUserDetailController controller = loader.getController();
        controller.initData(user);
        borderPane.setCenter(pane);
    }

    @FXML
    private void handleManageSubjectCategories(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/complaintCategory/complaintCategory.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        Parent root = loader.load();
        ComplaintCategoryDetailController controller = loader.getController();
        controller.initData(user);
        borderPane.setCenter(root);
    }

    @FXML
    private void handleManageComplaint(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        borderPane.setCenter(loader.load());
    }

    @FXML
    private void handleApplyForStaff(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/teacher-register.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);

        Parent pane = loader.load();
        TeacherRegisterController teacherRegisterController = loader.getController();
        teacherRegisterController.initData(user);
        borderPane.setCenter(pane);
    }

    @FXML
    private void handleReport() {

    }

    @FXML
    private void handleAddAgency(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/agency/agency.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);

        Parent pane = loader.load();
        AgencyController controller = loader.getController();
        controller.initData(user);
        borderPane.setCenter(pane);
    }
}