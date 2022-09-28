package ku.cs.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TableView;
import ku.cs.models.*;
import ku.cs.services.ComplaintListDataSource;
import ku.cs.services.DataSource;

import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.ResourceBundle;


public class ComplaintDetailController implements Initializable {

    User user;
    Sorter sorter;
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
    @FXML
    private ComboBox<String> sortSelector;

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

    public void initData(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = new ComplaintListDataSource("data", "complaint.csv");
        complaintList = data.readData();

        sorter = new Sorter();
        if (sortSelector != null) {
            sortSelector.getItems().addAll(sorter.getAllTSortList());
            sortSelector.setOnAction(e -> handleSort(e));
        }

        initColumn();
        loadData();
    }

    private void initColumn(){

//        number.setCellValueFactory(new PropertyValueFactory<>("number"));
        category.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getComplaintCategory().getName()));
        detail.setCellValueFactory(new PropertyValueFactory<>("detail"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));
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
    public  void  handleSort(ActionEvent actionEvent) {
        // TODO: implement the sort method that use comparator to sort complaint.csv
        String sortType = sortSelector.getValue();
        if (sortType.equals("ล่าสุด")) {
            sorter.sortByMost(complaintList, new DateComparator());
        } else if (sortType.equals("เก่าสุด")) {
            sorter.sortByLow(complaintList, new DateComparator());
        } else if (sortType.equals("โหวตมากสุด")) {
            sorter.sortByMost(complaintList, new VoteComparator());
        } else if (sortType.equals("โหวตน้อยสุด")) {
            sorter.sortByLow(complaintList, new VoteComparator());
        }
        loadData();
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/dashboard.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        Parent pane = loader.load();

        DashboardDetailController controller = loader.getController();
        controller.initData(user);
        borderPane.setCenter(pane);
    }


}
