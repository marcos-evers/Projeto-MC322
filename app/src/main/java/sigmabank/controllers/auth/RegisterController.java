package sigmabank.controllers.auth;

import java.io.IOException;
import java.util.Map;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sigmabank.controllers.BaseController;
import sigmabank.model.register.Client;
import sigmabank.model.register.InvalidAddressException;
import sigmabank.model.register.InvalidBirthDateException;
import sigmabank.model.register.InvalidCPFException;
import sigmabank.model.register.InvalidEmailException;
import sigmabank.model.register.InvalidPhoneNumberException;
import sigmabank.net.ClientConnection;

public class RegisterController extends BaseController<Client> {
    @FXML private TextField name;
    @FXML private TextField email;
    @FXML private DatePicker dateOfBirth;
    @FXML private TextField phoneNumber;
    @FXML private TextField cpf;
    @FXML private TextField address;
    @FXML private PasswordField password;
    @FXML private PasswordField confirmPassword;

    private boolean checkPasswords() {
        if (password.getText().equals(confirmPassword.getText())) {
            return true;
        }

        BaseController.errorDialog("As senhas não conferem");
        return false;
    }

    private boolean hasEmptyField() {
        return
            this.name.getText() == null || this.name.getText().trim().isEmpty() ||
            this.email.getText() == null || this.email.getText().trim().isEmpty() ||
            this.dateOfBirth.getValue() == null ||
            this.phoneNumber.getText() == null || this.phoneNumber.getText().trim().isEmpty() ||
            this.cpf.getText() == null || this.cpf.getText().trim().isEmpty() ||
            this.address.getText() == null || this.address.getText().trim().isEmpty() ||
            this.password.getText() == null ||
            this.confirmPassword.getText() == null;
    }
        
    public void keyTrySubmit(KeyEvent e) throws IOException {
        if (e.getCode() == KeyCode.ENTER){
            trySubmit(null);
        }
    }

    public void trySubmit(ActionEvent e) throws IOException {
        if (this.hasEmptyField()) {
            BaseController.errorDialog("Preencha todos os campos!");
            return;
        }

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
        } catch (InvalidBirthDateException err) {
            BaseController.errorDialog("Data de nascimento inválida: é preciso ter mais de 18 anos.");
            return;
        } catch (InvalidCPFException err) {
            BaseController.errorDialog("CPF inválido.");
            return;
        } catch (InvalidPhoneNumberException err) {
            BaseController.errorDialog("Número de telefone inválido.");
            return;
        } catch (InvalidAddressException err) {
            BaseController.errorDialog("Endereço inválido.");
            return;
        } catch (InvalidEmailException err) {
            BaseController.errorDialog("Endereço de e-mail inválido.");
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
        this.loadView("auth/login", "Login", null);
    }

    public void leave(ActionEvent e) {
        Platform.exit();
    }

    @Override
    public void initData() throws IOException { }
}
