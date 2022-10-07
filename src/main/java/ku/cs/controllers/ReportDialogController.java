package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import ku.cs.models.Complaint;
import ku.cs.models.Report;
import ku.cs.models.ReportList;
import ku.cs.models.User;
import ku.cs.services.DataSource;
import ku.cs.services.ReportListDataSource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReportDialogController {

    private User user;
    private Complaint reportComplaint;

    @FXML private CheckBox isComplaint;
    @FXML private CheckBox isUser;
    @FXML private TextArea detailTextArea;
    @FXML private VBox typeContainer;

    private List<CheckBox> typeCheckboxes;

    private ReportList reportList;
    private DataSource<ReportList> reportListDataSource;

    public void initData(User user, Complaint reportComplaint) {
        this.user = user;
        this.reportComplaint = reportComplaint;

        reportListDataSource = new ReportListDataSource("data", "report.csv");
        reportList = reportListDataSource.readData();

        typeCheckboxes = new ArrayList<>();

        // set default select
        isComplaint.setSelected(true);
        showComplaintReportType();
    }

    @FXML
    private void handleCancelButton(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    @FXML
    private void handleSelectComplaint() {
        isUser.setSelected(false);
        isComplaint.setSelected(true);
        showComplaintReportType();
    }

    @FXML
    private void handleSelectUser() {
        isComplaint.setSelected(false);
        isUser.setSelected(true);
        showUserReportType();
    }

    private void showComplaintReportType() {
        typeContainer.getChildren().clear();
        typeCheckboxes = new ArrayList<>();
        String[] types = { "เนื้อหาไม่เหมาะสม", "ข่าวปลอม", "เนื้อหาล่อแหลม", "เนื้อหามีความรุนแรง" };
        for (String type : types) {
            CheckBox typeCheckBox = new CheckBox();
            typeCheckBox.setText(type);
            typeCheckBox.setOnAction(e -> handleSelectType(typeCheckBox));
            typeCheckboxes.add(typeCheckBox);
            typeContainer.getChildren().add(typeCheckBox);
        }
        // make the first one always select to prevent user not selecting anything
        typeCheckboxes.get(0).setSelected(true);
    }

    private void showUserReportType() {
        typeContainer.getChildren().clear();
        typeCheckboxes = new ArrayList<>();
        String[] types = { "ผู้ใช้ใช้คําไม่สุภาพ", "ผู้ใช้รายงานเนื้อหาไม่เกี่ยวข้อง" };
        for (String type : types) {
            CheckBox typeCheckBox = new CheckBox();
            typeCheckBox.setText(type);
            typeCheckBox.setOnAction(e -> handleSelectType(typeCheckBox));
            typeCheckboxes.add(typeCheckBox);
            typeContainer.getChildren().add(typeCheckBox);
        }
        // make the first one always select to prevent user not selecting anything
        typeCheckboxes.get(0).setSelected(true);
    }

    private void handleSelectType(CheckBox selectedType) {
        for (CheckBox type : typeCheckboxes) {
            if (!type.equals(selectedType)) {
                type.setSelected(false);
            }
        }
        selectedType.setSelected(true);
    }

    @FXML
    private void handleSendButton(ActionEvent actionEvent) {
        if (!isComplaint.isSelected() && !isUser.isSelected()) {
            return;
        }

//        if (detailTextArea.getText().isEmpty()) {
//            return;
//        }

        String selectedType = null;
        for (CheckBox type : typeCheckboxes) {
            if (type.isSelected()) {
                selectedType = type.getText();
            }
        }

        if (selectedType == null) {
            return;
        }

        Report report;
        if (isComplaint.isSelected()) {
            report = new Report(reportComplaint, selectedType, detailTextArea.getText());
        } else {
            report = new Report(reportComplaint.getUser(), selectedType, detailTextArea.getText());
        }

        // write to file
        reportList.addReport(report);
        reportListDataSource.writeData(reportList);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

}
