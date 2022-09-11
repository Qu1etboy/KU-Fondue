package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ku.cs.models.CategoryAttribute;

public class AddChoiceDialogController {
    @FXML private Label infoLabel;
    @FXML private TextField choiceNameTextField;
    @FXML private Label attributeNameLabel;

    private CategoryAttribute categoryAttribute;

    public void initData(CategoryAttribute categoryAttribute) {
        this.categoryAttribute = categoryAttribute;
        infoLabel.setText("ใส่ชื่อ" + categoryAttribute.getNameAttribute() + "ที่ต้องการจะเพิ่ม");
        attributeNameLabel.setText("ชื่อ" + categoryAttribute.getNameAttribute());
    }

    @FXML
    public void handleDoneButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        String choiceName = choiceNameTextField.getText();

        categoryAttribute.addInputData(choiceName);

        stage.close();
    }

    @FXML
    public void handleCancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
