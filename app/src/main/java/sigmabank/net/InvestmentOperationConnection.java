package sigmabank.net;

import java.math.BigDecimal;
import java.io.IOException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import sigmabank.model.investment.AssetInvestment;
import sigmabank.model.investment.Investment;
import sigmabank.model.investment.RateInvestment;

public class InvestmentOperationConnection extends Connection<Investment> {

    public InvestmentOperationConnection(String uri) {
        super(uri);
    }

    /**
     * Send an investment operation to the server
     *
     * @param investment the investment of the operation
     * @param operation the operation name, 'retrieve' or 'investmore'
     * @param amount how much to retrieve or how much to invest more
     */
    public void sendOperation(Investment investment, String operation, BigDecimal amount) throws IOException {
        Map<String, Object> params = new HashMap<>();

        params.put("optype", operation);
        if (investment instanceof AssetInvestment) {
            params.put("invtype", "asset");
            params.put("invenum", ((AssetInvestment) investment).getAssetType());
        } else {
            params.put("invtype", "rate");
            params.put("invenum", ((RateInvestment) investment).getRateType());
        }
        params.put("uuid", investment.getClientUUID());
        params.put("amount", amount);

        send(params);
    }

    public List<Investment> fetch(Map<String, Object> params) throws IOException {
        return null;
    }
}
