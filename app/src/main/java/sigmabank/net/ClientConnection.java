package sigmabank.net;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataOutputStream;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import java.util.Map;
import java.util.List;

import sigmabank.model.register.Client;

public class ClientConnection implements IConnection<Client> {
    private final String uri;

    public ClientConnection(String uri) {
        this.uri = uri;
    }

    /**
     * Do a GET request to the server for search a client
     *
     * @param params A map with two keys:
     *      - CPF (String)
     *      - passwordHash (String)
     * @return an empty list or a list containing only the desired client
     * @throws IOException if a error occur connecting to the server
     */
    public List<Client> fetch(Map<String, Object> params) throws IOException {
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
        // TODO create client object
        return null;
    }

    /**
     * Do a POST request to register a client that will pending approval
     *
     * @param params A map with client attributes.
     * @throws IOException if a error occur connecting to the server
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

    private String buildFetchURI(Map<String, Object> params) {
        return uri + "?"
            + "cpf=" + params.get("cpf") + "&"
            + "password=" + params.get("password");
    }

    private String buildPostData(Map<String, Object> params) {
        return "name=" + (String) params.get("name") + "&"
            +  "dob=" + (String) params.get("dataOfBirth") + "&"
            +  "cpf=" + (String) params.get("cpf") + "&"
            +  "phoneNumber=" + (String) params.get("phoneNumber") + "&"
            +  "address=" + (String) params.get("address") + "&"
            +  "email=" + (String) params.get("email") + "&"
            +  "password=" + (String) params.get("passwordHash");
    }
}
