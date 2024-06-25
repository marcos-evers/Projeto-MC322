package sigmabank.controllers.client.investment;

import java.io.IOException;
import java.math.BigDecimal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.investment.Investment;
import sigmabank.model.register.Client;
import sigmabank.net.InvestmentOperationConnection;
import sigmabank.utils.Rounder;

public class InvestMoreController extends BaseController<Investment> {
    @FXML private TextField value;
    @FXML private Text balance;

    @Override
    public void initData() throws IOException {
        this.balance.setText("Saldo em conta: R$ " + Rounder.round(((Client)this.additionalData).getBalance()));
    }

    /**
     * 
     * @param e
     * @throws IOException
     */
    public void confirm(ActionEvent e) throws IOException {
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

        InvestmentOperationConnection conn = new InvestmentOperationConnection("http://localhost:8000/investment/operate");
        conn.sendOperation(this.object, "investmore", valueToInvest);
        
        client.setBalance(client.getBalance().subtract(valueToInvest));

        this.leave(e);
    }
    
    /**
     * 
     * @param e
     */
    public void leave(ActionEvent e) {
        this.stage.close();
    }
}
