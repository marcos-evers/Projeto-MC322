package sigmabank.model.card;

import java.math.BigDecimal;
import java.math.RoudingMode;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        this.closeDay = 0;
        this.dueDay = 0;
    }

    public CreditCard(String dueDate, String securityCode, UUID clientUuid, BigDecimal credit, short closeDay, short dueDay) {
        super(dueDate, securityCode, clientUuid);
        this.credit = credit;
        this.closeDay = closeDay;
        this.dueDay = dueDay;
        this.statement = new ArrayList<>();
    }

    /**
     * Sets the CreditCard's credit.
     * 
     * @param credit the new credit to be set.
     * @throws InvalidAttributeValueException if the provided credit is not positive.
     */
    public void setCredit(BigDecimal credit) throws InvalidAttributeValueException {
        if (credit.compareTo(BigDecimal.ZERO) > 0) {
            BigDecimal temp = credit.setScale(2, RoudingMode.ROUND);
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
