package sigmabank.model.account;

import java.math.BigDecimal;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement
public class SalaryAccount extends Account {
    @XmlElement private UUID sellerUUID;

    public SalaryAccount(UUID clientUUID, UUID sellerUUID) {
        super(clientUUID);
        this.sellerUUID = sellerUUID;
    }

    /**
     * Initializes a transfer of funds from this account to the seller's account if it belongs to the same owner
     * 
     * @param clientSeller The UUID of the client seller's account to transfer funds to.
     * @param amount The amount of money to transfer.
     * @param description A description of the transfer.
     * @param password The password of the account to transfer funds from.
     * @throws InvalidAttributeValueException if the password is incorrect
     * @throws InvalidAttributeValueException if the cli
     */
    @Override
    public void Transfer(UUID clientSeller, BigDecimal amount, String description, String password) {
        // TODO: method implementation made the request to the database to check if the transfer is valid and then execute it
        // String realPassword = HashPassword.hashPassword(this.clientUUID, password);

    }
}
