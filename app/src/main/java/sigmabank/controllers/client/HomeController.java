package sigmabank.controllers.client;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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
import sigmabank.utils.Rounder;

public class HomeController extends BaseController<Client> {
    @FXML private Text greeting;

    @FXML private Text balance;
    
    @FXML private VBox loansBox;
    @FXML private Text loansTotal;

    @FXML private VBox investmentsBox;
    @FXML private Text investmentsTotal;

    private void reset() {
        for (int i = investmentsBox.getChildren().size() - 1; i > 0; --i) {
            investmentsBox.getChildren().remove(i);
        }

        for (int i = loansBox.getChildren().size() - 1; i > 0; --i) {
            loansBox.getChildren().remove(i);
        }
    }

    @Override
    public void initData() throws IOException {
        this.greeting.setText("Olá, " + this.object.getName().split(" ")[0] + "!");
        this.balance.setText("R$ " + Rounder.round(this.object.getBalance()));
        
        InvestmentConnection connInvest = new InvestmentConnection("http://localhost:8000/investment");
        List<Investment> investments = connInvest.fetch(Map.of(
            "clientUUID", this.object.getUUID().toString()
        ));
        
        LoanConnection connLoan = new LoanConnection("http://localhost:8000/loan");
        List<Loan> loans = connLoan.fetch(Map.of(
            "clientUUID", this.object.getUUID().toString()
        ));
        
        investments.sort(Comparator.comparing(Investment::getStartDate).reversed());
        loans.sort(Comparator.comparing(Loan::getStartDay).reversed());

        this.reset();

        BigDecimal investmentTotal = BigDecimal.ZERO;
        for (Investment investment : investments) {
            investmentTotal = investmentTotal.add(investment.calculateProfit());
            investmentsBox.getChildren().add(this.getView("client/investment/investment_item", investment, this.object));
        }
        this.investmentsTotal.setText("Seus investimentos já renderam R$ " + Rounder.round(investmentTotal));
        
        BigDecimal loanTotal = BigDecimal.ZERO;
        for (Loan loan : loans) {
            loanTotal = loanTotal.add(loan.getAmount());
            loansBox.getChildren().add(this.getView("client/loan/loan_item", loan, this.object));
        }
        this.loansTotal.setText("Seus empréstimos somam R$ " + Rounder.round(loanTotal));
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

    public void leave(ActionEvent e) throws IOException {
        this.loadView("auth/login", "Login", null);
    }
}
