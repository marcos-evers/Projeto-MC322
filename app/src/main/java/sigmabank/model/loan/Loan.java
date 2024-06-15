package sigmabank.model.loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public class Loan {
    private final BigDecimal value;
    private final BigDecimal fee;
    private final UUID clientUUID;
    private final LocalDate startDay;
    private BigDecimal amount;

    public Loan(){
        this.value = null;
        this.fee = null;
        this.clientUUID = null;
        this.startDay = null;
        this.amount = null;
    }

    public Loan(BigDecimal value, BigDecimal fee, UUID clientUUID, LocalDate startDay){
        this.value = value;
        this.fee = fee;
        this.clientUUID = clientUUID;
        this.startDay = startDay;
        this.amount = value;
    }

    public Loan(BigDecimal value, BigDecimal fee, UUID clientUUID, LocalDate startDay, BigDecimal amount){
        this.value = value;
        this.fee = fee;
        this.clientUUID = clientUUID;
        this.startDay = startDay;
        this.amount = amount;
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

    /**
     * Pay a portion of the loan.
     * @param valuePay
     */
    public void payLoan(BigDecimal valuePay){
        //TODO check if the client has the money to pay the loan
        this.amount = amount.subtract(valuePay);
        //TODO remember to remove the money from the client account 
    }

    /**
     * Calculates the amount of the total loan and updates the amount attribute.
     * The formula used to calculate the amount is: amount = amount * (1 + fee)
     */
    public void calculateAmount(){
        BigDecimal one = BigDecimal.valueOf(1);
        BigDecimal sum = this.fee.add(one);
        BigDecimal newAmount = this.amount.multiply(sum);
        this.amount = newAmount;
    }


    /**
     * Simulates the payment of the loan for a given number of months.
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
