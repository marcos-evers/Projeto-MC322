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
gradle :app:test # to run unit the app tests
```

## Functionalities

- Register of new clients
- Bank transfer payment
- Balance and statement
- Loan
- Investment
- Different types of bank accounts

## Classes TODO

- [x] Register
    - UUID (final)
    - name: string (final)
    - dateOfBirth: string (final)
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
    - creationDate: string (final)
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
    - dueDate: string (final)
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

- [ ] Transaction
    - UUID
    - buyer: UUID
    - seller: UUID
    - type: TransactionType
    - value: BigNumber
    - time: string
    - status: TransactionStatus

- [ ] TransactionType(Enum)
    - TRANSFER
    - PAYMENT
    - INVESTMENT
    - LOAN
    - CREDIT_CARD
    - DEBIT_CARD

- [ ] TransactionStatus(Enum)
    - PROCESSING
    - APPROVED
    - CANCELLED

- [ ] Admin(Person)
    - CPF: String (final)

- [ ] Investment

- [ ] Loan

