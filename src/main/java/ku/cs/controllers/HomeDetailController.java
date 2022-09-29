package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import ku.cs.datastructure.ListMap;
import ku.cs.datastructure.Pair;
import ku.cs.models.*;
import ku.cs.services.ComplaintCategoryListDataSource;
import ku.cs.services.ComplaintListDataSource;
import ku.cs.services.DataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private List <Image> imageList;



    @FXML
    public void initData(User user){
        data = new ComplaintListDataSource("data","complaint.csv") ;
        complaintList =  data.readData();
        data2 = new ComplaintCategoryListDataSource("data","complaint_category.csv");
        complaintCategoryList = data2.readData();

        textFieldList = new ArrayList<>();
        comboBoxList = new ArrayList<>();
        questionAnswer = new ListMap<>();
        imageList = new ArrayList<>();
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


    public void handleUploadImageButton(ActionEvent event){
        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        imageList.add(new Image(file.toURI().toString()));
//        if (file != null){
//            try {
//                // CREATE FOLDER IF NOT EXIST
//                File destDir = new File("images");
//                if (!destDir.exists()) destDir.mkdirs();
//                // RENAME FILE
//                String[] fileSplit = file.getName().split("\\.");
//                String filename = LocalDate.now() + "_"+System.currentTimeMillis() + "."
//                        + fileSplit[fileSplit.length - 1];
//                Path target = FileSystems.getDefault().getPath(
//                        destDir.getAbsolutePath()+System.getProperty("file.separator")+filename
//                );
//                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
//                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
//                // SET NEW FILE PATH TO IMAGE
//               imageList.add((new Image(target.toUri().toString()));
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
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
                button.setOnAction(e -> handleUploadImageButton(e));
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
        for(Image image : imageList){
            System.out.println(image.getUrl());
            File file = new File(image.getUrl().substring(5));
            if (file != null){
                try {
                    // CREATE FOLDER IF NOT EXIST
                    File destDir = new File("images");
                    if (!destDir.exists()) destDir.mkdirs();
                    // RENAME FILE
                    String[] fileSplit = file.getName().split("\\.");
                    String filename = LocalDate.now() + "_"+System.currentTimeMillis() + "."
                            + fileSplit[fileSplit.length - 1];
                    Path target = FileSystems.getDefault().getPath(
                            destDir.getAbsolutePath()+System.getProperty("file.separator")+filename
                    );
                    // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                    Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                    // SET NEW FILE PATH TO IMAGE
                   sendComplaint.addImageAnswer((new Image(target.toUri().toString())));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
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
