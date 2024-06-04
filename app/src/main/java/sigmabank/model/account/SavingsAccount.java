package sigmabank.model.account;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import java.util.UUID;


@XmlRootElement
public class SavingsAccount extends Account {
    @XmlElement private Object savingInvestment;    

    public SavingsAccount(UUID clientUUID) {
        super(clientUUID);
        this.savingInvestment = null; // TODO: substitute the objetct by a savingInvestment object
    }

    public Object getSavingInvestment() {
        return savingInvestment;
    }    
    
}
