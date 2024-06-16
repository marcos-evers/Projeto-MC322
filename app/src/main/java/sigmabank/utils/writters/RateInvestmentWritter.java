package sigmabank.utils.writters;

import java.io.File;
// import java.math.BigDecimal;
// import java.time.LocalDate;
// import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

// import sigmabank.model.investment.ROIFrequencyType;
import sigmabank.model.investment.RateInvestment;

public class RateInvestmentWritter implements WritterXML<RateInvestment> {
    @Override
    public void writeToXML(RateInvestment investment, String pathToXML) {

        try {
            File xmlFile = new File(pathToXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            // Check if file exists and has content
            if (xmlFile.exists() && xmlFile.length() != 0) {
                doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();
            } else {
                doc = dBuilder.newDocument();
                Element rootElement = doc.createElement("Investments");
                doc.appendChild(rootElement);
            }

            // Root element
            Element root = doc.getDocumentElement();

            // RateInvestment element
            Element investmentElement = doc.createElement("RateInvestment");
            root.appendChild(investmentElement);

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

    /* 
    public static void main(String[] args) {
        RateInvestment investment = new RateInvestment(
                new BigDecimal("1000.00"),
                UUID.randomUUID(),
                LocalDate.parse("2023-01-01"),
                new BigDecimal("0.05"),
                ROIFrequencyType.MONTHLY
        );

        RateInvestmentWritter writter = new RateInvestmentWritter();
        try {
            writter.writeToXML(investment, "app/src/main/java/sigmabank/utils/xml_test/rateInvestment.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
}
