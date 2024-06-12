package sigmabank.writterXML;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import sigmabank.model.register.ClientPersonal;

public class WriteToXML {
    public static void writeToXML(ClientPersonal client, String pathToXML) {
        try {       
            File xmlFile = new File(pathToXML);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc;

            if (xmlFile.exists() && xmlFile.length() != 0) {
                doc = dBuilder.parse(xmlFile);
                doc.getDocumentElement().normalize();
            } else {
                doc = dBuilder.newDocument();
                doc.appendChild(doc.createElement("Clients"));
            }

            Element root = doc.getDocumentElement();
            Element clientElement = doc.createElement("ClientPersonal");
            Element name = doc.createElement("name");
            Element cpf = doc.createElement("cpf");
            Element dateOfBirth = doc.createElement("dateOfBirth");
            Element email = doc.createElement("email");
            Element phoneNumber = doc.createElement("phoneNumber");

            root.appendChild(clientElement);
            clientElement.appendChild(name)
                         .appendChild(doc.createTextNode(client.getName()));
            clientElement.appendChild(cpf)
                         .appendChild(doc.createTextNode(client.getCpf()));
            clientElement.appendChild(dateOfBirth)
                         .appendChild(doc.createTextNode(client.getDateOfBirth().toString()));
            clientElement.appendChild(email)
                         .appendChild(doc.createTextNode(client.getEmail()));
            clientElement.appendChild(phoneNumber)
                         .appendChild(doc.createTextNode(client.getPhoneNumber()));

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
