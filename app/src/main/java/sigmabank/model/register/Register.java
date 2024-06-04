package sigmabank.model.register;

import java.util.UUID;

import javax.xml.bind.annotation.XmlRootElement;
import javax.management.InvalidAttributeValueException;
import javax.xml.bind.annotation.XmlElement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@XmlRootElement
public class Register {
    // Personal Data
    @XmlElement private final UUID uuid;
    @XmlElement private final String name;
    @XmlElement private final String dateOfBirth;

    // Contact Data
    private String email;
    private String phoneNumber;
    private String address;

    public Register() {
        this.uuid = UUID.randomUUID();
        this.name = null;
        this.dateOfBirth = null;
    }

    public Register(String name, String dateOfBirth) {
        // TODO add validation to dateOfBirth
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Sets the email attribute of the Register.
     *
     * @param email The phone number to set.
     * @throws InvalidAttributeValueException if the provided email does not match the expected format.
     */
    public void setEmail(String email) throws InvalidAttributeValueException {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);

        if (matcher.find())
            this.email = email;
        else
            throw new InvalidAttributeValueException(email + " is not a email");
    }

    /**
     * Sets the phone number attribute of the Register.
     *
     * @param phoneNumber The phone number to set.
     * @throws InvalidAttributeValueException if the provided phone number does not match the expected format.
     */
    public void setPhoneNumber(String phoneNumber) throws InvalidAttributeValueException {
        // TODO fix regex
        Pattern pattern = Pattern.compile("^\\+?[0-9()-]*$");
        Matcher matcher = pattern.matcher(phoneNumber);
        
        if (matcher.find())
            this.phoneNumber = phoneNumber;
        else
            throw new InvalidAttributeValueException(phoneNumber + " is not a phone number");
    }

    /**
     * Sets the address attribute of the Register.
     *
     * @param address The phone number to set.
     * @throws InvalidAttributeValueException if the provided address does not match the expected format.
     */
    public void setAddress(String address) throws InvalidAttributeValueException {
        Pattern pattern = Pattern.compile("^(Rua|Avenida) [a-zA-Z0-9 ]+, [0-9]+, [0-9]{5}-[0-9]{3}");
        Matcher matcher = pattern.matcher(address);

        if (matcher.find())
            this.address = address;
        else
            throw new InvalidAttributeValueException(address + " is not a address");
    }

    public UUID getUuid() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public String getDateOfBirth() {
        return this.dateOfBirth;
    }

    public String getEmail() {
        return this.email;
    }

    public String getAddress() {
        return this.address;
    }

    public String getPhoneNumber() {
        return this.phoneNumber;
    }
}
