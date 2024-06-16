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

public class ClientReader implements ReaderXML<Client>{
    @Override
    public ArrayList<Client> readFromXML(String path) {
        ArrayList<Client> clients = new ArrayList<>();

        try {
            File file = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("Client");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element clientElement = (Element) nodeList.item(i);

                String name = clientElement.getElementsByTagName("name").item(0).getTextContent();

                String dateOfBirth = clientElement.getElementsByTagName("dateOfBirth").item(0).getTextContent();
                
                String cpf = clientElement.getElementsByTagName("cpf").item(0).getTextContent();

                String passwordHash = clientElement.getElementsByTagName("passwordHash").item(0).getTextContent();

                Client client = new Client(name, LocalDate.parse(dateOfBirth), cpf, passwordHash);
                clients.add(client);
            }

        } catch (Exception e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public Client readFromXML(String pathToXML, String identifier){
        // TODO implementation
        return null;
    }

    
}
