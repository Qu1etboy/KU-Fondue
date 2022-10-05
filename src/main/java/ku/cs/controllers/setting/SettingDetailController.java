package ku.cs.controllers.setting;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.controllers.setting.ChangeNameController;
import ku.cs.controllers.setting.ChangePasswordController;
import ku.cs.models.Appearance;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.services.DataSource;
import ku.cs.services.UserListDataSource;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.Date;

public class SettingDetailController {
    private User user;
    private UserList userList;
    private DataSource<UserList> data;
    private Appearance appearance;
    @FXML private Label nameLabel;
    @FXML private ComboBox<String> themeSelector;

    @FXML private ComboBox<String> fontSelector;

    @FXML private ComboBox<String> fontSizeSelector;
    @FXML private HBox usernameProfileContainer;

    public void initData(User user) {
        this.user = user;
        // set user profile image and username;
        showUsernameProfile();
        nameLabel.setText(user.getName());

        data = new UserListDataSource("data", "user.csv");
        userList = data.readData();

        appearance = new Appearance();
        themeSelector.getItems().addAll(appearance.getAllTheme());
        themeSelector.setOnAction(e -> handleChangeTheme(e));
        themeSelector.setValue(user.getTheme());

        fontSelector.getItems().addAll(appearance.getAllFont());
        fontSelector.setOnAction(e -> handleChangFont(e));
        fontSelector.setValue(user.getFont());

        fontSizeSelector.getItems().addAll(appearance.getAllFontSize());
        fontSizeSelector.setOnAction(e -> handleChangFontSize(e));
        fontSizeSelector.setValue(user.getFontSize() + "px");
    }
/*
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

 */

    private void showUsernameProfile() {
        usernameProfileContainer.getChildren().clear();

        Label usernameLabel = new Label(user.getUsername());
        usernameLabel.getStyleClass().add("title");
        usernameLabel.setStyle("-fx-font-weight: 600");

        usernameProfileContainer.getChildren().add(user.getProfileImageView());
        usernameProfileContainer.getChildren().add(usernameLabel);
    }
    @FXML private void handleChangeName(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/changeNameDialog.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane) ((Node) actionEvent.getSource()).getScene().getRoot()).getChildren().get(0);
        Parent pane = loader.load();
        ChangeNameController changeNameController = loader.getController();
        changeNameController.initData(user);
        borderPane.setCenter(pane);
    }
    @FXML private void handleChangeProfile (ActionEvent actionEvent) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Choose profile image");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        File file = chooser.showOpenDialog(((Node) actionEvent.getSource()).getScene().getWindow());
        if (file != null) {
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("images");
                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");
                String filename = user.getId() + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath() + System.getProperty("file.separator") + filename
                );
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                // SET NEW FILE PATH TO IMAGE
                user.setProfileImage(new Image(target.toUri().toString()));
                showUsernameProfile();
                userList.updateUser(user);
                data.writeData(userList);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    @FXML private void handleChangePassword (ActionEvent actionEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/changePasswordDialog.fxml"));
        BorderPane borderPane = (BorderPane) ((StackPane) ((Node) actionEvent.getSource()).getScene().getRoot()).getChildren().get(0);
        Parent pane = loader.load();
        ChangePasswordController changePasswordController = loader.getController();
        changePasswordController.initData(user);
        borderPane.setCenter(pane);
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

    @FXML private void handleChangFont(ActionEvent actionEvent) {
        String newFont = fontSelector.getValue();
        String currFont = user.getFont();

        String newFontCSS = this.getClass().getResource("/ku/cs/css/fonts/" + newFont + ".css").toExternalForm();
        String currFontCSS = this.getClass().getResource("/ku/cs/css/fonts/" + currFont + ".css").toExternalForm();

        StackPane stackPane = (StackPane) ((Node) actionEvent.getSource()).getScene().getRoot();
        stackPane.getStylesheets().remove(currFontCSS);
        stackPane.getStylesheets().add(newFontCSS);

        user.setFont(newFont);
        userList.updateUser(user);
        data.writeData(userList);
    }

    @FXML private void handleChangFontSize(ActionEvent actionEvent) {
        String newFontSize = fontSizeSelector.getValue();
        String currFontSize = user.getFontSize() + "px";

        String newFontSizeCSS = this.getClass().getResource("/ku/cs/css/fontSize/" + newFontSize + ".css").toExternalForm();
        String currFontSizeCSS = this.getClass().getResource("/ku/cs/css/fontSize/" + currFontSize + ".css").toExternalForm();

        StackPane stackPane = (StackPane) ((Node) actionEvent.getSource()).getScene().getRoot();
        stackPane.getStylesheets().remove(currFontSizeCSS);
        stackPane.getStylesheets().add(newFontSizeCSS);

        user.setFontSize(Integer.parseInt(newFontSize.substring(0, newFontSize.length() - 2)));
        userList.updateUser(user);
        data.writeData(userList);
    }

    @FXML
    private void handleLogOutButton(ActionEvent actionEvent) throws IOException {
//        user.setLastOnline(LocalDateTime.now());
//        userList.updateUser(user);
//        data.writeData(userList);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/login.fxml"));
        Scene scene = new Scene(loader.load());
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setMinWidth(0);
        stage.setScene(scene);
        stage.show();

    }
}
