package sigmabank.model.register;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.management.InvalidAttributeValueException;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import sigmabank.model.investment.Investment;
import sigmabank.model.loan.Loan;
import sigmabank.utils.DocumentValidator;
import sigmabank.utils.HashPassword;


@XmlRootElement(name="Client")
public class Client extends Register {
    @XmlElement private final String cpf;
    private BigDecimal balance;

    private List<Investment> investments;
    private List<Loan> loans;

    public Client() {
        super();
        this.cpf = "00000000000";
        this.balance = BigDecimal.valueOf(0);
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
        this.balance = BigDecimal.valueOf(0);
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
        this.balance = BigDecimal.valueOf(0);
    }

    public Client(String name, LocalDate dateOfBirth, String cpf, UUID uuid, BigDecimal balance) throws InvalidCPFException, InvalidBirthDateException {
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
        this.balance = balance;
    }

    /**
     * Adds a new investment to the client.
     *
     * @param investment The investment object to be added.
     */
    public void addInvestment(Investment investment) {
        if (investment.getValue().compareTo(BigDecimal.ZERO) == 0)
            return;
        this.balance = this.balance.subtract(investment.getValue());
        this.investments.add(investment);
    }

    /**
     * Adds a new loan to the client.
     *
     * @param loan The loan object to be added.
     */
    public void addLoan(Loan loan) {
        if (loan.getValue().compareTo(BigDecimal.ZERO) == 0)
            return;
        this.balance = this.balance.add(loan.getValue());
        this.loans.add(loan);
    }

    /**
     * Remove a loan to the client.
     *
     * @param loan The loan object to be added.
     */
    public void removeLoan(Loan loan) {
        this.balance = this.balance.subtract(loan.getValue());
        this.loans.remove(loan);
    }

    /**
     * Remove a investment to the client.
     *
     * @param loan The loan object to be added.
     */
    public void removeInvestment(Investment investment) {
        this.balance = this.balance.add(investment.getValue());
        this.investments.remove(investment);
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

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    /**
     * Updates the value of the investments and loans of the client.
     */
    public void updateValues(){
        for (Investment investment : investments) {
            investment.updateValue();
        }
        
        for (Loan loan : loans) {
            loan.updateAmount();
        }
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

    /**
     * (Feature for future implementation) Initializes a transfer of funds from this account to the account of the specified client seller
     * 
     * @param clientSellerAccount The UUID of the client seller's account to transfer funds to.
     * @param amount The amount of money to transfer.
     * @param description A description of the transfer.
     * @param password The password of the account to transfer funds from.
     * @throws InvalidAttributeValueException if the password is incorrect
     * @throws InvalidAttributeValueException if the client seller's account does not exist
     * @throws InvalidAttributeValueException if the amount is higher than the balance of the account
     */
    public void Transfer(UUID clientSellerAccount, BigDecimal amount, String description, String password) {
        // Future implementation
    }
}
