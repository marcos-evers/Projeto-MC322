package sigmabank.model.investment.InfoInvestments;

import java.io.File;
import java.math.BigDecimal;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import sigmabank.model.investment.ROIFrequencyType;

public class readerRateInfo {
    /**
     * Read Infoinvest - RateInvestment objects from an XML source file.
     * 
     * @param pathToXML path to the XML file.
     * @param identifier the identifier of the rate investment to read.
     * @return InfoInvest
     */
    public static InfoInvest readRateInvestments(String pathToXML, String identifier) {
        InfoInvest investment = null;
        try {
            File file = new File(pathToXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("RateInvestment");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element element = (Element) nodeList.item(i);

                String type = element.getElementsByTagName("type").item(0).getTextContent();
                String name = element.getElementsByTagName("name").item(0).getTextContent();
                BigDecimal rate = new BigDecimal(element.getElementsByTagName("rate").item(0).getTextContent());
                ROIFrequencyType frequencyType = ROIFrequencyType.valueOf(element.getElementsByTagName("frequencyType").item(0).getTextContent().toUpperCase());

                if (type.equals(identifier)) {
                    investment = new InfoInvest(name, rate, frequencyType);
                }
            }

        } catch (Exception e) {
            System.err.println("Error reading the XML file: " + e.getMessage());
            e.printStackTrace();
        }
        return investment;
    }
}
