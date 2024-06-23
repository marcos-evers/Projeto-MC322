package sigmabank.controllers.client.investment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.investment.RateInvestment;
import sigmabank.model.investment.InfoInvestments.InfoInvest;
import sigmabank.model.register.Client;
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
        this.loadView("client/home", "Home", this.additionalData);
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

        RateInvestment investment = new RateInvestment(
            this.object.getName(),
            valueToInvest,
            client.getUUID(),
            LocalDate.now(),
            this.object.getRate(),
            this.object.getFrequencyType(),
            this.object.getRateType()
        );

        // call database stuff

        this.goBack(e);
    }

    public void leave(ActionEvent e) {
        Platform.exit();
    }
}
