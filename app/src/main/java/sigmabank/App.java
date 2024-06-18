package sigmabank;

import sigmabank.controllers.BaseController;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sigmabank/views/login.fxml"));
        Parent root = (Parent) loader.load();
        ((BaseController) loader.getController()).setStage(stage);
        stage.getIcons().add(new Image("/sigmabank/images/logo.png"));
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        launch();
    }
}
