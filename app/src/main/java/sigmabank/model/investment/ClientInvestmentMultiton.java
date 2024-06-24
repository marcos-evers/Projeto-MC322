package sigmabank.model.investment;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import sigmabank.model.investment.InfoInvestments.InfoInvest;
import sigmabank.model.investment.InfoInvestments.ReaderRateInfo;
import sigmabank.model.investment.InfoInvestments.ReaderAssetInfo;
import sigmabank.utils.readers.ReaderFactory;
import sigmabank.utils.writters.WritterFactory;
import sigmabank.utils.readers.ReaderXML;
import sigmabank.utils.writters.WritterXML;

/**
 * ClientInvestmentMultiton manages client investments in rate-based and asset-based instruments.
 * It ensures each client has only one investment of each type, utilizing a multiton pattern.
 */
public class ClientInvestmentMultiton {
    private static ClientInvestmentMultiton instance;
    private Map<UUID, Map<RateInvestEnum, RateInvestment>> rateInvestments;
    private Map<UUID, Map<AssetInvestEnum, AssetInvestment>> assetInvestments;

    /**
     * Private constructor to prevent instantiation from outside.
     * Initializes the maps for rate and asset investments.
     */
    private ClientInvestmentMultiton() {
        this.rateInvestments = new HashMap<>();
        this.assetInvestments = new HashMap<>();
    }

    /**
     * Returns the singleton instance of ClientInvestmentMultiton.
     * Synchronized to handle concurrent access during instance creation.
     * @return Singleton instance of ClientInvestmentMultiton
     */
    public static synchronized ClientInvestmentMultiton getInstance() {
        if (instance == null) {
            instance = new ClientInvestmentMultiton();
        }
        return instance;
    }

    /**
     * Creates a rate-based investment for the client identified by clientUUID.
     * If the investment type does not exist for the client, a new investment is created.
     * @param clientUUID Unique identifier for the client
     * @param rateType Type of rate-based investment
     * @param investedValue Initial amount invested
     * @param startDate Start date of the investment
     * @return RateInvestment object representing the investment
     */
    public RateInvestment getRateInvestment(UUID clientUUID, RateInvestEnum rateType, BigDecimal investedValue, LocalDate startDate) {
        rateInvestments.putIfAbsent(clientUUID, new HashMap<>());
        Map<RateInvestEnum, RateInvestment> clientRateInvestments = rateInvestments.get(clientUUID);
        InfoInvest info = ReaderRateInfo.readRateInvestment("src/main/resources/RateInvestments.xml", rateType.toString());

        if (!clientRateInvestments.containsKey(rateType)) {
            RateInvestment newInvestment = new RateInvestment(info.getName(), investedValue, clientUUID, startDate, info.getRate(), info.getFrequencyType(), rateType);
            clientRateInvestments.put(rateType, newInvestment);
        }

        return clientRateInvestments.get(rateType);
    }

    /**
     * Loads rate-based investments from an XML file.
     * @param pathToXML Path to the XML file containing rate-based investments
     */
    private void loadRateInvestments(String pathToXML) {
        ReaderXML<RateInvestment> reader = ReaderFactory.createReader(RateInvestment.class);
        List<Object> rateInvestmentsArray = reader.readFromXML(pathToXML);

        for (Object rateInvestmentObj : rateInvestmentsArray) {
            RateInvestment rateInvestment = (RateInvestment) rateInvestmentObj;

            rateInvestments.putIfAbsent(rateInvestment.getClientUUID(), new HashMap<>());
            Map<RateInvestEnum, RateInvestment> clientRateInvestments = rateInvestments.get(rateInvestment.getClientUUID());
        
            if (!clientRateInvestments.containsKey(rateInvestment.getRateType())) {
                clientRateInvestments.put(rateInvestment.getRateType(), rateInvestment);
            }
        }
    }

    /**
     * Save rate-based investments to an XML file.
     * @param pathToXML Path to the XML file containing rate-based investments
     */
    private void saveRateInvestments(String pathToXML) throws IOException {
        WritterXML<RateInvestment> writter = WritterFactory.createWritter(RateInvestment.class);

        List<Object> rateInvestmentsList = new ArrayList<>();
        for (UUID uuid: assetInvestments.keySet()) {
            for (RateInvestment investment: rateInvestments.get(uuid).values()) {
                rateInvestmentsList.add((Object) investment);
            }
        }
        writter.writeToXML("RateInvestments", rateInvestmentsList, pathToXML);
    }



    /**
     * Retrieves or creates an asset-based investment for the client identified by clientUUID.
     * If the investment type does not exist for the client, a new investment is created.
     * @param clientUUID Unique identifier for the client
     * @param assetType Type of asset-based investment
     * @param investedValue Initial amount invested
     * @param startDate Start date of the investment
     * @return AssetInvestment object representing the investment
     */
    public AssetInvestment getAssetInvestment(UUID clientUUID, AssetInvestEnum assetType, BigDecimal investedValue, LocalDate startDate) {
        assetInvestments.putIfAbsent(clientUUID, new HashMap<>());
        Map<AssetInvestEnum, AssetInvestment> clientAssetInvestments = assetInvestments.get(clientUUID);
        InfoInvest info = ReaderAssetInfo.readAssetInvestment("src/main/resources/AssetInvestments.xml", assetType.toString());
        
        if (!clientAssetInvestments.containsKey(assetType)) {
            AssetInvestment newInvestment = new AssetInvestment(info.getName(), investedValue, clientUUID, startDate, info.getAssetValue(), assetType);
            clientAssetInvestments.put(assetType, newInvestment);
        }

        return clientAssetInvestments.get(assetType);
    }

