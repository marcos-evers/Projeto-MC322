package sigmabank.controllers.client.investment;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import sigmabank.controllers.BaseController;
import sigmabank.model.investment.InfoInvestments.InfoInvest;

public class ChooseController extends BaseController<List<InfoInvest>> {
    @FXML private VBox investmentItems;

    @Override
    public void initData() throws IOException {
        for (InfoInvest investment : this.object) {
            investmentItems.getChildren().add(this.getView("client/investment/choose_item", investment, this.additionalData));
        }
    }

    /**
     * 
     * @param e
     */
    public void leave(ActionEvent e) {
        this.stage.close();
    }
}
