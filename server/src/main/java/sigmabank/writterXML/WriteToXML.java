package sigmabank.writterXML;

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import sigmabank.model.register.ClientPersonal;

public class WriteToXML {
    public static void writeToXML(ClientPersonal client, String pathToXML) {
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
            cpf.appendChild(doc.createTextNode(client.getCpf())); // Assuming getCpf() method exists
            clientElement.appendChild(cpf);

            // DateOfBirth element
            Element dateOfBirth = doc.createElement("dateOfBirth");
            dateOfBirth.appendChild(doc.createTextNode(client.getDateOfBirth().toString())); // Assuming getDateOfBirth() returns a LocalDate
            clientElement.appendChild(dateOfBirth);

            // Email element
            Element email = doc.createElement("email");
            email.appendChild(doc.createTextNode(client.getEmail())); // Assuming getEmail() method exists
            clientElement.appendChild(email);

            // PhoneNumber element
            Element phoneNumber = doc.createElement("phoneNumber");
            phoneNumber.appendChild(doc.createTextNode(client.getPhoneNumber())); // Assuming getPhoneNumber() method exists
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
