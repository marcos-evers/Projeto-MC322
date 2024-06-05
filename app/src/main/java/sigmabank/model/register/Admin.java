package sigmabank.model.register;

import javax.xml.bind.annotation.XmlElement;
import java.util.Date;

public class Admin extends Register {
    @XmlElement private final String cpf;

    public Admin(String name, Date dateOfBirth, String cpf) {
        super(name, dateOfBirth);
        this.cpf = cpf;
    }

    public String getCPF() {
        return this.cpf;
    }
}
