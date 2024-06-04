package sigmabank.model.card;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;
import javax.management.InvalidAttributeValueException;
import javax.xml.bind.annotation.XmlElement;
import java.util.Objects;

public class DebitCard extends BankCard {
    @XmlElement private final UUID accountUuid;

    public DebitCard() {
        this.accountUuid = null;
    }

    public DebitCard(String dueDate, String securityCode, UUID clientUuid, UUID accountUuid) {
        super(dueDate, securityCode, clientUuid);
        this.accountUuid = accountUuid;
    }

    public UUID getAccountUuid() {
        return this.accountUuid;
    }    
}
