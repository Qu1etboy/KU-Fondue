package ku.cs.controllers.complaintcategory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ku.cs.models.CategoryAttribute;

public class RenameChoiceDialogController {
    @FXML private TextField choiceNameTextField;
    private CategoryAttribute categoryAttribute;
    private String oldChoiceName;

    public void initData(String oldChoiceName, CategoryAttribute categoryAttribute) {
        this.categoryAttribute = categoryAttribute;
        this.oldChoiceName = oldChoiceName;
        choiceNameTextField.setText(oldChoiceName);
    }

    @FXML
    private void handleCancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleDoneButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        String newChoiceName = choiceNameTextField.getText();
        categoryAttribute.changeChoiceName(oldChoiceName, newChoiceName);

        stage.close();
    }
}
