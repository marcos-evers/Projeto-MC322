package sigmabank.model.investment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import sigmabank.model.investment.InfoInvestments.InfoInvest;
import sigmabank.model.investment.InfoInvestments.readerRateInfo;
import sigmabank.model.investment.InfoInvestments.readerAssetInfo;
import sigmabank.utils.readers.ReaderFactory;
import sigmabank.utils.readers.ReaderXML;

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
        InfoInvest info = readerRateInfo.readRateInvestments("app/src/main/resources/RateInvestments.xml", rateType.toString());

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
    private void LoadRateInvestments(String pathToXML) {
        ReaderXML<RateInvestment> reader = ReaderFactory.createReader(RateInvestment.class);
        ArrayList<RateInvestment> rateInvestmentsArray = reader.readFromXML(pathToXML);

        for (RateInvestment rateInvestment : rateInvestmentsArray) {
            rateInvestments.putIfAbsent(rateInvestment.getClientUUID(), new HashMap<>());
            Map<RateInvestEnum, RateInvestment> clientRateInvestments = rateInvestments.get(rateInvestment.getClientUUID());
        
            if (!clientRateInvestments.containsKey(rateInvestment.getRateType())) {
                clientRateInvestments.put(rateInvestment.getRateType(), rateInvestment);
            }
        }

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
        InfoInvest info = readerAssetInfo.readAssetInvestments("app/src/main/resources/AssetInvestments.xml", assetType.toString());

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
    private void LoadAssetInvestments(String pathToXML) {
        ReaderXML<AssetInvestment> reader = ReaderFactory.createReader(AssetInvestment.class);
        ArrayList<AssetInvestment> assetInvestmentsArray = reader.readFromXML(pathToXML);

        for (AssetInvestment assetInvestment : assetInvestmentsArray) {
            assetInvestments.putIfAbsent(assetInvestment.getClientUUID(), new HashMap<>());
            Map<AssetInvestEnum, AssetInvestment> clientAssetInvestments = assetInvestments.get(assetInvestment.getClientUUID());
        
            if (!clientAssetInvestments.containsKey(assetInvestment.getAssetType())) {
                clientAssetInvestments.put(assetInvestment.getAssetType(), assetInvestment);
            }
        }

        
    }

    /**
     * Loads rate-based and asset-based investments from XML files.
     * @param pathToRateInvestments Path to the XML file containing rate-based investments
     * @param pathToAssetInvestments Path to the XML file containing asset-based investments
     */
    public void loadInvestments(String pathToRateInvestments, String pathToAssetInvestments) {
        LoadRateInvestments(pathToRateInvestments);
        LoadAssetInvestments(pathToAssetInvestments);
    }

    /*  Testing 
    public static void main(String args[]) {
        ClientInvestmentMultiton clientInvestmentMultiton = ClientInvestmentMultiton.getInstance();
        UUID clientUUID = UUID.randomUUID();
        RateInvestment rateInvestment = clientInvestmentMultiton.getRateInvestment(clientUUID, RateInvestEnum.POUPANCA, BigDecimal.valueOf(1000), LocalDate.now());
        AssetInvestment assetInvestment = clientInvestmentMultiton.getAssetInvestment(clientUUID, AssetInvestEnum.BITCOIN, BigDecimal.valueOf(1000), LocalDate.now());
        AssetInvestment assetInvestment1 = clientInvestmentMultiton.getAssetInvestment(clientUUID, AssetInvestEnum.BITCOIN, BigDecimal.valueOf(10), LocalDate.now());
        System.out.println(rateInvestment.getName());
        System.out.println(assetInvestment.getName());
        System.out.println(assetInvestment.getValue());
        System.out.println(assetInvestment1.getValue());
    }
    */
}
