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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import ku.cs.models.*;
import ku.cs.services.AgencyListDataSource;
import ku.cs.services.ComplaintCategoryListDataSource;
import ku.cs.services.DataSource;
import ku.cs.services.UserListDataSource;
import ku.cs.services.collection.DateComparator;
import ku.cs.services.collection.Filterer;
import ku.cs.services.collection.Sorter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class ShowUserDetailController implements Initializable {

    private User user;
    @FXML
    private TableView<User> userTable;
    @FXML
    private TableColumn<User, ImageView> profileImage;
    @FXML
    private TableColumn<User,String> name;
    @FXML
    private TableColumn<User, String> agency;
    @FXML
    private TableColumn<User,String> role;
    @FXML
    private TableColumn<User, String> lastOnline;
    @FXML
    private ComboBox<String> roleSelector;
    @FXML
    private DataSource<AgencyList> agencyData;
    @FXML
    private ComboBox<Agency> agencySelector;

    private AgencyList agencyList;
    private UserList userList;
    private DataSource<UserList> data;

    public void initData(User user) {
        this.user = user;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = new UserListDataSource("data", "user.csv");
        userList = data.readData();

        agencyData = new AgencyListDataSource("data", "agency.csv");
        agencyList = agencyData.readData();
        agencySelector.getItems().addAll(agencyList.getAgencyList());
        agencySelector.setOnAction(e -> filter());

        initColumn();
        loadData();
        initComboBox();
        initRoleSelector();

    }

    private void initColumn(){

        profileImage.setCellValueFactory(new PropertyValueFactory<>("profileImageView"));

        name.setCellValueFactory(new PropertyValueFactory<>("username"));
//        category.setCellValueFactory(cellData ->
//                new SimpleStringProperty(cellData.getValue().getComplaintCategory().getName()));
        agency.setCellValueFactory(cellData -> {
            Agency agency = cellData.getValue().getAgency();
            return new SimpleStringProperty(agency == null ? "ไม่มี" : agency.getName());
        });
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        lastOnline.setCellValueFactory(new PropertyValueFactory<>("loginTime"));
    }

    private void loadData() {
        ObservableList<User> dataTable = FXCollections.observableArrayList();

        Sorter sorter = new Sorter();
        sorter.sortByLow(userList, new Comparator<User>() {
            @Override
            public int compare(User o1, User o2) {
                return o1.getDate().compareTo(o2.getDate());
            }
        });

        dataTable.addAll(userList.getUserList());

        userTable.setItems(dataTable);
    }

    private void loadData(UserList roleUser) {
//        System.out.println(roleUser.getUserList());
        ObservableList<User> dataTable = FXCollections.observableArrayList();
        dataTable.addAll(roleUser.getUserList());
        userTable.setItems(dataTable);
    }

    private void initComboBox() {
        roleSelector.setButtonCell(new ListCell<String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty) ;
                if (empty || item == null) {
                    setText("ทุกบทบาท");
                } else {
                    setText(item);
                }
            }
        });

        agencySelector.setButtonCell(new ListCell<Agency>() {
            @Override
            protected void updateItem(Agency item, boolean empty) {
                super.updateItem(item, empty) ;
                if (empty || item == null) {
                    setText("เลือกหน่วยงาน");
                } else {
                    setText(item.getName());
                }
            }
        });


    }
    private void initRoleSelector() {
        List<String> roles = new ArrayList<>();
        roles.add("STUDENT");
        roles.add("TEACHER");
        roles.add("ADMIN");
        roleSelector.getItems().addAll(roles);
        roleSelector.setOnAction(e -> filter());
    }


    private void filter() {
        UserList filteredUserList = new UserList();
        filteredUserList = userList.filterBy(new Filterer<User>() {
            @Override
            public boolean filter(User o) {
                if (roleSelector.getValue() == null) return true;
                return o.getRole().toString().equals(roleSelector.getValue());
            }
        });

        filteredUserList = filteredUserList.filterBy(new Filterer<User>() {
            @Override
            public boolean filter(User o) {
                if (agencySelector.getValue() == null) return true;
                if(o.getAgency()==null){
                    return false;
                }
                return o.getAgency().getName().equals(agencySelector.getValue().getName());
            }
        });

        loadData(filteredUserList);


    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/dashboard.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane) ((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);

        Parent pane = loader.load();
        DashboardDetailController dashboardDetailController = loader.getController();
        dashboardDetailController.initData(user);
        borderPane.setCenter(pane);
    }
    @FXML
    public void handleClearSearchButton(ActionEvent actionEvent){
        roleSelector.setValue(null);
        agencySelector.setValue(null);
        loadData(userList);
    }
}

