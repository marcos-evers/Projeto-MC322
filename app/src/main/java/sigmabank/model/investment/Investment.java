package sigmabank.model.investment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Investment {
    @XmlElement protected BigDecimal investedValue;
    @XmlElement protected BigDecimal value;
    @XmlElement protected final UUID clientUUID;
    @XmlElement protected final LocalDate startDate;
    

    public Investment(BigDecimal investedValue, UUID clientUUID, LocalDate startDate){
        this.investedValue = investedValue;
        this.clientUUID = clientUUID;
        this.startDate = startDate;
        this.value = this.investedValue;
    }

    public BigDecimal getInvestedValue(){
        return investedValue;
    }

    public BigDecimal getValue(){
        return value;
    }

    public UUID getClientUUID(){
        return clientUUID;
    }

    public LocalDate getStartDate(){
        return startDate;
    }

    public void setInvestedValue(BigDecimal investedValue){
        this.investedValue = investedValue;
    }

    public void setValue(BigDecimal value){
        this.value = value;
    }
    
    public BigDecimal calculateProfit(){
        return value.subtract(investedValue);
    }
}
