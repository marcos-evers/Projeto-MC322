package sigmabank.controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sigmabank.model.register.Client;

public class RegisterController extends BaseController<Client> {
    @FXML private TextField name;
    @FXML private TextField email;
    @FXML private DatePicker dateOfBirth;
    @FXML private TextField phoneNumber;
    @FXML private TextField cpf;
    @FXML private TextField address;
    @FXML private TextField password;
    @FXML private TextField confirmPassword;

    private boolean checkPasswords() {
        if (password.getText().equals(confirmPassword.getText())) {
            return true;
        }

        BaseController.errorDialog("As senhas não conferem");
        return false;
    }

    public void trySubmit(ActionEvent e) throws IOException {
        if (!this.checkPasswords()) {
            return;
        }

        Client client;
        try {
            client = new Client(
                this.name.getText(),
                this.dateOfBirth.getValue(),
                this.cpf.getText()
            );
        }

        this.login(e);
    }

    public void login(ActionEvent e) throws IOException {
        this.loadView("login", "Login", null);
    }

    public void leave(ActionEvent e) {
        Platform.exit();
    }

    @Override
    public void initData() throws IOException { }
}
