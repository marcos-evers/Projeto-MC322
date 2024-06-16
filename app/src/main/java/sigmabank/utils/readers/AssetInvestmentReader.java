package sigmabank.utils.readers;

import java.io.File;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import sigmabank.model.investment.AssetInvestment;


public class AssetInvestmentReader implements ReaderXML<AssetInvestment>{
    @Override
    public ArrayList<AssetInvestment> readFromXML(String pathToXML) {
        ArrayList<AssetInvestment> investments = new ArrayList<>();

        try {
            File file = new File(pathToXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("AssetInvestment");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element investmentElement = (Element) nodeList.item(i);

                BigDecimal investedValue = new BigDecimal(investmentElement.getElementsByTagName("investedValue").item(0).getTextContent());
                BigDecimal value = new BigDecimal(investmentElement.getElementsByTagName("value").item(0).getTextContent());
                BigDecimal retrievedValue = new BigDecimal(investmentElement.getElementsByTagName("retrievedValue").item(0).getTextContent());
                String clientUUIDStr = investmentElement.getElementsByTagName("clientUUID").item(0).getTextContent();
                LocalDate startDate = LocalDate.parse(investmentElement.getElementsByTagName("startDate").item(0).getTextContent());
                BigDecimal assetValue = new BigDecimal(investmentElement.getElementsByTagName("assetValue").item(0).getTextContent());
                BigDecimal assetQuantity = new BigDecimal(investmentElement.getElementsByTagName("assetQuantity").item(0).getTextContent());
                
                UUID clientUUID = UUID.fromString(clientUUIDStr);
                AssetInvestment investment = new AssetInvestment(investedValue, value, retrievedValue, clientUUID, startDate, assetValue, assetQuantity);

                investments.add(investment);
            }

        } catch (Exception e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return investments;
    }

    @Override
    public ArrayList<AssetInvestment> readFromXML(String pathToXML, String identifier){
        ArrayList<AssetInvestment> investments = new ArrayList<>();

        try {
            File file = new File(pathToXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("AssetInvestment");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element investmentElement = (Element) nodeList.item(i);

                BigDecimal investedValue = new BigDecimal(investmentElement.getElementsByTagName("investedValue").item(0).getTextContent());
                BigDecimal value = new BigDecimal(investmentElement.getElementsByTagName("value").item(0).getTextContent());
                BigDecimal retrievedValue = new BigDecimal(investmentElement.getElementsByTagName("retrievedValue").item(0).getTextContent());
                String clientUUIDStr = investmentElement.getElementsByTagName("clientUUID").item(0).getTextContent();
                LocalDate startDate = LocalDate.parse(investmentElement.getElementsByTagName("startDate").item(0).getTextContent());
                BigDecimal assetValue = new BigDecimal(investmentElement.getElementsByTagName("assetValue").item(0).getTextContent());
                BigDecimal assetQuantity = new BigDecimal(investmentElement.getElementsByTagName("assetQuantity").item(0).getTextContent());
                UUID clientUUID = UUID.fromString(clientUUIDStr);

                if(clientUUID.toString().equals(identifier)){
                    AssetInvestment investment = new AssetInvestment(investedValue, value, retrievedValue, clientUUID, startDate, assetValue, assetQuantity);
                    investments.add(investment);
                }
            }

        } catch (Exception e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return investments;
    }

    /* 
    public static void main(String[] args) {
        AssetInvestmentReader reader = new AssetInvestmentReader();
        ArrayList<AssetInvestment> investments = reader.readFromXML("app/src/main/java/sigmabank/utils/xml_test/assetInvestment.xml");
        System.out.println(investments.size());
        for (AssetInvestment investment : investments) {
            System.out.println(investment);
        }
    }  
    */  
}
