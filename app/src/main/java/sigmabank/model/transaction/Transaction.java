package sigmabank.model.transaction;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.management.InvalidAttributeValueException;
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
    @XmlElement private final String descrition;

    public Transaction() {
        this.uuid = UUID.randomUUID();
        this.idBuyer = null;
        this.idSeller = null;
        this.value = null;
        this.dateTime = null;
        this.type = null;
        this.descrition = null;
    }
    
    public Transaction(UUID idBuyer, UUID idSeller, BigDecimal value, LocalDate dateTime, TransactionType type, String descrition) {
        this.uuid = UUID.randomUUID();
        this.idBuyer = idBuyer;
        this.idSeller = idSeller;
        this.value = value;
        this.dateTime = dateTime;
        this.type = type;
        this.status = TransactionStatus.PROCESSING;
        this.descrition = descrition;
    }

    /**
     * Sets the Transaction's status.
     * 
     * @param status the status to be set.
     * @throws InvalidAttributeValueException if tries to undo a cancellation
     * or reprocess a transaction.
     */
    public void setStatus(TransactionStatus status) throws InvalidAttributeValueException {
        if (this.status == TransactionStatus.CANCELLED && status != TransactionStatus.CANCELLED) {
            throw new InvalidAttributeValueException("Can't undo a cancellation of Transaction");
        }
        
        if (this.status == TransactionStatus.APPROVED && status == TransactionStatus.PROCESSING) {
            throw new InvalidAttributeValueException("Can't reprocess a Transaction");
        }
        
        this.status = status;
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public UUID getIdBuyer() {
        return this.idBuyer;
    }

    public UUID getIdSeller() {
        return this.idSeller;
    }

    public BigDecimal getValue() {
        return this.value;
    }

    public LocalDate getDateTime() {
        return this.dateTime;
    }

    public TransactionType getType() {
        return this.type;
    }

    public TransactionStatus getStatus() {
        return this.status;
    }
}