#!/bin/bash

touch server/src/main/resources/database/Clients.xml
touch server/src/main/resources/database/ClientsToApproval.xml
touch server/src/main/resources/database/Loans.xml
touch server/src/main/resources/database/LoansToApproval.xml
touch server/src/main/resources/database/RateInvestments.xml
touch server/src/main/resources/database/AssetInvestments.xml

echo "<Clients/>" | tee server/src/main/resources/database/Clients.xml
echo "<ClientsToApproval/>" | tee server/src/main/resources/database/ClientsToApproval.xml

echo "<Loans/>" | tee server/src/main/resources/database/Loans.xml
echo "<LoansToApproval/>" | tee server/src/main/resources/database/LoansToApproval.xml

echo "<RateInvestments/>" | tee server/src/main/resources/database/RateInvestments.xml
echo "<AssetInvestments/>" | tee server/src/main/resources/database/AssetInvestments.xml
