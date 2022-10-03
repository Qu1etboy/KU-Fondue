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

import ku.cs.datastructure.ListMap;
import ku.cs.models.*;
import ku.cs.services.ComplaintCategoryListDataSource;
import ku.cs.services.ComplaintListDataSource;
import ku.cs.services.DataSource;

import java.io.IOException;
import java.net.URL;
import java.security.Key;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Predicate;


public class ComplaintDetailController implements Initializable {

    private User user;
    private Sorter sorter;
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
    private TableView<Complaint> myComplaintTable;
    @FXML
    private TableColumn<Complaint, String> number1;
    @FXML
    private TableColumn<Complaint, String> category1;
    @FXML
    private TableColumn<Complaint, String> topic1;
    @FXML
    private TableColumn<Complaint, String> detail1;
    @FXML
    private TableColumn<Complaint, Integer> vote1;
    @FXML
    private TableColumn<Complaint, String> date1;
    @FXML
    private TableColumn<Complaint, String> status1;
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
    @FXML private TextField wordTextField;

    private ComplaintList complaintList;
    private DataSource<ComplaintList> data;

    final private ObservableList<Complaint> dataTable = FXCollections.observableArrayList();

    @FXML private Label reportCount;
    @FXML private Label inProgressCount;
    @FXML private Label doneCount;
    @FXML private VBox barChartContainer;
    @FXML private ComboBox<String> statusSelector;
    @FXML private DatePicker fromDate;
    @FXML private DatePicker toDate;
    @FXML private Button backButton;
    private BarChart<Number, String> complaintBarChart;

    private DataSource<ComplaintList> complaintData;
    private DataSource<ComplaintCategoryList> categoryData;

    public void initData(User user) {
        this.user = user;

        // student can access this page via dashboard button from sidebar so no need to go back
        if (user.getRole() == Role.STUDENT) {
            backButton.setVisible(false);
        }

        loadMyComplaintData();
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
        categorySelector.setOnAction(e -> filter());

        sorter = new Sorter();
        if (sortSelector != null) {
            sortSelector.getItems().addAll(sorter.getAllTSortList());
            sortSelector.setOnAction(e -> filter());
        }

        initStatusSelector();
        initCombobox();
        initColumn();
        loadData(complaintList);
        setStatusCount();
        initBarChart();
    }

    private void initStatusSelector() {
        List<String> status = new ArrayList<>();
        status.add("รอรับเรื่อง");
        status.add("ดําเนินการ");
        status.add("เสร็จสิ้น");
        statusSelector.getItems().addAll(status);
        statusSelector.setOnAction(e -> filter());
    }

