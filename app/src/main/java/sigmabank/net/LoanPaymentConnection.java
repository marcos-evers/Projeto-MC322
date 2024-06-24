package sigmabank.net;

import java.io.IOException;

import java.util.Map;
import java.util.List;

import sigmabank.model.loan.Loan;

public class LoanPaymentConnection extends Connection<Loan> {
    public LoanPaymentConnection(String uri) {
        super(uri);
    }

    public List<Loan> fetch(Map<String, Object> params) throws IOException {
        return null;
    }

    /**
     * Do a POST request to register a client that will pending approval
     *
     * @param params A map with the attributes:
     *      - clientuuid
     *      - loanuuid
     *      - value
     * @throws IOException if a error occur connecting to the server
     */
    public void send(Map<String, Object> params) throws IOException {
        System.out.println(params);
        super.send(params);
    }
}
