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

## Backend TODO

- [ ] Investment Connection
    - [x] Create Investment
    - [x] Get Investments
    - [x] Connection Investment
    - [x] Invest More Investment
    - [x] Retrieve Investment
    - [ ] Connection Investment Operation
- [ ] Loan Connection
    - [x] Post Loan
    - [x] Get Loan
    - [x] Connection Loan Front and Back 
    - [ ] Pay Loan
    - [ ] Sell Loan
    - [ ] Connection Loan Pay/Sell

