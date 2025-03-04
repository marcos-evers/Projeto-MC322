package sigmabank.controllers.client.investment;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sigmabank.controllers.BaseController;
import sigmabank.model.investment.InfoInvestments.InfoInvest;
import sigmabank.model.register.Client;
import sigmabank.net.InvestmentConnection;
import sigmabank.utils.Rounder;

public class NewAssetController extends BaseController<InfoInvest> {
    @FXML private Text greeting;
    @FXML private Text name;
    @FXML private Text assetValue;
    @FXML private TextField value;
    @FXML private Text balance;
    @FXML private Text amount;

    @Override
    public void initData() throws IOException {
        this.greeting.setText("Olá, " + ((Client)this.additionalData).getName().split(" ")[0] + "!");
        this.name.setText(this.object.getName());
        this.assetValue.setText("R$ " + Rounder.round(this.object.getAssetValue()));
        value.textProperty().addListener((observable, oldValue, newValue) -> valueChanged(newValue));
        this.balance.setText("R$ " + Rounder.round(((Client)this.additionalData).getBalance()));
    }

    /**
     * 
     * @param newValue
     * @return null or BigDecimal
     */
    private BigDecimal getValueToInvest(String newValue) {
        try {
            return new BigDecimal(newValue);
        } catch (Exception err) {
            return null;
        }
    }

    /**
     * 
     * @param e
     * @throws IOException
     */
    public void goBack(Event e) throws IOException {
        Stage parentStage = (Stage) this.stage.getOwner();

        this.loadView("client/home", "Home", (Client) this.additionalData);

        parentStage.close();
    }

    /**
     * 
     * @param newValue
     * @throws IOException
     */
    public void valueChanged(String newValue) {
        BigDecimal valueToInvest = this.getValueToInvest(newValue);
        BigDecimal quantity = this.getQuantity(valueToInvest);

        this.amount.setText(quantity == null ? "-" : quantity.toString());
    }

    /**
     * 
     * @param valueToInvest
     * @return
     */
    private BigDecimal getQuantity(BigDecimal valueToInvest) {
        if (valueToInvest == null || valueToInvest.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        return valueToInvest.divide(this.object.getAssetValue(), new MathContext(10, RoundingMode.HALF_UP));
    }

    /**
     * 
     * @param e
     * @throws IOException
     */
    public void confirm(Event e) throws IOException {
        BigDecimal valueToInvest = this.getValueToInvest(this.value.getText());
        BigDecimal quantity = this.getQuantity(valueToInvest);
        if (quantity == null) {
            BaseController.errorDialog("Insira um valor válido.");
            return;
        }

        final Client client = (Client)this.additionalData;
        if (client.getBalance().compareTo(valueToInvest) < 0) {
            BaseController.errorDialog("Saldo insuficiente.");            
            return;
        }

        InvestmentConnection conn = new InvestmentConnection("http://localhost:8000/investment");
        conn.send(Map.of(
            "clientUUID", client.getUUID(),
            "invtype", "asset",
            "type", this.object.getAssetType(),
            "investedvalue", valueToInvest,
            "startdate", LocalDate.now()
        ));

        client.setBalance(client.getBalance().subtract(valueToInvest));

        this.goBack(e);
    }

    /**
     * 
     * @param e
     * @throws IOException
     */
    public void leave(ActionEvent e) throws IOException {
        this.loadView("auth/login", "Login", null);
    }
}
