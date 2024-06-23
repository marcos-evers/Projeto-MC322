package sigmabank.controllers;

import java.io.IOException;
import java.math.BigDecimal;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sigmabank.model.investment.Investment;
import sigmabank.model.register.Client;

public class InvestimentRetrieveController extends BaseController<Investment> {
    @FXML private TextField value;
    @FXML private Text maxValueToRetrive;

    @Override
    public void initData() throws IOException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'initData'");
    }

    public void confirm(ActionEvent e) {
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

        final Client client = (Client) this.additionalData;
        //TODO comparar se o dinheiro no investimento é suficiente
        BigDecimal newBalance = client.getBalance().add(valueToRetrieve);
        client.setBalance(newBalance);
    }

    public void leave(ActionEvent e) {
        this.stage.close();
    }
}
