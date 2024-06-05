# Projeto MC322 - Sigma Bank

Este é o nosso projeto final para a disciplina de Orientação a Objetos.
Nosso projeto é um sistema que simula um banco virtual feito em java.

## Ferramentas Usadas

- ```MySQL``` para Banco de Dados,
- ```JavaFX``` para GUI,
- ```Gradle``` como Build System,
- ```JUnit Jupiter``` como Framework de Teste

## Build

```shell
gradle :app:run  # to run the app
gradle :app:test # to run unit the app tests
```

## Functionalities

- Register of new clients
- Bank Transfer payment
- Balance and Statement
- Loan
- Investment
- Different types of Bank Accounts

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

- [ ] Transaction
    - UUID
    - buyer: UUID
    - seller: UUID
    - type: TransactionType
    - value: BigNumber
    - time: Date
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

- [ ] Loan
    - value: BigDecimal
    - rate: BigDecimal
    - termInMonths: uint32
    - clientUUID: UUID
    - startDay: Date

- [ ] Investment

