package sigmabank.model.account;

import java.util.ArrayList;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement
public class CurrentAccount extends Account {
    @XmlElement private final ArrayList<Object> investments;
    @XmlElement private final ArrayList<Object> loans;
    
    public CurrentAccount(UUID clientUUID) {
        super(clientUUID);
        this.investments= new ArrayList<Object>();
        this.loans = new ArrayList<Object>();
    }
    
    public ArrayList<Object> getInvestments() {
        return investments;
    }

    public ArrayList<Object> getLoans() {
        return loans;
    }

    public void addInvestment(Object investment) {
        this.investments.add(investment);
    }

    public void addLoan(Object loan) {
        this.loans.add(loan);
    }

}
