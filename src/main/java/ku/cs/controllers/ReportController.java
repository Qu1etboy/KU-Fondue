package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import ku.cs.models.User;

import java.io.IOException;

public class ReportController {
    User user;

    public void initData(User user) {
        this.user = user;
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
