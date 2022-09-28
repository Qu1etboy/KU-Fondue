package ku.cs.controllers.agency;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ku.cs.models.Agency;
import ku.cs.models.AgencyList;

public class RenameAgencyDialogController {
    @FXML
    private TextField agencyNameTextField;

    Agency agency;
    public void initData(Agency agency) {
        this.agency = agency;
    }

    @FXML
    public void handleCancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleDoneButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        String agencyName = agencyNameTextField.getText();

        agency.setName(agencyName);

        stage.close();
    }
}
