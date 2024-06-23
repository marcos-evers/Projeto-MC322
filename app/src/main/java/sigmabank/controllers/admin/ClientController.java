package sigmabank.controllers.admin;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.register.Client;
import sigmabank.utils.DateFormatter;

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

        this.dateOfBirth.setText(DateFormatter.format(this.object.getDateOfBirth()));
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
