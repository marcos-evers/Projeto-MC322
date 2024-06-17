package sigmabank.utils.writters;

import java.io.File;
import java.io.IOException;

//import java.math.BigDecimal;
//import java.time.LocalDate;
//import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

//import sigmabank.model.investment.AssetInvestEnum;
import sigmabank.model.investment.AssetInvestment;


public class AssetInvestmentWritter implements WritterXML<AssetInvestment>{
    @Override
    public void writeToXML(AssetInvestment investment, String pathToXML) throws IOException{
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

            // AssetInvestment element
            Element investmentElement = doc.createElement("AssetInvestment");
            root.appendChild(investmentElement);

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

            // AssetValue element
            Element assetValue = doc.createElement("assetValue");
            assetValue.appendChild(doc.createTextNode(investment.getAssetValue().toString()));
            investmentElement.appendChild(assetValue);

            // AssetQuantity element
            Element assetQuantity = doc.createElement("assetQuantity");
            assetQuantity.appendChild(doc.createTextNode(investment.getAssetQuantity().toString()));
            investmentElement.appendChild(assetQuantity);

            // AssetType element
            Element assetType = doc.createElement("assetType");
            assetType.appendChild(doc.createTextNode(investment.getAssetType().toString()));
            investmentElement.appendChild(assetType);

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
        AssetInvestment investment = new AssetInvestment(
                "Test Asset Investment",
                new BigDecimal("1000.00"),
                UUID.randomUUID(),
                LocalDate.parse("2023-01-01"),
                new BigDecimal("50.00"),
                AssetInvestEnum.BITCOIN
        );

        WritterXML<AssetInvestment> writter = WritterFactory.createWritter(WritterFactory.WritterType.ASSETINVESTMENT);
        try {
            writter.writeToXML(investment, "app/src/main/java/sigmabank/utils/xml_test/assetInvestment.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
    
}
