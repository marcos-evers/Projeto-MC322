# MC322 - Sigma Bank

This is our final project for the OOP discipline.
Our project aims to model a system that emulates a virtual bank, made with Java.

## Tools

We used ```JavaFX``` for the GUI and ```Gradle``` for the build system. The server was made
with the Java SE api, and little of the xml parsing with JAXB.

## Build commands

```shell
gradle :app:run  # to run the app
gradle :app:test # to run the app unit tests

gradle :server:run  # to run the server
gradle :server:test # to run the server unit tests

chmod +x ./sigmarun.sh # linux command for allowing the bash file to be executed
./sigmarun.sh # to run both app and server, the full application 

./sigmarun.bat # to run both app and server, the full application windows
```

## Functionalities

- Register of new clients
- Creation of two types of investments -- asset and rate investments
- Creation of loan
- Approvement of new clients and new loans
- An administrator

## Front issues
- Erro ao tentar abrir a janela de resgatar o investimento
- Não consegue criar investimento de juros (valor inválido)

