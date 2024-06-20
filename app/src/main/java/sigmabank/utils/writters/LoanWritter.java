package sigmabank.utils.writters;

import java.io.File;
import java.io.IOException;
import java.util.List;
 
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sigmabank.model.loan.Loan;

public class LoanWritter implements WritterXML<Loan> {
    /**
     * Create an XML element from a Loan object.
     * 
     * @param doc
     * @param loan
     * @return Element Loan
     */
    private Element createElementFromLoan(Document doc, Loan loan) {
        Element loanElement = doc.createElement("Loan");

        Element value = doc.createElement("value");
        value.appendChild(doc.createTextNode(loan.getValue().toString()));
        loanElement.appendChild(value);

        Element fee = doc.createElement("fee");
        fee.appendChild(doc.createTextNode(loan.getFee().toString()));
        loanElement.appendChild(fee);

        Element clientUUID = doc.createElement("clientUUID");
        clientUUID.appendChild(doc.createTextNode(loan.getClientUUID().toString()));
        loanElement.appendChild(clientUUID);

        Element loanUUID = doc.createElement("loanUUID");
        loanUUID.appendChild(doc.createTextNode(loan.getLoanUUID().toString()));
        loanElement.appendChild(loanUUID);

        Element startDay = doc.createElement("startDay");
        startDay.appendChild(doc.createTextNode(loan.getStartDay().toString()));
        loanElement.appendChild(startDay);

        Element lastUpdatedDate = doc.createElement("lastUpdatedDate");
        lastUpdatedDate.appendChild(doc.createTextNode(loan.getLastUpdateDate().toString()));
        loanElement.appendChild(lastUpdatedDate);

        Element amount = doc.createElement("amount");
        amount.appendChild(doc.createTextNode(loan.getAmount().toString()));
        loanElement.appendChild(amount);
        return loanElement;
    }

    /**
     * Write a list of loans to an XML file.
     * 
     * @param label the label to use for the root element.
     * @param loans the list of loans to write.
     * @param filename the name of the file to write to.
     * @throws IOException if an error occurs while writing the file.
     */
    @Override
    public void writeToXML(String label, List<Object> loans, String filename) throws IOException {
        try {
            File xmlFile = new File(filename); // Using the pathToXML variable correctly
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            doc = dBuilder.newDocument();
            Element root = doc.createElement(label);
            doc.appendChild(root);

            for (Object loan: loans)
                root.appendChild(createElementFromLoan(doc, (Loan) loan));

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(xmlFile);
            transformer.transform(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
