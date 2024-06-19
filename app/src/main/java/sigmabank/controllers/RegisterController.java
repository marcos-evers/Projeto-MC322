package sigmabank.controllers;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import sigmabank.model.register.Client;
import sigmabank.model.register.InvalidBirthDateException;
import sigmabank.model.register.InvalidCPFException;
import sigmabank.utils.HashPassword;
import sigmabank.utils.readers.ClientReader;
import sigmabank.utils.writters.ClientWritter;

public class RegisterController extends BaseController<Client> {
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

        this.login(e);
    }

    public void login(ActionEvent e) throws IOException {
        BaseController.loadView("login", "Login", this, null);
    }

    public void leave(ActionEvent e) {
        Platform.exit();
    }

    @Override
    public void initData(Client t) throws IOException { }
}
