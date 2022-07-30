package ku.cs.models;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Router {

    /*
    * This method use to go between scene get path and actionEvent
    * as a parameter, actionEvent use to get the current stage
    * */
    public static void switchScene(String path, ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(Router.class.getResource("/ku/cs/view/" + path));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();

        stage.setScene(scene);
        stage.show();
    }
}
