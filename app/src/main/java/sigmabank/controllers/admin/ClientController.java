package sigmabank.controllers.admin;

import java.io.IOException;
import java.util.Map;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.register.Client;
import sigmabank.net.ApprovalConnection;
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

    public void deny(Event e) throws IOException {
        ApprovalConnection conn = new ApprovalConnection("http://localhost:8000/toapproval");
        conn.send(
            Map.of(
                "type", "client",
                "uuid", this.object.getUUID().toString(),
                "isapproved", "false"
            )
        );

        this.leave(e);
    }

    public void approve(Event e) throws IOException {
        ApprovalConnection conn = new ApprovalConnection("http://localhost:8000/toapproval");
        conn.send(
            Map.of(
                "type", "client",
                "uuid", this.object.getUUID().toString(),
                "isapproved", "true"
            )
        );
        
        this.leave(e);
    }

    public void leave(Event e) {
        this.stage.close();
    }
}
