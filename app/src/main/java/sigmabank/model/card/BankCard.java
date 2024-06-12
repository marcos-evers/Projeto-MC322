package sigmabank.model.card;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
        this.dueDate = null;
        this.securityCode = null;
        this.clientUuid = null;
        this.number = null;
    }

    public BankCard(LocalDate dueDate, String securityCode, UUID clientUuid) {
        // TODO add validation to dueDate, securityCode and clientUuid
        // TODO generate card number
        this.dueDate = dueDate;
        this.securityCode = securityCode;
        this.clientUuid = clientUuid;
        this.number = "";
    }

    /**
     * Sets the password attribute of the BankCard.
     *
     * @param password The password to set.
     * @throws InvalidAttributeValueException if the provided password does not match the expected format.
     */
    public void setPassword(String password) throws InvalidAttributeValueException {
        Pattern pattern = Pattern.compile("^[0-9]{4}$");
        Matcher matcher = pattern.matcher(password);

        if (matcher.find())
            this.password = password;
        else
            throw new InvalidAttributeValueException(password + " is not a valid password");
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

    public String getNumber() {
        return this.number;
    }
}
