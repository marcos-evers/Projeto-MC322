package sigmabank.model.register;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import sigmabank.model.investment.Investment;
import sigmabank.model.loan.Loan;
import sigmabank.utils.DocumentValidator;
import sigmabank.utils.HashPassword;


@XmlRootElement
public class Client extends Register {
    @XmlElement private final String cpf;

    private List<Investment> investments;
    private List<Loan> loans;

    public Client() {
        super();
        this.cpf = "00000000000";
    }

    public Client(String cpf) {
        super();
        this.cpf = cpf;
    }

    public Client(String name, LocalDate dateOfBirth, String cpf) throws InvalidCPFException, InvalidBirthDateException {
        super(name, dateOfBirth);

        if (!DocumentValidator.isValidCPF(cpf)) {
            throw new InvalidCPFException("CPF inválido " + cpf);
        }
        if (!isAdult(dateOfBirth)) {
            throw new InvalidBirthDateException("A data de nascimento não é válida " + dateOfBirth);
        }
        
        this.cpf = cpf;
        this.investments = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    public Client(String name, LocalDate dateOfBirth, String cpf, UUID uuid) throws InvalidCPFException, InvalidBirthDateException {
        super(name, dateOfBirth, uuid);

        if (!DocumentValidator.isValidCPF(cpf)) {
            throw new InvalidCPFException("CPF inválido " + cpf);
        }
        if (!isAdult(dateOfBirth)) {
            throw new InvalidBirthDateException("A data de nascimento não é válida " + dateOfBirth);
        }
        
        this.cpf = cpf;
        this.investments = new ArrayList<>();
        this.loans = new ArrayList<>();
    }

    /**
     * Adds a new investment to the client.
     *
     * @param investment The investment object to be added.
     */
    public void addInvestiment(Investment investment) {
        this.investments.add(investment);
    }

    /**
     * Adds a new loan to the client.
     *
     * @param loan The loan object to be added.
     */
    public void addLoan(Loan loan) {
        this.loans.add(loan);
    }

    /**
     * Sets the password of the client.
     *
     * @param password The password string to be set.
     */
    public void setPassword(String password) {
        String passwordHash = HashPassword.hashPassword(cpf, password);

        this.setPasswordHash(passwordHash);
    }

    public String getCpf() {
        return this.cpf;
    }

    public String getFormattedCpf() {
        return this.cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");

    }

    public List<Investment> getInvestments() {
        return investments;
    }

    public List<Loan> getLoan() {
        return loans;
    }

    /**
     * Checks if the client is an adult, more than 18 years old.
     *
     * @param dateOfBirth The date of birth of the client.
     * @return True if the client is an adult, false otherwise.
     */
    private boolean isAdult(LocalDate dateOfBirth) {
        LocalDate eighteenYearsAgo = LocalDate.now().minusYears(18);
        return dateOfBirth.isBefore(eighteenYearsAgo);
    }
}
