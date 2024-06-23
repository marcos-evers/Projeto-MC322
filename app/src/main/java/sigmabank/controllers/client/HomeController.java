package sigmabank.controllers.client;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.investment.ClientInvestmentMultiton;
import sigmabank.model.investment.Investment;
import sigmabank.model.investment.InfoInvestments.InfoInvest;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Client;
import sigmabank.net.InvestmentConnection;
import sigmabank.net.LoanConnection;

public class HomeController extends BaseController<Client> {
    @FXML private Text greeting;

    @FXML private Text balance;
    
    @FXML private VBox loansBox;
    @FXML private Text loansTotal;

    @FXML private VBox investmentsBox;
    @FXML private Text investmentsTotal;

    @Override
    public void initData() throws IOException {
        this.greeting.setText("Olá, " + this.object.getName().split(" ")[0] + "!");
        this.balance.setText("R$ " + this.object.getBalance());
        
        InvestmentConnection connInvest = new InvestmentConnection("localhost:8000/investment");
        List<Investment> investments = connInvest.fetch(Map.of(
            "uuid", this.object.getUUID().toString()
        ));

        LoanConnection connLoan = new LoanConnection("localhost:8000/loan");
        List<Loan> loans = connLoan.fetch(Map.of(
            "uuid", this.object.getUUID().toString()
        ));

        // hardcoded test:
        // UUID random = UUID.randomUUID();
        // List<Investment> investments = new ArrayList<>();
        // investments.add(new Investment("dogecoin", BigDecimal.valueOf(123.4), random, LocalDate.now()));

        // List<Loan> loans = new ArrayList<>();
        // loans.add(new Loan(BigDecimal.valueOf(11000.0), random, LocalDate.now().minusYears(1)));

        BigDecimal investmentTotal = BigDecimal.ZERO;
        for (Investment investment : investments) {
            investmentTotal = investmentTotal.add(investment.getValue()).subtract(investment.getInvestedValue());
            investmentsBox.getChildren().add(this.getView("client/investment/investment_item", investment, this.object));
        }
        this.investmentsTotal.setText("Seus investimentos já renderam R$ " + investmentTotal.toString());

        BigDecimal loanTotal = BigDecimal.ZERO;
        for (Loan loan : loans) {
            loanTotal = loanTotal.add(loan.getAmount());
            loansBox.getChildren().add(this.getView("client/loan/loan_item", loan, this.object));
        }
        this.loansTotal.setText("Seus empréstimos somam R$ " + loanTotal.toString());
    }
    
    public void newInvestment(ActionEvent e) throws IOException {
        final List<InfoInvest> infoInvestments = ClientInvestmentMultiton
            .getInstance()
            .getNotExistingAssetInvestments(this.object.getUUID());
        
        infoInvestments.addAll(
            ClientInvestmentMultiton
                .getInstance()
                .getNotExistingRateInvestments(this.object.getUUID())
        );

        this.openModal("client/investment/choose", "Escolher investimento", infoInvestments, this.object);
    }
    
    public void requestLoan(ActionEvent e) throws IOException {
        this.loadView("client/loan/request", "Solicitar empréstimo", this.object);
    }

    public void leave(ActionEvent e) {
        Platform.exit();
    }
}
