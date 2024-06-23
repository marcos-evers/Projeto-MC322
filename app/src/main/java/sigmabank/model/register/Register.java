package sigmabank.model.register;

import java.time.LocalDate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import sigmabank.utils.LocalDateAdapter;

@XmlRootElement
public abstract class Register {
    // Personal Data
    @XmlElement private final UUID uuid;
    @XmlElement private final String name;

    @XmlElement
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private final LocalDate dateOfBirth;

    private String passwordHash;

    // Contact Data
    private String email;
    private String phoneNumber;
    private String address;

    public Register() {
        this.uuid = UUID.randomUUID();
        this.name = null;
        this.dateOfBirth = null;
        this.passwordHash = null;
    }

    public Register(String name, LocalDate dateOfBirth) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    public Register(String name, LocalDate dateOfBirth, UUID uuid) {
        this.uuid = uuid;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Sets the email attribute of the Register.
     *
     * @param email The emall string to be set
     * @throws InvalidEmailException if the provided email does not match the expected format.
     */
    public void setEmail(String email) throws InvalidEmailException {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);

        if (matcher.find())
            this.email = email;
        else
            throw new InvalidEmailException(email + " is not a email");
    }

    /**
     * Sets the phone number attribute of the Register.
     *
     * @param phoneNumber The phone number string to set.
     * @throws InvalidPhoneNumberException if the provided phone number does not match the expected format.
     */
    public void setPhoneNumber(String phoneNumber) throws InvalidPhoneNumberException {
        // TODO fix regex
        Pattern pattern = Pattern.compile("^\\+?[0-9()-]*$");
        Matcher matcher = pattern.matcher(phoneNumber);
        
        if (matcher.find())
            this.phoneNumber = phoneNumber;
        else
            throw new InvalidPhoneNumberException(phoneNumber + " is not a phone number");
    }

    /**
     * Sets the address attribute of the Register.
     *
     * @param address The adress string to set.
     * @throws InvalidAddressException if the provided address does not match the expected format.
     */
    public void setAddress(String address) throws InvalidAddressException {
        Pattern pattern = Pattern.compile("^(Rua|Avenida) [a-zA-Z0-9 ]+, [0-9]+, [0-9]{5}-[0-9]{3}");
        Matcher matcher = pattern.matcher(address);

        if (matcher.find())
            this.address = address;
        else
            throw new InvalidAddressException(address + " is not a address");
    }

    public abstract void setPassword(String password);

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }

    public LocalDate getDateOfBirth() {
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

    public String getPasswordHash() {
        return this.passwordHash;
    }
}
