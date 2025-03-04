package sigmabank.model.investment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import sigmabank.utils.LocalDateAdapter;

@XmlRootElement
public class Investment {
    @XmlElement protected String name;
    protected BigDecimal investedValue;
    protected BigDecimal value;
    @XmlElement protected BigDecimal retrievedValue;
    @XmlElement protected final UUID clientUUID;

    @XmlElement
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    protected final LocalDate startDate;

    public Investment() {
        clientUUID = null;
        startDate = null;
    }
    
    public Investment(String name, BigDecimal investedValue, UUID clientUUID, LocalDate startDate){
        this.name = name;
        this.investedValue = investedValue;
        this.clientUUID = clientUUID;
        this.startDate = startDate;
        this.value = this.investedValue;
        this.retrievedValue = BigDecimal.valueOf(0);
    }

    public String getName(){
        return name;
    }

    public BigDecimal getInvestedValue(){
        return investedValue;
    }

    public BigDecimal getValue(){
        return value;
    }

    public BigDecimal getRetrievedValue(){
        return retrievedValue;
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

    public void updateValue(){
        throw new UnsupportedOperationException("This method is not implemented yet");
    }
    
    /**
     * Calculate the total profit of the investment generated throught history,
     * taking account even the removed value
     * 
     * @return the profit of the investment
     */
    public BigDecimal calculateProfit(){
        return this.value.subtract(this.investedValue).add(this.retrievedValue);
    }
}
