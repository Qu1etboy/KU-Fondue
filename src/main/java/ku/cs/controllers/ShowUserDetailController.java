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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import ku.cs.models.Agency;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserListDataSource;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ShowUserDetailController implements Initializable {

    User user;
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

    private UserList userList;
    private DataSource<UserList> data;

    public void initData(User user) {
        this.user = user;
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        data = new UserListDataSource("data", "user.csv");
        userList = data.readData();

        initColumn();
        loadData();
    }

    private void initColumn(){

        profileImage.setCellValueFactory(new PropertyValueFactory<>("profileImageView"));
        name.setCellValueFactory(new PropertyValueFactory<>("username"));
//        category.setCellValueFactory(cellData ->
//                new SimpleStringProperty(cellData.getValue().getComplaintCategory().getName()));
        agency.setCellValueFactory(cellData -> {
            Agency agency = cellData.getValue().getAgency();
            return new SimpleStringProperty(agency == null ? "null" : agency.getName());
        });
        role.setCellValueFactory(new PropertyValueFactory<>("role"));
        lastOnline.setCellValueFactory(new PropertyValueFactory<>("lastOnline"));
    }
    private void loadData() {
        ObservableList<User> dataTable = FXCollections.observableArrayList();
        dataTable.addAll(userList.getUserList());


        userTable.setItems(dataTable);

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
}

