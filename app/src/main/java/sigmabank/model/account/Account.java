package sigmabank.model.account;

import java.math.BigDecimal;
import java.math.RoundingMode;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;
import javax.management.InvalidAttributeValueException;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement
public class Account {
    // TODO: substitute the type Object on statement by Operations/Transaction
    @XmlElement protected final UUID uuid;
    @XmlElement protected final UUID clientUUID;
    @XmlElement protected final String creationDate;
    @XmlElement protected BigDecimal balance;
    @XmlElement protected final ArrayList<Object> statement; 

    public Account(UUID clientUUID){
        this.uuid = UUID.randomUUID();
        this.clientUUID = clientUUID;
        this.creationDate = LocalDate.now().toString();
        this.balance = new BigDecimal(0);
        this.statement = new ArrayList<Object>();
    }

    public UUID getUUID() {
        return uuid;
    }

    public UUID getClientUUID() {
        return clientUUID;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public BigDecimal getBalance() { 
        return balance;
    }

    public ArrayList<Object> getStatement() {
        return statement;
    }

    /**
     * Adding a transfer to the statement of this bank account after the transfer process is over 
     * 
     * @param transfer the Transfer object to be added
     * @throws InvalidAttributeValueException if the object Transfer does not exist on the database
     */
    public void addStatement(Object transfer) throws InvalidAttributeValueException{
        // TODO: Check if the transfer exists in the database
        this.statement.add(transfer);
    }

    /**
     * Initializes a transfer of funds from this account to the account of the specified client seller.
     * 
     * @param clientSeller The UUID of the client seller's account to transfer funds to.
     */
    public void Transfer(UUID clientSeller) {
        // TODO: method implementation
    }
}
