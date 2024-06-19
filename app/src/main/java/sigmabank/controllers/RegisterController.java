package sigmabank.controllers;

import java.io.IOException;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
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

    private void showErrorAlert(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText("Ocorreu um erro no cadastro:");
        alert.setContentText(message);

        alert.showAndWait();
    }

    private boolean checkPasswords() {
        if (password.getText().equals(confirmPassword.getText())) {
            return true;
        }

        this.showErrorAlert("As senhas não conferem");
        return false;
    }

    public void trySubmit(ActionEvent e) throws IOException {
        if (!this.checkPasswords()) {
            return;
        }
                
        // String path = "/server/src/main/resources/database/Clients.xml";

        // ClientReader reader = new ClientReader();
        // ClientWritter writer = new ClientWritter();
        
        // List<Client> clients = reader.readFromXML(path);
        
        // Client client;
        // try {
        //     client = new Client(
        //         name.getText(),
        //         dateOfBirth.getValue(),
        //         cpf.getText(),
        //         HashPassword.hashPassword(cpf.getText(), password.getText())
        //     );
        // } catch (InvalidBirthDateException|InvalidCPFException err) {
        //     this.showErrorAlert(err.getMessage());
        //     return;
        // }

        // clients.add(client);

        // // TODO na verdade deveria ser um write pra tabela de clientes pra confirmar
        // writer.writeToXML("clients", (List<Object>)(Object) clients, path);

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
