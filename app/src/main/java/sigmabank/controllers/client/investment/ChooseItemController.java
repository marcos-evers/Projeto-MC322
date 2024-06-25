package sigmabank.controllers.client.investment;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.investment.InfoInvestments.InfoInvest;

public class ChooseItemController extends BaseController<InfoInvest> {
    @FXML private Label name;
    @FXML private Text rateOrAsset;

    @Override
    public void initData() throws IOException {
        this.name.setText(this.object.getName());
        
        if (this.object.getRate() != null) {
            // it's a rate investment
            this.rateOrAsset.setText("Taxa");
        } else {
            // it's an asset investment
            this.rateOrAsset.setText("Ação");
        }
    }

    /**
     * 
     * @param e
     * @throws IOException
     */
    public void open(Event e) throws IOException {
        String destinyView = "client/investment/new_";
        destinyView += this.object.getRate() != null ? "rate" : "asset";
        this.openModal(destinyView, "Novo investimento", this.object, this.additionalData);
    }
}
