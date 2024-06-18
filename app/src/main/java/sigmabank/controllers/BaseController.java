package sigmabank.controllers;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class BaseController {
    protected Stage stage;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public static void loadView(String viewName, String viewTitle, BaseController context) throws IOException {
        FXMLLoader loader = new FXMLLoader(context.getClass().getResource("/sigmabank/views/" + viewName + ".fxml"));
        Parent root = (Parent) loader.load();
        ((BaseController) loader.getController()).setStage(context.stage);
        context.stage.setScene(new Scene(root));
        context.stage.setTitle(viewTitle);
        context.stage.show();
    }
}
