package sigmabank.controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class RegisterController extends BaseController {
    @FXML private TextField name;
    @FXML private TextField email;
    @FXML private DatePicker dateOfBirth;
    @FXML private TextField phoneNumber;
    @FXML private TextField cpf;
    @FXML private TextField address;
    @FXML private TextField password;
    @FXML private TextField confirmPassword;

    public void trySubmit(ActionEvent e) throws IOException {
        System.out.println("trySubmit");

        // if everything is valid:
        this.login(e);
    }

    public void login(ActionEvent e) throws IOException {
        BaseController.loadView("login", "Login", this);
    }

    public void leave(ActionEvent e) {
        Platform.exit();
    }
}
