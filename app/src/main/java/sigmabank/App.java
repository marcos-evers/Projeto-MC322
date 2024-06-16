package sigmabank;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sigmabank.model.register.Client;
import sigmabank.net.PostClient;
import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/sigmabank/fxml/register.fxml"));
        Parent root = loader.load();

        stage.setTitle("FXML test");
        stage.setScene(new Scene(root));
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        // launch();

        PostClient pcp = new PostClient("http://localhost:8000/register");
        Client cp = new Client("marcos", LocalDate.of(2005, 2, 21), "12312312312");
        cp.setEmail("marcos@paulo.evers");
        cp.setPhoneNumber("85912341234");
        cp.setAddress("Rua 123, 123, 00123-123");
        System.out.println("Response Code: " + pcp.send(cp));
    }
}
