package sigmabank.controllers.client.investment;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.investment.Investment;
import sigmabank.model.register.Client;
import sigmabank.utils.DateFormatter;
import sigmabank.utils.Rounder;

public class InvestmentController extends BaseController<Investment> {
    @FXML private Text investmentName;
    @FXML private Text startDate;
    @FXML private Text invested;
    @FXML private Text value;
    @FXML private Text retrieved;

    @Override
    public void initData() throws IOException {
        this.investmentName.setText(this.object.getName());
        
        this.startDate.setText("Iniciado em " + DateFormatter.format(this.object.getStartDate()));
        this.invested.setText("R$ " + Rounder.round(this.object.getInvestedValue()));
        this.value.setText("R$ " + Rounder.round(this.object.getValue()));
        this.retrieved.setText("R$ " + Rounder.round(this.object.getRetrievedValue()));
    }
    
    public void leave(ActionEvent e) {
        this.stage.close();
    }

    public void investMore(Event e) throws IOException {
        this.openModal("client/investment/invest_more", "Aplicar", this.object, (Client) this.additionalData);
    }

    public void retrieve(Event e) throws IOException {
        this.openModal("retrieve", "Resgatar investimento", this.object, (Client) this.additionalData);
    }
}
