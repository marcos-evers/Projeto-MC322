package sigmabank.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import java.util.Map;
import java.util.ArrayList;
import java.util.List;

import sigmabank.model.register.Client;
import sigmabank.model.loan.Loan;
import sigmabank.utils.readers.ReaderFactory;

public class ApprovalConnection implements IConnection<Object> {
    private final String uri;

    public ApprovalConnection(String uri) {
        this.uri = uri;
    }

    /**
     * Send if a Client or a Loan was approved:
     *      - type = loan or client
     *      - uuid = uuid if of the laon or the client
     *      - isapproved = true or false
     */
    public void send(Map<String, Object> params) throws IOException {
        URL url = URI.create(uri).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        try(DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
            os.writeBytes(buildPostData(params));
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            System.out.println("[MSG] Response: " + responseCode + "/" + response.toString());
        }
        connection.disconnect();
    }
    
    /**
     * Get Clients and Loans to approve
     * @param params a empty map
     * @return a list with two list where:
     *  0 - list with clients to approval
     *  1 - list with loans to approval
     */
    public List<Object> fetch(Map<String, Object> params) throws IOException {
        URL url = URI.create(uri).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 

        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        String responseBody = "";
        try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            StringBuilder sb = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                sb.append(inputLine);
            }
            responseBody = sb.toString();
            System.out.println("[MSG] Response: " + responseCode + "/" + responseBody);
        }
        connection.disconnect();

        List<Object> clients = ReaderFactory.createReader(Client.class).readStringXML(responseBody);
        List<Object> loans = ReaderFactory.createReader(Loan.class).readStringXML(responseBody);

        List<Object> lists = new ArrayList<>();
        lists.add(clients);
        lists.add(loans);

        return lists;
    }

    private String buildPostData(Map<String, Object> params) {
        return "type=" + params.get("type") + "&"
            +   "uuid=" + params.get("uuid") + "&"
            +   "isapproved=" + params.get("isapproved");
    }
}
