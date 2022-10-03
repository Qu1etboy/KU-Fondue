package ku.cs.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.util.Duration;
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
    public void initData(User user) {
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

        this.user = user;
        nameLabel.setText("ยินดีต้อนรับ " + user.getUsername());

    }


    public void handleUploadImageButton(ActionEvent event, FlowPane flowPane) {
        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        Image image = new Image(file.toURI().toString());
        imageList.add(image);

        String[] fileSplit = file.toURI().toString().split("/");

        HBox box = new HBox();

        box.getChildren().add(new FontAwesomeIconView(FontAwesomeIcon.FILE_IMAGE_ALT));
        box.getChildren().add(new Label(fileSplit[fileSplit.length - 1]));
        box.setPrefWidth(Region.USE_COMPUTED_SIZE);
        box.setPrefHeight(50);
        box.setMaxWidth(200);
        box.setPadding(new Insets(3, 10, 3, 10));
        box.getStyleClass().add("border-box");
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);

        Button removeImage = new Button("X");
        removeImage.setOnAction(e -> handleRemoveImage(image, box, flowPane));
        removeImage.getStyleClass().add("transparent-button");
        box.getChildren().add(removeImage);

        flowPane.getChildren().add(box);

    }

    private void handleRemoveImage(Image image, HBox box, FlowPane flowPane) {
        imageList.remove(image);
        flowPane.getChildren().remove(box);

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
                FlowPane flowPane = new FlowPane();
                flowPane.setHgap(10);
                flowPane.setVgap(10);
                Button button = new Button("Upload Image");
                button.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.UPLOAD));
                button.setOnAction(e -> handleUploadImageButton(e, flowPane));
                VBox vBox = new VBox(flowPane, button);
                vBox.setSpacing(10);
                formContainer.getChildren().add(vBox);
            }
        }

        Label detailLabel = new Label("รายละเอียด");
//        TextArea detailTextArea ;
        formContainer.getChildren().add(detailLabel);
        formContainer.getChildren().add(detailTextArea);

        Button sendButton = new Button("Send");
        sendButton.setGraphic(new FontAwesomeIconView(FontAwesomeIcon.PAPER_PLANE));
        sendButton.setOnAction(e->handleSendButton());
        formContainer.getChildren().add(sendButton);

        FadeTransition fade = new FadeTransition();
        fade.setNode(formContainer);
        fade.setDuration(Duration.millis(1000));
        fade.setFromValue(0);
        fade.setToValue(1);

        TranslateTransition transition = new TranslateTransition();
        transition.setNode(formContainer);
        transition.setFromY(50);
        transition.setToY(0);
        transition.setDuration(Duration.millis(1000));

        ParallelTransition pt = new ParallelTransition(transition, fade);

        pt.play();
    }

    public void handleSendButton() {

        boolean valid = !topicTextField.getText().isEmpty() && !detailTextArea.getText().isEmpty();

        for(TextField textField : textFieldList) {
            if (textField.getText().isEmpty()) {
                valid = false;
                continue;
            }
            questionAnswer.put(textField.getId(),textField.getText());
        }

        for(ComboBox comboBox: comboBoxList){
            if (comboBox.getValue() == null) {
                valid = false;
                continue;
            }
            questionAnswer.put(comboBox.getId(),(String) comboBox.getValue());
        }

        if (!valid) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("กรุณากรอกข้อมูลให้ครบ");
            alert.show();
            return;
        }

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

        clearInput();
    }

    public void clearInput() {
        topicTextField.clear();
        detailTextArea.clear();
        formContainer.getChildren().clear();
        comboBoxList.clear();
        textFieldList.clear();
        questionAnswer.clear();
    }
}
