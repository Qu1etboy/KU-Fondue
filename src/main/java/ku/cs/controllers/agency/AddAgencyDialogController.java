package ku.cs.controllers.agency;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import ku.cs.models.Agency;
import ku.cs.models.AgencyList;
import ku.cs.models.ComplaintCategory;
import ku.cs.models.ComplaintCategoryList;

public class AddAgencyDialogController {
    @FXML
    private TextField agencyNameTextField;

    AgencyList agencyList;
    public void initData(AgencyList agencyList) {
        this.agencyList = agencyList;
    }

    @FXML
    private void handleCancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleDoneButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        String agencyName = agencyNameTextField.getText();

        agencyList.addAgency(new Agency(agencyName));

        stage.close();
    }
}
