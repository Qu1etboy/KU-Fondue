package ku.cs.controllers.complaintcategory;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ku.cs.controllers.*;
import ku.cs.models.*;
import ku.cs.services.CategoryAttributeListDataSource;
import ku.cs.services.ComplaintCategoryListDataSource;
import ku.cs.services.DataSource;

import java.io.IOException;

public class ComplaintCategoryDetailController {
    @FXML
    private User user;
    @FXML
    private ListView<ComplaintCategory> complaintCategoryListView;
    @FXML
    private ListView<CategoryAttribute> attributeListView;

    ListView<String> choiceListView;
    @FXML
    private Label categoryNameLabel;
    @FXML
    private VBox detailContent;
    @FXML
    private VBox defaultContent;
    @FXML
    private VBox attributeDetailContainer;

    private DataSource<ComplaintCategoryList> categoryData;
    private DataSource<CategoryAttributeList> attributeData;
    private ComplaintCategoryList complaintCategoryList;
    private CategoryAttributeList categoryAttributeList;
    private ComplaintCategory complaintCategory;
    private CategoryAttribute categoryAttribute;
    private String choiceName;

    /**
     * This method initialize user and information need for this page
     * @param user The login user
     */
    public void initData(User user) {
        this.user = user;

        categoryData = new ComplaintCategoryListDataSource("data", "complaint_category.csv");
        complaintCategoryList = categoryData.readData();

        attributeData = new CategoryAttributeListDataSource("data", "attribute.csv");
        categoryAttributeList = attributeData.readData();

        complaintCategoryListView.getItems().addAll(complaintCategoryList.getComplaintCategoryList());
        complaintCategoryListView.getSelectionModel().selectedItemProperty().addListener(
                (observableValue, complaintCategory, t1) -> showComplaintCategoryDetail(t1)
        );
        attributeListView.getSelectionModel().selectedItemProperty()
                .addListener((obs, attribute, newAttribute) -> showAttributeDetail(newAttribute));
        attributeDetailContainer.setSpacing(5);

        choiceListView = new ListView<>();
        choiceListView.setPrefHeight(200);
        choiceListView.setMinHeight(Region.USE_PREF_SIZE);
        choiceListView.setMaxHeight(Region.USE_PREF_SIZE);
        choiceListView.getSelectionModel().selectedItemProperty().addListener(
                (obs, choiceName, newChoiceName) -> {
                    this.choiceName = newChoiceName;
            }
        );

    }

    public void showComplaintCategoryDetail(ComplaintCategory complaintCategory) {
        defaultContent.setVisible(false);
        detailContent.setVisible(true);

        if (complaintCategory == null) {
            return;
        }

        System.out.println(complaintCategory.getName() + " is selected");

        this.complaintCategory = complaintCategory;

        attributeDetailContainer.getChildren().clear();
        attributeListView.getItems().clear();
        attributeListView.getItems().addAll(complaintCategory.getCategoryAttributeList());
        categoryNameLabel.setText(complaintCategory.getName());
    }

