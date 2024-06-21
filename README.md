# MC322 - Sigma Bank

This is our final project for the OOP discipline.
Our project aims to model a system that emulates a virtual bank, made with Java.

## Tools

- ```JavaFX``` for the GUI;
- ```Gradle``` as build tool;
- ```JUnit Jupiter``` como testing framework.

## Build commands

```shell
gradle :app:run  # to run the app
gradle :app:test # to run the app unit tests

gradle :server:run  # to run the server
gradle :server:test # to run the server unit tests

chmod +x ./sigmarun.sh # linux command for allowing the bash file to be executed
./sigmarun.sh # to run both app and server, the full aplication 
```

## Functionalities

- Register of new clients
- Bank transfer as payment method
- Balance and statement
- Loans
- Investments
- Different types of bank accounts

## Classes TODO

- [x] Register
    - UUID (final)
    - name: string (final)
    - dateOfBirth: Date (final)
    - email: string
    - phonenumber: string
    - address: string
    - password: string (db only)

- [x] ClientPersonal(Person)
    - CPF: string (final)
    - currentAccount (final)
    - savingsAccount (final)
    - salaryAccount  (final)
    - creditCards: List<CreditCard>
    - debitCards: List<DebitCard>

- [x] ClientEnterprise(Person)
    - CNPJ: string (final)
    - currentAccount (final)
    - creditCards: List<CreditCard>
    - debitCards: List<DebitCard>

- [ ] Account
    - UUID (final)
    - clientUUID: UUID (final)
    - creationDate: Date (final) *
    - balance: BigNumber
    - statement: List<Transaction>

- [ ] CurrentAccount(Account)
    - investments: List<Investment>
    - loans: List<Loan>

- [ ] SavingsAccount(Account)
    - savingInvestment: Investment

- [ ] SalaryAccount(Account)
    - sellerUUID: UUID

- [x] BankCard
    - number: string (final)
    - dueDate: Date (final) *
    - securityCode: string (final)
    - clientUUID: UUID (final)
    - password: string

- [x] CreditCard(BankCard)
    - credit: BigNumber
    - statement: List<Transaction> (final)
    - dueDay: uint8 (final)
    - closeDay: uint8 (final)

- [x] DebitCard(BankCard)
    - accountUUID (final)

- [x] Transaction
    - UUID (final)
    - buyer: UUID (final)
    - seller: UUID (final)
    - type: TransactionType (final)
    - value: BigNumber (final)
    - time: Date (final)
    - status: TransactionStatus
    - discrition: String (final)

- [x] TransactionType(Enum)
    - TRANSFER
    - PAYMENT
    - INVESTMENT
    - LOAN
    - CREDIT_CARD
    - DEBIT_CARD

- [x] TransactionStatus(Enum)
    - PROCESSING
    - APPROVED
    - CANCELLED

- [ ] Admin(Person)
    - CPF: String (final)

- [ ] Loan
    - value: BigDecimal (final)
    - fee: BigDecimal (final)
    - clientUUID: UUID (final)
    - startDay: Date (final)
    - montante: BigDecimal 

- [ ] Investment

