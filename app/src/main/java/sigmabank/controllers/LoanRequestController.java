package sigmabank.controllers;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sigmabank.model.loan.Loan;

public class LoanRequestController extends BaseController<Loan> {
    @FXML private Text startDate;
    @FXML private Text value;
    @FXML private Text clientUuid;

    @Override
    public void initData() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.startDate.setText(this.object.getStartDay().format(formatter));
        
        this.value.setText(this.object.getValue().toString());
        this.clientUuid.setText(this.object.getClientUUID().toString());
    }

    public void deny(Event e) {
        // TODO delete this loan from "loansToApproval" (or smth like that) table
    }
    
    public void approve(Event e) {
        // TODO delete from table "loansToApproval" (or smth like that)
        // and add to the table of regular loans
    }

    public void leave(ActionEvent e) {
        this.stage.close();
    }
}