    /**
     * Loads asset-based investments from an XML file.
     * @param pathToXML Path to the XML file containing asset-based investments
     */
    private void loadAssetInvestments(String pathToXML) {
        ReaderXML<AssetInvestment> reader = ReaderFactory.createReader(AssetInvestment.class);
        List<Object> assetInvestmentsArray = reader.readFromXML(pathToXML);

        for (Object object : assetInvestmentsArray) {
            AssetInvestment assetInvestment = (AssetInvestment) object;
            assetInvestments.putIfAbsent(assetInvestment.getClientUUID(), new HashMap<>());
            Map<AssetInvestEnum, AssetInvestment> clientAssetInvestments = assetInvestments.get(assetInvestment.getClientUUID());
        
            if (!clientAssetInvestments.containsKey(assetInvestment.getAssetType())) {
                clientAssetInvestments.put(assetInvestment.getAssetType(), assetInvestment);
            }
        }
    }

    /**
     * Save asset-based investments to an XML file.
     * @param pathToXML Path to the XML file containing asset-based investments
     */
    private void saveAssetInvestments(String pathToXML) throws IOException {
        WritterXML<AssetInvestment> writter = WritterFactory.createWritter(AssetInvestment.class);

        List<Object> assetInvestmentsList = new ArrayList<>();
        for (UUID uuid: assetInvestments.keySet()) {
            for (AssetInvestment investment: assetInvestments.get(uuid).values()) {
                assetInvestmentsList.add((Object) investment);
            }
        }
        writter.writeToXML("AssetInvestments", assetInvestmentsList, pathToXML);
    }

    /**
     * Loads rate-based and asset-based investments from XML files.
     * @param pathToRateInvestments Path to the XML file containing rate-based investments
     * @param pathToAssetInvestments Path to the XML file containing asset-based investments
     */
    public void loadInvestments(String pathToRateInvestments, String pathToAssetInvestments) {
        loadRateInvestments(pathToRateInvestments);
        loadAssetInvestments(pathToAssetInvestments);
    }

    /**
     * Save rate-based and asset-based investments from XML files.
     * @param pathToRateInvestments Path to the XML file containing rate-based investments
     * @param pathToAssetInvestments Path to the XML file containing asset-based investments
     */
    public void saveInvestments(String pathToRateInvestments, String pathToAssetInvestments) throws IOException {
        saveRateInvestments(pathToRateInvestments);
        saveAssetInvestments(pathToAssetInvestments);
    }

    /**
     * Retrieves the rate-based investments for a client identified by clientUUID.
     * @param clientUUID Unique identifier for the client
     * @return Map of rate-based investments for the client
     */
    public Map<RateInvestEnum, RateInvestment> getRateInvestments(UUID clientUUID) {
        rateInvestments.putIfAbsent(clientUUID, new HashMap<>());
        return rateInvestments.get(clientUUID);
    }

    /**
     * Retrieves the asset-based investments for a client identified by clientUUID.
     * @param clientUUID Unique identifier for the client
     * @return Map of asset-based investments for the client
     */
    public Map<AssetInvestEnum, AssetInvestment> getAssetInvestments(UUID clientUUID) {
        assetInvestments.putIfAbsent(clientUUID, new HashMap<>());
        return assetInvestments.get(clientUUID);
    }

    /**
     * Retrives the InfoInvest objects that the client does not have for rate - based investments
     * 
     * @param clientUUID
     * @return ArrayList<InfoInvest> 
     */ 
    public ArrayList<InfoInvest> getNotExistingRateInvestments(UUID clientUUID) {
        ArrayList<InfoInvest> notExistingRateInvestments = new ArrayList<>();
        InfoInvest info;
        rateInvestments.putIfAbsent(clientUUID, new HashMap<>());
        for (RateInvestEnum rateInvestEnum : RateInvestEnum.values()) {
            if (!rateInvestments.get(clientUUID).containsKey(rateInvestEnum)) {
                info = ReaderRateInfo.readRateInvestment("src/main/resources/RateInvestments.xml", rateInvestEnum.toString());
                notExistingRateInvestments.add(info);
            }
        }
        System.out.println(notExistingRateInvestments);
        return notExistingRateInvestments;
    }

    /**
     * Retrives the InfoInvest objects that the client does not have for asset - based investments
     * 
     * @param clientUUID
     * @return ArrayList<InfoInvest> 
     */
    public ArrayList<InfoInvest> getNotExistingAssetInvestments(UUID clientUUID) {
        ArrayList<InfoInvest> notExistingAssetInvestments = new ArrayList<>();
        InfoInvest info;
        assetInvestments.putIfAbsent(clientUUID, new HashMap<>());
        for (AssetInvestEnum assetInvestEnum : AssetInvestEnum.values()) {
            if (!assetInvestments.get(clientUUID).containsKey(assetInvestEnum)) {
                info = ReaderAssetInfo.readAssetInvestment("src/main/resources/AssetInvestments.xml", assetInvestEnum.toString());
                notExistingAssetInvestments.add(info);
            }
        }
        return notExistingAssetInvestments;
    }
}