    public void showAttributeDetail(CategoryAttribute attribute) {
        if (attribute == null) {
            return;
        }
        this.categoryAttribute = attribute;
        // System.out.println(attribute.getNameAttribute() + " is selected");

        Label nameLabel = new Label("ชื่อคุณลักษณะ : " + attribute.getNameAttribute());
        Label typeLabel = new Label("ประเภทคุณลักษณะ : " + attribute.getTypeAttribute());

        attributeDetailContainer.getChildren().clear();
        attributeDetailContainer.getChildren().add(nameLabel);
        attributeDetailContainer.getChildren().add(typeLabel);


        if (attribute.getTypeAttribute().equals("combobox")) {
            attributeDetailContainer.getChildren().add(new Label("รายละเอียดของตัวเลือก"));

            choiceListView.getItems().clear();
            for (String choice : attribute.getInputData()) {
                // Label choiceLabel = new Label(" - " + choice);
                choiceListView.getItems().add(choice);
            }
            attributeDetailContainer.getChildren().add(choiceListView);
            Button addChoice = new Button("เพิ่มตัวเลือก");
            Button removeChoice = new Button("ลบตัวเลือก");
            Button changeChoiceName = new Button("เปลี่ยนชื่อตัวเลือก");

            removeChoice.getStyleClass().add("danger-button");

            addChoice.setOnAction(e -> {
                try {
                    handleAddChoice(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            changeChoiceName.setOnAction(e -> {
                try {
                    handleChangeChoiceName(e);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            });

            removeChoice.setOnAction(e -> {
                handleRemoveChoice(e);
            });

            HBox buttons = new HBox(addChoice, changeChoiceName, removeChoice);
            buttons.setSpacing(10);

            attributeDetailContainer.getChildren().add(buttons);
        }

    }

    public void showCategoryListView(ComplaintCategoryList complaintCategoryList) {
        complaintCategoryListView.getItems().setAll(complaintCategoryList.getComplaintCategoryList());
    }

    public void showAttributeListView(ComplaintCategory complaintCategory) {
        attributeListView.getItems().setAll(complaintCategory.getCategoryAttributeList());
    }

    public void showChoiceListView(CategoryAttribute categoryAttribute) {
        choiceListView.getItems().setAll(categoryAttribute.getInputData());
    }

    @FXML
    public void handleAddComplaintCategory(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/complaintCategory/addCategoryDialog.fxml"));
        Parent root = loader.load();

        AddCategoryDialogController controller = loader.getController();
        controller.initData(complaintCategoryList);

        initDialogBox(actionEvent, root);

        categoryData.writeData(complaintCategoryList);

        showCategoryListView(complaintCategoryList);

    }

    @FXML
    public void handleAddAttribute(ActionEvent actionEvent) throws IOException {

        if (complaintCategory == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/complaintCategory/addAttributeDialog.fxml"));
        Parent root = loader.load();

        AddAttributeDialogController controller = loader.getController();
        controller.initData(complaintCategory, categoryAttributeList);

        initDialogBox(actionEvent, root);

        attributeData.writeData(categoryAttributeList);

        showAttributeListView(complaintCategory);

    }

    public void handleAddChoice(ActionEvent actionEvent) throws IOException{

        if (categoryAttribute == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/complaintCategory/addChoiceDialog.fxml"));
        Parent root = loader.load();

        AddChoiceDialogController controller = loader.getController();
        controller.initData(categoryAttribute);

        initDialogBox(actionEvent, root);

        categoryAttributeList.updateAttribute(categoryAttribute);
        attributeData.writeData(categoryAttributeList);

        showChoiceListView(categoryAttribute);

    }

    @FXML
    public void handleChangeCategoryName(ActionEvent actionEvent) throws IOException {

        if (complaintCategory == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/complaintCategory/renameCategoryDialog.fxml"));
        Parent root = loader.load();

        RenameCategoryDialogController controller = loader.getController();
        controller.initData(complaintCategory);

        initDialogBox(actionEvent, root);

        complaintCategoryList.updateComplaintCategory(complaintCategory);
        categoryData.writeData(complaintCategoryList);

        showCategoryListView(complaintCategoryList);
        categoryNameLabel.setText(complaintCategory.getName());

    }

    @FXML
    public void handleChangeAttribute(ActionEvent actionEvent) throws IOException {

        if (categoryAttribute == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/complaintCategory/renameAttributeDialog.fxml"));
        Parent root = loader.load();

        RenameAttributeDialogController controller = loader.getController();
        controller.initData(categoryAttribute);

        initDialogBox(actionEvent, root);

        categoryAttributeList.updateAttribute(categoryAttribute);
        attributeData.writeData(categoryAttributeList);

        showAttributeListView(complaintCategory);

    }

    public void handleChangeChoiceName(ActionEvent actionEvent) throws IOException {

        if (choiceName == null) {
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/complaintCategory/renameChoiceDialog.fxml"));
        Parent root = loader.load();

        RenameChoiceDialogController controller = loader.getController();
        controller.initData(choiceName, categoryAttribute);

        initDialogBox(actionEvent, root);

        categoryAttributeList.updateAttribute(categoryAttribute);
        attributeData.writeData(categoryAttributeList);

        showChoiceListView(categoryAttribute);

    }

    @FXML
    public void handleRemoveComplaintCategory(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/confirmationDialog.fxml"));
        Parent root = loader.load();

        ConfirmationDialogController controller = loader.getController();
        controller.initData("คุณต้องการจะลบใช่ไหม?");

        initDialogBox(actionEvent, root);

        if (!controller.getConfirm()) return;

        categoryAttributeList.removeAllAttributeByCategoryId(complaintCategory);
        complaintCategoryList.removeComplaintCategory(complaintCategory);
        attributeData.writeData(categoryAttributeList);
        categoryData.writeData(complaintCategoryList);

        showCategoryListView(complaintCategoryList);

        detailContent.setVisible(false);
        defaultContent.setVisible(true);

    }

    @FXML
    public void handleRemoveAttribute(ActionEvent actionEvent) throws IOException {
        if (categoryAttribute == null) {
            alert();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/confirmationDialog.fxml"));
        Parent root = loader.load();

        ConfirmationDialogController controller = loader.getController();
        controller.initData("คุณต้องการจะลบใช่ไหม?");

        initDialogBox(actionEvent, root);

        if (!controller.getConfirm()) return;

        complaintCategory.removeCategoryAttribute(categoryAttribute);
        categoryAttributeList.removeAttribute(categoryAttribute);
        attributeData.writeData(categoryAttributeList);

        showAttributeListView(complaintCategory);

    }

    public void handleRemoveChoice(ActionEvent actionEvent) {
        if (choiceName == null) {
            alert();
            return;
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/confirmationDialog.fxml"));
            Parent root = loader.load();

            ConfirmationDialogController controller = loader.getController();
            controller.initData("คุณต้องการจะลบใช่ไหม?");

            initDialogBox(actionEvent, root);

            if (!controller.getConfirm()) return;

            categoryAttribute.removeChoice(choiceName);
            categoryAttributeList.updateAttribute(categoryAttribute);
            attributeData.writeData(categoryAttributeList);

            showChoiceListView(categoryAttribute);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/dashboard.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane) ((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);

        Parent pane = loader.load();
        DashboardDetailController dashboardDetailController = loader.getController();
        dashboardDetailController.initData(user);
        borderPane.setCenter(pane);
    }

    public void initDialogBox(ActionEvent actionEvent, Parent root) {

        Scene scene = new Scene(root);

        Stage parentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        StackPane stackPane = (StackPane) ((Node) actionEvent.getSource()).getScene().getRoot();
        VBox vBox = (VBox) stackPane.getChildren().get(1);

        Stage dialogBox = new Stage();

        String themeCSS = this.getClass().getResource("/ku/cs/css/themes/" + user.getTheme() + ".css").toExternalForm();
        String fontCSS = this.getClass().getResource("/ku/cs/css/fonts/" + user.getFont() + ".css").toExternalForm();
        root.getStylesheets().add(themeCSS);
        root.getStylesheets().add(fontCSS);

        dialogBox.initModality(Modality.APPLICATION_MODAL);
        dialogBox.initOwner(parentStage);
        dialogBox.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        dialogBox.setScene(scene);
        // make dialog box close when click outside
        dialogBox.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!isNowFocused) {
                dialogBox.close();
            }
        });

        // make dialog box always center to the screen of application
        ChangeListener<Number> widthListener = (observable, oldValue, newValue) -> {
            double stageWidth = newValue.doubleValue();
            dialogBox.setX(parentStage.getX() + parentStage.getWidth() / 2 - stageWidth / 2);
        };
        ChangeListener<Number> heightListener = (observable, oldValue, newValue) -> {
            double stageHeight = newValue.doubleValue();
            dialogBox.setY(parentStage.getY() + parentStage.getHeight() / 2 - stageHeight / 2);
        };
        dialogBox.widthProperty().addListener(widthListener);
        dialogBox.heightProperty().addListener(heightListener);
        dialogBox.setOnShown(e -> {
            dialogBox.widthProperty().removeListener(widthListener);
            dialogBox.heightProperty().removeListener(heightListener);
        });

        vBox.setVisible(true);
        // add transition when dark background appear
        FadeTransition ft = new FadeTransition(Duration.millis(500), vBox);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();

        dialogBox.showAndWait();

        vBox.setVisible(false);

    }

    public void alert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setContentText("กรุณาเลือกสิ่งท่านต้องการจะลบ");
        alert.show();
    }
}
