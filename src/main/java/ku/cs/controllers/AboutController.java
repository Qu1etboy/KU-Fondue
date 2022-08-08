package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class AboutController {
    @FXML private ImageView image1;
    @FXML private ImageView image2;
    @FXML private ImageView image3;
    @FXML private ImageView image4;

    @FXML
    public void initialize(){
//        String ong = getClass().getResource("/ku/cs/images/ong.jpg").toExternalForm();
//        image1.setImage(new Image(ong));
//        String arm = getClass().getResource("/ku/cs/images/arm.jpg").toExternalForm();
//        image2.setImage(new Image(arm));
//        String non = getClass().getResource("/ku/cs/images/non.jpg").toExternalForm();
//        image3.setImage(new Image(non));
//        String ice = getClass().getResource("/ku/cs/images/ice.jpg").toExternalForm();
//        image4.setImage(new Image(ice));
    }

    @FXML private void handleNextButton (ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/about2.fxml"));
        BorderPane borderPane = (BorderPane) ((Node) actionEvent.getSource()).getScene().getRoot();
        borderPane.setCenter(loader.load());
    }
    @FXML private void handlePreviousButton (ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/about1.fxml"));
        BorderPane borderPane = (BorderPane) ((Node) actionEvent.getSource()).getScene().getRoot();
        borderPane.setCenter(loader.load());
    }
}
