package sigmabank.controllers;

import java.io.IOException;
import java.util.Map;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import sigmabank.model.register.Client;
import sigmabank.model.register.InvalidBirthDateException;
import sigmabank.model.register.InvalidCPFException;
import sigmabank.net.ApprovalConnection;
import sigmabank.net.ClientConnection;
import sigmabank.utils.HashPassword;

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

        Client client = null;
        try {
            client = new Client(
                this.name.getText(),
                this.dateOfBirth.getValue(),
                this.cpf.getText()
            );
            client.setPhoneNumber(this.phoneNumber.getText());
            client.setAddress(this.address.getText());
            client.setEmail(this.email.getText());
            client.setPassword(this.password.getText());
        } catch (Exception err) {
            BaseController.errorDialog(err.getMessage());
            return;
        }

        ClientConnection conn = new ClientConnection("http://localhost:8000/client");

        conn.send(Map.of(
            "name", client.getName(),
            "dataOfBirth", client.getDateOfBirth(),
            "cpf", client.getCpf(),
            "phoneNumber", client.getPhoneNumber(),
            "address", client.getAddress(),
            "email", client.getEmail(),
            "password", client.getPasswordHash()
        ));

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
