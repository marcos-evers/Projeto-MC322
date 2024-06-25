package sigmabank.controllers.admin;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.register.Client;

public class ClientItemController extends BaseController<Client> {
    @FXML private Label name;
    @FXML private Text cpf;

    @Override
    public void initData() throws IOException {
        this.name.setText(this.object.getName());
        this.cpf.setText(this.object.getFormattedCpf());
    }

    /**
     * 
     * @param e
     * @throws IOException
     */
    public void open(Event e) throws IOException {
        this.openModal("admin/client", "Cliente", this.object, this.additionalData);
    }
}
