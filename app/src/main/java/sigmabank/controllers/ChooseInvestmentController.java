package sigmabank.controllers;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import sigmabank.model.investment.InfoInvestments.InfoInvest;

public class ChooseInvestmentController extends BaseController<List<InfoInvest>> {
    @FXML private VBox investmentItems;

    @Override
    public void initData() throws IOException {
        for (InfoInvest investment : this.object) {
            investmentItems.getChildren().add(this.getView("choose_investment_item", investment, this.additionalData));
        }
    }

    public void leave(ActionEvent e) {
        this.stage.close();
    }
}
