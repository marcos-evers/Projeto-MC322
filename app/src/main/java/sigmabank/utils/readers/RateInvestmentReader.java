package sigmabank.utils.readers;

import java.io.File;
import java.io.StringReader;
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
import org.xml.sax.InputSource;

import sigmabank.model.investment.ROIFrequencyType;
import sigmabank.model.investment.RateInvestEnum;
import sigmabank.model.investment.RateInvestment;

public class RateInvestmentReader implements ReaderXML<RateInvestment> {
    /**
     * Read RateInvestment objects from an XML file.
     * 
     * @param pathToXML
     * @return List<RateInvestment>
     */
    @Override
    public List<Object> readFromXML(String pathToXML) {
        List<Object> investments = new ArrayList<>();

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

                investments.add((Object) investment);
            }
        } catch (Exception e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return investments;
    }

    @Override
    public List<Object> readStringXML(String xmlContent){
        List<Object> investments = new ArrayList<>();


        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(new InputSource(new StringReader(xmlContent)));
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

                investments.add((Object) investment);
            }
        } catch (Exception e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return investments;
    }

    /**
     * Read RateInvestment objects from an XML file.
     * 
     * @param pathToXML
     * @param identifier
     * @return List<RateInvestment>
     */
    public List<RateInvestment> readFromXML(String pathToXML, String identifier){
        List<RateInvestment> investments = new ArrayList<>();

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

    /**
     * Query a RateInvestment object from an XML file using the.
     * 
     * @param pathToXML path to the XML file.
     * @param identifier
     * @param rateType
     * @return RateInvestment
     */
    public RateInvestment queryInvestment(String pathToXML, String identifier, RateInvestEnum rateType){
        List<RateInvestment> investments = readFromXML(pathToXML, identifier);
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
        List<RateInvestment> investments = reader.readFromXML("app/src/main/java/sigmabank/utils/xml_test/rateInvestment.xml");
        System.out.println(investments.size());
        for (RateInvestment investment : investments) {
            System.out.println(investment);
        }
    }
    */
    
}
