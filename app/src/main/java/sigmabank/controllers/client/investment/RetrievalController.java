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

public class RetrievalController extends BaseController<Investment> {
    @FXML private TextField value;
    @FXML private Text available;

    @Override
    public void initData() throws IOException {
        this.available.setText("R$ " + Rounder.round(this.object.getValue()));
    }

    /**
     * 
     * @param e
     * @throws IOException
     */
    public void confirm(ActionEvent e) throws IOException {
        BigDecimal valueToRetrieve;
        try {
            valueToRetrieve = new BigDecimal(value.getText());
        } catch (Exception err) {
            BaseController.errorDialog("Insira um valor numérico.");
            return;
        }

        if (valueToRetrieve.compareTo(BigDecimal.ZERO) <= 0) {
            BaseController.errorDialog("Valor inválido.");
            return;
        }

        if (this.object.getValue().compareTo(valueToRetrieve) < 0) {
            BaseController.errorDialog("Valor indisponível.");
            return;
        }

        final Client client = (Client) this.additionalData;
        
        InvestmentOperationConnection conn = new InvestmentOperationConnection("http://localhost:8000/investment/operate");
        conn.sendOperation(this.object, "retrieve", valueToRetrieve);

        client.setBalance(client.getBalance().add(valueToRetrieve));

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
