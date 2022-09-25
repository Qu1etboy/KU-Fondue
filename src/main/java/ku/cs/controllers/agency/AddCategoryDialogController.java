package ku.cs.controllers.agency;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import ku.cs.models.*;

public class AddCategoryDialogController {
    @FXML
    private ListView<ComplaintCategory> categoryListView;

    ComplaintCategoryList complaintCategoryList;
    ComplaintCategory complaintCategory;
    Agency agency;

    public void initData(ComplaintCategoryList complaintCategoryList, Agency agency) {
        this.complaintCategoryList = complaintCategoryList;
        this.agency = agency;

        categoryListView.setCellFactory(param -> new ListCell<ComplaintCategory>() {
            @Override
            protected void updateItem(ComplaintCategory item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        categoryListView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ComplaintCategory>() {
            @Override
            public void changed(ObservableValue<? extends ComplaintCategory> observableValue, ComplaintCategory category, ComplaintCategory t1) {
                complaintCategory = t1;
            }
        }
    );

        showCategoryListView(complaintCategoryList);
    }

    private void showCategoryListView(ComplaintCategoryList complaintCategoryList) {
        categoryListView.getItems().setAll(complaintCategoryList.getComplaintCategoryList());
    }

    @FXML
    private void handleCancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleDoneButton(ActionEvent actionEvent) {
        if (complaintCategory == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Please select complaint category to add");
            alert.show();
            return;
        }

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();

        agency.addManagedCategory(complaintCategory);

        stage.close();
    }
}
