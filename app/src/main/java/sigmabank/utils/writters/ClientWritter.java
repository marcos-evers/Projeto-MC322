package sigmabank.utils.writters;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sigmabank.model.register.Client;


public class ClientWritter implements WritterXML<Client>{
    private Element createElementFromClient(Document doc, Client client) {
        // Client element
        Element clientElement = doc.createElement("Client");

        // ID element
        Element uuid = doc.createElement("uuid");
        uuid.appendChild(doc.createTextNode(client.getUUID().toString()));
        clientElement.appendChild(uuid);

        // Name element
        Element name = doc.createElement("name");
        name.appendChild(doc.createTextNode(client.getName()));
        clientElement.appendChild(name);

        // CPF element
        Element cpf = doc.createElement("cpf");
        cpf.appendChild(doc.createTextNode(client.getCpf())); 
        clientElement.appendChild(cpf);

        // DateOfBirth element
        Element dateOfBirth = doc.createElement("dateOfBirth");
        dateOfBirth.appendChild(doc.createTextNode(client.getDateOfBirth().toString())); 
        clientElement.appendChild(dateOfBirth);

        Element registerPasswordHash = doc.createElement("passwordHash");
        registerPasswordHash.appendChild(doc.createTextNode(client.getPasswordHash()));
        clientElement.appendChild(registerPasswordHash);

        // Address element  
        Element address = doc.createElement("address");
        address.appendChild(doc.createTextNode(client.getAddress()));
        clientElement.appendChild(address);

        // Email element
        Element email = doc.createElement("email");
        email.appendChild(doc.createTextNode(client.getEmail())); 
        clientElement.appendChild(email);

        // PhoneNumber element
        Element phoneNumber = doc.createElement("phoneNumber");
        phoneNumber.appendChild(doc.createTextNode(client.getPhoneNumber())); 
        clientElement.appendChild(phoneNumber);

        return clientElement;
    }

    @Override
    public void writeToXML(String label, List<Object> clients, String pathToXML) throws IOException {
        try {       
            File xmlFile = new File(pathToXML); // Using the pathToXML variable correctly
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            doc = dBuilder.newDocument();

            Element root = doc.createElement(label);
            doc.appendChild(root);

            for (Object client: clients)
                root.appendChild(createElementFromClient(doc, (Client) client));

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
}
