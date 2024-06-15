package sigmabank.model.register;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import sigmabank.model.card.CreditCard;
import sigmabank.model.card.DebitCard;
import sigmabank.utils.DocumentValidator;

@XmlRootElement
public class ClientPersonal extends Register {
    @XmlElement private final String cpf;

    private List<CreditCard> creditCards;
    private List<DebitCard> debitCards;

    public ClientPersonal(String name, LocalDate dateOfBirth, String cpf) {
        super(name, dateOfBirth);

        if (!DocumentValidator.isValidCPF(cpf)) {
            throw new IllegalArgumentException("Invalid CPF: " + cpf);
        }
        if (!isAdult(dateOfBirth)) {
            throw new IllegalArgumentException("Invalid date of birth: " + dateOfBirth);
        }
        
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
        CreditCard cardToRemove = null;
        for (CreditCard creditCard: creditCards) {
            if (creditCard.getNumber().equals(number))
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
        DebitCard cardToRemove = null;
        for (DebitCard debitCard: debitCards) {
            if (debitCard.getNumber().equals(number))
                cardToRemove = debitCard;
        }
        if (cardToRemove != null)
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

    private boolean isAdult(LocalDate dateOfBirth) {
        LocalDate eighteenYearsAgo = LocalDate.now().minusYears(18);
        return dateOfBirth.isBefore(eighteenYearsAgo);
    }
}
