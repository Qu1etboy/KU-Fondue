package ku.cs.controllers;

import javafx.animation.FadeTransition;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Duration;
import ku.cs.models.*;
import ku.cs.services.*;
import ku.cs.services.collection.Filterer;

import java.io.IOException;

public class ReportController {
    private User user;

    @FXML private TableView<Report> userTable;
    @FXML private TableColumn<Report, ImageView> profileImage;
    @FXML private TableColumn<Report, String> username;
    @FXML private TableColumn<Report, String> type;
    @FXML private TableColumn<Report, String> detail;

    @FXML private TableView<Report> complaintTable;
    @FXML private TableColumn<Report, String> category;
    @FXML private TableColumn<Report, String> topic;
    @FXML private TableColumn<Report, String> complaintDetail;
    @FXML private TableColumn<Report, String> reportType;
    @FXML private TableColumn<Report, String> reportDetail;
    @FXML private TableView<SuspendUser> suspendUserTable;
    @FXML private TableColumn<SuspendUser, ImageView> profileImage2;
    @FXML private TableColumn<SuspendUser, String> username2;
    @FXML private TableColumn<SuspendUser, String> count;
    @FXML private Label reasonLabel;
    @FXML private VBox defaultContent;
    @FXML private VBox detailContent;
    @FXML private ListView<SuspendUser> suspendUserListView;
    @FXML private VBox requestDetail;
    @FXML private HBox userDetailContainer;
    @FXML private TabPane reportTabPane;

    private ReportList reportList;
    private DataSource<ReportList> reportData;
    private UserList userList;
    private DataSource<UserList> userData;
    private ComplaintList complaintList;
    private DataSource<ComplaintList> complaintData;
    private SuspendUserList suspendUserList;
    private DataSource<SuspendUserList> suspendUserData;

    public void initData(User user) {
        this.user = user;
        reportData = new ReportListDataSource("data", "report.csv");
        reportList = reportData.readData();
        userData = new UserListDataSource("data", "user.csv");
        userList = userData.readData();
        complaintData = new ComplaintListDataSource("data", "complaint.csv");
        complaintList = complaintData.readData();
        suspendUserData = new SuspendUserListDataSource("data", "suspend_user.csv");
        suspendUserList = suspendUserData.readData();

        loadUserTableData();
        loadComplaintTableData();
        loadSuspendUserTableData();
        loadRequestPage();
        initColumn();
    }

    public void setTab(int index) {
        reportTabPane.getSelectionModel().select(index);
    }

    private void loadUserTableData() {
        ObservableList<Report> dataTable = FXCollections.observableArrayList();

        ReportList reportedUser = reportList.filterBy(new Filterer<Report>() {
            @Override
            public boolean filter(Report o) {
                return o.getUser() != null;
            }
        });

        dataTable.addAll(reportedUser.getReportList());
        userTable.setItems(dataTable);
    }

    private void loadComplaintTableData() {
        ObservableList<Report> dataTable = FXCollections.observableArrayList();

        ReportList reportedComplaint = reportList.filterBy(new Filterer<Report>() {
            @Override
            public boolean filter(Report o) {
                return o.getComplaint() != null;
            }
        });

        dataTable.addAll(reportedComplaint.getReportList());
        complaintTable.setItems(dataTable);
    }

    private void loadSuspendUserTableData() {
        ObservableList<SuspendUser> dataTable = FXCollections.observableArrayList();

        dataTable.addAll(suspendUserList.getSuspendUserList());
        suspendUserTable.setItems(dataTable);
    }

    private void loadRequestPage() {
        SuspendUserList requestSuspendUser = suspendUserList.filterBy(new Filterer<SuspendUser>() {
            @Override
            public boolean filter(SuspendUser o) {
                return o.isRequest();
            }
        });

        if (requestSuspendUser.getSuspendUserList().isEmpty()) {
            detailContent.setVisible(false);
            defaultContent.setVisible(true);
            return;
        }

        suspendUserListView.getItems().setAll(requestSuspendUser.getSuspendUserList());
        suspendUserListView.setCellFactory(param -> new ListCell<SuspendUser>() {
            @Override
            protected void updateItem(SuspendUser item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getUser().getUsername() == null) {
                    setText(null);
                } else {
                    setText(item.getUser().getUsername());
                }
            }
        });

        suspendUserListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<SuspendUser>() {
            @Override
            public void changed(ObservableValue<? extends SuspendUser> observableValue, SuspendUser suspendUser, SuspendUser t1) {
                if (t1 == null) {
                    return;
                }
                showRequestDetail(t1);
            }
        });

        defaultContent.setVisible(false);
        detailContent.setVisible(true);
    }

    private void showRequestDetail(SuspendUser suspendUser) {
        Label usernameLabel = new Label(suspendUser.getUser().getUsername());

        userDetailContainer.getChildren().add(suspendUser.getUser().getProfileImageView());
        userDetailContainer.getChildren().add(usernameLabel);

        reasonLabel.setText(suspendUser.getReason());

        requestDetail.setVisible(true);
    }

    private void initColumn() {
        profileImage.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getUser().getProfileImageView()));
        username.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUser().getUsername()));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));

        detail.setCellValueFactory(new PropertyValueFactory<>("detail"));
        detail.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Report, String> call(TableColumn<Report, String> arg0) {
                return new TableCell<>() {
                    private Text text;

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            text = new Text(item);
                            text.setWrappingWidth(200);
                            text.getStyleClass().add("text-color");
                            this.setWrapText(true);

                            setGraphic(text);
                        }
                    }
                };
            }
        });


        category.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getComplaint().getComplaintCategoryName()));
        topic.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getComplaint().getTopic()));
        complaintDetail.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getComplaint().getDetail()));
        reportType.setCellValueFactory(new PropertyValueFactory<>("type"));
        reportDetail.setCellValueFactory(new PropertyValueFactory<>("detail"));
        reportDetail.setCellFactory(new Callback<>() {
            @Override
            public TableCell<Report, String> call(TableColumn<Report, String> arg0) {
                return new TableCell<>() {
                    private Text text;

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (!isEmpty()) {
                            text = new Text(item);
                            text.getStyleClass().add("text-color");
                            text.setWrappingWidth(200);
                            this.setWrapText(true);

                            setGraphic(text);
                        }
                    }
                };
            }
        });

        profileImage2.setCellValueFactory(cellData ->
                new SimpleObjectProperty<>(cellData.getValue().getUser().getProfileImageView()));
        username2.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getUser().getUsername()));
        count.setCellValueFactory(new PropertyValueFactory<>("loginCount"));

    }

    @FXML
    private void handleSuspendUser(ActionEvent actionEvent) throws IOException {
        Report report = userTable.getSelectionModel().getSelectedItem();

        if (report == null) {
            return;
        }

        User user = userTable.getSelectionModel().getSelectedItem().getUser();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/confirmationDialog.fxml"));
        Parent root = loader.load();

        ConfirmationDialogController controller = loader.getController();
        controller.initData("คุณต้องการจะระงับการใช้งานของผู้ใช้นี้ใช่ไหม");

        initDialogBox(actionEvent, root);

        if (!controller.getConfirm()) return;

        // set user suspend to true and remove it from report
        user.setSuspend(true);
        userList.updateUser(user);
        userData.writeData(userList);
        reportList.removeReport(report);
        reportData.writeData(reportList);

        // add user to suspend user list
        suspendUserList.addSuspendUser(new SuspendUser(user));
        suspendUserData.writeData(suspendUserList);

        loadUserTableData();
        loadSuspendUserTableData();
    }

    @FXML
    private void handleRemoveReport() {
        Report report = userTable.getSelectionModel().getSelectedItem();

        if (report == null) {
            return;
        }

        reportList.removeReport(report);
        reportData.writeData(reportList);
        loadUserTableData();
    }

    @FXML
    private void handleRemoveComplaint(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/confirmationDialog.fxml"));
        Parent root = loader.load();

        ConfirmationDialogController controller = loader.getController();
        controller.initData("คุณต้องการจะลบเรื่องร้องเรียนนี้ใช่ไหม?");

        initDialogBox(actionEvent, root);

        if (!controller.getConfirm()) return;

        Report report = complaintTable.getSelectionModel().getSelectedItem();
        Complaint complaint = complaintTable.getSelectionModel().getSelectedItem().getComplaint();
        reportList.removeReport(report);
        complaintList.removeComplaint(complaint);
        complaintData.writeData(complaintList);
        reportData.writeData(reportList);

        loadComplaintTableData();
    }

    @FXML
    private void handleViewDetailButton(ActionEvent actionEvent) throws IOException {
        if (complaintTable.getSelectionModel().getSelectedItem() == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("กรุณาเลือกเรื่องร้องเรียน");
            alert.show();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/complaintDetail.fxml"));
        Parent pane = loader.load();

        Complaint complaint = complaintTable.getSelectionModel().getSelectedItem().getComplaint();

        ComplaintInfoController controller = loader.getController();
        controller.initData(user, complaint, "report");

        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        borderPane.setCenter(pane);
    }

    @FXML
    private void handleUnSuspend(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/confirmationDialog.fxml"));
        Parent root = loader.load();

        ConfirmationDialogController controller = loader.getController();
        controller.initData("คุณต้องการคืนสิทธิ์การใช้งานให้กับผู้ใช้คนนี้?");

        initDialogBox(actionEvent, root);

        if (!controller.getConfirm()) return;

        SuspendUser suspendUser = suspendUserTable.getSelectionModel().getSelectedItem();

        // set user suspend to true and remove it from report
        unSuspend(suspendUser.getUser());
    }

    private void unSuspend(User user) {
        user.setSuspend(false);
        userList.updateUser(user);
        userData.writeData(userList);
        suspendUserList.removeSuspendUser(user);
        suspendUserData.writeData(suspendUserList);
        loadSuspendUserTableData();
    }

    @FXML
    private void handleRejectButton() {
        SuspendUser suspendUser = suspendUserListView.getSelectionModel().getSelectedItem();
        suspendUser.setRequest(false);
        loadRequestPage();
        requestDetail.setVisible(false);

        suspendUserList.updateSuspendUser(suspendUser);
        suspendUserData.writeData(suspendUserList);
    }

    @FXML
    private void handleApproveButton(ActionEvent actionEvent) throws IOException {
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/confirmationDialog.fxml"));
//        Parent root = loader.load();
//
//        ConfirmationDialogController controller = loader.getController();
//        controller.initData("คุณต้องการคืนสิทธิ์การใช้งานให้กับผู้ใช้คนนี้?");
//
//        initDialogBox(actionEvent, root);
//
//        if (!controller.getConfirm()) return;

        SuspendUser suspendUser = suspendUserListView.getSelectionModel().getSelectedItem();
        unSuspend(suspendUser.getUser());

        loadRequestPage();
        requestDetail.setVisible(false);
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
