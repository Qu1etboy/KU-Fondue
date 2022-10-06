package ku.cs.controllers.agency;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ku.cs.controllers.ConfirmationDialogController;
import ku.cs.controllers.DashboardDetailController;
import ku.cs.models.*;
import ku.cs.services.AgencyListDataSource;
import ku.cs.services.ComplaintCategoryListDataSource;
import ku.cs.services.DataSource;
import ku.cs.services.UserListDataSource;
import ku.cs.services.collection.Filterer;

import java.io.IOException;

public class AgencyController {
    User user;

    @FXML private ListView<Agency> agencyListView;
    @FXML private ListView<User> teacherListView;
    @FXML private ListView<ComplaintCategory> categoryListView;
    @FXML private Label agencyNameLabel;
    @FXML private VBox defaultContent;
    @FXML private VBox detailContent;

    DataSource<AgencyList> agencyData;
    AgencyList agencyList;

    Agency agency;
    UserList userList;
    ComplaintCategoryList complaintCategoryList;
    ComplaintCategory complaintCategory;
    DataSource<ComplaintCategoryList> categoryData;
    DataSource<UserList> userData;

    public void initData(User user) {
        this.user = user;
        agencyData = new AgencyListDataSource("data", "agency.csv");
        agencyList = agencyData.readData();
        userData = new UserListDataSource("data", "user.csv");
        userList = userData.readData();
        categoryData = new ComplaintCategoryListDataSource("data", "complaint_category.csv");
        complaintCategoryList = categoryData.readData();

        agencyListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Agency>() {
            @Override
            public void changed(ObservableValue<? extends Agency> observableValue, Agency agency, Agency t1) {
                showAgencyDetail(t1);
            }
        });

        teacherListView.setCellFactory(param -> new ListCell<User>() {
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

        categoryListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ComplaintCategory>() {
            @Override
            public void changed(ObservableValue<? extends ComplaintCategory> observableValue, ComplaintCategory category, ComplaintCategory t1) {
                complaintCategory = t1;
            }
        });

        showAgencyList(agencyList);
    }

    private void showAgencyList(AgencyList agencyList) {
        agencyListView.getItems().setAll(agencyList.getAgencyList());
    }

    private void showAgencyDetail(Agency agency) {
        if (agency == null) {
            return;
        }

        this.agency = agency;

        defaultContent.setVisible(false);
        detailContent.setVisible(true);

        agencyNameLabel.setText(agency.getName());
        showAgencyStaff(userList);
        showCategory(agency);

    }

    private void showAgencyStaff(UserList userList) {
        UserList staffList = userList.filterBy(new Filterer<User>() {
            @Override
            public boolean filter(User o) {
                if (o.getRole() != Role.TEACHER || o.getAgency() == null) return false;
                return o.getAgency().getId().equals(agency.getId());
            }
        });

        teacherListView.getItems().setAll(staffList.getUserList());
    }
    private void showCategory(Agency agency) {
        categoryListView.getItems().setAll(agency.getManagedCategory());
    }

    @FXML
    private void handleAddAgency(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/agency/addAgencyDialog.fxml"));
        Parent root = loader.load();
        AddAgencyDialogController controller = loader.getController();
        controller.initData(agencyList);

        initDialogBox(actionEvent, root);

        agencyData.writeData(agencyList);
        showAgencyList(agencyList);

    }

    @FXML
    private void handleAddStaff(ActionEvent actionEvent) throws IOException {
        UserList staffList = userList.filterBy(new Filterer<User>() {
            @Override
            public boolean filter(User o) {
                if (o.getRole() != Role.TEACHER) return false;
                if (o.getAgency() == null) return true;
                return !o.getAgency().getId().equals(agency.getId());
            }
        });

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/agency/assignAgencyDialog.fxml"));
        Parent root = loader.load();
        AssignAgencyDialogController controller = loader.getController();
        controller.initData(staffList, userList, agency);

        initDialogBox(actionEvent, root);

        userData.writeData(userList);
        showAgencyStaff(userList);
    }

    @FXML
    private void handleAddCategory(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/agency/addCategoryDialog.fxml"));
        Parent root = loader.load();
        AddCategoryDialogController controller = loader.getController();

        // show only category that are not manage by agency
        ComplaintCategoryList filteredCategory = complaintCategoryList.filterBy(new Filterer<ComplaintCategory>() {
            @Override
            public boolean filter(ComplaintCategory o) {
                return !agency.getManagedCategory().contains(o);
            }
        });

        controller.initData(filteredCategory, agency);

        initDialogBox(actionEvent, root);

        agencyData.writeData(agencyList);
        showCategory(agency);
    }

