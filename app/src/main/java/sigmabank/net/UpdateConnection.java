package sigmabank.net;

import java.io.IOException;

import java.util.Map;
import java.util.List;

public class UpdateConnection extends Connection<Object> {
    public UpdateConnection(String uri) {
        super(uri);
    }

    /**
     * Send a post to the server to update the loans and investments
     */
    public void send(Map<String, Object> params) throws IOException {
        super.send(params);
    }

    /**
     * useless
     */
    public List<Object> fetch(Map<String, Object> params) throws IOException {
        return null;
    }
}
