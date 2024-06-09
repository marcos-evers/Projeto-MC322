package sigmabank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import sigmabank.writterXML.WriteToXML;

import sigmabank.model.register.ClientPersonal;

public class Server {
    private static int port = 8000;
    private static HttpServer server;

    public static void main(String[] args) throws IOException {
        server = HttpServer.create(new InetSocketAddress(port), 0);
        server.createContext("/form", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String response = "<!DOCTYPE html>" +
                        "<html lang='en'>" +
                        "<head>" +
                        "<meta charset='UTF-8'>" +
                        "<meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                        "<title>Register</title>" +
                        "<link rel='stylesheet' href='style.css'>" +
                        "</head>" +
                        "<body>" +
                        "<form action='/register' method='post'>" +
                        "<label for='name'>Name:</label>" +
                        "<input type='text' id='name' name='name' required>" +
                        "<label for='dob'>Date of Birth:</label>" +
                        "<input type='date' id='dob' name='dob' required>" +
                        "<label for='CPF'>CPF :</label>" +
                        "<input type='CPF' id='CPF' name='CPF' required>" +
                        "<label for='phoneNumber'>phoneNumber:</label>" +
                        "<input type='phoneNumber' id='phoneNumber' name='phoneNumber' required>" +
                        "<label for='email'>email :</label>" +
                        "<input type='email' id='email' name='email' required>" +
                        "<input type='submit' value='Register'>" +
                        "</form>" +
                        "</body>" +
                        "</html>";

                exchange.sendResponseHeaders(200, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        });
        
        server.createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String filePath = "./post.html"; // Correct path to your HTML file
                byte[] fileBytes = Files.readAllBytes(Paths.get(filePath));
                exchange.getResponseHeaders().set("Content-Type", "text/html");
                exchange.sendResponseHeaders(200, fileBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(fileBytes);
                }
            }
        });

        server.createContext("/style.css", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                byte[] fileBytes = Files.readAllBytes(Paths.get("./style.css")); 
                exchange.getResponseHeaders().set("Content-Type", "text/css");
                exchange.sendResponseHeaders(200, fileBytes.length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(fileBytes);
                }
            }
        });

        server.createContext("/register", new RegistrationHandler());
        server.setExecutor(null); // creates a default executor
        System.out.println("[MSG] Server started on port " + port);
        server.start();
    }

    static class RegistrationHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8);
                
                BufferedReader br = new BufferedReader(isr);
                String query = br.readLine();
                Map<String, String> parsedQuery = parseQuery(query);

                // Assuming you have methods to set these values in your ClientPersonal class
                try{
                    ClientPersonal client = new ClientPersonal(
                        parsedQuery.get("name"),
                        LocalDate.parse(parsedQuery.get("dob")),
                        parsedQuery.get("CPF")
                    );
                    
                    client.setEmail(parsedQuery.get("email"));
                    client.setPhoneNumber(parsedQuery.get("phoneNumber"));

                    WriteToXML.writeToXML(client, "src/main/java/sigmabank/database/registerDB.xml");

                    String response = "Registration Successful: " + client.toString();
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }

                }catch(Exception e){
                    String response = "Registration Unsuccessful: " + e;
                    exchange.sendResponseHeaders(200, response.getBytes().length);
                    try (OutputStream os = exchange.getResponseBody()) {
                        os.write(response.getBytes());
                    }
                }
            } else {
                String response = "Invalid request";
                exchange.sendResponseHeaders(405, response.getBytes().length);
                try (OutputStream os = exchange.getResponseBody()) {
                    os.write(response.getBytes());
                }
            }
        }

        private Map<String, String> parseQuery(String query) throws UnsupportedEncodingException {
            Map<String, String> result = new HashMap<>();
            for (String param : query.split("&")) {
                String[] entry = param.split("=");
                if (entry.length > 1) {
                    result.put(entry[0], URLDecoder.decode(entry[1], StandardCharsets.UTF_8.name()));
                } else {
                    result.put(entry[0], "");
                }
            }
            return result;
        }
    }
}
