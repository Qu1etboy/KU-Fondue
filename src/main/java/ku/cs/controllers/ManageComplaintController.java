package ku.cs.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import ku.cs.models.Complaint;
import ku.cs.models.ComplaintList;
import ku.cs.models.User;
import ku.cs.services.ComplaintListDataSource;
import ku.cs.services.DataSource;

import java.io.IOException;

public class ManageComplaintController {
    User user;

    @FXML
    private TableView<Complaint> complaintTable;
    @FXML
    private TableColumn<Complaint, String> number;
    @FXML
    private TableColumn<Complaint, String> category;
    @FXML
    private TableColumn<Complaint, String> topic;
    @FXML
    private TableColumn<Complaint, String> detail;
    @FXML
    private TableColumn<Complaint, Integer> vote;
    @FXML
    private TableColumn<Complaint, String> date;
    @FXML
    private TableColumn<Complaint, String> status;

    DataSource<ComplaintList> data;
    ComplaintList complaintList;

    public void initData(User user) {
        this.user = user;
        data = new ComplaintListDataSource("data", "complaint.csv");
        complaintList = data.readData();

        initColumn();
        loadData();
    }

    private void initColumn(){

//        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        category.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getComplaintCategoryName()));
        detail.setCellValueFactory(new PropertyValueFactory<>("detail"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
        topic.setCellValueFactory(new PropertyValueFactory<>("topic"));
        vote.setCellValueFactory(new PropertyValueFactory<>("vote"));
        date.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSimpleDate()));
    }
    private void loadData() {
        ObservableList<Complaint> dataTable = FXCollections.observableArrayList();
        dataTable.addAll(complaintList.getComplaintList());
//            for (Complaint complaint : complaintList.getComplaintList()) {
//                dataTable.add(complaint);
//            }
//            for(int i = 1;i < 7; i++){
//                dataTable.add(new Complaint(String.valueOf(i),"category " + i,"detail " + i,
//                        "status " + i));
//            }

        complaintTable.setItems(dataTable);

    }

    @FXML
    public void handleViewDetail(ActionEvent actionEvent) throws IOException {
        Complaint selectedComplaint = complaintTable.getSelectionModel().getSelectedItem();

        if (selectedComplaint == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("กรุณาเลือกเรื่องร้องเรียน");
            alert.show();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/complaintDetail.fxml"));
        Parent pane = loader.load();

        ComplaintInfoController controller = loader.getController();
        controller.initData(user, selectedComplaint, true);

        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        borderPane.setCenter(pane);

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
}