    @FXML
    private void handleChangeAgencyName(ActionEvent actionEvent) throws IOException {
        if (agency == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("กรุณาเลือกหน่วยงาน");
            alert.show();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/agency/renameAgencyDialog.fxml"));
        Parent root = loader.load();
        RenameAgencyDialogController controller = loader.getController();
        controller.initData(agency);

        initDialogBox(actionEvent, root);

        agencyData.writeData(agencyList);
        agencyNameLabel.setText(agency.getName());
        showAgencyList(agencyList);
    }

    @FXML
    private void handleRemoveAgency(ActionEvent actionEvent) throws IOException {
        if (agency == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("กรุณาเลือกหน่วยงาน");
            alert.show();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/confirmationDialog.fxml"));
        Parent root = loader.load();

        ConfirmationDialogController controller = loader.getController();
        controller.initData("คุณต้องการจะลบใช่ไหม?");

        initDialogBox(actionEvent, root);

        if (!controller.getConfirm()) return;

        agencyList.removeAgency(agency);
        // set staff that has a removed agency to null
        userList.updateUserAgency(agency, null);
        agencyData.writeData(agencyList);
        userData.writeData(userList);

        showAgencyList(agencyList);
        detailContent.setVisible(false);
        defaultContent.setVisible(true);

    }

    @FXML
    private void handleRemoveCategory(ActionEvent actionEvent) throws IOException {
        if (complaintCategory == null || agency == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("กรุณาเลือกสิ่งที่ต้องการจะลบ");
            alert.show();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/confirmationDialog.fxml"));
        Parent root = loader.load();

        ConfirmationDialogController controller = loader.getController();
        controller.initData("คุณต้องการจะลบใช่ไหม?");

        initDialogBox(actionEvent, root);

        if (!controller.getConfirm()) return;

        agency.removeManagedCategory(complaintCategory);
        agencyData.writeData(agencyList);
        showCategory(agency);
    }

    @FXML
    private void handleBackButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/dashboard.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        Parent pane = loader.load();

        DashboardDetailController controller = loader.getController();
        controller.initData(user);
        borderPane.setCenter(pane);
    }

    private void initDialogBox(ActionEvent actionEvent, Parent root) {

        Scene scene = new Scene(root);

        Stage parentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        StackPane stackPane = (StackPane) ((Node) actionEvent.getSource()).getScene().getRoot();
        VBox vBox = (VBox) stackPane.getChildren().get(1);

        Stage dialogBox = new Stage();

        String themeCSS = this.getClass().getResource("/ku/cs/css/themes/" + user.getTheme() + ".css").toExternalForm();
        String fontCSS = this.getClass().getResource("/ku/cs/css/fonts/" + user.getFont() + ".css").toExternalForm();
        root.getStylesheets().add(themeCSS);
        root.getStylesheets().add(fontCSS);

        dialogBox.initModality(Modality.APPLICATION_MODAL);
        dialogBox.initOwner(parentStage);
        dialogBox.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        dialogBox.setScene(scene);
        // make dialog box close when click outside
        dialogBox.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                dialogBox.close();
            }
        });

        // make dialog box always center to the screen of application
        ChangeListener<Number> widthListener = (observable, oldValue, newValue) -> {
            double stageWidth = newValue.doubleValue();
            dialogBox.setX(parentStage.getX() + parentStage.getWidth() / 2 - stageWidth / 2);
        };
        ChangeListener<Number> heightListener = (observable, oldValue, newValue) -> {
            double stageHeight = newValue.doubleValue();
            dialogBox.setY(parentStage.getY() + parentStage.getHeight() / 2 - stageHeight / 2);
        };
        dialogBox.widthProperty().addListener(widthListener);
        dialogBox.heightProperty().addListener(heightListener);
        dialogBox.setOnShown(e -> {
            dialogBox.widthProperty().removeListener(widthListener);
            dialogBox.heightProperty().removeListener(heightListener);
        });

        vBox.setVisible(true);
        // add transition when dark background appear
        FadeTransition ft = new FadeTransition(Duration.millis(500), vBox);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();

        dialogBox.showAndWait();

        vBox.setVisible(false);

    }

}