package sigmabank.httphandler;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;

import java.util.List;

import sigmabank.database.Database;
import sigmabank.model.register.Client;

public class UpdateHttpHandler implements HttpHandler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        if ("POST".equalsIgnoreCase(exchange.getRequestMethod())) {
            handlePOSTMethod(exchange);
        }  else {
            String response = "Invalid request";
            exchange.sendResponseHeaders(405, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }

    private void handlePOSTMethod(HttpExchange exchange) throws IOException {
        System.out.println("[MSG] Recieve Update Request");
        try { 
            List<Object> clients = Database.getInstance().query("Clients", (Object obj) -> true);
            for (Object obj: clients) {
                ((Client) obj).updateValues();
            }

            Database.getInstance().saveToXML("src/main/resources/database");

            String response = "Update Successful";
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch(Exception e) {
            String response = "Update Unsuccessful: " + e.getMessage();
            exchange.sendResponseHeaders(200, response.getBytes().length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        }
    }
}
