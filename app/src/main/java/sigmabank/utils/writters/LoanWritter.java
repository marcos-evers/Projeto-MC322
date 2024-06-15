package sigmabank.utils.writters;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.management.InvalidAttributeValueException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sigmabank.model.loan.Loan;

 
public class LoanWritter implements WritterXML<Loan>{
    @Override
    public void writeToXML(Loan loan, String filename) throws IOException{
        try {
            File xmlFile = new File(filename);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            if (xmlFile.exists()) {
                doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();
            } else {
                doc = dBuilder.newDocument();
                Element rootElement = doc.createElement("Loans");
                doc.appendChild(rootElement);
            }

            Element root = doc.getDocumentElement();
            Element loanElement = doc.createElement("Loan");
            root.appendChild(loanElement);

            Element value = doc.createElement("value");
            value.appendChild(doc.createTextNode(loan.getValue().toString()));
            loanElement.appendChild(value);

            Element fee = doc.createElement("fee");
            fee.appendChild(doc.createTextNode(loan.getFee().toString()));
            loanElement.appendChild(fee);

            Element clientUUID = doc.createElement("clientUUID");
            clientUUID.appendChild(doc.createTextNode(loan.getClientUUID().toString()));
            loanElement.appendChild(clientUUID);

            Element startDay = doc.createElement("startDay");
            startDay.appendChild(doc.createTextNode(loan.getStartDay().toString()));
            loanElement.appendChild(startDay);

            Element amount = doc.createElement("amount");
            amount.appendChild(doc.createTextNode(loan.getAmount().toString()));
            loanElement.appendChild(amount);

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InvalidAttributeValueException {
        Loan loan = new Loan(BigDecimal.valueOf(1000), BigDecimal.valueOf(0.5), UUID.randomUUID() , LocalDate.now());
        LoanWritter writter = new LoanWritter();

        try {
            writter.writeToXML(loan, "app/src/main/java/sigmabank/utils/xml_test/loan.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
