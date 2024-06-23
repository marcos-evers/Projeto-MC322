package sigmabank.controllers.admin;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.loan.Loan;
import sigmabank.utils.Rounder;

public class LoanItemController extends BaseController<Loan> {
    @FXML private Label clientUuid;
    @FXML private Text requestedValue;

    @Override
    public void initData() throws IOException {
        this.clientUuid.setText(this.object.getClientUUID().toString());
        this.requestedValue.setText("R$ " + Rounder.round(this.object.getValue()));
    }

    public void open(Event e) throws IOException {
        this.openModal("admin/loan", "Solicitação de empréstimo", this.object, this.additionalData);
    }
}
