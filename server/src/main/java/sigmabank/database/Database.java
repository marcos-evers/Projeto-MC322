package sigmabank.database;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Predicate;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import sigmabank.model.investment.Investment;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.ClientEnterprise;
import sigmabank.model.register.ClientPersonal;

public class Database {
    private static Database instance;
    private Map<String, List<Object>> tables = new HashMap<>();

    private Database() {}

    public static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    public Database addTable(String label) {
        tables.put(label, new ArrayList<>());
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
            JAXBContext jaxbctx = JAXBContext.newInstance(ClientPersonal.class);
            Marshaller marshaller = jaxbctx.createMarshaller();

            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

            for (String key: tables.keySet()) {
                List<Object> table = tables.get(key);
                StringWriter result = new StringWriter();

                result.write("<" + key + ">");
                for (Object obj: table) {
                    marshaller.marshal(obj, result);
                }
                result.write("</" + key + ">");

                // TODO proper save to file
                System.out.println(result.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();   
        }
    }

    public void loadFromXML() {
        // TODO implement
    }

    @Override
    public String toString() {
        return tables.toString();
    }
}
