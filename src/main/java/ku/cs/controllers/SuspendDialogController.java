package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ku.cs.models.SuspendUser;
import ku.cs.models.SuspendUserList;
import ku.cs.models.User;
import ku.cs.services.DataSource;
import ku.cs.services.SuspendUserListDataSource;

import java.io.IOException;

public class SuspendDialogController {

    private User user;

    @FXML private VBox textContainer;
    @FXML private HBox buttonContainer;
    private Button closeButton = new Button("OK");
    private Button requestButton = new Button("ขอคืนสิทธิ์การใช้งาน");

    private SuspendUserList suspendUserList;
    private DataSource<SuspendUserList> suspendUserData;

    public void initData(User user) {
        this.user = user;
        suspendUserData = new SuspendUserListDataSource("data", "suspend_user.csv");
        suspendUserList = suspendUserData.readData();

        SuspendUser suspendUser = suspendUserList.findSuspendUser(user);

        closeButton.getStyleClass().add("login-btn");
        requestButton.getStyleClass().add("login-btn");

        closeButton.setOnAction(e -> handleCloseButton(e));
        requestButton.setOnAction(e -> handleRequestButton(e));

        if (suspendUser.isRequest()) {
            textContainer.getChildren().add(new Label("คุณได้ขอคืนสิทธิ์ไปแล้ว กรุณารอผู้ดูแลระบบคืนสิทธิ์เข้าใช้งาน"));
            buttonContainer.getChildren().add(closeButton);
            return;
        }

        buttonContainer.getChildren().add(requestButton);
        buttonContainer.getChildren().add(closeButton);


    }

    private void handleCloseButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    private void handleRequestButton(ActionEvent actionEvent) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/requestUnsuspendDialog.fxml"));
        Parent root = null;
        try {
            root = loader.load();
            RequestUnsuspendDialogController controller = loader.getController();
            controller.initData(user);

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

}
