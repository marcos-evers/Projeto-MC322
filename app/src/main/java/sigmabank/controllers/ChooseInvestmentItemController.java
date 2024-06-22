package sigmabank.controllers;

import java.io.IOException;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import sigmabank.model.investment.InfoInvestments.InfoInvest;

public class ChooseInvestmentItemController extends BaseController<InfoInvest> {
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

    public void open(Event e) throws IOException {
        final String destinyView = this.object.getRate() != null ? "new_rate_investment" : "new_asset_investment";
        this.openModal(destinyView, "Novo investimento", this.object, this.additionalData);
    }
}
