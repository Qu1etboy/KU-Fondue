package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainApplicationController {
    @FXML
    private Button homeButton;
    @FXML
    private Button dashboardButton;
    @FXML
    private Button aboutButton;
    @FXML
    private Button helpButton;
    @FXML
    private Button settingButton;

    @FXML
    public void handleHomeButton() {
        homeButton.getStyleClass().add("active");
        dashboardButton.getStyleClass().remove("active");
        aboutButton.getStyleClass().remove("active");
        helpButton.getStyleClass().remove("active");
        settingButton.getStyleClass().remove("active");
    }

    @FXML
    public void handleDashboardButton() {
        homeButton.getStyleClass().remove("active");
        dashboardButton.getStyleClass().add("active");
        aboutButton.getStyleClass().remove("active");
        helpButton.getStyleClass().remove("active");
        settingButton.getStyleClass().remove("active");
    }

    @FXML
    public void handleAboutButton() {
        homeButton.getStyleClass().remove("active");
        dashboardButton.getStyleClass().remove("active");
        aboutButton.getStyleClass().add("active");
        helpButton.getStyleClass().remove("active");
        settingButton.getStyleClass().remove("active");
    }

    @FXML
    public void handleHelpButton() {
        homeButton.getStyleClass().remove("active");
        dashboardButton.getStyleClass().remove("active");
        aboutButton.getStyleClass().remove("active");
        helpButton.getStyleClass().add("active");
        settingButton.getStyleClass().remove("active");
    }

    @FXML
    public void handleSettingButton() {
        homeButton.getStyleClass().remove("active");
        dashboardButton.getStyleClass().remove("active");
        aboutButton.getStyleClass().remove("active");
        helpButton.getStyleClass().remove("active");
        settingButton.getStyleClass().add("active");
    }



}
