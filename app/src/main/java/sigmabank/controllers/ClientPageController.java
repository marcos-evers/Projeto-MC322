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
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Client;

public class ClientPageController extends BaseController<Client> {
    @FXML private Text greeting;
    
    @FXML private VBox loansBox;
    @FXML private Text loansTotal;

    @FXML private VBox investmentsBox;
    @FXML private Text investmentsTotal;

    private List<Loan> loans = new ArrayList<>();
    private List<Investment> investments = new ArrayList<>();

    @Override
    public void initData() throws IOException {
        // // TODO replace cpf with client's queried name
        this.greeting.setText("Olá, " + this.object.getCpf() + "!");

        this.investments.add(new Investment("dogecoin", BigDecimal.valueOf(1000.0), UUID.fromString("6e8dfd5d-3ced-4499-bd9a-94e5b08595c0"), LocalDate.now()));
        BigDecimal investmentTotal = BigDecimal.ZERO;
        for (Investment investment : this.investments) {
            investmentTotal = investmentTotal.add(investment.getRetrievedValue());            
            investmentsBox.getChildren().add(BaseController.getView("investment_item", this, investment));
        }
        this.investmentsTotal.setText("Seus investimentos já renderam R$ " + investmentTotal.toString());

        this.loans.add(new Loan(BigDecimal.valueOf(10000), BigDecimal.valueOf(0.05), UUID.fromString("6e8dfd5d-3ced-4499-bd9a-94e5b08595c0"), LocalDate.now()));
        BigDecimal loanTotal = BigDecimal.ZERO;
        for (Loan loan : this.loans) {
            loanTotal = loanTotal.add(loan.getAmount());
            loansBox.getChildren().add(BaseController.getView("loan_item", this, loan));
        }
        this.loansTotal.setText("Seus empréstimos somam R$ " + loanTotal.toString());
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
