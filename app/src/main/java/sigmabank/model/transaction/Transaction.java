package sigmabank.model.transaction;

import java.math.BigDecimal;

public class Transaction {
    private String UUIID;
    private String IDsender;
    private String IDremittee;
    private BigDecimal value;
    private String dateTime;
    private TransactionType type;
    private TransactionStatus status;

}