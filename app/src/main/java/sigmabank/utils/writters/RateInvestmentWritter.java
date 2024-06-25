package sigmabank.utils.writters;

import java.io.File;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sigmabank.model.investment.RateInvestment;

public class RateInvestmentWritter implements WritterXML<RateInvestment> {
    /**
     * Create an XML element from a RateInvestment object.
     * 
     * @param doc the document to create the element from.
     * @param investment the RateInvestment object to create the element from.
     * @return Element RateInvestment 
     */
    private Element createElementFromInvestment(Document doc, RateInvestment investment) {
        // RateInvestment element
        Element investmentElement = doc.createElement("RateInvestment");

        // Name element
        Element name = doc.createElement("name");
        name.appendChild(doc.createTextNode(investment.getName()));
        investmentElement.appendChild(name);


        // InvestedValue element
        Element investedValue = doc.createElement("investedValue");
        investedValue.appendChild(doc.createTextNode(investment.getInvestedValue().toString()));
        investmentElement.appendChild(investedValue);

        // Value element
        Element value = doc.createElement("value");
        value.appendChild(doc.createTextNode(investment.getValue().toString()));
        investmentElement.appendChild(value);

        // RetrievedValue element
        Element retrievedValue = doc.createElement("retrievedValue");
        retrievedValue.appendChild(doc.createTextNode(investment.getRetrievedValue().toString()));
        investmentElement.appendChild(retrievedValue);

        // ClientUUID element
        Element clientUUID = doc.createElement("clientUUID");
        clientUUID.appendChild(doc.createTextNode(investment.getClientUUID().toString()));
        investmentElement.appendChild(clientUUID);

        // StartDate element
        Element startDate = doc.createElement("startDate");
        startDate.appendChild(doc.createTextNode(investment.getStartDate().toString()));
        investmentElement.appendChild(startDate);

        // Rate element
        Element rate = doc.createElement("rate");
        rate.appendChild(doc.createTextNode(investment.getRate().toString()));
        investmentElement.appendChild(rate);

        // AddedValue element
        Element addedValue = doc.createElement("addedValue");
        addedValue.appendChild(doc.createTextNode(investment.getAddedValue().toString()));
        investmentElement.appendChild(addedValue);

        // FrequencyType element
        Element frequencyType = doc.createElement("frequencyType");
        frequencyType.appendChild(doc.createTextNode(investment.getFrequencyType().toString()));
        investmentElement.appendChild(frequencyType);

        // LastUpdateDate element
        Element lastUpdateDate = doc.createElement("lastUpdateDate");
        lastUpdateDate.appendChild(doc.createTextNode(investment.getLastUpdateDate().toString()));
        investmentElement.appendChild(lastUpdateDate);

        // RateINvestEnum element
        Element rateType = doc.createElement("rateType");
        rateType.appendChild(doc.createTextNode(investment.getRateType().toString()));
        investmentElement.appendChild(rateType);

        return investmentElement;
    }

    /**
     * Write a list of RateInvestment objects to an XML file.
     * 
     * @param label label to use for the root element.
     * @param investments list of RateInvestment objects to write.
     * @param pathToXML path to the file to write to.
     */
    @Override
    public void writeToXML(String label, List<Object> investments, String pathToXML) {
        try {
            File xmlFile = new File(pathToXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element root = doc.createElement("Investments");
            doc.appendChild(root);

            for (Object investment: investments)
                root.appendChild(createElementFromInvestment(doc, (RateInvestment) investment));

            // Save XML content
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
