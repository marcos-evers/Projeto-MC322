package sigmabank.controllers.client.loan;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.loan.Loan;
import sigmabank.utils.DateFormatter;
import sigmabank.utils.Rounder;

public class LoanItemController extends BaseController<Loan> {
    @FXML private Text lastUpdated;
    @FXML private Label amount;

    @Override
    public void initData() throws IOException {
        this.lastUpdated.setText(DateFormatter.format(this.object.getLastUpdateDate()));        
        this.amount.setText("R$ " + Rounder.round(this.object.getAmount()));
    }
    
    public void open(Event e) throws IOException {
        this.openModal("client/loan/loan", "Empréstimo", this.object, this.additionalData);
    }
}
