package sigmabank.database;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.function.Predicate;

import sigmabank.model.investment.AssetInvestEnum;
import sigmabank.model.investment.AssetInvestment;
import sigmabank.model.investment.ClientInvestmentMultiton;
import sigmabank.model.investment.RateInvestEnum;
import sigmabank.model.investment.RateInvestment;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Client;
import sigmabank.utils.readers.ReaderFactory;
import sigmabank.utils.readers.ReaderXML;
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

    /**
     * 
     * @return
     */
    public static Database getInstance() {
        if (instance == null)
            instance = new Database();
        return instance;
    }

    /**
     * 
     * @param label
     * @param tableClass
     * @return
     */
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

    /**
     * 
     * @param label
     * @param func
     * @return
     */
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

    public void attachInvestments() {
        for (Object clientobj: tables.get("Clients")) {
            Client client = (Client) clientobj;
            Map<RateInvestEnum, RateInvestment> rateInvestments = ClientInvestmentMultiton.getInstance()
                .getRateInvestments(client.getUUID());
            Map<AssetInvestEnum, AssetInvestment> assetInvestments = ClientInvestmentMultiton.getInstance()
                .getAssetInvestments(client.getUUID());

            for (RateInvestment investment: rateInvestments.values())
                client.addInvestment(investment);
            for (AssetInvestment investment: assetInvestments.values())
                client.addInvestment(investment);
        }
    }

    /**
     * 
     * @param client
     * @param loans
     */
    private void attachLoansToClient(Client client, List<Object> loans) {
        for (Object loanobj: loans) {
            Loan loan = (Loan) loanobj;
            client.addLoan(loan);
        }
    }

    public void attachLoans() {
        for (Object clientobj: tables.get("Clients")) {
            Client client = (Client) clientobj;
            List<Object> loans = query("Loans",
                (Object obj) -> {
                    return ((Loan) obj).getClientUUID().equals(client.getUUID());
                });
            attachLoansToClient(client, loans);
        }
    }

    public void initClients() {
        for (Object clientobj: tables.get("Clients")) {
            ((Client) clientobj).setBalance(BigDecimal.ZERO);
        }

        attachLoans();
        attachInvestments();
    }

    /**
     * 
     * @param path
     */
    public void saveToXML(String path) {
        try {
            for (String label: tables.keySet()) {
                List<Object> table = tables.get(label);
                Class<?> tableClass = tableClasses.get(label);
                WritterXML<?> wx = WritterFactory.createWritter(tableClass);
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

    /**
     * 
     * @param path
     */
    public void loadFromXML(String path) {
        try {
            for (String key: tables.keySet()) {
                Class<?> tableClass = tableClasses.get(key);
                ReaderXML<?> reader = ReaderFactory.createReader(tableClass);
                String filepath = path + "/" + key + ".xml"; 
                List<Object> objects = reader.readFromXML(filepath);

                tables.put(key, objects);
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
