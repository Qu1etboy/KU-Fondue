package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import ku.cs.models.AppProblem;
import ku.cs.models.AppProblemList;
import ku.cs.services.AppProblemDataSource;
import ku.cs.services.DataSource;


import java.io.IOException;


public class HelpController {

    private DataSource<AppProblemList> data;

    private AppProblemList appProblemList;

    private  String appProblem;

    @FXML private TextField reportProblemTextField;

    public void initialize() {
        data = new AppProblemDataSource("data","app_problem.csv");
        appProblemList = data.readData();
    }

    @FXML
    public void handleSubmitButton(ActionEvent actionEvent) throws IOException {
        appProblem = reportProblemTextField.getText();
        if(appProblem.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("กรุณากรอกข้อมูล");
            alert.show();
        }else {
            appProblemList.addAppProblem(new AppProblem(appProblem));
            data.writeData(appProblemList);
            reportProblemTextField.clear();
        }

    }
}
