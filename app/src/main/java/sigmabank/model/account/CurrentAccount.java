package sigmabank.model.account;

import java.util.ArrayList;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import sigmabank.model.investment.Investment;
import sigmabank.model.loan.Loan;

@XmlRootElement
public class CurrentAccount extends Account {
    @XmlElement private final ArrayList<Investment> investments;
    @XmlElement private final ArrayList<Loan> loans;
    
    public CurrentAccount(UUID clientUUID) {
        super(clientUUID);
        this.investments= new ArrayList<>();
        this.loans = new ArrayList<>();
    }
    
    public ArrayList<Investment> getInvestments() {
        return investments;
    }

    public ArrayList<Loan> getLoans() {
        return loans;
    }

    public void addInvestment(Investment investment) {
        this.investments.add(investment);
    }

    public void addLoan(Loan loan) {
        this.loans.add(loan);
    }

}
