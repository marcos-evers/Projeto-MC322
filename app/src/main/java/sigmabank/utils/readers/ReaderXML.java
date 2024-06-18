package sigmabank.utils.readers;

import java.util.List;

public interface ReaderXML<T> {
    /**
     * Read an object from an XML file at the specified path using the given identifier.
     * 
     * @param indentifier the identifier of the object to be read from XML.
     * @param pathToXML the path to the XML file.
     * @return the object read from XML.
     * @throws IOException if an I/O error occurs.
     */
    public List<T> readFromXML(String pathToXML);

    /**
     * Read an object from an XML file at the specified path using the given identifier.
     * 
     * @param indentifier the identifier of the object to be read from XML.
     * @param pathToXML the path to the XML file.
     * @return the object read from XML.
     * @throws IOException if an I/O error occurs.
     */
    public List<T> readFromXML(String pathToXML, String identifier);
}
