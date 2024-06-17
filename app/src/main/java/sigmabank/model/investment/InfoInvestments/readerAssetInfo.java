package sigmabank.model.investment.InfoInvestments;

import java.io.File;
import java.math.BigDecimal;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class readerAssetInfo {

    public static InfoInvest readAssetInvestments(String pathToXML, String identifier) {
        InfoInvest investment = null;
        try {
            File file = new File(pathToXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("AssetInvestment");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                String type = element.getElementsByTagName("type").item(0).getTextContent();
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                BigDecimal assetValue = new BigDecimal(element.getElementsByTagName("assetValue").item(0).getTextContent());

                if (type.equals(identifier)) {
                    investment = new InfoInvest(name, assetValue);
                    
                }
            }

        } catch (Exception e) {
            System.err.println("Error reading the XML file: " + e.getMessage());
            e.printStackTrace();
        }
        return investment;
    }
}
