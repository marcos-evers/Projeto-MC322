package sigmabank.controllers;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import sigmabank.model.loan.Loan;

public class LoanRequestItemController extends BaseController<Loan> {
    @FXML private Label clientUuid;
    @FXML private Text requestedValue;

    @Override
    public void initData() throws IOException {
        this.clientUuid.setText(this.object.getClientUUID().toString());
        this.requestedValue.setText(this.object.getValue().toString());
    }

    public void open(Event e) throws IOException {
        this.openModal("loan_request", "Solicitação de empréstimo", this.object);
    }
}
