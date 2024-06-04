package sigmabank.model.register;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;
import javax.management.InvalidAttributeValueException;
import javax.xml.bind.annotation.XmlElement;

import sigmabank.model.account.CurrentAccount;
import sigmabank.model.account.SavingsAccount;
import sigmabank.model.account.SalaryAccount;

import sigmabank.model.bankcard.CreditCard;
import sigmabank.model.bankcard.DebitCard;

@XmlRootElement
public class ClientPersonal extends Register {
    @XmlElement private final String cpf;

    private UUID currentAccountUUID;
    private UUID savingsAccountUUID;
    private UUID salaryAccountUUID;

    private List<CreditCard> creditCards;
    private List<DebitCard> debitCards;

    public ClientPersonal(String name, String dateOfBirth, String cpf) {
        super(name, dateOfBirth);
        this.cpf = cpf;

        creditCards = new ArrayList<>();
        debitCards = new ArrayList<>();
    }

    /**
     * Adds a new credit card to the list of credit cards associated with the register.
     *
     * @param card The credit card object to be added.
     */
    public void addCreditCard(CreditCard card) {
        creditCards.add(card);
    }

    /**
     * Adds a new debit card to the list of debit cards associated with the register.
     *
     * @param card The debit card object to be added.
     */
    public void addDebitCard(DebitCard card) {
        debitCards.add(card);
    }

    /**
    * Removes the credit card with the specified card number from the list of credit cards associated with the register.
    *
    * @param number The card number of the credit card to be removed.
    */
    public void removeCreditCard(String number) {
        CreditCard cardToRemove;
        for (CreditCard creditCard: creditCards) {
            if (creditCard.getNumber().equals(number))
                cardToRemove = creditCard;
        }
        creditCards.remove(cardToRemove);
    }

    /**
    * Removes the debit card with the specified card number from the list of credit cards associated with the register.
    *
    * @param number The card number of the debit card to be removed.
    */
    public void removeDebitCard(String number) {
        DebitCard cardToRemove;
        for (DebitCard debitCard: debitCards) {
            if (debitCard.getNumber().equals(number))
                cardToRemove = debitCard;
        }
        debitCards.remove(cardToRemove);
    }

    public String getCpf() {
        return this.cpf;
    }

    public List<CreditCard> getCreditCards() {
        return this.creditCards;
    }

    public List<DebitCard> getDebitCards() {
        return this.debitCards;
    }
}
