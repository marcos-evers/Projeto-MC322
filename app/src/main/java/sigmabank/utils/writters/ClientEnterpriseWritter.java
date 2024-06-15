package sigmabank.utils.writters;

import java.io.File;
import java.io.IOException;

// import java.time.LocalDate;
// import javax.management.InvalidAttributeValueException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sigmabank.model.register.ClientEnterprise;

public class ClientEnterpriseWritter implements WritterXML<ClientEnterprise> {
    @Override
    public void writeToXML(ClientEnterprise client, String pathToXML) throws IOException {
        try {       
            File xmlFile = new File(pathToXML); // Using the pathToXML variable correctly
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            // Check if file exists and has content
            if (xmlFile.exists() && xmlFile.length() != 0) {
                doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();
            } else {
                doc = dBuilder.newDocument();
                Element rootElement = doc.createElement("Clients");
                doc.appendChild(rootElement);
            }

            // Root element
            Element root = doc.getDocumentElement();

            // Client element
            Element clientElement = doc.createElement("ClientEnterprise");
            root.appendChild(clientElement);

            // Name element
            Element name = doc.createElement("name");
            name.appendChild(doc.createTextNode(client.getName()));
            clientElement.appendChild(name);

            // CNPJ element
            Element cnpj = doc.createElement("cnpj");
            cnpj.appendChild(doc.createTextNode(client.getCNPJ()));
            clientElement.appendChild(cnpj);

            // DateOfBirth element
            Element dateOfBirth = doc.createElement("dateOfBirth");
            dateOfBirth.appendChild(doc.createTextNode(client.getDateOfBirth().toString())); 
            clientElement.appendChild(dateOfBirth);

            // Email element
            Element email = doc.createElement("email");
            email.appendChild(doc.createTextNode(client.getEmail())); 
            clientElement.appendChild(email);

            // PhoneNumber element
            Element phoneNumber = doc.createElement("phoneNumber");
            phoneNumber.appendChild(doc.createTextNode(client.getPhoneNumber())); 
            clientElement.appendChild(phoneNumber);

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

    /* Testing the writter
    public static void main(String[] args) throws InvalidAttributeValueException {
        ClientEnterprise client = new ClientEnterprise("Sigma Bank", LocalDate.parse("2021-01-01") ,"11111111111111");
        client.setEmail( "idvbnnuiwqerbv@gmail.com");
        client.setPhoneNumber("123456789");

        ClientEnterpriseWritter writter = new ClientEnterpriseWritter();
        try {
            writter.writeToXML(client, "app/src/main/java/sigmabank/utils/xml_test/clientEnterprise.xml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
