package sigmabank.controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import sigmabank.model.register.Client;

public class LoginController extends BaseController<Client> {
    @FXML private TextField cpf;
    @FXML private TextField password;

    public void trySubmit(ActionEvent e) throws IOException {
        if (cpf.getText().equals("admin")) {
            this.loadView("admin_page", "Administração", null);

            return;
        }

        // TODO make login validation
        // TODO: query the client here
        this.loadView("client_page", "Home", new Client(cpf.getText()));
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
