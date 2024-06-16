package sigmabank.utils.readers;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import sigmabank.model.register.Client;

public class ClientReader implements ReaderXML<Client> {
    @Override
    public ArrayList<Client> readFromXML(String path) {
        ArrayList<Client> clients = new ArrayList<>();

        try {
            File file = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("ClientPersonal");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element clientElement = (Element) nodeList.item(i);

                String name = clientElement.getElementsByTagName("name").item(0).getTextContent();
                String dateOfBirth = clientElement.getElementsByTagName("dateOfBirth").item(0).getTextContent();
                String cpf = clientElement.getElementsByTagName("cpf").item(0).getTextContent();
                String registerPasswordHash = clientElement.getElementsByTagName("registerPasswordHash").item(0).getTextContent();
                String address = clientElement.getElementsByTagName("address").item(0).getTextContent();
                String email = clientElement.getElementsByTagName("email").item(0).getTextContent();
                String phoneNumber = clientElement.getElementsByTagName("phoneNumber").item(0).getTextContent();

                Client client = new Client(name, LocalDate.parse(dateOfBirth), cpf);
                client.setRegisterPasswordHash(registerPasswordHash);
                client.setEmail(email);
                client.setPhoneNumber(phoneNumber);
                client.setAddress(address);

                clients.add(client);
            }

        } catch (Exception e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public ArrayList<Client> readFromXML(String pathToXML, String identifier) {
        // TODO implementation
        return null;
    }

    /* 
    public static void main(String[] args) {
        ClientReader reader = new ClientReader();
        ArrayList<Client> clients = reader.readFromXML("app/src/main/java/sigmabank/utils/xml_test/clientPersonal.xml");
        System.out.println(clients.size());
        for (Client client : clients) {
            System.out.println(client);
        }
    }
    */
}
