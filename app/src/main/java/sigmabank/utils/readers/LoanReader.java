package sigmabank.utils.readers;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import sigmabank.model.loan.Loan;

public class LoanReader implements ReaderXML<Loan> {
    @Override
    public List<Loan> readFromXML(String path) {
        List<Loan> loans = new ArrayList<>();
        try {
            File file = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Loan");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element loanElement = (Element) nodeList.item(i);

                BigDecimal value = new BigDecimal(loanElement.getElementsByTagName("value").item(0).getTextContent());
                BigDecimal fee = new BigDecimal(loanElement.getElementsByTagName("fee").item(0).getTextContent());
                UUID clientUUID = UUID.fromString(loanElement.getElementsByTagName("clientUUID").item(0).getTextContent());
                UUID loanUUID = UUID.fromString(loanElement.getElementsByTagName("loanUUID").item(0).getTextContent());
                LocalDate startDay = LocalDate.parse(loanElement.getElementsByTagName("startDay").item(0).getTextContent());
                LocalDate lastUpdateDate = LocalDate.parse(loanElement.getElementsByTagName("lastUpdatedDate").item(0).getTextContent());
                BigDecimal amount = new BigDecimal(loanElement.getElementsByTagName("amount").item(0).getTextContent());
                
                Loan loan = new Loan(value, fee, clientUUID, loanUUID,startDay, amount, lastUpdateDate);
                loans.add(loan);
            }

        } catch (Exception e) {
            System.err.println("Error reading the XML file: " + e.getMessage());
            e.printStackTrace();
        }

        return loans;
    }

    @Override
    public List<Loan> readFromXML(String pathToXML, String identifier){
        List<Loan> loans = new ArrayList<>();
        try {
            File file = new File(pathToXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Loan");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element loanElement = (Element) nodeList.item(i);

                BigDecimal value = new BigDecimal(loanElement.getElementsByTagName("value").item(0).getTextContent());
                BigDecimal fee = new BigDecimal(loanElement.getElementsByTagName("fee").item(0).getTextContent());
                UUID clientUUID = UUID.fromString(loanElement.getElementsByTagName("clientUUID").item(0).getTextContent());
                UUID loanUUID = UUID.fromString(loanElement.getElementsByTagName("loanUUID").item(0).getTextContent());
                LocalDate startDay = LocalDate.parse(loanElement.getElementsByTagName("startDay").item(0).getTextContent());
                LocalDate lastUpdateDate = LocalDate.parse(loanElement.getElementsByTagName("lastUpdateDate").item(0).getTextContent());
                BigDecimal amount = new BigDecimal(loanElement.getElementsByTagName("amount").item(0).getTextContent());
                
                if(clientUUID.toString().equals(identifier)){
                    Loan loan = new Loan(value, fee, clientUUID, loanUUID,startDay, amount, lastUpdateDate);
                    loans.add(loan);
                }
            }

        } catch (Exception e) {
            System.err.println("Error reading the XML file: " + e.getMessage());
            e.printStackTrace();
        }
        return loans;
    }

    public Loan queryLoan(String pathToXML, String identifier, String loanUUID){
        List<Loan> loans = readFromXML(pathToXML, identifier);
        for (Loan loan : loans) {
            if(loan.getLoanUUID().toString().equals(loanUUID)){
                return loan;
            }
        }
        return null;
    }

    /* 
    public static void main(String[] args) {
        ReaderXML<Loan> reader = ReaderFactory.createReader(ReaderFactory.ReaderType.LOAN);
        List<Loan> loans = reader.readFromXML("app/src/main/java/sigmabank/utils/xml_test/loanXML.xml");
        System.out.println(loans.size());
        for (Loan loan : loans) {
            System.out.println(loan);
        }
    }
    */
}