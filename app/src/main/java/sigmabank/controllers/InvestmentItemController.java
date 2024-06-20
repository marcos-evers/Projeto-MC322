package sigmabank.controllers;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import sigmabank.model.investment.Investment;

public class InvestmentItemController extends BaseController<Investment> {
    @FXML private Label name;
    @FXML private Text value;

    @Override
    public void initData() throws IOException {
        this.name.setText(this.object.getName());
        this.value.setText(this.object.getValue().toString());
    }
    
    public void open(Event e) throws IOException {
        BaseController.openModal("investment", "Investimento", this, this.object);
    }
}
