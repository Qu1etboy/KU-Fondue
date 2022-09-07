package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TableView;
import ku.cs.models.Complaint;
import ku.cs.models.ComplaintList;
import ku.cs.services.ComplaintDataSource;
import ku.cs.services.DataSource;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class ComplaintDetailController implements Initializable {
    @FXML
    private TableView<Complaint> complaintTable;
    @FXML
    private TableColumn<Complaint, String> number;
    @FXML
    private TableColumn<Complaint, String> category;
    @FXML
    private TableColumn<Complaint, String> detail;
    @FXML
    private TableColumn<Complaint, String> status;

    private ComplaintList complaintList;
    private DataSource<ComplaintList> data;

//    ObservableList<Complaint> list = FXCollections.observableArrayList(
//            new Complaint("1","category1","aaa","in progress"),
//            new Complaint("2","category2","bbb","in progress"),
//            new Complaint("3","category3","ccc","finish"),
//            new Complaint("4","category4","ddd","finish")
//
//    );

//    public void initialize(){
//
//        number.setCellValueFactory(new PropertyValueFactory<Complaint, String>("number"));
//        category.setCellValueFactory(new PropertyValueFactory<Complaint, String>("category"));
//        detail.setCellValueFactory(new PropertyValueFactory<Complaint, String>("detail"));
//        status.setCellValueFactory(new PropertyValueFactory<Complaint, String>("status"));
//
////        complaintTable.setItems(list);
//    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initColumn();
        loadData();
    }

    private void initColumn(){

        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        category.setCellValueFactory(new PropertyValueFactory<>("category"));
        detail.setCellValueFactory(new PropertyValueFactory<>("detail"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
    }
    private void loadData() {
            ObservableList<Complaint> dataTable = FXCollections.observableArrayList();

            for(int i = 1;i < 7; i++){
                dataTable.add(new Complaint(String.valueOf(i),"category " + i,"detail " + i,
                        "status " + i));
            }

            complaintTable.setItems(dataTable);

    }



    @FXML
    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/adminDashboard.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        borderPane.setCenter(loader.load());
    }


}