    private void initCombobox() {
        // make combobox set to prompt text when value is null;
        statusSelector.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty) ;
                if (empty || item == null) {
                    setText("ทุกสถานะ");
                } else {
                    setText(item);
                }
            }
        });

        sortSelector.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty) ;
                if (empty || item == null) {
                    setText("เรียงโดย");
                } else {
                    setText(item);
                }
            }
        });

        categorySelector.setButtonCell(new ListCell<ComplaintCategory>() {
            @Override
            protected void updateItem(ComplaintCategory item, boolean empty) {
                super.updateItem(item, empty) ;
                if (empty || item == null) {
                    setText("เลือกหมวดหมู่");
                } else {
                    setText(item.getName());
                }
            }
        });

    }

    private void initColumn(){

        //number.setCellValueFactory(new PropertyValueFactory<>("number"));
        category.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getComplaintCategoryName()));
        detail.setCellValueFactory(new PropertyValueFactory<>("detail"));
        status.setCellValueFactory(new PropertyValueFactory<>("status"));

        vote.setCellValueFactory(new PropertyValueFactory<>("vote"));

        topic.setCellValueFactory(new PropertyValueFactory<>("topic"));
        date.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSimpleDate()));

        category1.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getComplaintCategoryName()));
        detail1.setCellValueFactory(new PropertyValueFactory<>("detail"));
        status1.setCellValueFactory(new PropertyValueFactory<>("status"));

        vote1.setCellValueFactory(new PropertyValueFactory<>("vote"));

        topic1.setCellValueFactory(new PropertyValueFactory<>("topic"));
        date1.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSimpleDate()));

    }

    private void loadData(ComplaintList complaintList) {
        ObservableList<Complaint> dataTable = FXCollections.observableArrayList();
        dataTable.addAll(complaintList.getComplaintList());
        complaintTable.setItems(dataTable);
    }

    private void loadMyComplaintData() {
        ObservableList<Complaint> dataTable = FXCollections.observableArrayList();

        ComplaintList myComplaintList = complaintList.filterBy(new Filterer<Complaint>() {
            @Override
            public boolean filter(Complaint o) {
                return o.getUser().getId().equals(user.getId());
            }
        });

        dataTable.addAll(myComplaintList.getComplaintList());
        myComplaintTable.setItems(dataTable);
    }

    @FXML
    public  void  handleSort(ComplaintList complaintList) {
        // TODO: implement the sort method that use comparator to sort complaint.csv
        String sortType = sortSelector.getValue();
        switch (sortType) {
            case "ล่าสุด" -> sorter.sortByMost(complaintList, new DateComparator());
            case "เก่าสุด" -> sorter.sortByLow(complaintList, new DateComparator());
            case "โหวตมากสุด" -> sorter.sortByMost(complaintList, new VoteComparator());
            case "โหวตน้อยสุด" -> sorter.sortByLow(complaintList, new VoteComparator());
        }

    }

    public void setStatusCount() {
        reportCount.setText(Integer.toString(complaintList.getReportCount()));
        inProgressCount.setText(Integer.toString(complaintList.getInProgressCount()));
        doneCount.setText(Integer.toString(complaintList.getDoneCount()));
    }

    @FXML
    public void handleViewDetail(ActionEvent actionEvent) throws IOException {
        Complaint selectedComplaint = complaintTable.getSelectionModel().getSelectedItem();
        Complaint selectedMyComplaint = myComplaintTable.getSelectionModel().getSelectedItem();

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

    @FXML
    public void handleViewMyDetail(ActionEvent actionEvent) throws IOException {
        Complaint selectedMyComplaint = myComplaintTable.getSelectionModel().getSelectedItem();

        if (selectedMyComplaint == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("กรุณาเลือกเรื่องร้องเรียน");
            alert.show();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/complaintDetail.fxml"));
        Parent pane = loader.load();

        ComplaintInfoController controller = loader.getController();
        controller.initData(user, selectedMyComplaint, false);

        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        borderPane.setCenter(pane);

    }

    public void atLeastVote(KeyEvent keyEvent){
        filter();
    }

    public void atMostVote(KeyEvent keyEvent){
        filter();
    }

    private void filter() {
        // filter by status
        ComplaintList filteredComplaintList = complaintList.filterBy(new Filterer<Complaint>() {
            @Override
            public boolean filter(Complaint o) {
                if (statusSelector.getValue() == null) return true;
                return o.getStatus().equals(statusSelector.getValue());
            }
        });

        // filter by category
        filteredComplaintList = filteredComplaintList.filterBy(new Filterer<Complaint>() {
            @Override
            public boolean filter(Complaint o) {
                if (categorySelector.getValue() == null) return true;
                return o.getComplaintCategoryName().equals(categorySelector.getValue().getName());
            }
        });

        // filter by vote
        filteredComplaintList = filteredComplaintList.filterBy(new Filterer<Complaint>() {
            @Override
            public boolean filter(Complaint o) {
                String atMost = atMostTextField.getText();
                String atLeast = atLeastTextField.getText();
                if (atLeast.isEmpty() && atMost.isEmpty()) return true;
                try {
                    if (atLeast.isEmpty()) return o.getVote() <= Integer.parseInt(atMost);
                    if (atMost.isEmpty()) return o.getVote() >= Integer.parseInt(atLeast);
                    return o.getVote() >= Integer.parseInt(atLeast) &&
                            o.getVote() <= Integer.parseInt(atMost);

                } catch (NumberFormatException e) {
                    return false;
                }
            }
        });

        // filter by word that contain in topic, detail, additional detail of complaint
        filteredComplaintList = filteredComplaintList.filterBy(new Filterer<Complaint>() {
            @Override
            public boolean filter(Complaint o) {
                String word = wordTextField.getText();
                if (word.isEmpty()) return true;
                if (o.getTopic().contains(word) || o.getDetail().contains(word)) return true;
                boolean found = false;
                for (String question : o.getAdditionalDetail().keyList()) {
                    if (o.getAdditionalDetail().get(question).contains(word)) {
                        found = true;
                    }
                }
                return found;
            }
        });

        // filter by date
        filteredComplaintList = filteredComplaintList.filterBy(new Filterer<Complaint>() {
            @Override
            public boolean filter(Complaint o) {
                if (fromDate.getValue() == null && toDate.getValue() == null) return true;
                if (toDate.getValue() == null) {
                    return o.getDate().toLocalDate().compareTo(fromDate.getValue()) >= 0;
                }
                if (fromDate.getValue() == null) {
                    return o.getDate().toLocalDate().compareTo(toDate.getValue()) <= 0;
                }
                return o.getDate().toLocalDate().compareTo(fromDate.getValue()) >= 0 &&
                        o.getDate().toLocalDate().compareTo(toDate.getValue()) <= 0;
            }
        });

        // sort
        if (sortSelector.getValue() != null) {
            handleSort(filteredComplaintList);
        }

        loadData(filteredComplaintList);
    }

    @FXML
    public void handleClearSearchButton(ActionEvent actionEvent){
        atLeastTextField.clear();
        atMostTextField.clear();

        statusSelector.setValue(null);
        sortSelector.setValue(null);
        categorySelector.setValue(null);

        fromDate.setValue(null);
        toDate.setValue(null);

        loadData(complaintList);
    }

    @FXML
    private void handleSearch(KeyEvent keyEvent) {
        filter();
    }

    @FXML
    private void handleSelectDate() {
        filter();
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
