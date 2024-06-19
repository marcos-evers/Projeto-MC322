package sigmabank.controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sigmabank.model.register.Client;

public class ClientPageController extends BaseController<Client> {
    @FXML private Text greeting;
    
    @FXML private VBox loansBox;
    @FXML private Text loansTotal;

    @FXML private VBox investmentsBox;
    @FXML private Text investmentsTotal;

    @Override
    public void initData(Client client) throws IOException {
        // ClientReader clientReader = new ClientReader();
        // Client client = null;clientReader.queryClient("/server/src/main/resources/database/Clients.xml", cpf);

        // TODO replace cpf with client's queried name
        greeting.setText("Olá, " + client.getCpf() + "!");

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
        // List<Investment> investments = (List<Investment>)(Object)assetInvestmentReader.readFromXML("/server/src/main/resources/database/AssetInvestment.xml", client.getUUID().toString());
        // FXMLLoader investmentLoader;
        // BigDecimal investmentTotal = BigDecimal.ZERO;
        // for (Investment investment : investments) {
        //     investmentTotal = investmentTotal.add(investment.getRetrievedValue());
        //     investmentLoader = new FXMLLoader(getClass().getResource(""));
        // }
        loansTotal.setText("Seus empréstimos somam R$ " + "13000.0");
        investmentsTotal.setText("Seus investimentos já renderam R$ " + 31.5);
    }
    
    public void newInvestment(ActionEvent e) {
        System.out.println("New investment");
    }
    
    public void requestLoan(ActionEvent e) {
        System.out.println("Request loan");
    }

    public void leave(ActionEvent e) {
        Platform.exit();
    }
}
