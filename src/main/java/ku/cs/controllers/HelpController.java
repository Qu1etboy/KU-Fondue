package ku.cs.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class HelpController {


//    @FXML private TextField reportProblemTextField;

    public void initialize() {

    }

    @FXML
    private void handleHomePageButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/help/homeDetail.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);

        Parent pane = loader.load();
        borderPane.setCenter(pane);
    }

    @FXML
    private void handleDashboardPageButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/help/dashboardDetail.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);

        Parent pane = loader.load();
        borderPane.setCenter(pane);
    }

    @FXML
    private void handleSettingPageButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/help/settingDetail.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);

        Parent pane = loader.load();
        borderPane.setCenter(pane);
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/help/help.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);

        Parent pane = loader.load();
        borderPane.setCenter(pane);
    }




//    public void handleSubmitButton(ActionEvent actionEvent) throws IOException {
//        appProblem = reportProblemTextField.getText();
//        if(appProblem.isEmpty()){
//            Alert alert = new Alert(Alert.AlertType.ERROR);
//            alert.setContentText("กรุณากรอกข้อมูล");
//            alert.show();
//        }else {
//            appProblemList.addAppProblem(new AppProblem(appProblem));
//            data.writeData(appProblemList);
//            reportProblemTextField.clear();
//        }
//
//    }
}
