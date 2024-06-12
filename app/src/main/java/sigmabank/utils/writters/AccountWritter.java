package sigmabank.utils.writters;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sigmabank.model.account.CurrentAccount;
import sigmabank.model.investment.Investment;
import sigmabank.model.loan.Loan;

public class AccountWritter implements WritterXML<CurrentAccount> {
    @Override
    public void writeToXML(CurrentAccount account, String filename) throws IOException{
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
                Element rootElement = doc.createElement("CurrentAccounts");
                doc.appendChild(rootElement);
            }

            Element root = doc.getDocumentElement();
            Element accountElement = doc.createElement("CurrentAccount");
            root.appendChild(accountElement);

            Element investmentsElement = doc.createElement("Investments");
            for (Investment investment : account.getInvestments()) {
                Element investmentElement = doc.createElement("Investment");
                // Assuming Investment has a method toString() that outputs relevant data
                investmentElement.appendChild(doc.createTextNode(investment.toString()));
                investmentsElement.appendChild(investmentElement);
            }
            accountElement.appendChild(investmentsElement);

            Element loansElement = doc.createElement("Loans");
            for (Loan loan : account.getLoans()) {
                Element loanElement = doc.createElement("Loan");
                // Assuming Loan has a method toString() that outputs relevant data
                loanElement.appendChild(doc.createTextNode(loan.toString()));
                loansElement.appendChild(loanElement);
            }
            accountElement.appendChild(loansElement);

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
