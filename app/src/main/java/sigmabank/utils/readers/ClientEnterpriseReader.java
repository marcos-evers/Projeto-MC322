package sigmabank.utils.readers;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import sigmabank.model.register.ClientEnterprise;

public class ClientEnterpriseReader implements ReaderXML<ClientEnterprise>{
    @Override
    public ArrayList<ClientEnterprise> readFromXML(String path) {
        ArrayList<ClientEnterprise> clients = new ArrayList<>();

        try {
            File file = new File(path);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();

            NodeList nodeList = doc.getElementsByTagName("ClientEnterprise");

            for (int i = 0; i < nodeList.getLength(); i++) {
                Element clientElement = (Element) nodeList.item(i);

                String name = clientElement.getElementsByTagName("name").item(0).getTextContent();

                String dateOfBirth = clientElement.getElementsByTagName("dateOfBirth").item(0).getTextContent();
                
                String cpf = clientElement.getElementsByTagName("cnpj").item(0).getTextContent();

                ClientEnterprise client = new ClientEnterprise(name, LocalDate.parse(dateOfBirth), cpf);
                clients.add(client);
            }

        } catch (Exception e) {
            System.err.println("Error reading the file: " + e.getMessage());
            e.printStackTrace();
        }

        return clients;
    }

    @Override
    public ClientEnterprise readFromXML(String pathToXML, String identifier){
        // TODO implementation
        return null;
    }

    /* 
    public static void main(String[] args) {
        ClientEnterpriseReader reader = new ClientEnterpriseReader();
        ArrayList<ClientEnterprise> clients = reader.readFromXML("app/src/main/java/sigmabank/utils/xml_test/clientEnterprise.xml");
        System.out.println(clients.size());
        for (ClientEnterprise client : clients) {
            System.out.println(client);
        }
    }
    */
     
}
