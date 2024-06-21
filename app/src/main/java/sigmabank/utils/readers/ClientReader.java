package sigmabank.utils.readers;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import sigmabank.model.register.Client;

public class ClientReader implements ReaderXML<Client> {

    /**
     * Read Client objects from an XML file.
     * 
     * @param pathToXML path to the XML file.
     * @return List<Client>
     */
    @Override
    public List<Object> readFromXML(String pathToXML) {
        List<Object> clients = new ArrayList<>();

        try {
            File file = new File(pathToXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Client");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element clientElement = (Element) nodeList.item(i);

                // TODO add uuid
                String name = clientElement.getElementsByTagName("name").item(0).getTextContent();
                String dateOfBirth = clientElement.getElementsByTagName("dateOfBirth").item(0).getTextContent();
                String cpf = clientElement.getElementsByTagName("cpf").item(0).getTextContent();
                String address = clientElement.getElementsByTagName("address").item(0).getTextContent();
                String email = clientElement.getElementsByTagName("email").item(0).getTextContent();
                String phoneNumber = clientElement.getElementsByTagName("phoneNumber").item(0).getTextContent();
                String passwordHash = clientElement.getElementsByTagName("passwordHash").item(0).getTextContent();

                Client client = new Client(name, LocalDate.parse(dateOfBirth), cpf);
                client.setPasswordHash(passwordHash);
                client.setEmail(email);
                client.setPhoneNumber(phoneNumber);
                client.setAddress(address);

                clients.add((Object) client);
            }
        } catch (Exception e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return clients;
    }

    /**
     * Read Client objects from an XML file.
     * 
     * @param pathToXML path to the XML file.
     * @param identifier identifier to use for filtering.
     * @return List<Client>
     */
    @Override
    public List<Client> readFromXML(String pathToXML, String identifier) {
        
        return null;
    }
}
