package ku.cs.controllers.complaintcategory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ku.cs.models.ComplaintCategory;
import ku.cs.models.ComplaintCategoryList;

public class RenameCategoryDialogController {
    @FXML private TextField categoryNameTextField;

    ComplaintCategory complaintCategory;

    public void initData(ComplaintCategory complaintCategory) {
        this.complaintCategory = complaintCategory;
        categoryNameTextField.setText(complaintCategory.getName());
    }

    @FXML
    private void handleCancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleDoneButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        String categoryName = categoryNameTextField.getText();

        complaintCategory.setName(categoryName);

        stage.close();
    }


}
