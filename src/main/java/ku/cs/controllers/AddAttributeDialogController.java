package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;

public class AddAttributeDialogController {

    @FXML private CheckBox textCheckBox;
    @FXML private CheckBox choiceCheckBox;
    @FXML private CheckBox imageCheckBox;
    @FXML
    public void handleTextCheckBox() {
        choiceCheckBox.setSelected(false);
        imageCheckBox.setSelected(false);
    }

    @FXML
    public void handleChoiceCheckBox() {
        textCheckBox.setSelected(false);
        imageCheckBox.setSelected(false);
    }

    @FXML
    public void handleImageCheckBox() {
        textCheckBox.setSelected(false);
        choiceCheckBox.setSelected(false);
    }
}
