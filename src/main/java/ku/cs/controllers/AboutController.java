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
        String ong = getClass().getResource("/ku/cs/images/ong.jpg").toExternalForm();
        image1.setImage(new Image(ong));
        String arm = getClass().getResource("/ku/cs/images/arm.jpg").toExternalForm();
        image2.setImage(new Image(arm));
        String non = getClass().getResource("/ku/cs/images/non.jpg").toExternalForm();
        image3.setImage(new Image(non));
        String ice = getClass().getResource("/ku/cs/images/ice.jpg").toExternalForm();
        image4.setImage(new Image(ice));
    }

    @FXML private void handleNextButton (){

    }
    @FXML private void handlePreviousButton (){

    }
}
