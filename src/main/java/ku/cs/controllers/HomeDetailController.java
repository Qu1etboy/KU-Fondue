package ku.cs.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import ku.cs.datastructure.ListMap;
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
    private VBox background;
    @FXML
    private ComboBox<ComplaintCategory> categorySelector;
    @FXML
    private VBox formContainer;
    @FXML
    private TextArea detailTextArea = new TextArea();
    @FXML
    private TextField topicTextField = new TextField();

    private DataSource<ComplaintList> complaintData;
    private ComplaintList complaintList;

    private DataSource<ComplaintCategoryList> categoryData;
    private ComplaintCategoryList  complaintCategoryList;
    private ComplaintCategory complaintCategory;
    private List<TextField> textFieldList;
    private List<ComboBox> comboBoxList;
    private ListMap<String,String> questionAnswer ;
    private List <Image> imageList;

    // mainApplication button
    private Button homeButton;
    private Button dashboardButton;


    @FXML
    public void initData(User user, Button homeButton, Button dashboardButton) {
        complaintData = new ComplaintListDataSource("data","complaint.csv") ;
        complaintList =  complaintData.readData();
        categoryData = new ComplaintCategoryListDataSource("data","complaint_category.csv");
        complaintCategoryList = categoryData.readData();

        textFieldList = new ArrayList<>();
        comboBoxList = new ArrayList<>();
        questionAnswer = new ListMap<>();
        imageList = new ArrayList<>();
        categorySelector.getItems().addAll(complaintCategoryList.getComplaintCategoryList());
        categorySelector.setOnAction(e -> handleSelection());

        this.user = user;
        nameLabel.setText("ยินดีต้อนรับ " + user.getUsername());

        this.homeButton = homeButton;
        this.dashboardButton = dashboardButton;

    }


    private void handleUploadImageButton(ActionEvent event, FlowPane flowPane) {
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
        Label fileNameLabel = new Label(fileSplit[fileSplit.length - 1]);
        fileNameLabel.setMaxWidth(100);

        box.getChildren().add(new FontAwesomeIconView(FontAwesomeIcon.FILE_IMAGE_ALT));
        box.getChildren().add(fileNameLabel);
        box.setPrefWidth(Region.USE_COMPUTED_SIZE);
        box.setPrefHeight(50);
        box.setMaxWidth(200);
        box.setPadding(new Insets(3, 10, 3, 10));
        box.getStyleClass().add("border-box");
        box.setAlignment(Pos.CENTER);
        box.setSpacing(10);

        Button removeImage = new Button("X");
        removeImage.setOnAction(e -> handleRemoveImage(image, box, flowPane));
        removeImage.getStyleClass().add("transparent-danger-button");
        box.getChildren().add(removeImage);

        flowPane.getChildren().add(box);

//        System.out.println(fileNameLabel.getText());

    }

    private void handleRemoveImage(Image image, HBox box, FlowPane flowPane) {
        imageList.remove(image);
        flowPane.getChildren().remove(box);

    }


    private void handleSelection() {
        complaintCategory = categorySelector.getSelectionModel().getSelectedItem();
        formContainer.getChildren().clear();
        formContainer.setSpacing(10);

        detailTextArea.setWrapText(true);


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
        sendButton.setOnAction(e->handleSendButton(e));
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

    private void handleSendButton(ActionEvent actionEvent) {

        boolean valid = !topicTextField.getText().isEmpty() && !detailTextArea.getText().isEmpty();

        for(TextField textField : textFieldList) {
            if (textField.getText().isEmpty()) {
                valid = false;
                continue;
            }
            // replace "," because it is being use as a delimiter
            questionAnswer.put(textField.getId(),textField.getText().replaceAll(",", " "));
        }

        for(ComboBox comboBox: comboBoxList){
            if (comboBox.getValue() == null) {
                valid = false;
                continue;
            }
            // replace "," because it is being use as a delimiter
            questionAnswer.put(comboBox.getId(),((String) comboBox.getValue()).replaceAll(",", " "));
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
                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setContentText("หาไฟล์รูปภาพไม่เจอ (ชื่อไฟล์ห้ามมีช่องว่าง)");
                   alert.show();
                   return;
                    // e.printStackTrace();
                }
            }
        }
        complaintData.writeData(complaintList);
        clearInput();
        try {
            successPage(actionEvent);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void clearInput() {
        topicTextField.clear();
        detailTextArea.clear();
        formContainer.getChildren().clear();
        comboBoxList.clear();
        textFieldList.clear();
        questionAnswer.clear();
        background.getChildren().clear();

    }

    private void successPage(ActionEvent actionEvent) throws IOException {

        Label successText = new Label("ส่งคำร้องสำเร็จ!");
        successText.getStyleClass().add("logo-title");
        Label backText = new Label("กด \"Back\" หากต้องการจะส่งคำร้องเพิ่มเติม หรือกด \"Complaint\" หากต้องการจะดูเรื่องร้องเรียน");
        backText.setWrapText(true);
        Button backButton = new Button("Back");
        Button detailButton = new Button("Complaint");
        HBox hBox = new HBox();
        backButton.setOnAction(e -> {
            try {
                handleBackButton(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        detailButton.setOnAction(e-> {
            try {
                handleDetailButton(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });
        VBox container = new VBox(successText, backText);
        container.setAlignment(Pos.CENTER);
        container.setPrefWidth(Region.USE_COMPUTED_SIZE);
        container.setPrefHeight(Region.USE_COMPUTED_SIZE);
        background.setAlignment(Pos.CENTER);
        background.getChildren().add(container);
        background.getChildren().add(hBox);
        hBox.setSpacing(50);
        hBox.setAlignment(Pos.CENTER);
        hBox.getChildren().add(backButton);
        hBox.getChildren().add(detailButton);
        background.setSpacing(20);
    }


    private void handleBackButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/home.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        Parent pane = loader.load();
        HomeDetailController controller = loader.getController();
        controller.initData(user, homeButton, dashboardButton);
        borderPane.setCenter(pane);
    }

    private void handleDetailButton(ActionEvent actionEvent)throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/complaint.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane)((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);
        Parent pane = loader.load();
        ComplaintDetailController controller = loader.getController();
        removeActive();
        dashboardButton.getStyleClass().add("active");
        controller.initData(user);
        borderPane.setCenter(pane);
    }

    private void removeActive() {
        homeButton.getStyleClass().remove("active");
        dashboardButton.getStyleClass().remove("active");
    }


}
