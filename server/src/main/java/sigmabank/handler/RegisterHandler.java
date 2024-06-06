package sigmabank.handler;

import java.util.Date;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;

import sigmabank.ClientPersonal;
import sigmabank.Register;

public class RegisterHandler implements HttpHandler {
    private void handleGET(HttpExchange exchange) throws IOException {
        ClientPersonal register = new ClientPersonal("marcos", new Date(105, 2, 21), "000");
        try {
            register.setEmail("marcospauloeversc@gmail.com");
            register.setPhoneNumber("+5585991620649");
            register.setAddress("Rua 123, 123, 00123-123");
        } catch(Exception e) {}

        System.out.println(register.toString());

        String response = "";        

        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Register.class, ClientPersonal.class);
            Marshaller marshaller = jaxbContext.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_FRAGMENT, true);

            StringWriter sw = new StringWriter();
            marshaller.marshal(register, sw);

            System.out.println(register.toString());
            response += sw.toString();
        } catch(JAXBException e) {
            e.printStackTrace();
            response += e.getMessage();
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
