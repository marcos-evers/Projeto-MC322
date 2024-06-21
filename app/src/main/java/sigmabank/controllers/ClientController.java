package sigmabank.controllers;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sigmabank.model.register.Client;

public class ClientController extends BaseController<Client> {
    @FXML private Text name;
    @FXML private Text cpf;
    @FXML private Text email;
    @FXML private Text dateOfBirth;

    @Override
    public void initData() throws IOException {
        this.name.setText(this.object.getName());
        this.cpf.setText(this.object.getFormattedCpf());
        this.email.setText(this.object.getEmail());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.dateOfBirth.setText(this.object.getDateOfBirth().format(formatter));
    }

    public void deny(Event e) {
        // TODO delete client from table of "ClientsToApproval"

    }

    public void approve(Event e) {
        // TODO delete client from table of "ClientsToApproval" and add to the
        // table of regular clients
    }

    public void leave(ActionEvent e) {
        this.stage.close();
    }
}
