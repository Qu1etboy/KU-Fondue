package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.control.TableView;
import ku.cs.models.Complaint;

import java.io.IOException;

public class ComplaintDetailController {
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
    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/adminDashboard.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        borderPane.setCenter(loader.load());
    }
}
