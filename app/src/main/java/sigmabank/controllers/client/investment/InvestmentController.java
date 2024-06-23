package sigmabank.controllers.client.investment;

import java.io.IOException;
import java.time.format.DateTimeFormatter;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.investment.Investment;
import sigmabank.model.register.Client;

public class InvestmentController extends BaseController<Investment> {
    @FXML private Text investmentName;
    @FXML private Text startDate;
    @FXML private Text invested;
    @FXML private Text value;
    @FXML private Text retrieved;

    @Override
    public void initData() throws IOException {
        this.investmentName.setText(this.object.getName());
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        this.startDate.setText("Iniciado em " + this.object.getStartDate().format(formatter));

        this.invested.setText("R$ " + this.object.getInvestedValue());
        this.value.setText("R$ " + this.object.getValue());
        this.retrieved.setText("R$ " + this.object.getRetrievedValue());
    }
    
    public void leave(ActionEvent e) {
        this.stage.close();
    }

    public void investMore(Event e) throws IOException {
        this.openModal("client/investment/invest_more", "Aplicar", this.object, (Client) this.additionalData);
    }

    public void retrieve(Event e) throws IOException {
        // TODO
        this.openModal(null, null, null);
    }
}
