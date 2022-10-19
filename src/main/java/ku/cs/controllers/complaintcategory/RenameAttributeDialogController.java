package ku.cs.controllers.complaintcategory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ku.cs.models.CategoryAttribute;

public class RenameAttributeDialogController {
    @FXML private TextField attributeNameTextField;

    private CategoryAttribute categoryAttribute;

    public void initData(CategoryAttribute categoryAttribute) {
        this.categoryAttribute = categoryAttribute;
        attributeNameTextField.setText(categoryAttribute.getNameAttribute());
    }

    @FXML
    private void handleCancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleDoneButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        String attributeName = attributeNameTextField.getText().replaceAll(",", " ");

        categoryAttribute.setNameAttribute(attributeName);

        stage.close();
    }
}
