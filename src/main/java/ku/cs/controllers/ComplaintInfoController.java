package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ku.cs.datastructure.ListMap;
import ku.cs.models.Complaint;
import ku.cs.models.ComplaintList;
import ku.cs.models.User;
import ku.cs.services.ComplaintListDataSource;
import ku.cs.services.DataSource;

import java.io.IOException;

public class ComplaintInfoController {

    User user;
    Complaint complaint;
    DataSource<ComplaintList> complaintListDataSource;
    ComplaintList complaintList;

    @FXML private VBox contentContainer;
    @FXML private Label voteLabel;

    public void initData(User user, Complaint complaint) {
        this.user = user;
        this.complaint = complaint;
        complaintListDataSource = new ComplaintListDataSource("data", "complaint.csv");
        complaintList = complaintListDataSource.readData();

        showComplaintData(complaint);
        contentContainer.setSpacing(10);
    }

    private void showComplaintData(Complaint complaint) {
        Label topic = new Label(complaint.getTopic());
        topic.getStyleClass().add("title");
        Label detail = new Label(complaint.getDetail());
        detail.setWrapText(true);
        contentContainer.getChildren().add(topic);

        contentContainer.getChildren().add(new Label("หมวดหมู่ : " + complaint.getComplaintCategoryName()));

        voteLabel.setText(Integer.toString(complaint.getVote()));

        for (String question : complaint.getAdditionalDetail().keyList()) {

            if (question.isEmpty()) continue;
            Label qanda = new Label(question + " : " + complaint.getAdditionalDetail().get(question));

            contentContainer.getChildren().add(qanda);

        }

        contentContainer.getChildren().add(new Label("รายละเอียด"));
        contentContainer.getChildren().add(detail);

    }

    @FXML
    private void handleVoteButton() {
        complaint.addUserVote(user);
        voteLabel.setText(Integer.toString(complaint.getVote()));

        complaintList.updateComplaint(complaint);
        complaintListDataSource.writeData(complaintList);
    }


    @FXML
    private void handleBackButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/complaint.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        Parent pane = loader.load();
        ComplaintDetailController controller = loader.getController();
        controller.initData(user);
        borderPane.setCenter(pane);
    }
}
