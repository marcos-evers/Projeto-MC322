package sigmabank.model.card;

import java.time.LocalDate;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;
import javax.management.InvalidAttributeValueException;
import javax.xml.bind.annotation.XmlElement;

@XmlRootElement
public class BankCard {
    @XmlElement private final String number;
    @XmlElement private final LocalDate dueDate;
    @XmlElement private final String securityCode;
    @XmlElement private final UUID clientUuid;
    
    private String password;

    public BankCard() {
        this.uuid = UUID.randomUUID();
        this.dueDate = null;
        this.securityCode = null;
        this.clientUuid = null;
    }

    public BankCard(LocalDate dueDate, String securityCode, UUID clientUuid) {
        // TODO add validation to dueDate, securityCode and clientUuid
        this.uuid = UUID.randomUUID();
        this.dueDate = dueDate;
        this.securityCode = securityCode;
        this.clientUuid = clientUuid;
    }

    /**
     * Sets the password attribute of the BankCard.
     *
     * @param password The password to set.
     * @throws InvalidAttributeValueException if the provided password does not match the expected format.
     */
    public void setPassword(String password) throws InvalidAttributeValueException {
        Pattern pattern = Pattern.compile("^[0-9]{4}$");
        Matcher matcher = pattern.matcher(address);

        if (matcher.find())
            this.password = password;
        else
            throw new InvalidAttributeValueException(address + " is not a valid password");
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public LocalDate getDueDate() {
        return this.dueDate;
    }

    public String getSecurityCode() {
        return this.securityCode;
    }

    public UUID getClientUUID() {
        return this.clientUuid;
    }

    public String getPassword() {
        return this.password;
    }
}
