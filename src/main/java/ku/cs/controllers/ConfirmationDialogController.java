package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ConfirmationDialogController {
    @FXML private Label label;
    private boolean confirm;

    public void initData(String text) {
        label.setText(text);
    }

    @FXML
    private void handleCancelButton(ActionEvent actionEvent) {
        confirm = false;
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleOkButton(ActionEvent actionEvent) {
        confirm = true;
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    public boolean getConfirm() {
        return confirm;
    }

}
