package sigmabank.model.register;

import java.time.LocalDate;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import sigmabank.utils.HashPassword;

@XmlRootElement
public class Register {
    // Personal Data
    @XmlElement private final UUID uuid;
    @XmlElement private final String name;
    @XmlElement private final LocalDate dateOfBirth;
    @XmlElement private String registerPasswordHash;

    // Contact Data
    private String email;
    private String phoneNumber;
    private String address;

    public Register() {
        this.uuid = UUID.randomUUID();
        this.name = null;
        this.dateOfBirth = null;
    }

    public Register(String name, LocalDate dateOfBirth) {
        this.uuid = UUID.randomUUID();
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Sets the email attribute of the Register.
     *
     * @param email The phone number to set.
     * @throws IllegalArgumentException if the provided email does not match the expected format.
     * @info The used regex is ^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$
     */
    public void setEmail(String email) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher matcher = pattern.matcher(email);

        if (matcher.find())
            this.email = email;
        else
            throw new IllegalArgumentException(email + " is not a email");
    }

    /**
     * Sets the phone number attribute of the Register.
     *
     * @param phoneNumber The phone number to set.
     * @throws IllegalArgumentException if the provided phone number does not match the expected format.
     * @info The used regex is ^\\+?[0-9()-]*$"
     */
    public void setPhoneNumber(String phoneNumber) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile("^\\+?[0-9()-]*$");
        Matcher matcher = pattern.matcher(phoneNumber);
        
        if (matcher.find())
            this.phoneNumber = phoneNumber;
        else
            throw new IllegalArgumentException(phoneNumber + " is not a phone number");
    }

    /**
     * Sets the address attribute of the Register.
     *
     * @param address The phone number to set.
     * @throws IllegalArgumentException if the provided address does not match the expected format.
     * @info The used regex is ^(Rua|Avenida) [a-zA-Z0-9 ]+, [0-9]+, [0-9]{5}-[0-9]{3}
     */
    public void setAddress(String address) throws IllegalArgumentException {
        Pattern pattern = Pattern.compile("^(Rua|Avenida) [a-zA-Z0-9 ]+, [0-9]+, [0-9]{5}-[0-9]{3}");
        Matcher matcher = pattern.matcher(address);

        if (matcher.find())
            this.address = address;
        else
            throw new IllegalArgumentException(address + " is not a address");
    }

    public void setRegisterPassword(String password) {
        this.registerPasswordHash = HashPassword.hashPassword(uuid, password);
    }

    public UUID getUuid() {
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

    public boolean validatePassword(String password){
        return HashPassword.hashPassword(uuid, password).equals(this.registerPasswordHash);
    }
}
