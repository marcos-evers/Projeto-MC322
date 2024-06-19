package sigmabank;

import sigmabank.controllers.BaseController;

import java.io.IOException;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNDECORATED);
        stage.getIcons().add(new Image("/sigmabank/images/logo.png"));

        BaseController.loadView(
            getClass().getResource("/sigmabank/views/login.fxml"),
            "Login",
            stage
        );
    }

    public static void main(String[] args) throws Exception {
        launch();
    }
}
