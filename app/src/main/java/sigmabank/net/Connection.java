package sigmabank.net;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.IOException;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

import java.util.Map;
import java.util.List;

public abstract class Connection<T> {
    private final String uri;

    public Connection(String uri) {
        this.uri = uri;
    }

    public String getURI() {
        return this.uri;
    }

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
    
    public abstract List<T> fetch(Map<String, Object> params) throws IOException;

    protected String buildPostData(Map<String, Object> params) {
        String data = "";
        for (String key: params.keySet()) {
            if (data.length() != 0)
                data += '&';
            data += key + "=" + params.get(key).toString();
        }
        return data;
    }
}
