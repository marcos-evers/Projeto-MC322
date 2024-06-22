package sigmabank.controllers;

import java.io.IOException;
import java.math.BigDecimal;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sigmabank.model.investment.InfoInvestments.InfoInvest;
import sigmabank.model.register.Client;

public class NewRateInvestmentController extends BaseController<InfoInvest> {
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
        this.balance.setText("Saldo em conta: R$ " + client.getBalance());
    }

    public void goBack(ActionEvent e) throws IOException {
        this.loadView("client_page", "Home", this.additionalData);
    }

    public void confirm(Event e) {
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

        // call database stuff
    }

    public void leave(ActionEvent e) {
        Platform.exit();
    }
}
