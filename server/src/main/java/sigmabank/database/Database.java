package sigmabank.database;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Predicate;

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

    public void saveToXML() {
        // TODO implement
    }

    public void loadFromXML() {
        // TODO implement
    }

    @Override
    public String toString() {
        return tables.toString();
    }
}
