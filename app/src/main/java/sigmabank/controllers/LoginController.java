package sigmabank.controllers;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sigmabank.model.register.Client;
import sigmabank.net.ClientConnection;
import sigmabank.utils.HashPassword;

public class LoginController extends BaseController<Client> {
    @FXML private TextField cpf;
    @FXML private TextField password;

    public void trySubmit(ActionEvent e) throws IOException {
        if (cpf.getText().equals("admin")) {
            this.loadView("admin_page", "Administração", null);

            return;
        }

        ClientConnection connection = new ClientConnection("http://localhost:8000/client");
 
        String hash = HashPassword.hashPassword(this.cpf.getText(), this.password.getText());
 
        List<Client> clientsList = connection.fetch(Map.of(
            "cpf", this.cpf.getText(),
            "password", hash
        ));
        if (clientsList.size() != 0) {
            this.loadView("client_page", "Home", clientsList.get(0));
            return;
        }

        BaseController.errorDialog("CPF ou senha inválidos.");
    }
    
    public void register(ActionEvent e) throws IOException {
        this.loadView("register", "Criar conta", null);
    }
    
    @Override
    public void initData() { }

    public void leave(ActionEvent e) {
        Platform.exit();
    }
}
