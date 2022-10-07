package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import ku.cs.controllers.setting.SettingDetailController;
import ku.cs.models.Role;
import ku.cs.models.User;

import java.io.IOException;

public class MainApplicationController {
    private User user;
    @FXML private BorderPane borderPane;
    @FXML private StackPane stackPane;
    @FXML private Button homeButton;
    @FXML private Button dashboardButton;
    @FXML private Button aboutButton;
    @FXML private Button helpButton;
    @FXML private Button settingButton;

    /**
     * initialize user, user settings
     *
     * @param user a user object from loginController
     */
    public void initData(User user) throws IOException{
        this.user = user;
        String theme = user.getTheme();
        String font = user.getFont();
        String fontSize = user.getFontSize() + "px";
        String themeCSS = this.getClass().getResource("/ku/cs/css/themes/" + theme + ".css").toExternalForm();
        String fontSizeCSS = this.getClass().getResource("/ku/cs/css/fontSize/" + fontSize + ".css").toExternalForm();
        String fontCSS = this.getClass().getResource("/ku/cs/css/fonts/" + font + ".css").toExternalForm();
        stackPane.getStylesheets().add(themeCSS);
        stackPane.getStylesheets().add(fontSizeCSS);
        stackPane.getStylesheets().add(fontCSS);

        handleHomeButton();
    }

    @FXML
    private void handleHomeButton() throws IOException {
        removeActive();
        homeButton.getStyleClass().add("active");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/home.fxml"));
        Parent pane = loader.load();
        HomeDetailController homeDetailController = loader.getController();
        homeDetailController.initData(user, homeButton, dashboardButton);
        borderPane.setCenter(pane);
    }

    @FXML
    private void handleDashboardButton() throws IOException {
        removeActive();
        dashboardButton.getStyleClass().add("active");


//        FXMLLoader loader;

        // if user's role is student then go to all complaint page
        if (user.getRole() == Role.STUDENT) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/complaint.fxml"));
            Parent root = loader.load();
            ComplaintDetailController complaintController = loader.getController();
            complaintController.initData(user);
            borderPane.setCenter(root);
        } else {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/dashboard.fxml"));
            Parent root = loader.load();
            DashboardDetailController controller = loader.getController();
            controller.initData(user);
            borderPane.setCenter(root);
        }

    }

    @FXML
    private void handleAboutButton() throws IOException {
        removeActive();
        aboutButton.getStyleClass().add("active");


        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/about.fxml"));
        borderPane.setCenter(loader.load());
    }

    @FXML
    private void handleHelpButton() throws IOException{
        removeActive();
        helpButton.getStyleClass().add("active");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/help.fxml"));
        borderPane.setCenter(loader.load());
    }

    @FXML
    private void handleSettingButton() throws IOException {
        removeActive();
        settingButton.getStyleClass().add("active");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/setting.fxml"));
        Parent pane = loader.load();

        // sent data to setting controller
        SettingDetailController settingDetailController = loader.getController();
        settingDetailController.initData(user);

        borderPane.setCenter(pane);
    }

    private void removeActive() {
        homeButton.getStyleClass().remove("active");
        dashboardButton.getStyleClass().remove("active");
        aboutButton.getStyleClass().remove("active");
        helpButton.getStyleClass().remove("active");
        settingButton.getStyleClass().remove("active");
    }
}
