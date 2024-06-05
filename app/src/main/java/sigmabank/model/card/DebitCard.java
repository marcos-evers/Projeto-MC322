package sigmabank.model.card;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlElement;

import java.time.LocalDate;

@XmlRootElement
public class DebitCard extends BankCard {
    @XmlElement private final UUID accountUuid;

    public DebitCard() {
        this.accountUuid = null;
    }

    public DebitCard(LocalDate dueDate, String securityCode, UUID clientUuid, UUID accountUuid) {
        super(dueDate, securityCode, clientUuid);
        this.accountUuid = accountUuid;
    }

    public UUID getAccountUuid() {
        return this.accountUuid;
    }    
}
