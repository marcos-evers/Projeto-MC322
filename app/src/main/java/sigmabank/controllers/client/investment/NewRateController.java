package sigmabank.controllers.client.investment;

import java.io.IOException;
import java.math.BigDecimal;
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

public class NewRateController extends BaseController<InfoInvest> {
    @FXML private Text greeting;
    @FXML private Text name;
    @FXML private Text rate;
    @FXML private Text frequency;
    @FXML private TextField value;
    @FXML private Text balance;

    @Override
    public void initData() throws IOException {
        final Client client = (Client) this.additionalData;
        this.greeting.setText("Olá, " + client.getName() + "!");
        this.name.setText(this.object.getName());
        this.rate.setText(this.object.getRate().multiply(BigDecimal.valueOf(100.0)) + "%");
        this.frequency.setText(this.object.getFrequencyType().toString());
        this.balance.setText("Saldo em conta: R$ " + Rounder.round(client.getBalance()));
    }

    public void goBack(Event e) throws IOException {
        Stage parentStage = (Stage) this.stage.getOwner();

        this.loadView("client/home", "Home", this.additionalData);

        parentStage.close();
    }

    public void confirm(Event e) throws IOException {
        BigDecimal valueToInvest;
        try {
            valueToInvest = new BigDecimal(value.getText());
        } catch (Exception err) {
            BaseController.errorDialog("Insira um valor numérico.");
            return;
        }

        if (valueToInvest.compareTo(valueToInvest) <= 0) {
            BaseController.errorDialog("Valor inválido.");
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
            "invtype", "rate",
            "type", this.object.getRateType(),
            "investedvalue", valueToInvest,
            "startdate", LocalDate.now()
        ));

        client.setBalance(client.getBalance().subtract(valueToInvest));

        this.goBack(e);
    }

    public void leave(ActionEvent e) throws IOException {
        this.loadView("auth/login", "Login", null);
    }
}
