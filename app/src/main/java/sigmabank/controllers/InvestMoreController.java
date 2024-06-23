package sigmabank.controllers;

import java.io.IOException;
import java.math.BigDecimal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sigmabank.model.investment.Investment;
import sigmabank.model.register.Client;;

public class InvestMoreController extends BaseController<Investment> {
    @FXML private TextField value;
    @FXML private Text balance;

    @Override
    public void initData() throws IOException {
        this.balance.setText("Saldo em conta: R$ " + ((Client)this.additionalData).getBalance());
    }

    public void confirm(ActionEvent e) {
        BigDecimal valueToInvest;
        try {
            valueToInvest = new BigDecimal(value.getText());
        } catch (Exception err) {
            BaseController.errorDialog("Insira um valor numérico.");
            return;
        }

        if (valueToInvest.compareTo(BigDecimal.ZERO) <= 0) {
            BaseController.errorDialog("Valor inválido.");
            return;
        }

        final Client client = (Client) this.additionalData;
        if (client.getBalance().compareTo(valueToInvest) < 0) {
            BaseController.errorDialog("Saldo insuficiente.");            
            return;
        }
        
        // TODO update the investment and client's balance on database
        BigDecimal newBalance = client.getBalance().subtract(valueToInvest);
        client.setBalance(newBalance);
    }
    
    public void leave(ActionEvent e) {
        this.stage.close();
    }
}
