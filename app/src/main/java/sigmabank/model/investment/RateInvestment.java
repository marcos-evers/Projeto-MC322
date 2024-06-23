package sigmabank.model.investment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "RateInvestment")
public class RateInvestment extends Investment implements InvestmentOperations{
    @XmlElement private BigDecimal rate;
    @XmlElement private BigDecimal addedValue;
    @XmlElement private final ROIFrequencyType frequencyType;
    @XmlElement private LocalDate lastUpdateDate;
    @XmlElement private RateInvestEnum rateType;


    public RateInvestment(String name, BigDecimal investedvalue, UUID clientUUID, LocalDate startDate, BigDecimal rate, ROIFrequencyType frequencyType, RateInvestEnum rateType) {
        super(name, investedvalue, clientUUID, startDate);
        this.rate = rate;
        this.addedValue = BigDecimal.valueOf(0);
        this.frequencyType = frequencyType;
        this.lastUpdateDate = LocalDate.now();	
        this.rateType = rateType;
    }

    public RateInvestment(String name, BigDecimal investedValue, BigDecimal value, BigDecimal retrievedValue, UUID clientUUID, LocalDate startDate, BigDecimal rate, BigDecimal addedValue, ROIFrequencyType frequencyType, LocalDate lastUpdateDate, RateInvestEnum rateType) {
        super(name, investedValue, clientUUID, startDate);
        this.value = value;
        this.retrievedValue = retrievedValue;
        this.rate = rate;
        this.addedValue = addedValue;
        this.frequencyType = frequencyType;
        this.lastUpdateDate = lastUpdateDate;
        this.rateType = rateType;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public ROIFrequencyType getFrequencyType() {
        return frequencyType;
    }

    public LocalDate getLastUpdateDate() {
        return lastUpdateDate;
    }

    public BigDecimal getAddedValue() {
        return addedValue;
    }

    public RateInvestEnum getRateType() {
        return rateType;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    /**
     * Checks if the investment needs to be updated based on the frequency type and the last update date.
     * @return true if the investment needs to be updated, false otherwise.
     */
    private Boolean checkUpdateDate() {
        LocalDate currentDate = LocalDate.now();
    
        switch (this.frequencyType) {
            case DAILY:
                return currentDate.isAfter(this.lastUpdateDate);
            case WEEKLY:
                return currentDate.isAfter(this.lastUpdateDate.plusWeeks(1));
            case MONTHLY:
                return currentDate.isAfter(this.lastUpdateDate.plusMonths(1));
            case YEARLY:
                return currentDate.isAfter(this.lastUpdateDate.plusYears(1));
            default:
                return null;
        }
    }

    /**
     * Updates the value of the investment based on the rate, also, adds the added value to the investment value. If 
     */
    public void updateValue() {
        if(!this.checkUpdateDate()){
            return;
        }
        this.lastUpdateDate = LocalDate.now();

        BigDecimal one = BigDecimal.valueOf(1);
        BigDecimal sum = this.rate.add(one);
        BigDecimal newValue = this.value.multiply(sum);
        this.value = newValue.add(this.addedValue);
        this.addedValue = BigDecimal.valueOf(0);
    }

    /**
     * Method that retrieves a certain amount of money from the investment.
     * 
     * @param amount the amount of money to be retrieved.
     */
    @Override
    public BigDecimal retrieveInvestment(BigDecimal amount){
        if(amount.compareTo(this.value) > 0){
            return BigDecimal.valueOf(0);
        }

        this.value = this.value.subtract(amount);
        this.retrievedValue = this.retrievedValue.add(amount);

        return amount;
    }

    /**
     * Method that adds a certain amount of money to the investment, It keeps on the added value the amount of money added
     * and after the update value method is called - ROI is granted- the added value is added to the value of the investment.
     * 
     * @param amount the amount of money to be added.
     */
    @Override
    public void investMore(BigDecimal amount){
        this.investedValue = this.investedValue.add(amount);
        this.addedValue = this.addedValue.add(amount);
    }
}
