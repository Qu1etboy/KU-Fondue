package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ku.cs.models.ComplaintCategory;
import ku.cs.models.ComplaintCategoryList;

public class AddCategoryDialogController {
    @FXML private TextField categoryNameTextField;

    ComplaintCategoryList complaintCategoryList;
    public void initData(ComplaintCategoryList complaintCategoryList) {
        this.complaintCategoryList = complaintCategoryList;
    }

    @FXML
    public void handleCancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleDoneButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        String categoryName = categoryNameTextField.getText();

        complaintCategoryList.addComplaintCategory(new ComplaintCategory(categoryName));

        stage.close();
    }


}
