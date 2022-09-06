package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ku.cs.models.*;
import ku.cs.services.ComplaintCategoryListDataSource;
import ku.cs.services.ComplaintListDataSource;
import ku.cs.services.DataSource;

public class HomeDetailController {
    @FXML
    private Label nameLabel;
    private User user;
    @FXML
    private TextArea complaintDetail;

    @FXML
    private ComboBox<ComplaintCategory> categorySelector;
    @FXML
    private VBox formContainer;
    private DataSource<ComplaintList> data;
    private ComplaintList complaintList;

    private DataSource<ComplaintCategoryList> data2;
    private ComplaintCategoryList  complaintCategoryList;
    private ComplaintCategory complaintCategory;
    @FXML
    public void initData(User user){
        data = new ComplaintListDataSource("data","complaint.csv") ;
        complaintList = data.readData();
        data2 = new ComplaintCategoryListDataSource("data","complaint_category.csv");
        complaintCategoryList = data2.readData();

        categorySelector.getItems().addAll(complaintCategoryList.getComplaintCategoryList());
        categorySelector.setOnAction(e -> handleSelection());
//        formContainer.getChildren().add(new Label("1."));
//        formContainer.getChildren().add(new TextField());
//        formContainer.getChildren().add(new Label("2."));
//        formContainer.getChildren().add(new TextField());
//        formContainer.getChildren().add(new Label("3."));
//        formContainer.getChildren().add(new TextField());
//        formContainer.getChildren().add(new Label("4."));
//        formContainer.getChildren().add(new TextField());

        this.user = user;
        nameLabel.setText(user.getUsername());
    }


    @FXML
    public void handleUploadImageButton(){

    }

    public void handleSelection() {
        complaintCategory = categorySelector.getSelectionModel().getSelectedItem();
        formContainer.getChildren().clear();
        formContainer.setSpacing(10);


        Label topicLabel = new Label("หัวข้อ");
        TextField topicTextField = new TextField();
        formContainer.getChildren().add(topicLabel);
        formContainer.getChildren().add(topicTextField);

        // System.out.println(complaintCategory.getCategoryAttributeList());
        for (CategoryAttribute categoryAttribute : complaintCategory.getCategoryAttributeList()) {
            Label label = new Label(categoryAttribute.getNameAttribute());
            formContainer.getChildren().add(label);
            if (categoryAttribute.getTypeAttribute().equals("textfield")) {
                TextField textField = new TextField();
                formContainer.getChildren().add(textField);
            } else if (categoryAttribute.getTypeAttribute().equals("combobox")) {
                ComboBox<String> selector = new ComboBox<>();
                selector.getItems().addAll(categoryAttribute.getInputData());
                formContainer.getChildren().add(selector);
            } else {
                Button button = new Button("Upload Image");
                formContainer.getChildren().add(button);
            }
        }

        Label detailLabel = new Label("รายละเอียด");
        TextArea detailTextArea = new TextArea();
        formContainer.getChildren().add(detailLabel);
        formContainer.getChildren().add(detailTextArea);

        Button sendButton = new Button("Send");
        formContainer.getChildren().add(sendButton);
    }
    @FXML
    public void handleSendButton(){
        String detail = complaintDetail.getText();
        complaintList.addComplaint(new Complaint(user,"test",detail));
        data.writeData(complaintList);

        System.out.println("send test");
    }
}
