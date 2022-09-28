package ku.cs.controllers.complaintcategory;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ku.cs.models.CategoryAttribute;
import ku.cs.models.CategoryAttributeList;
import ku.cs.models.ComplaintCategory;
import ku.cs.services.CategoryAttributeListDataSource;
import ku.cs.services.DataSource;

public class AddAttributeDialogController {

    @FXML private CheckBox textCheckBox;
    @FXML private CheckBox choiceCheckBox;
    @FXML private CheckBox imageCheckBox;
    @FXML private TextField attributeNameTextField;
    private CategoryAttributeList categoryAttributeList;
    private ComplaintCategory complaintCategory;
    private String type;

    public void initData(ComplaintCategory complaintCategory, CategoryAttributeList categoryAttributeList) {
        this.complaintCategory = complaintCategory;
        this.categoryAttributeList = categoryAttributeList;
        type = null;
    }

    @FXML
    public void handleTextCheckBox() {
        choiceCheckBox.setSelected(false);
        imageCheckBox.setSelected(false);
        type = "textfield";
    }

    @FXML
    public void handleChoiceCheckBox() {
        textCheckBox.setSelected(false);
        imageCheckBox.setSelected(false);
        type = "combobox";
    }

    @FXML
    public void handleImageCheckBox() {
        textCheckBox.setSelected(false);
        choiceCheckBox.setSelected(false);
        type = "image";
    }

    @FXML
    public void handleCancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleDoneButton(ActionEvent actionEvent) {

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        String attributeName = attributeNameTextField.getText();

        if (type == null || attributeName.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("กรุณากรอกข้อมูลให้ครบท้วน");
            alert.show();
            return;
        }

        CategoryAttribute categoryAttribute = new CategoryAttribute(complaintCategory.getId(), attributeName, type);

        complaintCategory.addCategoryAttribute(categoryAttribute);
        categoryAttributeList.addCategoryAttribute(categoryAttribute);

        stage.close();
    }
}
