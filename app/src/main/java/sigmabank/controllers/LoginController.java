package sigmabank.controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;

public class LoginController extends BaseController {
    @FXML private TextField username;
    @FXML private TextField password;

    public void trySubmit(ActionEvent e) throws IOException {
        // if everything goes right:
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sigmabank/views/client_page.fxml"));

        Parent root = (Parent) loader.load();

        ClientPageController controller = (ClientPageController) loader.getController();
        controller.initData(username.getText());
        controller.setStage(this.stage);

        this.stage.setScene(new Scene(root));
        this.stage.setTitle("Home");
        this.stage.show();
    }
    
    public void register(ActionEvent e) throws IOException {
        BaseController.loadView("register", "Criar conta", this);
    }
    
    public void leave(ActionEvent e) {
        Platform.exit();
    }
}
