package sigmabank.model.account;

import java.math.BigDecimal;

import java.time.LocalDate;

import java.util.ArrayList;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;
import javax.management.InvalidAttributeValueException;
import javax.xml.bind.annotation.XmlElement;

import sigmabank.utils.HashPassword;

@XmlRootElement
public class Account {
    // TODO: substitute the type Object on statement by Operations/Transaction
    @XmlElement protected final UUID uuid;
    @XmlElement protected final UUID clientUUID;
    @XmlElement protected final LocalDate creationDate;
    @XmlElement protected BigDecimal balance;
    @XmlElement protected final ArrayList<Object> statement; 

    public Account(UUID clientUUID){
        this.uuid = UUID.randomUUID();
        this.clientUUID = clientUUID;
        this.creationDate = LocalDate.now();
        this.balance = new BigDecimal(0);
        this.statement = new ArrayList<Object>();
    }

    public UUID getUUID() {
        return uuid;
    }

    public UUID getClientUUID() {
        return clientUUID;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public BigDecimal getBalance() { 
        return balance;
    }

    public ArrayList<Object> getStatement() {
        return statement;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
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
     * Initializes a transfer of funds from this account to the account of the specified client seller
     * 
     * @param clientSellerAccount The UUID of the client seller's account to transfer funds to.
     * @param amount The amount of money to transfer.
     * @param description A description of the transfer.
     * @param password The password of the account to transfer funds from.
     * @throws InvalidAttributeValueException if the password is incorrect
     * @throws InvalidAttributeValueException if the client seller's account does not exist
     * @throws InvalidAttributeValueException if the amount is higher than the balance of the account
     */
    public void Transfer(UUID clientSellerAccount, BigDecimal amount, String description, String password) {
        // TODO: Check if the amount is higher than the balance of the account
        
        // TODO: implement the transfer logic here

        // TODO: Check if the password is correct
        //String realPassword = HashPassword.hashPassword(this.clientUUID, password);

        // TODO: Check if the client seller's account exists
    }
}
