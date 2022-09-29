package ku.cs.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;

import javafx.scene.chart.*;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

import javafx.scene.layout.VBox;

import ku.cs.models.*;
import ku.cs.services.ComplaintCategoryListDataSource;
import ku.cs.services.ComplaintListDataSource;
import ku.cs.services.DataSource;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Predicate;


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
    private TableColumn<Complaint, String> topic;
    @FXML
    private TableColumn<Complaint, String> detail;
    @FXML
    private TableColumn<Complaint, Integer> vote;
    @FXML
    private TableColumn<Complaint, String> date;
    @FXML
    private TableColumn<Complaint, String> status;
    @FXML
    private ComboBox<String> sortSelector;
    private DataSource<ComplaintCategoryList> CategoryData;
    private ComplaintCategoryList complaintCategoryList;

    private ComplaintCategory complaintCategory;
    @FXML
    private ComboBox<ComplaintCategory> categorySelector;
    @FXML
    private TextField atLeastTextField;
    @FXML
    private TextField atMostTextField;

    private ComplaintList complaintList;
    private DataSource<ComplaintList> data;

    final private ObservableList<Complaint> dataTable = FXCollections.observableArrayList();

    @FXML private Label reportCount;
    @FXML private Label inProgressCount;
    @FXML private Label doneCount;
    @FXML private VBox barChartContainer;
    private BarChart<Number, String> complaintBarChart;



    private DataSource<ComplaintList> complaintData;

    private DataSource<ComplaintCategoryList> categoryData;


    public void initData(User user) {
        this.user = user;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        complaintData = new ComplaintListDataSource("data", "complaint.csv");
        complaintList = complaintData.readData();
        categoryData = new ComplaintCategoryListDataSource("data", "complaint_category.csv");
        complaintCategoryList = categoryData.readData();


        CategoryData = new ComplaintCategoryListDataSource("data","complaint_category.csv");
        complaintCategoryList = CategoryData.readData();
        categorySelector.getItems().addAll(complaintCategoryList.getComplaintCategoryList());
        categorySelector.setOnAction(e -> handleSelectCategory());

        sorter = new Sorter();
        if (sortSelector != null) {
            sortSelector.getItems().addAll(sorter.getAllTSortList());
            sortSelector.setOnAction(e -> handleSort(e));
        }


//        category.setCellValueFactory(cellData ->
//                new SimpleStringProperty(cellData.getValue().getComplaintCategoryName()));
//        detail.setCellValueFactory(new PropertyValueFactory<>("detail"));
//        status.setCellValueFactory(new PropertyValueFactory<>("status"));
//        vote.setCellValueFactory(new PropertyValueFactory<>("vote"));
//
//
//        ObservableList<Complaint> dataTable = FXCollections.observableArrayList();
//        dataTable.addAll(complaintList.getComplaintList());
//        complaintTable.setItems(dataTable);
//
//        FilteredList<Complaint> filteredVote = new FilteredList<>(dataTable, b-> true);
//
//        atMostTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
//            filteredVote.setPredicate(complaint -> {
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//                if (complaint.getVote() <= Integer.parseInt(newValue)) {
//                    return true;
//                } else
//                    return false;
//            });
//        });
//
//        atLeastTextField.textProperty().addListener((observableValue, oldValue, newValue) -> {
//            filteredVote.setPredicate(complaint -> {
//                if (newValue == null || newValue.isEmpty()) {
//                    return true;
//                }
//                if (complaint.getVote() >= Integer.parseInt(newValue)) {
//                    return true;
//                } else
//                    return false;
//            });
//        });
//        SortedList<Complaint> sortedVote = new SortedList<>(filteredVote);
//
//        sortedVote.comparatorProperty().bind(complaintTable.comparatorProperty());
//
//        complaintTable.setItems(sortedVote);

//        complaintTable.setRowFactory( tv -> {
//            TableRow<Complaint> row = new TableRow<>();
//            row.setOnMouseClicked(event -> {
//                if (event.getClickCount() == 2 && (! row.isEmpty()) ) {
//                    Complaint rowData = row.getItem();
//                    handleViewDetail(event);
//                }
//            });
//            return row ;
//        });


        initColumn();
        loadData();
        setStatusCount();
        initBarChart();
    }


    private void initColumn(){

        //number.setCellValueFactory(new PropertyValueFactory<>("number"));
        category.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getComplaintCategoryName()));
        detail.setCellValueFactory(new PropertyValueFactory<>("detail"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        vote.setCellValueFactory(new PropertyValueFactory<>("vote"));

        topic.setCellValueFactory(new PropertyValueFactory<>("topic"));
        vote.setCellValueFactory(new PropertyValueFactory<>("vote"));
        date.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSimpleDate()));

    }
    private void loadData() {
        ObservableList<Complaint> dataTable = FXCollections.observableArrayList();
        dataTable.addAll(complaintList.getComplaintList());
        complaintTable.setItems(dataTable);

    }

    @FXML
    public  void  handleSort(ActionEvent actionEvent) {
        // TODO: implement the sort method that use comparator to sort complaint.csv
        String sortType = sortSelector.getValue();
        switch (sortType) {
            case "ล่าสุด" -> sorter.sortByMost(complaintList, new DateComparator());
            case "เก่าสุด" -> sorter.sortByLow(complaintList, new DateComparator());
            case "โหวตมากสุด" -> sorter.sortByMost(complaintList, new VoteComparator());
            case "โหวตน้อยสุด" -> sorter.sortByLow(complaintList, new VoteComparator());
        }
        loadData();
    }

    public void setStatusCount() {
        int report = 0, inProgress = 0, done = 0;
        for (Complaint complaint : complaintList.getComplaintList()) {
            if (complaint.getStatus().equals("รอรับเรื่อง")) {
                report++;
            } else if (complaint.getStatus().equals("รอดําเนินการ")) {
                inProgress++;
            } else {
                done++;
            }
        }

        reportCount.setText(Integer.toString(report));
        inProgressCount.setText(Integer.toString(inProgress));
        doneCount.setText(Integer.toString(done));
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
        controller.initData(user, selectedComplaint, false);

        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        borderPane.setCenter(pane);

    }
    public void handleSelectCategory() {


        ComplaintList filterComplaintCategory = complaintList.filterBy(new Filterer<Complaint>() {
            @Override
            public boolean filter(Complaint o) {

                if (o.getComplaintCategoryName().equals(categorySelector.getValue().getName())) return true;

                return false;
            }

        });
        //complaintTable.setItems((ObservableList<Complaint>) filterComplaintCategory.getComplaintList());
        ObservableList<Complaint> dataTable = FXCollections.observableArrayList();
        dataTable.addAll(filterComplaintCategory.getComplaintList());
        complaintTable.setItems(dataTable);

    }
    public void atLeastVote(KeyEvent keyEvent){
        ComplaintList filteredVote = complaintList.filterBy(new Filterer<Complaint>(){
            @Override
            public boolean filter(Complaint o) {
                if (atLeastTextField.getText().isEmpty()) {
                    return true;
                }
                if (o.getVote() >= Integer.parseInt(atLeastTextField.getText())) {
                    return true;
                } else
                    return false;
            }
                });

        ObservableList<Complaint> dataTable = FXCollections.observableArrayList();
        dataTable.addAll(filteredVote.getComplaintList());
        complaintTable.setItems(dataTable);

    }

    public void atMostVote(KeyEvent keyEvent){
        ComplaintList filteredVote = complaintList.filterBy(new Filterer<Complaint>(){
            @Override
            public boolean filter(Complaint o) {
                if (atMostTextField.getText().isEmpty()) {
                    return true;
                }
                if (o.getVote() <= Integer.parseInt(atMostTextField.getText())) {
                    return true;
                } else
                    return false;
            }
        });

        ObservableList<Complaint> dataTable = FXCollections.observableArrayList();
        dataTable.addAll(filteredVote.getComplaintList());
        complaintTable.setItems(dataTable);

    }
    @FXML
    public void handleClearSearchButton(ActionEvent actionEvent){

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

    public void initBarChart() {
        NumberAxis xAxis = new NumberAxis();
        CategoryAxis yAxis = new CategoryAxis();

        complaintBarChart = new BarChart<>(xAxis, yAxis);
        complaintBarChart.getXAxis().setTickLabelRotation(360);
        complaintBarChart.setCategoryGap(10);
        complaintBarChart.setBarGap(1);
        complaintBarChart.setTitle("สถิติการแจ้งเรื่องร้องเรียนในแต่ละหมวดหมู่");
        complaintBarChart.setPadding(new Insets(10, 10, 10, 10));
        xAxis.setLabel("จํานวนเรื่องร้องเรียน");

        for (ComplaintCategory category : complaintCategoryList.getComplaintCategoryList()) {
            XYChart.Series<Number, String> series = new XYChart.Series<>();
            series.getData().add(new XYChart.Data<>(complaintList.countCategory(category), category.getName()));
            series.setName(category.getName());
            complaintBarChart.getData().add(series);
        }

        barChartContainer.getChildren().add(complaintBarChart);

    }

}
