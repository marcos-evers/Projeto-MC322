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

import sigmabank.model.investment.ROIFrequencyType;
import sigmabank.model.investment.RateInvestEnum;
import sigmabank.model.investment.RateInvestment;

public class RateInvestmentReader implements ReaderXML<RateInvestment> {
    @Override
    public ArrayList<RateInvestment> readFromXML(String pathToXML) {
        ArrayList<RateInvestment> investments = new ArrayList<>();

        try {
            File file = new File(pathToXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("RateInvestment");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element investmentElement = (Element) nodeList.item(i);

                String name = investmentElement.getElementsByTagName("name").item(0).getTextContent();
                BigDecimal investedValue = new BigDecimal(investmentElement.getElementsByTagName("investedValue").item(0).getTextContent());
                BigDecimal value = new BigDecimal(investmentElement.getElementsByTagName("value").item(0).getTextContent());
                BigDecimal retrievedValue = new BigDecimal(investmentElement.getElementsByTagName("retrievedValue").item(0).getTextContent());
                String clientUUIDStr = investmentElement.getElementsByTagName("clientUUID").item(0).getTextContent();
                LocalDate startDate = LocalDate.parse(investmentElement.getElementsByTagName("startDate").item(0).getTextContent());
                BigDecimal rate = new BigDecimal(investmentElement.getElementsByTagName("rate").item(0).getTextContent());
                BigDecimal addedValue = new BigDecimal(investmentElement.getElementsByTagName("addedValue").item(0).getTextContent());
                String frequencyTypeStr = investmentElement.getElementsByTagName("frequencyType").item(0).getTextContent();
                LocalDate lastUpdateDate = LocalDate.parse(investmentElement.getElementsByTagName("lastUpdateDate").item(0).getTextContent());
                RateInvestEnum rateType = RateInvestEnum.valueOf(investmentElement.getElementsByTagName("rateType").item(0).getTextContent());

                UUID clientUUID = UUID.fromString(clientUUIDStr);
                ROIFrequencyType frequencyType = ROIFrequencyType.valueOf(frequencyTypeStr);
                RateInvestment investment = new RateInvestment(name, investedValue, value, retrievedValue, clientUUID, startDate, rate, addedValue, frequencyType, lastUpdateDate, rateType);

                investments.add(investment);
            }

        } catch (Exception e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return investments;
    }

    public ArrayList<RateInvestment> readFromXML(String pathToXML, String identifier){
        ArrayList<RateInvestment> investments = new ArrayList<>();

        try {
            File file = new File(pathToXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("RateInvestment");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element investmentElement = (Element) nodeList.item(i);

                String name = investmentElement.getElementsByTagName("name").item(0).getTextContent();
                BigDecimal investedValue = new BigDecimal(investmentElement.getElementsByTagName("investedValue").item(0).getTextContent());
                BigDecimal value = new BigDecimal(investmentElement.getElementsByTagName("value").item(0).getTextContent());
                BigDecimal retrievedValue = new BigDecimal(investmentElement.getElementsByTagName("retrievedValue").item(0).getTextContent());
                String clientUUIDStr = investmentElement.getElementsByTagName("clientUUID").item(0).getTextContent();
                LocalDate startDate = LocalDate.parse(investmentElement.getElementsByTagName("startDate").item(0).getTextContent());
                BigDecimal rate = new BigDecimal(investmentElement.getElementsByTagName("rate").item(0).getTextContent());
                BigDecimal addedValue = new BigDecimal(investmentElement.getElementsByTagName("addedValue").item(0).getTextContent());
                String frequencyTypeStr = investmentElement.getElementsByTagName("frequencyType").item(0).getTextContent();
                LocalDate lastUpdateDate = LocalDate.parse(investmentElement.getElementsByTagName("lastUpdateDate").item(0).getTextContent());
                RateInvestEnum rateType = RateInvestEnum.valueOf(investmentElement.getElementsByTagName("rateType").item(0).getTextContent());

                UUID clientUUID = UUID.fromString(clientUUIDStr);
                ROIFrequencyType frequencyType = ROIFrequencyType.valueOf(frequencyTypeStr);
                
                if(clientUUID.toString().equals(identifier)){
                    RateInvestment investment = new RateInvestment(name, investedValue, value, retrievedValue, clientUUID, startDate, rate, addedValue, frequencyType, lastUpdateDate, rateType);
                    investments.add(investment);
                }
            }

        } catch (Exception e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return investments;
    }

    public RateInvestment queryInvestment(String pathToXML, String identifier, RateInvestEnum rateType){
        ArrayList<RateInvestment> investments = readFromXML(pathToXML, identifier);
        for (RateInvestment investment : investments) {
            if(investment.getRateType().equals(rateType)){
                return investment;
            }
        }
        return null;
    }
    
    /* 
    public static void main(String[] args) {
        ReaderXML<RateInvestment> reader = ReaderFactory.createReader(ReaderFactory.ReaderType.RATEINVESTMENT);
        ArrayList<RateInvestment> investments = reader.readFromXML("app/src/main/java/sigmabank/utils/xml_test/rateInvestment.xml");
        System.out.println(investments.size());
        for (RateInvestment investment : investments) {
            System.out.println(investment);
        }
    }
    */
    
}
