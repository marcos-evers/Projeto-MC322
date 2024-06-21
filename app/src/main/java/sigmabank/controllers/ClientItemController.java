package sigmabank.controllers;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import sigmabank.model.register.Client;

public class ClientItemController extends BaseController<Client> {
    @FXML private Label name;
    @FXML private Text cpf;

    @Override
    public void initData() throws IOException {
        this.name.setText(this.object.getName());
        this.cpf.setText(this.object.getFormattedCpf());
    }

    public void open(Event e) throws IOException {
        this.openModal("client", "Cliente", this.object);
    }
}
