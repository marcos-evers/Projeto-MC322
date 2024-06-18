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
