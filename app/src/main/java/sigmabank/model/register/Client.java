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


@XmlRootElement
public class Client extends Register {
    @XmlElement private final String cpf;

    private List<Investment> investments;
    private List<Loan> loans;

    public Client() {
        super();
        this.cpf = "00000000000";
    }

    public Client(String name, LocalDate dateOfBirth, String cpf, String passwordHash) throws InvalidCPFException, InvalidBirthDateException {
        super(name, dateOfBirth, passwordHash);

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

    public String getCpf() {
        return this.cpf;
    }

    public List<Investment> getInvestments() {
        return investments;
    }

    public List<Loan> getLoan() {
        return loans;
    }

    private boolean isAdult(LocalDate dateOfBirth) {
        LocalDate eighteenYearsAgo = LocalDate.now().minusYears(18);
        return dateOfBirth.isBefore(eighteenYearsAgo);
    }
}
