# MC322 - Sigma Bank

This is our final project for the OOP discipline.
Our project aims to model a system that emulates investment and loan from a virtual bank, made with Java.

## Tools

We used ```JavaFX``` for the GUI and ```Gradle``` for the build system. The server was made
with the Java SE api, and little of the xml parsing with JAXB.

## Setup

To setup the file needed by the data base run ```./setup.sh```

## Build commands

```shell
gradle :app:run  # to run the desktop application
gradle :server:run  # to run the server

chmod +x ./sigmarun.sh # linux command for allowing the bash file to be executed

./sigmarun.sh # to run the desktop application and the server
./sigmarun.bat # to run the desktop application and the server
```

## Functionalities

- Register of new clients
- Creation of two types of investments -- asset and rate investments
- Creation of loan
- Approvement of new clients and new loans
- An administrator


