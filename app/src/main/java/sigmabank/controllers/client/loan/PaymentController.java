package sigmabank.controllers.client.loan;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Client;
import sigmabank.net.LoanPaymentConnection;
import sigmabank.utils.Rounder;

public class PaymentController extends BaseController<Loan> {
    @FXML private TextField value;
    @FXML private Text balance; 

    @Override
    public void initData() throws IOException {
        this.balance.setText("Saldo em conta: R$ " + Rounder.round(((Client)this.additionalData).getBalance()));
    }

    public void confirm(ActionEvent e) throws IOException {
        BigDecimal valueToPay;
        try {
            valueToPay = new BigDecimal(value.getText());
        } catch (Exception err) {
            BaseController.errorDialog("Insira um valor numérico.");
            return;
        }

        if (valueToPay.compareTo(BigDecimal.ZERO) <= 0) {
            BaseController.errorDialog("Valor inválido.");
            return;
        }

        final Client client = (Client) this.additionalData;
        if (client.getBalance().compareTo(valueToPay) < 0) {
            BaseController.errorDialog("Saldo insuficiente.");            
            return;
        }
        
        LoanPaymentConnection conn = new LoanPaymentConnection("http://localhost:8000/loan/payment");
        conn.send(Map.of(
            "clientuuid", this.object.getClientUUID().toString(),
            "loanuuid", this.object.getLoanUUID().toString(),
            "value", valueToPay
        ));

        client.setBalance(client.getBalance().subtract(valueToPay));
        this.object.payLoan(valueToPay);

        this.leave(e);
    }
    
    public void leave(ActionEvent e) {
        this.stage.close();
    }
}
