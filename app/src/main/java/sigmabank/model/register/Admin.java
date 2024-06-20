package sigmabank.model.register;

import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;

public class Admin extends Register {
    @XmlElement private final String cpf;

    public Admin(String name, LocalDate dateOfBirth, String cpf) {
        super(name, dateOfBirth);
        this.cpf = cpf;
    }

    public void setPassword(String password) {

    }

    public String getCPF() {
        return this.cpf;
    }
}
