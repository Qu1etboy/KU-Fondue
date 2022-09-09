package ku.cs.controllers;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ku.cs.models.*;
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
    @FXML
    private Label categoryNameLabel;
    @FXML
    private VBox detailContent;
    @FXML
    private VBox defaultContent;

    private DataSource<ComplaintCategoryList> categoryData;
    private ComplaintCategoryList complaintCategoryList;

    public void initData(User user) {
        this.user = user;

        categoryData = new ComplaintCategoryListDataSource("data", "complaint_category.csv");
        complaintCategoryList = categoryData.readData();

        complaintCategoryListView.getItems().addAll(complaintCategoryList.getComplaintCategoryList());
        complaintCategoryListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<ComplaintCategory>() {
                    @Override
                    public void changed(ObservableValue<? extends ComplaintCategory> observableValue, ComplaintCategory complaintCategory, ComplaintCategory t1) {
                        handleViewComplaintCategoryDetail(t1);
                    }
                }
        );
    }

    @FXML
    public void handleAddComplaintCategory(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/addCategoryDialog.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        String themeCSS = this.getClass().getResource("/ku/cs/css/themes/" + user.getTheme() + ".css").toExternalForm();
        String fontCSS = this.getClass().getResource("/ku/cs/css/fonts/" + user.getFont() + ".css").toExternalForm();
        root.getStylesheets().add(themeCSS);
        root.getStylesheets().add(fontCSS);

        Stage parentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        StackPane stackPane = (StackPane) ((Node) actionEvent.getSource()).getScene().getRoot();
        VBox vBox = (VBox) stackPane.getChildren().get(1);

        Stage dialogBox = new Stage();
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

        AddCategoryDialogController controller = loader.getController();
        controller.initData(complaintCategoryList);
        dialogBox.showAndWait();

        categoryData.writeData(complaintCategoryList);
        complaintCategoryListView.getItems().clear();
        complaintCategoryListView.getItems().addAll(complaintCategoryList.getComplaintCategoryList());

        vBox.setVisible(false);
    }

    public void handleViewComplaintCategoryDetail(ComplaintCategory complaintCategory) {
        defaultContent.setVisible(false);
        detailContent.setVisible(true);

        attributeListView.getItems().clear();
        attributeListView.getItems().addAll(complaintCategory.getCategoryAttributeList());
        categoryNameLabel.setText(complaintCategory.getName());
    }

    @FXML
    public void handleRemoveComplaintCategory() {

    }

    @FXML
    public void handleChangeAttribute() {

    }

    @FXML
    public void handleRemoveAttribute() {

    }

    @FXML
    public void handleAddAttribute(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/addAttributeDialog.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        String themeCSS = this.getClass().getResource("/ku/cs/css/themes/" + user.getTheme() + ".css").toExternalForm();
        String fontCSS = this.getClass().getResource("/ku/cs/css/fonts/" + user.getFont() + ".css").toExternalForm();
        root.getStylesheets().add(themeCSS);
        root.getStylesheets().add(fontCSS);

        Stage parentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        StackPane stackPane = (StackPane) ((Node) actionEvent.getSource()).getScene().getRoot();
        VBox vBox = (VBox) stackPane.getChildren().get(1);

        Stage dialogBox = new Stage();
        dialogBox.initModality(Modality.APPLICATION_MODAL);
        dialogBox.initOwner(parentStage);
        dialogBox.initStyle(StageStyle.TRANSPARENT);
        scene.setFill(Color.TRANSPARENT);
        dialogBox.setScene(scene);
        // make dialog box close when click outside
        dialogBox.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (!dialogBox.isFocused()) {
                System.out.println("Close");
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

    @FXML
    public void handleBackButton(ActionEvent actionEvent) throws IOException {
        // only admin can access this page so no need to check for role
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/adminDashboard.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane) ((Node) actionEvent.getSource()).getScene().getRoot()).
                getChildren().get(0);

        Parent pane = loader.load();
        DashboardDetailController dashboardDetailController = loader.getController();
        dashboardDetailController.initData(user);
        borderPane.setCenter(pane);
    }
}
