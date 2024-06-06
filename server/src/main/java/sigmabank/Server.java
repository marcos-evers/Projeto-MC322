import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import app.src.main.java.sigmabank.model.register.ClientPersonal;

public class Server {
    private static int port = 8000;
    private static HttpServer server;

    public static void main(String[] args) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/register", new RegistrationHandler());
        server.setExecutor(null); // creates a default executor
        System.out.println("[MSG] Server started on port " + port);
        server.start();
    }

    static class RegistrationHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String query = br.readLine();
                Map<String, String> parsedQuery = parseQuery(query);
                
                // Example of using parsed data to create a ClientPersonal object
                ClientPersonal client = new ClientPersonal(
                    parsedQuery.get("name"),
                    LocalDate.parse(parsedQuery.get("date")), // Example: modify according to your needs
                    parsedQuery.get("cpf")
                );

                // You would need to add additional fields and methods to handle them
                client.setEmail(parsedQuery.get("email"));
                client.setPhoneNumber(parsedQuery.get("phone"));
                client.setAddress(parsedQuery.get("address"));

                String response = "Registration Successful";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            } else {
                // Handle other HTTP methods, e.g., GET
                String response = "Invalid request";
                exchange.sendResponseHeaders(405, response.getBytes().length); // 405 Method Not Allowed
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }

        private Map<String, String> parseQuery(String query) throws UnsupportedEncodingException {
            Map<String, String> result = new HashMap<>();
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], URLDecoder.decode(entry[1], StandardCharsets.UTF_8.name()));
                }else{
                    result.put(entry[0], "");
                }
            }
            return result;
        }
    }
}