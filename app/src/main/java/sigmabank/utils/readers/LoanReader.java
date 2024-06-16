package sigmabank.utils.readers;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import sigmabank.model.loan.Loan;

public class LoanReader implements ReaderXML<Loan> {
    @Override
    public ArrayList<Loan> readFromXML(String path) {
        ArrayList<Loan> loans = new ArrayList<>();
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
                LocalDate startDay = LocalDate.parse(loanElement.getElementsByTagName("startDay").item(0).getTextContent());
                LocalDate lastUpdateDate = LocalDate.parse(loanElement.getElementsByTagName("lastUpdateDate").item(0).getTextContent());
                BigDecimal amount = new BigDecimal(loanElement.getElementsByTagName("amount").item(0).getTextContent());
                
                Loan loan = new Loan(value, fee, clientUUID, startDay, amount, lastUpdateDate);
                loans.add(loan);
            }

        } catch (Exception e) {
            System.err.println("Error reading the XML file: " + e.getMessage());
            e.printStackTrace();
        }

        return loans;
    }

    @Override
    public ArrayList<Loan> readFromXML(String pathToXML, String identifier){
        // TODO implementation
        return null;
    }

    /* 
    public static void main(String[] args) {
        LoanReader reader = new LoanReader();
        ArrayList<Loan> loans = reader.readFromXML("app/src/main/java/sigmabank/utils/xml_test/loan.xml");
        System.out.println(loans.size());
        for (Loan loan : loans) {
            System.out.println(loan);
        }
    }
        */
}