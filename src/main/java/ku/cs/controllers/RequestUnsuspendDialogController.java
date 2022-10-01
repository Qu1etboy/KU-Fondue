package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ku.cs.models.SuspendUser;
import ku.cs.models.SuspendUserList;
import ku.cs.models.User;
import ku.cs.services.DataSource;
import ku.cs.services.SuspendUserListDataSource;

import java.io.IOException;

public class RequestUnsuspendDialogController {
    @FXML private TextField usernameTextField;
    @FXML private TextArea reasonTextArea;

    private User user;
    private SuspendUserList suspendUserList;
    private DataSource<SuspendUserList> suspendUserData;

    public void initData(User user) {
        this.user = user;
        suspendUserData = new SuspendUserListDataSource("data", "suspend_user.csv");
        suspendUserList = suspendUserData.readData();
    }

    @FXML
    private void handleBackButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/suspendDialog.fxml"));
        Parent root = loader.load();

        SuspendDialogController controller = loader.getController();
        controller.initData(user);

        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void handleSubmitButton(ActionEvent actionEvent) {
        String username = usernameTextField.getText();
        String reason = reasonTextArea.getText();

        if (username.isEmpty() || reason.isEmpty()) {
            return;
        }
        if (!user.getUsername().equals(username)) {
            return;
        }

        SuspendUser suspendUser = suspendUserList.findSuspendUser(user);
        suspendUser.setReason(reason);
        suspendUser.setRequest(true);
        System.out.println(suspendUser);
        suspendUserList.updateSuspendUser(suspendUser);
        System.out.println(suspendUserList.getSuspendUserList());
        suspendUserData.writeData(suspendUserList);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
