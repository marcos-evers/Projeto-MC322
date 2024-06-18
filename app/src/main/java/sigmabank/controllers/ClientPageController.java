package sigmabank.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Client;
import sigmabank.utils.readers.AssetInvestmentReader;
import sigmabank.utils.readers.ClientReader;
import sigmabank.utils.readers.LoanReader;
import sigmabank.utils.readers.RateInvestmentReader;
import sigmabank.model.investment.Investment;

public class ClientPageController extends BaseController {
    @FXML private Text greeting;
    @FXML private VBox loansBox;
    @FXML private Text loansTotal;
    @FXML private VBox investmentsBox;
    @FXML private Text investmentsTotal;

    public void initData(String cpf) throws IOException {
        // ClientReader clientReader = new ClientReader();
        // Client client = clientReader.queryClient("./", cpf);

        greeting.setText("Olá, " + cpf + "!");
        // greeting.setText("Olá, " + client.getName().split(" ")[0] + "!");

        // LoanReader loanReader = new LoanReader();
        // List<Loan> loans = loanReader.readFromXML("./", client.getUUID().toString());
    
        // FXMLLoader loanLoader;
        // BigDecimal loanTotal = BigDecimal.ZERO;
        // for (Loan loan : loans) {
        //     loanTotal = loanTotal.add(loan.getAmount());

        //     loanLoader = new FXMLLoader(getClass().getResource("/sigmabank/views/loan_item.fxml"));
        //     loanLoader.getNamespace().put("startDay", loan.getStartDay().toString());
        //     loanLoader.getNamespace().put("montant", loan.getAmount().toString());
        //     loansBox.getChildren().add(loanLoader.load());
        // }

        // loansTotal.setText("Seus empréstimos somam R$ " + loanTotal.toString());

        // AssetInvestmentReader assetInvestmentReader = new AssetInvestmentReader();
        // RateInvestmentReader rateInvestmentReader = new RateInvestmentReader();

        // List<Investment> investments = assetInvestmentReader.readFromXML("./", client.getUUID().toString());

        // FXMLLoader investmentLoader;
        // BigDecimal investmentTotal = BigDecimal.ZERO;
        // for (Loan invesment : invesments) {}

    }

    public void leave(ActionEvent e) {
        Platform.exit();
    }
}
