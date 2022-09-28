package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ku.cs.datastructure.ListMap;
import ku.cs.datastructure.Pair;
import ku.cs.models.*;
import ku.cs.services.ComplaintCategoryListDataSource;
import ku.cs.services.ComplaintListDataSource;
import ku.cs.services.DataSource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeDetailController {
    @FXML
    private Label nameLabel;
    private User user;

    @FXML
    private ComboBox<ComplaintCategory> categorySelector;
    @FXML
    private VBox formContainer;
    @FXML
    private TextArea detailTextArea = new TextArea();
    @FXML
    private TextField topicTextField = new TextField();

    private DataSource<ComplaintList> data;
    private ComplaintList complaintList;

    private DataSource<ComplaintCategoryList> data2;
    private ComplaintCategoryList  complaintCategoryList;
    private ComplaintCategory complaintCategory;
    private List<TextField> textFieldList;
    private List<ComboBox> comboBoxList;
    private ListMap<String,String> questionAnswer ;


    @FXML
    public void initData(User user){
        data = new ComplaintListDataSource("data","complaint.csv") ;
        complaintList =  data.readData();
        data2 = new ComplaintCategoryListDataSource("data","complaint_category.csv");
        complaintCategoryList = data2.readData();

        textFieldList = new ArrayList<>();
        comboBoxList = new ArrayList<>();
        questionAnswer = new ListMap<>();
        categorySelector.getItems().addAll(complaintCategoryList.getComplaintCategoryList());
        categorySelector.setOnAction(e -> handleSelection());
//        formContainer.getChildren().add(new Label("1."));
//        formContainer.getChildren().add(new TextField());
//        formContainer.getChildren().add(new Label("2."));
//        formContainer.getChildren().add(new TextField());
//        formContainer.getChildren().add(new Label("3."));
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
        formContainer.getChildren().add(topicLabel);
        formContainer.getChildren().add(topicTextField);

        // System.out.println(complaintCategory.getCategoryAttributeList());
        for (CategoryAttribute categoryAttribute : complaintCategory.getCategoryAttributeList()) {
            Label label = new Label(categoryAttribute.getNameAttribute());
            formContainer.getChildren().add(label);
            if (categoryAttribute.getTypeAttribute().equals("textfield")) {
                TextField textField = new TextField();
                formContainer.getChildren().add(textField);
                textFieldList.add(textField);
                textField.setId(categoryAttribute.getNameAttribute());
            } else if (categoryAttribute.getTypeAttribute().equals("combobox")) {
                ComboBox<String> selector = new ComboBox<>();
                selector.getItems().addAll(categoryAttribute.getInputData());
                formContainer.getChildren().add(selector);
                comboBoxList.add(selector);
                selector.setId(categoryAttribute.getNameAttribute());
            } else {
                Button button = new Button("Upload Image");
                formContainer.getChildren().add(button);
            }
        }

        Label detailLabel = new Label("รายละเอียด");
//        TextArea detailTextArea ;
        formContainer.getChildren().add(detailLabel);
        formContainer.getChildren().add(detailTextArea);

        Button sendButton = new Button("Send");
        sendButton.setOnAction(e->handleSendButton());
        formContainer.getChildren().add(sendButton);
    }

    public void handleSendButton(){

        for(TextField textField : textFieldList){
            questionAnswer.put(textField.getId(),textField.getText());
        }

        for(ComboBox comboBox: comboBoxList){
            questionAnswer.put(comboBox.getId(),(String) comboBox.getValue());
        }

//        for (String q : questionAnswer.keyList()) {
//            System.out.println(q + " => " + questionAnswer.get(q));
//        }

        complaintCategory = categorySelector.getSelectionModel().getSelectedItem();
        Complaint sendComplaint = new Complaint(user, topicTextField.getText(), detailTextArea.getText().replaceAll("\n", " "), complaintCategory.getName());
        for (String q : questionAnswer.keyList()) {
            sendComplaint.addQuestionAnswer(q, questionAnswer.get(q));
        }
        complaintList.addComplaint(sendComplaint);
        data.writeData(complaintList);

        topicTextField.clear();
        detailTextArea.clear();
        formContainer.getChildren().clear();
        comboBoxList.clear();
        textFieldList.clear();
        questionAnswer.clear();
        System.out.println("send test");
    }
}
