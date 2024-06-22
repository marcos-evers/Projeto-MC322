package sigmabank.controllers;

import java.io.IOException;
import java.math.BigDecimal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Client;

public class PaymentController extends BaseController<Loan> {
    @FXML private TextField value;
    @FXML private Text balance; 

    @Override
    public void initData() throws IOException {
        this.balance.setText("Saldo em conta: R$ " + ((Client)this.additionalData).getBalance());
    }

    public void confirm(ActionEvent e) {
        BigDecimal valueToPay;
        try {
            valueToPay = new BigDecimal(value.getText());
        } catch (Exception err) {
            BaseController.errorDialog("Insira um valor numérico.");
            return;
        }

        if (valueToPay.compareTo(valueToPay) <= 0) {
            BaseController.errorDialog("Valor inválido.");
            return;
        }

        final Client client = (Client) this.additionalData;
        if (client.getBalance().compareTo(valueToPay) < 0) {
            BaseController.errorDialog("Saldo insuficiente.");            
            return;
        }
        
        // TODO update the loan and client's balance on database
    }
    
    public void leave(ActionEvent e) {
        this.stage.close();
    }
}
