package sigmabank.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController extends Controller {
    @FXML private TextField username;
    @FXML private TextField password;
    @FXML private Button submit;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stage.setTitle("Login");
    }

    public void trySubmit(ActionEvent e) {
        System.out.println("tentou submitar");
    }
    
    public void register(ActionEvent e) {        
        System.out.println("tentou register");
    }
    
    public void leave(ActionEvent e) {
        System.out.println("tentou sair");
    }
}
