package sigmabank.controllers.admin;

import java.io.IOException;
import java.util.Map;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.loan.Loan;
import sigmabank.net.ApprovalConnection;
import sigmabank.utils.DateFormatter;
import sigmabank.utils.Rounder;

public class LoanController extends BaseController<Loan> {
    @FXML private Text startDate;
    @FXML private Text value;
    @FXML private Text clientUuid;

    @Override
    public void initData() throws IOException {
        this.startDate.setText(DateFormatter.format(this.object.getStartDay()));        
        this.value.setText("R$ " + Rounder.round(this.object.getValue()));
        this.clientUuid.setText(Rounder.round(this.object.getClientUUID()));
    }

    public void deny(Event e) throws IOException {
        ApprovalConnection conn = new ApprovalConnection("http://localhost:8000/toapproval");
        conn.send(Map.of(
            "type", "loan",
            "uuid", this.object.getLoanUUID().toString(),
            "isapproved", "false"
        ));
        
        this.leave(e);
    }
    
    public void approve(Event e) throws IOException {
        ApprovalConnection conn = new ApprovalConnection("http://localhost:8000/toapproval");
        conn.send(Map.of(
            "type", "loan",
            "uuid", this.object.getLoanUUID().toString(),
            "isapproved", "true"
        ));

        this.leave(e);
    }

    public void leave(Event e) {
        this.stage.close();
    }
}
