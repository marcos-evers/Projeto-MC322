package sigmabank.handler;

import java.io.IOException;
import java.io.OutputStream;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

public class RegisterHandler implements HttpHandler {
    private void handleGET(HttpExchange exchange) throws IOException {
        String[] params = exchange.getRequestURI().toString().substring("/register?".length()).split("&");
        String response = "";
        for (int i = 0; i < params.length; i++) {
            response += params[i] + "\n";
        }

        exchange.sendResponseHeaders(200, response.getBytes().length);
        OutputStream os = exchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {

        if ("GET".equals(exchange.getRequestMethod())) {
            handleGET(exchange);
        } else {
            String response = "Unsupported method";
            exchange.sendResponseHeaders(501, response.getBytes().length);
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
}
