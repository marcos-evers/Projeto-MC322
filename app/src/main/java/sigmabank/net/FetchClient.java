package sigmabank.net;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.BufferedReader;

import java.net.URI;
import java.net.URL;
import java.net.HttpURLConnection;

import sigmabank.model.register.Client;

public class FetchClient {
    private final String uri;

    public FetchClient(String uri) throws Exception {
        this.uri = uri;
    }

    public Client fetch(String cpf, String passwordHash) throws IOException {
        URI requestURI = URI.create(uri + "?cpf=" + cpf + "&password=" + passwordHash);
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
        return null;
    }
}
