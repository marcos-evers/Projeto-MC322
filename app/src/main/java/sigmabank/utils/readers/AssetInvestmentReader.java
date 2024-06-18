package sigmabank.utils.readers;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import sigmabank.model.investment.AssetInvestEnum;
import sigmabank.model.investment.AssetInvestment;


public class AssetInvestmentReader implements ReaderXML<AssetInvestment>{
    @Override
    public List<AssetInvestment> readFromXML(String pathToXML) {
        List<AssetInvestment> investments = new ArrayList<>();

        try {
            File file = new File(pathToXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("AssetInvestment");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element investmentElement = (Element) nodeList.item(i);

                String name = investmentElement.getElementsByTagName("name").item(0).getTextContent();
                BigDecimal investedValue = new BigDecimal(investmentElement.getElementsByTagName("investedValue").item(0).getTextContent());
                BigDecimal value = new BigDecimal(investmentElement.getElementsByTagName("value").item(0).getTextContent());
                BigDecimal retrievedValue = new BigDecimal(investmentElement.getElementsByTagName("retrievedValue").item(0).getTextContent());
                String clientUUIDStr = investmentElement.getElementsByTagName("clientUUID").item(0).getTextContent();
                LocalDate startDate = LocalDate.parse(investmentElement.getElementsByTagName("startDate").item(0).getTextContent());
                BigDecimal assetValue = new BigDecimal(investmentElement.getElementsByTagName("assetValue").item(0).getTextContent());
                BigDecimal assetQuantity = new BigDecimal(investmentElement.getElementsByTagName("assetQuantity").item(0).getTextContent());
                AssetInvestEnum assetType = AssetInvestEnum.valueOf(investmentElement.getElementsByTagName("assetType").item(0).getTextContent());

                UUID clientUUID = UUID.fromString(clientUUIDStr);
                AssetInvestment investment = new AssetInvestment(name, investedValue, value, retrievedValue, clientUUID, startDate, assetValue, assetQuantity, assetType);

                investments.add(investment);
            }

        } catch (Exception e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return investments;
    }

    @Override
    public List<AssetInvestment> readFromXML(String pathToXML, String identifier){
        List<AssetInvestment> investments = new ArrayList<>();

        try {
            File file = new File(pathToXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("AssetInvestment");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element investmentElement = (Element) nodeList.item(i);

                String name = investmentElement.getElementsByTagName("name").item(0).getTextContent();
                BigDecimal investedValue = new BigDecimal(investmentElement.getElementsByTagName("investedValue").item(0).getTextContent());
                BigDecimal value = new BigDecimal(investmentElement.getElementsByTagName("value").item(0).getTextContent());
                BigDecimal retrievedValue = new BigDecimal(investmentElement.getElementsByTagName("retrievedValue").item(0).getTextContent());
                String clientUUIDStr = investmentElement.getElementsByTagName("clientUUID").item(0).getTextContent();
                LocalDate startDate = LocalDate.parse(investmentElement.getElementsByTagName("startDate").item(0).getTextContent());
                BigDecimal assetValue = new BigDecimal(investmentElement.getElementsByTagName("assetValue").item(0).getTextContent());
                BigDecimal assetQuantity = new BigDecimal(investmentElement.getElementsByTagName("assetQuantity").item(0).getTextContent());
                UUID clientUUID = UUID.fromString(clientUUIDStr);
                AssetInvestEnum assetType = AssetInvestEnum.valueOf(investmentElement.getElementsByTagName("assetType").item(0).getTextContent());

                if(clientUUID.toString().equals(identifier)){
                    AssetInvestment investment = new AssetInvestment(name, investedValue, value, retrievedValue, clientUUID, startDate, assetValue, assetQuantity, assetType);
                    investments.add(investment);
                }
            }

        } catch (Exception e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return investments;
    }

    public AssetInvestment queryInvestment(String pathToXML, String identifier, AssetInvestEnum assetType){
        List<AssetInvestment> investments = readFromXML(pathToXML, identifier);
        for (AssetInvestment investment : investments) {
            if(investment.getAssetType().equals(assetType)){
                return investment;
            }
        }
        return null;
    }
}
