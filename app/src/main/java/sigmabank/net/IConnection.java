package sigmabank.net;

import java.io.IOException;
import java.util.Map;
import java.util.List;

// TODO Change to Abstract Class
public interface IConnection<T> {
    public void send(Map<String, Object> params) throws IOException;
    public List<T> fetch(Map<String, Object> params) throws IOException;
}
