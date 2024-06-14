package sigmabank.model.account;

import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import sigmabank.model.investment.Investment;


@XmlRootElement
public class SavingsAccount extends Account {
    @XmlElement private Investment savingInvestment;    

    public SavingsAccount(UUID clientUUID) {
        super(clientUUID);
        this.savingInvestment = null; // TODO: substitute the objetct by a savingInvestment object
    }

    public Object getSavingInvestment() {
        return savingInvestment;
    }    
    
}
