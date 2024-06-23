package sigmabank.controllers.admin;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.loan.Loan;
import sigmabank.utils.DateFormatter;

public class LoanController extends BaseController<Loan> {
    @FXML private Text startDate;
    @FXML private Text value;
    @FXML private Text clientUuid;

    @Override
    public void initData() throws IOException {
        this.startDate.setText(DateFormatter.format(this.object.getStartDay()));        
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
