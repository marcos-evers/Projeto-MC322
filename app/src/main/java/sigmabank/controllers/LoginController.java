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
            BaseController.loadView("admin_page", "Administração", this, null);

            return;
        }

        // if everything goes right:

        BaseController.loadView("client_page", "Home", this, new Client(cpf.getText()));
    }
    
    public void register(ActionEvent e) throws IOException {
        BaseController.loadView("register", "Criar conta", this, null);
    }
    
    @Override
    public void initData() { }

    public void leave(ActionEvent e) {
        Platform.exit();
    }
}
