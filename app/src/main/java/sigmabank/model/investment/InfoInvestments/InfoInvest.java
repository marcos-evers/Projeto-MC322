package sigmabank.model.investment.InfoInvestments;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import sigmabank.model.investment.AssetInvestEnum;
import sigmabank.model.investment.ROIFrequencyType;
import sigmabank.model.investment.RateInvestEnum;


/**
 * Class that represents the information of an investment thats saved on a xml that defines the 
 * caracteristics of each type of investment
 */
@XmlRootElement
public class InfoInvest {
    @XmlElement private String name;
    @XmlElement private BigDecimal assetValue;
    @XmlElement private BigDecimal rate;
    @XmlElement private ROIFrequencyType frequencyType;
    @XmlElement private AssetInvestEnum assetype;
    @XmlElement private RateInvestEnum ratetype;

    public InfoInvest(String name, BigDecimal rate, ROIFrequencyType frequencyType, RateInvestEnum Ratetype) {
        this.name = name;
        this.rate = rate;
        this.frequencyType = frequencyType;
        this.ratetype = Ratetype;
        this.assetValue = null;
    }

    public InfoInvest(String name, BigDecimal assetValue, AssetInvestEnum assetype) {
        this.name = name;
        this.assetValue = assetValue;
        this.assetype = null;
        this.rate = null;
        this.frequencyType = null;
        this.ratetype = null;	
    }

    public String getName() {
        return name;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public ROIFrequencyType getFrequencyType() {
        return frequencyType;
    }

    public BigDecimal getAssetValue() {
        return assetValue;
    }
}
