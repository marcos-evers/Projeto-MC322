package sigmabank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement
public class ClientPersonal extends Register {
    public static class Card {
        public String getNumber() {
            return "000";
        }
    }

    @XmlElement private final String cpf;

    private List<Object> creditCards;
    private List<Object> debitCards;

    public ClientPersonal() {
        super();
        this.cpf = "";

        creditCards = null;
        debitCards = null;
    }

    public ClientPersonal(String name, Date dateOfBirth, String cpf) {
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
    public void addCreditCard(Object card) {
        creditCards.add(card);
    }

    /**
     * Adds a new debit card to the list of debit cards associated with the register.
     *
     * @param card The debit card object to be added.
     */
    public void addDebitCard(Object card) {
        debitCards.add(card);
    }

    /**
    * Removes the credit card with the specified card number from the list of credit cards associated with the register.
    *
    * @param number The card number of the credit card to be removed.
    */
    public void removeCreditCard(String number) {
        Object cardToRemove = null;
        for (Object creditCard: creditCards) {
            if (((Card) creditCard).getNumber().equals(number))
                cardToRemove = creditCard;
        }
        if (cardToRemove != null)
            creditCards.remove(cardToRemove);
    }

    /**
    * Removes the debit card with the specified card number from the list of credit cards associated with the register.
    *
    * @param number The card number of the debit card to be removed.
    */
    public void removeDebitCard(String number) {
        Object cardToRemove = null;
        for (Object debitCard: debitCards) {
            if (((Card) debitCard).getNumber().equals(number))
                cardToRemove = debitCard;
        }
        if (cardToRemove != null)
            debitCards.remove(cardToRemove);
    }

    public String getCpf() {
        return this.cpf;
    }

    public List<Object> getCreditCards() {
        return this.creditCards;
    }

    public List<Object> getDebitCards() {
        return this.debitCards;
    }
}
