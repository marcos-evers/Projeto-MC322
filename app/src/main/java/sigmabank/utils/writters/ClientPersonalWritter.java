package sigmabank.utils.writters;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sigmabank.model.register.ClientPersonal;

public class ClientPersonalWritter implements WritterXML<ClientPersonal>{
    @Override
    public void writeToXML(ClientPersonal client, String pathToXML) throws IOException {
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
            Element clientElement = doc.createElement("ClientPersonal");
            root.appendChild(clientElement);

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
}