package sigmabank.model.investment.InfoInvestments;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import sigmabank.model.investment.AssetInvestEnum;

public class ReaderAssetInfo {

    /**
     * Read Infoinvest - AssetInvestment objects from an XML source file.
     * 
     * @param pathToXML path to the XML file.
     * @param identifier the identifier of the asset investment to read.
     * @return InfoInvest
     */
    public static InfoInvest readAssetInvestment(String pathToXML, String identifier) {
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

                AssetInvestEnum assetType = AssetInvestEnum.valueOf(element.getElementsByTagName("type").item(0).getTextContent());
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                BigDecimal assetValue = new BigDecimal(element.getElementsByTagName("assetValue").item(0).getTextContent());

                if (assetType.toString().equals(identifier)) {
                    investment = new InfoInvest(name, assetValue, assetType);
                    break;
                }
            }

        } catch (Exception e) {
            System.err.println("Error reading the XML file: " + e.getMessage());
            e.printStackTrace();
        }
        return investment;
    }
    
    /**
     * Read all Infoinvest - AssetInvestment objects from an XML source file presented in resources.
     * 
     * @return List<InfoInvest>
     */
    public static List<InfoInvest> readAssetInvestments() {
        List<InfoInvest> investments = new ArrayList<>();
        try {
            File file = new File("src/main/resources/AssetInvestments.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("AssetInvestment");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                AssetInvestEnum assetType = AssetInvestEnum.valueOf(element.getElementsByTagName("type").item(0).getTextContent());
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                BigDecimal assetValue = new BigDecimal(element.getElementsByTagName("assetValue").item(0).getTextContent());

                investments.add(new InfoInvest(name, assetValue, assetType));
            }
        } catch (Exception e) {
            System.err.println("Error reading the XML file: " + e.getMessage());
            e.printStackTrace();
        }

        return investments;
    }
}
