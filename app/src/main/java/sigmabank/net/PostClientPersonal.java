package sigmabank.net;

import java.io.DataOutputStream;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import sigmabank.model.register.ClientPersonal;

public class PostClientPersonal {
    private final URL url;

    public PostClientPersonal(String uri) throws Exception {
        url = new URI(uri).toURL();
    }

    private String buildPostData(ClientPersonal client) {
        return "name=" + client.getName() + "&"
            +  "dateOfBirth=" + client.getDateOfBirth() + "&"
            +  "cpf=" + client.getCpf() + "&"
            +  "phoneNumber=" + client.getPhoneNumber() + "&"
            +  "email=" + client.getEmail();
    }

    public int send(ClientPersonal client) throws Exception {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection(); 

        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        try(DataOutputStream os = new DataOutputStream(connection.getOutputStream())) {
            os.writeBytes(buildPostData(client));
            os.flush();
        }

        int responseCode = connection.getResponseCode();
        connection.disconnect();
        return responseCode;
    }
}
