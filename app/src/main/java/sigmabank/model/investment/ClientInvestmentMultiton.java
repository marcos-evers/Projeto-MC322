package sigmabank.model.investment;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import sigmabank.model.investment.InfoInvestments.InfoInvest;
import sigmabank.model.investment.InfoInvestments.readerRateInfo;
import sigmabank.model.investment.InfoInvestments.readerAssetInfo;


public class ClientInvestmentMultiton {
    private static ClientInvestmentMultiton instance;
    private Map<UUID, Map<RateInvestEnum, RateInvestment>> rateInvestments;
    private Map<UUID, Map<AssetInvestEnum, AssetInvestment>> assetInvestments;

    private ClientInvestmentMultiton() {
        this.rateInvestments = new HashMap<>();
        this.assetInvestments = new HashMap<>();
    }

    public static synchronized ClientInvestmentMultiton getInstance() {
        if (instance == null) {
            instance = new ClientInvestmentMultiton();
        }
        return instance;
    }

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
