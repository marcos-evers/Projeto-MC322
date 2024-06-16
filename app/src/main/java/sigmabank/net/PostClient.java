package sigmabank.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import sigmabank.model.register.Client;

public class PostClient {
    private final URL url;

    public PostClient(String uri) throws Exception {
        url = new URI(uri).toURL();
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

    public int send(Client client) throws Exception {
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
            System.out.println(response.toString());
        }
        connection.disconnect();
        return responseCode;
    }
}
