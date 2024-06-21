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

import sigmabank.model.investment.AssetInvestment;


public class AssetInvestmentWritter implements WritterXML<AssetInvestment> {

    /**
     * Create an XML element from a AssetInvestment object.
     * 
     * @param doc the document to create the element from.
     * @param investment the AssetInvestment object to create the element from.
     * @return Element AssetInvestment
     */
    private Element createElementFromInvestment(Document doc, AssetInvestment investment) {
        // AssetInvestment element
        Element investmentElement = doc.createElement("AssetInvestment");

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

        return investmentElement;
    }

    /**
     * Write a list of asset investments to an XML file.
     * 
     * @param label the label to use for the root element.
     * @param investments the list of asset investments to write.
     * @param pathToXML the path to the file to write to.
     */
    @Override
    public void writeToXML(String label , List<Object> investments, String pathToXML) throws IOException {
        try {
            File xmlFile = new File(pathToXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element root = doc.createElement(label);
            doc.appendChild(root);

            for (Object investment: investments)
                root.appendChild(createElementFromInvestment(doc, (AssetInvestment) investment));

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
