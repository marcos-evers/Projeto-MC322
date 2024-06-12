package sigmabank.model.loan;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.UUID;

public class Loan {
    private final BigDecimal value;
    private final BigDecimal fee;
    private final UUID clientUUID;
    private final Date startDay;
    private BigDecimal amount;

    public Loan(){
        this.value = null;
        this.fee = null;
        this.clientUUID = null;
        this.startDay = null;
        this.amount = null;
    }

    public Loan(BigDecimal value, BigDecimal fee, UUID clientUUID, Date startDay, BigDecimal amount){
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

    public Date getStartDay(){
        return this.startDay;
    }
    
    public BigDecimal getAmount(){
        return this.amount;
    }

    public void pagarParteEmprestimo(BigDecimal valuePay){
        //falta comparar se o cliente tem valor na conta pra pagar
        BigDecimal zero = BigDecimal.valueOf(0);
        if(valuePay.compareTo(zero) <= -1){
            //o valor para o pagamento é invalido(zero ou negativo)
            return;
        }
        this.amount = amount.subtract(valuePay);
        //Precisa ainda retirar o valor da conta do cliente
    }

    public void calculateAmount(){
        //A cada virada de mes recalcular o amount de acordo com o juros
        BigDecimal one = BigDecimal.valueOf(1);
        BigDecimal sum = this.fee.add(one);
        BigDecimal newAmount = this.amount.multiply(sum);
        this.amount = newAmount;
    }

    public BigDecimal simulatePayment(int numMonth){
        //Formula é valorParcela = (amount * fee) / (1- ((1 + fee)^(-n)))
        BigDecimal one = BigDecimal.valueOf(1);
        BigDecimal valueParcel = null;
        BigDecimal amountFee = this.amount.multiply(this.fee);
        BigDecimal sumFee = one.add(this.fee);
        BigDecimal sumFeeN = sumFee.pow(numMonth);
        BigDecimal sumFeeNDiv = one.divide(sumFeeN);
        BigDecimal feeSub = one.subtract(sumFeeNDiv);
        valueParcel = amountFee.divide(feeSub);
        return valueParcel;
    }
}
