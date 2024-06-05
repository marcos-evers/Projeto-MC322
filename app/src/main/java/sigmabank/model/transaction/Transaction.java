package sigmabank.model.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class Transaction {
    @XmlElement private final UUID uuid;
    @XmlElement private final UUID idBuyer;
    @XmlElement private final UUID idSeller;
    @XmlElement private final BigDecimal value;
    @XmlElement private final LocalDate dateTime;
    @XmlElement private final TransactionType type;
    @XmlElement private TransactionStatus status;

    public Transaction() {
        this.uuid = UUID.randomUUID();
        this.idBuyer = null;
        this.idSeller = null;
        this.value = null;
        this.dateTime = null;
        this.type = null;
    }
    
    public Transaction(UUID idBuyer, UUID idSeller, BigDecimal value, LocalDate dateTime, TransactionType type) {
        this.uuid = UUID.randomUUID();
        this.idBuyer = idBuyer;
        this.idSeller = idSeller;
        this.value = value;
        this.dateTime = dateTime;
        this.type = type;
        this.status = TransactionStatus.PROCESSING;
    }
}