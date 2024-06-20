package sigmabank.net;

import java.io.IOException;
import java.util.Map;

public interface IConnection<T> {
    public void send(T obj) throws IOException;
    public T fetch(Map<String, Object> params) throws IOException;
}
