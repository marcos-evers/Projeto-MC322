# Projeto MC322 - Sigma Bank

Este é o nosso projeto final para a disciplina de Orientação a Objetos.
Nosso projeto é um sistema que simula um banco virtual feito em java.

## Ferramentas Usadas

- ```MySQL``` para Banco de Dados,
- ```JavaFX``` para GUI,
- ```Gradle``` como Build System
- ```JUnit Jupiter``` como Framework de Test

## Build

```shell
./gradlew run # to run the app
./gradlew test # to run unit tests
```

## Functionalities

- Register of new clients
- Bank Transfer payment
- Balance and Statement
- Loan
- Investment
- Different types of Bank Accounts

## Classes TODO

- [ ] Register
    - UUID (final)
    - name: string (final)
    - dateOfBirth: string (final)
    - email: string
    - phonenumber: string
    - address: string
    - password: string (db only)

- [ ] ClientPersonal(Person)
    - CPF: string (final)
    - currentAccount (final)
    - savingsAccount (final)
    - salaryAccount  (final)
    - creditCards: List<CreditCard>
    - debitCards: List<DebitCard>

- [ ] ClientEnterprise(Person)
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

- [ ] BankCard
    - number: UUID
    - dueData: string
    - securityCode: string
    - clientUUID: UUID
    - password: string

- [ ] CreditCard(BankCard)
    - credit: BigNumber
    - statement: List<Transaction>
    - dueDay: uint8
    - closeDay: uint8

- [ ] DebitCard(BankCard)
    - accountUUID

- [ ] Transaction
    - UUID
    - buyer: UUID
    - seller: UUID
    - type: TransactionType
    - value: BigNumber
    - time: string

- [ ] TransactionType(Enum)
    - TRANSFER
    - PAYMENT
    - INVESTMENT
    - LOAN
    - CREDIT_CARD
    - DEBIT_CARD

- [ ] Admin(Person)
    - CPF: String (final)

- [ ] Investment

- [ ] Loan

