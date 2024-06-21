package sigmabank.controllers;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import sigmabank.model.investment.Investment;
import sigmabank.model.register.Client;

public class InvestmentItemController extends BaseController<Investment> {
    @FXML private Label name;
    @FXML private Text value;

    @Override
    public void initData() throws IOException {
        this.name.setText(this.object.getName());
        this.value.setText("R$ " + this.object.getValue());
    }
    
    public void open(Event e) throws IOException {
        this.openModal("investment", "Investimento", this.object, (Client) this.additionalData);
    }
}
