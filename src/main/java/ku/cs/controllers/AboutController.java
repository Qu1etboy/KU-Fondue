package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class AboutController {
    @FXML private ImageView image1;
    @FXML private ImageView image2;
    @FXML private ImageView image3;
    @FXML private ImageView image4;

    @FXML
    public void initialize(){
        String url = getClass().getResource("/ku/cs/images/test.jpg").toExternalForm();
        image1.setImage(new Image(url));
        image2.setImage(new Image(url));
        image3.setImage(new Image(url));
        image4.setImage(new Image(url));
    }

    @FXML private void handleNextButton (){

    }
    @FXML private void handlePreviousButton (){

    }
}
