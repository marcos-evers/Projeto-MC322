package sigmabank.model.investment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AssetInvestment extends Investment implements InvestmentOperations {
    @XmlElement private BigDecimal assetValue;
    @XmlElement private BigDecimal assetQuantity;

    public AssetInvestment(BigDecimal investedvalue, UUID clientUUID, LocalDate startDate, BigDecimal assetValue) {
        super(investedvalue, clientUUID, startDate);
        this.assetValue = assetValue;
        this.assetQuantity = this.investedValue.divide(this.assetValue);
    }

    public AssetInvestment(BigDecimal investedValue, BigDecimal value, BigDecimal retrievedValue, UUID clientUUID, LocalDate startDate, BigDecimal assetValue, BigDecimal assetQuantity) {
        super(investedValue, clientUUID, startDate);
        this.value = value;
        this.retrievedValue = retrievedValue;
        this.assetValue = assetValue;
        this.assetQuantity = assetQuantity;
    }

    public BigDecimal getAssetQuantity() {
        return assetQuantity;
    }

    public BigDecimal getAssetValue() {
        return assetValue;
    }

    public void setAssetQuantity(BigDecimal assetQuantity) {
        this.assetQuantity = assetQuantity;
    }
    
    public void setAssetValue(BigDecimal assetValue) {
        this.assetValue = assetValue;
        this.value = this.assetQuantity.multiply(this.assetValue);
    }
    

    @Override
    public BigDecimal retrieveInvestment(BigDecimal amount){
        if(amount.compareTo(this.value) > 0){
            return BigDecimal.valueOf(0);
        }

        this.assetQuantity = this.assetQuantity.subtract(amount.divide(this.assetValue));
        this.value = this.assetQuantity.multiply(this.assetValue);
        this.retrievedValue = this.retrievedValue.add(amount);

        return amount;
    }

    @Override
    public void investMore(BigDecimal additionalValue){
        this.investedValue = this.investedValue.add(additionalValue);
        this.assetQuantity = this.assetQuantity.add(additionalValue.divide(this.assetValue));
        this.value = this.assetQuantity.multiply(this.assetValue);
    }
}
