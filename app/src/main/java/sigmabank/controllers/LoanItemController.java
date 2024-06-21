package sigmabank.controllers;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import sigmabank.model.loan.Loan;

public class LoanItemController extends BaseController<Loan> {
    @FXML private Text lastUpdated;
    @FXML private Label amount;

    @Override
    public void initData() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.lastUpdated.setText(this.object.getLastUpdateDate().format(formatter));
        
        this.amount.setText("R$ " + this.object.getAmount());
    }
    
    public void open(Event e) throws IOException {
        this.openModal("loan", "Empréstimo", this.object);
    }
}
