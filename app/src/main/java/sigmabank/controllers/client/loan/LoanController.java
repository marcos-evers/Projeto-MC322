package sigmabank.controllers.client.loan;

import java.io.IOException;
import java.math.BigDecimal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.loan.Loan;
import sigmabank.utils.DateFormatter;
import sigmabank.utils.Rounder;

public class LoanController extends BaseController<Loan> {
    @FXML private Text startDate;
    @FXML private Text initialValue;
    @FXML private Text fee;
    @FXML private Text amount;

    @Override
    public void initData() throws IOException {
        this.startDate.setText("Realizado em " + DateFormatter.format(this.object.getStartDay()));

        this.initialValue.setText("R$ " + Rounder.round(this.object.getValue()));
        // TODO see if this fee exhibition is right
        this.fee.setText(this.object.getFee().multiply(BigDecimal.valueOf(100.0)) + "%");
        this.amount.setText("R$ " + Rounder.round(this.object.getAmount()));
    }

    public void pay(ActionEvent e) throws IOException {
        this.openModal("client/loan/payment", "Realizar pagamento", this.object, this.additionalData);
    }

    public void leave(ActionEvent e) {
        this.stage.close();
    }
}