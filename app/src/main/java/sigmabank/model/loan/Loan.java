package sigmabank.model.loan;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import sigmabank.utils.LocalDateAdapter;

@XmlRootElement(name="Loan")
public class Loan {
    @XmlElement private final BigDecimal value;
    @XmlElement private final BigDecimal fee;
    @XmlElement private final UUID clientUUID;
    @XmlElement private UUID loanUUID;

    @XmlElement
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private final LocalDate startDay;

    @XmlElement
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate lastUpdateDate;

    @XmlElement private BigDecimal amount;

    public Loan() {
        this.value = null;
        this.fee = null;
        this.clientUUID = null;
        this.startDay = null;
    }
    
    public Loan(BigDecimal value, UUID clientUUID, LocalDate startDay){
        this.value = value;
        this.fee = calculateFee(value);
        this.clientUUID = clientUUID;
        this.loanUUID = UUID.randomUUID();
        this.startDay = startDay;
        this.amount = value;
        this.lastUpdateDate = LocalDate.now();
    }

    public Loan(BigDecimal value, BigDecimal fee, UUID clientUUID, UUID loanUUID, LocalDate startDay, BigDecimal amount, LocalDate lastUpdateDate) {
        this.value = value;
        this.fee = fee;
        this.clientUUID = clientUUID;
        this.loanUUID = loanUUID;
        this.startDay = startDay;
        this.amount = amount;
        this.lastUpdateDate = lastUpdateDate;
    }

    public BigDecimal getValue(){
        return this.value;
    }

    public BigDecimal getFee(){
        return this.fee;
    }

    public UUID getClientUUID(){
        return this.clientUUID;
    }

    public LocalDate getStartDay(){
        return this.startDay;
    }
    
    public BigDecimal getAmount(){
        return this.amount;
    }

    public UUID getLoanUUID(){
        return this.loanUUID;
    }

    public LocalDate getLastUpdateDate(){
        return this.lastUpdateDate;
    }

    /**
     * Check if the loan needs to be updated.
     * @return true if the loan needs to be updated, false otherwise.
     */
    private Boolean checkUpdateDate(){
        LocalDate currentDate = LocalDate.now();
        return currentDate.isAfter(this.lastUpdateDate.plusMonths(1));
    }

    /**
     * Pay a portion of the loan.
     * @param valuePay
     */
    public void payLoan(BigDecimal valuePay){
        this.amount = amount.subtract(valuePay);
    }

    /**
     * Calculates the amount of the total loan and updates the amount attribute, 
     * if the date of due is reached.
     * The formula used to calculate the amount is: amount = amount * (1 + fee)
     */
    public void updateAmount(){
        if(!this.checkUpdateDate()){
            return;
        }
        this.lastUpdateDate = LocalDate.now();

        BigDecimal one = BigDecimal.valueOf(1);
        BigDecimal sum = this.fee.add(one);
        BigDecimal newAmount = this.amount.multiply(sum);
        this.amount = newAmount;
    }

    public static BigDecimal calculateFee(BigDecimal loanValue) {
        // MathContext for precision
        BigDecimal marketRate = new BigDecimal(0.05);
        MathContext mc = new MathContext(10, RoundingMode.HALF_UP);

        // Calculate tempo = pow(2 * valor, 1/2)
        BigDecimal two = new BigDecimal("2");
        BigDecimal tempo = sqrt(two.multiply(loanValue, mc), mc);

        // Calculate pow(1.05, tempo)
        BigDecimal onePointZeroFive = new BigDecimal("1.05");
        BigDecimal powOnePointZeroFive = onePointZeroFive.pow(tempo.intValue(), mc);

        // Calculate pow(pow(1.05, tempo) / tempo, 1 / (tempo + 1))
        BigDecimal division = powOnePointZeroFive.divide(tempo, mc);
        BigDecimal oneOverTempoPlusOne = BigDecimal.ONE.divide(tempo.add(BigDecimal.ONE), mc);
        BigDecimal innerPower = pow(division, oneOverTempoPlusOne, mc);

        // Calculate final result
        BigDecimal result = innerPower.subtract(BigDecimal.ONE).multiply(new BigDecimal("3"), mc);

        if(result.compareTo(BigDecimal.ZERO) < 0 || result.compareTo(marketRate) < 0)
            return marketRate.round(new MathContext(4));

        BigDecimal resultScaled = new BigDecimal(result.setScale(4, RoundingMode.HALF_UP).toString());
        return resultScaled;
    }

    
    private static BigDecimal sqrt(BigDecimal loanValue, MathContext mc) {
        BigDecimal x0 = new BigDecimal(Math.sqrt(loanValue.doubleValue()));
        BigDecimal x1 = loanValue.divide(x0, mc);
        x1 = x1.add(x0);
        x1 = x1.divide(new BigDecimal("2"), mc);
        return x1;
    }

    
    private static BigDecimal pow(BigDecimal base, BigDecimal exponent, MathContext mc) {
        int signOf2 = exponent.signum();
        double dn1 = base.doubleValue();
        exponent = exponent.multiply(new BigDecimal(signOf2)); 
        BigDecimal remainderOf2 = exponent.remainder(BigDecimal.ONE);
        BigDecimal n2IntPart = exponent.subtract(remainderOf2);
        BigDecimal intPow = base.pow(n2IntPart.intValueExact(), mc);
        BigDecimal doublePow = new BigDecimal(Math.pow(dn1, remainderOf2.doubleValue()));

        BigDecimal result = intPow.multiply(doublePow, mc);
        if (signOf2 == -1) {
            result = BigDecimal.ONE.divide(result, mc.getPrecision(), RoundingMode.HALF_UP);
        }
        return result;
    }
    
    

    /**
     * Simulates the payment of the loan for a given number of months numMonth.
     * The formula used to calculate the payment is: valueParcel = (amount * fee) / (1- ((1 + fee)^(-n)))
     * 
     * @param numMonth the number of months to simulate the payment.
     * @return the value of the payment.
     * @throws IllegalArgumentException if the number of months is less than 1.
     */
    public BigDecimal simulatePayment(int numMonth){
        BigDecimal one = BigDecimal.valueOf(1);
        BigDecimal amountFee = this.amount.multiply(this.fee);
        BigDecimal sumFee = one.add(this.fee);
        BigDecimal sumFeeN = sumFee.pow(numMonth);
        BigDecimal sumFeeNDiv = one.divide(sumFeeN);
        BigDecimal feeSub = one.subtract(sumFeeNDiv);
        BigDecimal valueParcel = amountFee.divide(feeSub);

        return valueParcel;
    }
}
