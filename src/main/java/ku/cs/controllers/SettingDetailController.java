package ku.cs.controllers;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import ku.cs.models.Appearance;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserListDataSource;

import java.io.IOException;

public class SettingDetailController {
    private User user;
    private UserList userList;
    private DataSource<UserList> data;
    private Appearance appearance;
    @FXML private Label usernameLabel;
    @FXML private ComboBox<String> themeSelector;
    public void initData(User user) {
        this.user = user;
        usernameLabel.setText(user.getUsername());

        data = new UserListDataSource("data", "user.csv");
        userList = data.readData();

        appearance = new Appearance();
        themeSelector.getItems().addAll(appearance.getAllTheme());
        themeSelector.setOnAction(e -> handleChangeTheme(e));
    }

    @FXML private void handleChangeName(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/changeNameDialog.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        String themeCSS = this.getClass().getResource("/ku/cs/css/themes/" + user.getTheme() + ".css").toExternalForm();
        String fontCSS = this.getClass().getResource("/ku/cs/css/fonts/" + user.getFont() + ".css").toExternalForm();
        root.getStylesheets().add(themeCSS);
        root.getStylesheets().add(fontCSS);

        // TODO: send data to changeNameDialog

        Stage parentStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        VBox vBox = (VBox) ((StackPane) ((Node) actionEvent.getSource()).getScene().getRoot()).getChildren().get(1);

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

        // make background of parent stage darker
        vBox.setVisible(true);
        // add transition when dark background appear
        FadeTransition ft = new FadeTransition(Duration.millis(500), vBox);
        ft.setFromValue(0.0);
        ft.setToValue(1.0);
        ft.play();

        dialogBox.showAndWait();

        vBox.setVisible(false);
    }
    @FXML private void handleChangeProfile (){

    }
    @FXML private void handleChangePassword (){

    }
    @FXML private void handleChangeTheme(ActionEvent actionEvent) {
        String newTheme = themeSelector.getValue();
        String currTheme = user.getTheme();

        String newThemeCSS = this.getClass().getResource("/ku/cs/css/themes/" + newTheme + ".css").toExternalForm();
        String currThemeCSS = this.getClass().getResource("/ku/cs/css/themes/" + currTheme + ".css").toExternalForm();

        StackPane stackPane = (StackPane) ((Node) actionEvent.getSource()).getScene().getRoot();
        stackPane.getStylesheets().remove(currThemeCSS);
        stackPane.getStylesheets().add(newThemeCSS);

        user.setTheme(newTheme);
        userList.updateUser(user);
        data.writeData(userList);
    }
}
