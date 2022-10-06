package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ProjectApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ku/cs/view/login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 900, 600);
        // scene.getStylesheets().add(this.getClass().getResource("/ku/cs/css/login.css").toExternalForm());

        stage.setTitle("KU Complaint");
        stage.setMinWidth(900);
        stage.setMinHeight(450);
        stage.getIcons().add(new Image(getClass().getResource("/ku/cs/images/ku-logo.png").toExternalForm()));
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}

