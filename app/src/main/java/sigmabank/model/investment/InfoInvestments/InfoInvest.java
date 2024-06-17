package sigmabank.model.investment.InfoInvestments;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import sigmabank.model.investment.ROIFrequencyType;

@XmlRootElement
public class InfoInvest {
    @XmlElement private String name;
    @XmlElement private BigDecimal assetValue;
    @XmlElement private BigDecimal rate;
    @XmlElement private ROIFrequencyType frequencyType;

    public InfoInvest(String name, BigDecimal rate, ROIFrequencyType frequencyType) {
        this.name = name;
        this.rate = rate;
        this.frequencyType = frequencyType;
        this.assetValue = null;
    }

    public InfoInvest(String name, BigDecimal assetValue) {
        this.name = name;
        this.assetValue = assetValue;
        this.rate = null;
        this.frequencyType = null;
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
