package sigmabank.utils.writters;

import java.io.IOException;
import java.util.List;

public interface WritterXML<T> {
    /**
     * Write the given object to an XML file at the specified path.
     * 
     * @param objects the list of object to be written to XML.
     * @param pathToXML the path to the XML file.
     * @throws IOException if an I/O error occurs.
     */
    void writeToXML(String label, List<Object> objects, String pathToXML) throws IOException;
}
