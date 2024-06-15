package sigmabank.utils.writters;

import java.io.IOException;

public interface WritterXML<T> {
    /**
     * Write the given object to an XML file at the specified path.
     * 
     * @param object the object to be written to XML.
     * @param pathToXML the path to the XML file.
     * @throws IOException if an I/O error occurs.
     */
    void writeToXML(T object, String pathToXML) throws IOException;
}