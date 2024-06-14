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
    public void retrieveInvestment(BigDecimal amount){
        // TODO: method implementation made the request to the database to check if the retrieval is valid and then execute it
        this.investedValue = this.investedValue.subtract(amount);
        this.assetQuantity = this.assetQuantity.subtract(amount.divide(this.assetValue));
        this.value = this.assetQuantity.multiply(this.assetValue);
    }

    @Override
    public void investMore(BigDecimal addedValue){
        // TODO: method implementation made the request to the database to check if the investmore  is valid and then execute it
        this.investedValue = this.investedValue.add(addedValue);
        this.assetQuantity = this.assetQuantity.add(addedValue.divide(this.assetValue));
        this.value = this.assetQuantity.multiply(this.assetValue);
    }
}
