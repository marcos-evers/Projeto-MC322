package sigmabank.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sigmabank.model.investment.Investment;
import sigmabank.model.investment.InfoInvestments.InfoInvest;
import sigmabank.model.investment.InfoInvestments.ReaderAssetInfo;
import sigmabank.model.investment.InfoInvestments.ReaderRateInfo;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Client;

public class ClientPageController extends BaseController<Client> {
    @FXML private Text greeting;

    @FXML private Text balance;
    
    @FXML private VBox loansBox;
    @FXML private Text loansTotal;

    @FXML private VBox investmentsBox;
    @FXML private Text investmentsTotal;

    private List<Loan> loans = new ArrayList<>();
    private List<Investment> investments = new ArrayList<>();

    @Override
    public void initData() throws IOException {
        // // TODO replace cpf with client's queried name
        this.greeting.setText("Olá, " + this.object.getName() + "!");

        this.balance.setText("R$ " + this.object.getBalance());

        this.investments.add(new Investment("dogecoin", BigDecimal.valueOf(1000.0), UUID.fromString("6e8dfd5d-3ced-4499-bd9a-94e5b08595c0"), LocalDate.now()));
        BigDecimal investmentTotal = BigDecimal.ZERO;
        for (Investment investment : this.investments) {
            investmentTotal = investmentTotal.add(investment.getValue()).subtract(investment.getInvestedValue());
            investmentsBox.getChildren().add(this.getView("investment_item", investment, this.object));
        }
        this.investmentsTotal.setText("Seus investimentos já renderam R$ " + investmentTotal.toString());

        this.loans.add(new Loan(BigDecimal.valueOf(10000), UUID.fromString("6e8dfd5d-3ced-4499-bd9a-94e5b08595c0"), LocalDate.now()));
        BigDecimal loanTotal = BigDecimal.ZERO;
        for (Loan loan : this.loans) {
            loanTotal = loanTotal.add(loan.getAmount());
            loansBox.getChildren().add(this.getView("loan_item", loan, this.object));
        }
        this.loansTotal.setText("Seus empréstimos somam R$ " + loanTotal.toString());
    }
    
    public void newInvestment(ActionEvent e) throws IOException {
        final List<InfoInvest> infoInvestments = ReaderRateInfo.readRateInvestments();
        infoInvestments.addAll(ReaderAssetInfo.readAssetInvestments());
        // TODO filter to exhibit only valid investments (the ones the client doesn't have yet)
        this.openModal("choose_investment", "Escolher investimento", infoInvestments, this.object);
    }
    
    public void requestLoan(ActionEvent e) throws IOException {
        this.loadView("request_loan", "Solicitar empréstimo", this.object);
    }

    public void leave(ActionEvent e) {
        Platform.exit();
    }
}
