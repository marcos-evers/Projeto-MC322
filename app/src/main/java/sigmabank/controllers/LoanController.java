package sigmabank.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sigmabank.model.loan.Loan;

public class LoanController extends BaseController<Loan> {
    @FXML private Text startDate;
    @FXML private Text initialValue;
    @FXML private Text fee;
    @FXML private Text amount;

    @Override
    public void initData() throws IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.startDate.setText("Realizado em " + this.object.getStartDay().format(formatter));

        this.initialValue.setText("R$ " + this.object.getValue());
        // TODO see if this fee exhibition is right
        this.fee.setText(this.object.getFee().multiply(BigDecimal.valueOf(100.0)) + "%");
        this.amount.setText("R$ " + this.object.getAmount());
    }

    public void leave(ActionEvent e) {
        this.stage.close();
    }
}