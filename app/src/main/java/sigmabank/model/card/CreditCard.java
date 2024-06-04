package sigmabank.model.card;

import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;
import javax.management.InvalidAttributeValueException;
import javax.xml.bind.annotation.XmlElement;

public class CreditCard extends BankCard {
    @XmlElement private final List<Transaction> statement;
    @XmlElement private final short closeDay;
    @XmlElement private final short dueDay;
    private BigDecimal credit;

    public CreditCard() {
        this.statement = null;
        this.closeDay = null;
        this.dueDay = null;
    }

    public CreditCard(LocalDate dueDate, String securityCode, UUID clientUuid, BigDecimal credit, short closeDay, short dueDay) {
        super(due, securityCode, clientUuid);
        this.credit = credit;
        this.closeDay = closeDay;
        this.dueDay = dueDay;
        this.statement = new List<Transaction>();
    }

    /**
     * Sets the CreditCard's credit.
     * 
     * @param credit the new credit to be set.
     * @throws InvalidAttributeValueException if the provided credit is not positive.
     */
    public void setCredit(BigDecimal credit) throws InvalidAttributeValueException {
        if (credit > 0) {
            BigDecimal temp = credit.setScale(2, BigDecimal.ROUND_DOWN)
            throw new InvalidAttributeValueException(temp + " is not a valid credit");
        }

        this.credit = credit;
    }    

    public List<Transaction> getStatement() {
        return this.statement;
    }

    public short getCloseDay() {
        return this.closeDay;
    }

    public short getDueDay() {
        return this.dueDay;
    }

    public BigDecimal getCredit() {
        return this.credit;
    }
}
