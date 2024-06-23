package sigmabank.model.investment;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AssetInvestment")
public class AssetInvestment extends Investment implements InvestmentOperations {
    private BigDecimal assetValue;
    private BigDecimal assetQuantity;
    private AssetInvestEnum assetType;

    public AssetInvestment(String name, BigDecimal investedvalue, UUID clientUUID, LocalDate startDate, BigDecimal assetValue, AssetInvestEnum assetType) {
        super(name, investedvalue, clientUUID, startDate);
        this.assetValue = assetValue;
        this.assetQuantity = this.investedValue.divide(this.assetValue, new MathContext(2, RoundingMode.HALF_UP));
        this.assetType = assetType;
    }

    public AssetInvestment(String name, BigDecimal investedValue, BigDecimal value, BigDecimal retrievedValue, UUID clientUUID, LocalDate startDate, BigDecimal assetValue, BigDecimal assetQuantity, AssetInvestEnum assetType) {
        super(name, investedValue, clientUUID, startDate);
        this.value = value;
        this.retrievedValue = retrievedValue;
        this.assetValue = assetValue;
        this.assetQuantity = assetQuantity;
        this.assetType = assetType;
    }

    public BigDecimal getAssetQuantity() {
        return assetQuantity;
    }

    public BigDecimal getAssetValue() {
        return assetValue;
    }

    public AssetInvestEnum getAssetType() {
        return assetType;
    }

    public void setAssetQuantity(BigDecimal assetQuantity) {
        this.assetQuantity = assetQuantity;
    }
    
    public void setAssetValue(BigDecimal assetValue) {
        this.assetValue = assetValue;
        this.value = this.assetQuantity.multiply(this.assetValue);
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

        this.assetQuantity = this.assetQuantity.subtract(amount.divide(this.assetValue, new MathContext(2, RoundingMode.HALF_UP)));
        this.value = this.assetQuantity.multiply(this.assetValue);
        this.retrievedValue = this.retrievedValue.add(amount);

        return amount;
    }

    /**
     * Method that adds a certain amount of money to the investment, It instantly buys the 
     * asset by the price defined 
     * 
     * @param additionalValue the amount of money to be added.
     */
    @Override
    public void investMore(BigDecimal additionalValue){
        this.investedValue = this.investedValue.add(additionalValue);
        this.assetQuantity = this.assetQuantity.add(additionalValue.divide(this.assetValue));
        this.value = this.assetQuantity.multiply(this.assetValue);
    }
}
