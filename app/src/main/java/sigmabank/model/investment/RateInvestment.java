package sigmabank.model.investment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class RateInvestment extends Investment implements InvestmentOperations{
    @XmlElement private BigDecimal rate;

    public RateInvestment(BigDecimal investedvalue, UUID clientUUID, LocalDate startDate, BigDecimal rate) {
        super(investedvalue, clientUUID, startDate);
        this.rate = rate;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public void updateValue() {
        BigDecimal one = BigDecimal.valueOf(1);
        BigDecimal sum = this.rate.add(one);
        BigDecimal newValue = this.value.multiply(sum);
        this.value = newValue;
    }

    @Override
    public void retrieveInvestment(BigDecimal amount){
        // TODO: method implementation made the request to the database to check if the retrieval is valid and then execute it
    }

    @Override
    public void investMore(BigDecimal amount){
        // TODO: method implementation made the request to the database to check if the investmore  is valid and then execute it
    }
}
