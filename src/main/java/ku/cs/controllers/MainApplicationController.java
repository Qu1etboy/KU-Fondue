package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class MainApplicationController {
    @FXML private BorderPane borderPane;
    @FXML private Button homeButton;
    @FXML private Button dashboardButton;
    @FXML private Button aboutButton;
    @FXML private Button helpButton;
    @FXML private Button settingButton;

    @FXML
    public void initialize() {
        // initialize user app appearance
        String theme = "dark";
        String themeCSS = this.getClass().getResource("/ku/cs/css/themes/" + theme + ".css").toExternalForm();
        borderPane.getStylesheets().add(themeCSS);
    }
    @FXML
    public void handleHomeButton() throws IOException {
        homeButton.getStyleClass().add("active");
        dashboardButton.getStyleClass().remove("active");
        aboutButton.getStyleClass().remove("active");
        helpButton.getStyleClass().remove("active");
        settingButton.getStyleClass().remove("active");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/home.fxml"));
        borderPane.setCenter(loader.load());
    }

    @FXML
    public void handleDashboardButton() throws IOException {
        homeButton.getStyleClass().remove("active");
        dashboardButton.getStyleClass().add("active");
        aboutButton.getStyleClass().remove("active");
        helpButton.getStyleClass().remove("active");
        settingButton.getStyleClass().remove("active");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/adminDashboard.fxml"));
        borderPane.setCenter(loader.load());
    }

    @FXML
    public void handleAboutButton() throws IOException {
        homeButton.getStyleClass().remove("active");
        dashboardButton.getStyleClass().remove("active");
        aboutButton.getStyleClass().add("active");
        helpButton.getStyleClass().remove("active");
        settingButton.getStyleClass().remove("active");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/about1.fxml"));
        borderPane.setCenter(loader.load());
    }

    @FXML
    public void handleHelpButton() throws IOException{
        homeButton.getStyleClass().remove("active");
        dashboardButton.getStyleClass().remove("active");
        aboutButton.getStyleClass().remove("active");
        helpButton.getStyleClass().add("active");
        settingButton.getStyleClass().remove("active");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/help.fxml"));
        borderPane.setCenter(loader.load());
    }

    @FXML
    public void handleSettingButton() throws IOException{
        homeButton.getStyleClass().remove("active");
        dashboardButton.getStyleClass().remove("active");
        aboutButton.getStyleClass().remove("active");
        helpButton.getStyleClass().remove("active");
        settingButton.getStyleClass().add("active");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ku/cs/view/setting.fxml"));
        borderPane.setCenter(loader.load());
    }



}
