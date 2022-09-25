package ku.cs.controllers.agency;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ku.cs.models.Agency;
import ku.cs.models.User;
import ku.cs.models.UserList;

public class AssignAgencyDialogController {

    @FXML private ListView<User> staffListView;

    UserList staffList;
    UserList userList;
    User staff;
    Agency agency;

    public void initData(UserList staffList, UserList userList, Agency agency) {
        this.staffList = staffList;
        this.agency = agency;
        this.userList = userList;

        staffListView.setCellFactory(param -> new ListCell<User>() {
            @Override
            protected void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getUsername() == null) {
                    setText(null);
                } else {
                    setText(item.getUsername());
                }
            }
        });

        staffListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<User>() {
            @Override
            public void changed(ObservableValue<? extends User> observableValue, User user, User t1) {
                staff = t1;
            }
        });

        showStaffListView(staffList);
    }

    private void showStaffListView(UserList staffList) {
        staffListView.getItems().setAll(staffList.getUserList());
    }

    @FXML
    private void handleCancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleDoneButton(ActionEvent actionEvent) {
        if (staff == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select staff to assign");
            alert.show();
            return;
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        staff.setAgency(agency);
        userList.updateUser(staff);

        stage.close();
    }
}
