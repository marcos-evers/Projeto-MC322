package sigmabank.database;

import java.io.File;
import java.io.FileWriter;
import java.io.StringWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Predicate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

import sigmabank.model.investment.Investment;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Client;
import sigmabank.utils.writters.WritterFactory;
import sigmabank.utils.writters.WritterXML;

public class Database {
    private static Database instance;
    private static final int MAX_UNSAVED_OPERATIONS = 10;
    private static final String DB_PATH = "src/main/resources/database";

    private Map<String, List<Object>> tables = new HashMap<>();
    private Map<String, Class<?>> tableClasses = new HashMap<>();

    private int unsavedOperations;

    private Database() {
    }

    public static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    public Database addTable(String label, Class<?> tableClass) {
        tables.put(label, new ArrayList<>());
        tableClasses.put(label, tableClass);
        return this;
    }

    /**
     * Add a entry to the table with label
     *
     * @param label the label of the table
     * @param entry the entry to be added
     */
    public void addEntry(String label, Object entry) {
        tables.get(label).add(entry);

        this.unsavedOperations++;
        if (this.unsavedOperations > MAX_UNSAVED_OPERATIONS) {
            this.saveToXML(DB_PATH);
            this.unsavedOperations = 0;
        }
    }

    public List<Object> deleteEntries(String label, Predicate<Object> func) {
        List<Object> remain = new ArrayList<>();
        List<Object> deleted = new ArrayList<>();
        for (Object obj: tables.get(label)) {
            if (func.test(obj)) {
                deleted.add(obj);
            } else { 
                remain.add(obj);
            }
        }
        this.tables.put(label, remain);

        this.unsavedOperations += deleted.size();
        if (this.unsavedOperations > MAX_UNSAVED_OPERATIONS) {
            this.saveToXML(DB_PATH);
            this.unsavedOperations = 0;
        }

        return deleted;
    }

    /**
     * Query for all entries in given table such that func evaluates to true
     *
     * @param label the label of the table
     * @param func the predicate to filter entries
     */
    public List<Object> query(String label, Predicate<Object> func) {
        List<Object> result = new ArrayList<>();
        for (Object obj: tables.get(label)) {
            if (func.test(obj)) {
                result.add(obj);
            }
        }
        return result;
    }

    public void saveToXML(String path) {
        try {

            for (String label: tables.keySet()) {
                List<Object> table = tables.get(label);
                Class<?> tableClass = tableClasses.get(label);
                WritterXML<Client> wx = WritterFactory.createWritter(tableClass);
                String filepath = path + "/" + label + ".xml";

                wx.writeToXML(label, table, filepath);
            }
        } catch (Exception e) {
            e.printStackTrace();   
        }
    }

    public void saveToXML() {
        saveToXML(DB_PATH);
    }

    public void loadFromXML(String path) {
        try {
            JAXBContext jaxbctx = JAXBContext.newInstance(Client.class);
            Unmarshaller unmarshaller = jaxbctx.createUnmarshaller();

            for (String key: tables.keySet()) {
                List<Object> table = tables.get(key);
                String className = tableClasses.get(key).getName().toLowerCase();
                DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
                DocumentBuilder db = dbf.newDocumentBuilder();
                Document doc = db.parse(new File(path + "/" + key + ".xml"));
                doc.getDocumentElement().normalize();

                NodeList nodeList = doc.getElementsByTagName(className);
                for (int i = 0; i < nodeList.getLength(); i++) {
                    Object obj = unmarshaller.unmarshal(nodeList.item(i));

                    table.add(obj);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();   
        }
    }

    public void loadFromXML() {
        loadFromXML(DB_PATH);
    }

    @Override
    public String toString() {
        return tables.toString();
    }
}
