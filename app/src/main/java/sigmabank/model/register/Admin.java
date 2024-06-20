package sigmabank.model.register;

import javax.xml.bind.annotation.XmlElement;

import sigmabank.utils.HashPassword;

import java.time.LocalDate;

public class Admin extends Register {
    @XmlElement private final String cpf;

    public Admin(String name, LocalDate dateOfBirth, String cpf) {
        super(name, dateOfBirth);
        this.cpf = cpf;
    }

    /**
     * Sets the password of the Admin.
     * 
     * @param password The password to be set
     */
    public void setPassword(String password) {
        String passwordHash = HashPassword.hashPassword(cpf, password);

        this.setPasswordHash(passwordHash);
    }

    public String getCPF() {
        return this.cpf;
    }
}
