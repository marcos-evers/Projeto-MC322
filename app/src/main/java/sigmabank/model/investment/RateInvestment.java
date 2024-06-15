package sigmabank.model.investment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RateInvestment extends Investment implements InvestmentOperations{
    @XmlElement private BigDecimal rate;
    @XmlElement private BigDecimal addedValue;

    public RateInvestment(BigDecimal investedvalue, UUID clientUUID, LocalDate startDate, BigDecimal rate) {
        super(investedvalue, clientUUID, startDate);
        this.rate = rate;
        this.addedValue = BigDecimal.valueOf(0);
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * Updates the value of the investment based on the rate, also, adds the added value to the investment value.
     */
    public void updateValue() {
        BigDecimal one = BigDecimal.valueOf(1);
        BigDecimal sum = this.rate.add(one);
        BigDecimal newValue = this.value.multiply(sum);
        this.value = newValue.add(this.addedValue);
        this.addedValue = BigDecimal.valueOf(0);
    }

    @Override
    public BigDecimal retrieveInvestment(BigDecimal amount){
        if(amount.compareTo(this.value) > 0){
            return BigDecimal.valueOf(0);
        }

        if(amount.compareTo(this.value) == 0){
            return BigDecimal.valueOf(0);
        }

        this.value = this.value.subtract(amount);
        this.retrievedValue = this.retrievedValue.add(amount);

        return amount;
    }

    @Override
    public void investMore(BigDecimal amount){
        this.investedValue = this.investedValue.add(amount);
        this.addedValue = this.addedValue.add(amount);
    }
}
