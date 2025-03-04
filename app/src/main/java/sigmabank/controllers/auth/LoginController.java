package sigmabank.controllers.auth;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import sigmabank.controllers.BaseController;
import sigmabank.model.register.Client;
import sigmabank.net.ClientConnection;
import sigmabank.utils.HashPassword;

public class LoginController extends BaseController<Client> {
    @FXML private TextField cpf;
    @FXML private PasswordField password;

    /**
     * 
     * @param e
     * @throws IOException
     */
    public void keyTrySubmit(KeyEvent e) throws IOException {
        if (e.getCode() == KeyCode.ENTER){
            trySubmit(null);
        }
    }

    /**
     * 
     * @param e
     * @throws IOException
     */
    public void trySubmit(ActionEvent e) throws IOException {
        if (cpf.getText() == null || cpf.getText().trim().isEmpty() || password.getText() == null || password.getText().trim().isEmpty()) {
            BaseController.errorDialog("Preencha todos os campos!");
            return;
        }

        if (cpf.getText().equals("admin")) {
            this.loadView("admin/home", "Administração", null);

            return;
        }
        
        ClientConnection connection = new ClientConnection("http://localhost:8000/client");
        
        String hash = HashPassword.hashPassword(this.cpf.getText(), this.password.getText());
        
        List<Client> client = connection.fetch(Map.of(
            "cpf", this.cpf.getText(),
            "password", hash
        ));
        if (client.size() != 0) {
            this.loadView("client/home", "Home", client.get(0));
            return;
        }

        BaseController.errorDialog("CPF ou senha inválidos.");
    }
    
    /**
     * 
     * @param e
     * @throws IOException
     */
    public void register(ActionEvent e) throws IOException {
        this.loadView("auth/register", "Criar conta", null);
    }
    
    @Override
    public void initData() { }
    
    /**
     * 
     * @param e
     */
    public void leave(ActionEvent e) {
        Platform.exit();
    }
}
