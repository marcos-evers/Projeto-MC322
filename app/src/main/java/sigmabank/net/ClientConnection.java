package sigmabank.net;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.DataOutputStream;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import java.util.Map;

import sigmabank.model.register.Client;

public class ClientConnection implements IConnection<Client> {
    private final String uri;

    public ClientConnection(String uri) {
        this.uri = uri;
    }

    public Client fetch(Map<String, Object> params) throws IOException {
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

    public void send(Client client) throws IOException {
        URL url = URI.create(uri).toURL();
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        try(DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
            os.writeBytes(buildPostData(client));
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

    private String buildPostData(Client client) {
        return "name=" + client.getName() + "&"
            +  "dob=" + client.getDateOfBirth() + "&"
            +  "cpf=" + client.getCpf() + "&"
            +  "phoneNumber=" + client.getPhoneNumber() + "&"
            +  "address=" + client.getAddress() + "&"
            +  "email=" + client.getEmail() + "&"
            +  "password=" + client.getPasswordHash();
    }
}
