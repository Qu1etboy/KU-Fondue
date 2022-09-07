package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import ku.cs.models.User;

import java.io.IOException;

public class DashboardDetailController {
    private User user;
    public void initData(User user) {
        this.user = user;
    }

    @FXML
    private void handleComplaint() {

    }

    @FXML
    private void handleMyComplaint() {

    }

    @FXML
    private void handleUserList(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/showUser.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        borderPane.setCenter(loader.load());
    }

    @FXML
    private void handleManageSubjectCategories(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/complaintCategory.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        Parent root = loader.load();
        ComplaintCategoryDetailController controller = loader.getController();
        controller.initData(user);
        borderPane.setCenter(root);
    }

    @FXML
    private void handleManageComplaint() {
        System.out.println(user.getAgency());
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
}
