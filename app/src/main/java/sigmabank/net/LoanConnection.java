package sigmabank.net;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import sigmabank.model.loan.Loan;
import sigmabank.utils.readers.ReaderFactory;

public class LoanConnection extends Connection<Loan> {
    public LoanConnection(String uri) {
        super(uri);
    }

    /**
     * Do a GET request to the server for search for the loans of a client.
     *
     * @param params A map with one key:
     *      - clientUUID.
     * @return a list containing the loans of the client.
     * @throws IOException if a error occur connecting to the server.
     */
    public List<Loan> fetch(Map<String, Object> params) throws IOException {
        URI requestURI = URI.create(buildFetchURI(params));
        URL requestURL = requestURI.toURL();
        HttpURLConnection connection = (HttpURLConnection) requestURL.openConnection(); 

        connection.setRequestMethod("GET");
        
        String responseBody = "";
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            responseBody = response.toString();
            System.out.println("[MSG] Fetch client: " + responseBody);
        }
        connection.disconnect();
        
        List<Object> loans = ReaderFactory.createReader(Loan.class).readStringXML(responseBody);
        List<Loan> loansLoan = new ArrayList<>();
        for (Object loan : loans) {
            loansLoan.add((Loan) loan);
        }

        return loansLoan;
    }

    /**
     * Do a POST request to register a client that will pending approval
     *
     * @param params A map with the attributes:
     *      - value
     *      - uuid (Client UUID)
     *      - startday
     * @throws IOException if a error occur connecting to the server
     */
    public void send(Map<String, Object> params) throws IOException {
        super.send(params);
    }

    private String buildFetchURI(Map<String, Object> params) {
        return getURI() + "?"
            + "uuid=" + params.get("clientUUID").toString();
    }
}
