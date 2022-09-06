package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ku.cs.models.AppProblem;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserListDataSource;

import java.io.IOException;


public class HelpController {
    private String reportProblem;


    @FXML private TextField reportProblemTextField;

    public void initialize() {

    }

    @FXML
    public void handleSubmitButton(ActionEvent actionEvent) throws IOException {
        String input = reportProblemTextField.getText();

        reportProblemTextField.clear();

    }
}
