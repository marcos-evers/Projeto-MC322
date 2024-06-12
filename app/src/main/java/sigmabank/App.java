package sigmabank;

import java.time.LocalDate;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sigmabank.model.register.ClientPersonal;
import sigmabank.net.PostClientPersonal;

/* Saving for later use 
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import sigmabank.model.register.Register;
*/

public class App extends Application {
    @Override
    public void start(Stage stage) {
        String javaVersion = System.getProperty("java.version");
        String javafxVersion = System.getProperty("javafx.version");
        Label l = new Label("Hello, JavaFX " + javafxVersion + ", running on Java " + javaVersion + ".");
        Scene scene = new Scene(new StackPane(l), 640, 480);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws Exception {
        // launch();

        PostClientPersonal pcp = new PostClientPersonal("http://localhost:8000/register");
        ClientPersonal cp = new ClientPersonal("marcos", LocalDate.of(2005, 2, 21), "12312312312");
        cp.setEmail("marcos@paulo.evers");
        cp.setPhoneNumber("85912341234");
        System.out.println("Response Code: " + pcp.send(cp));
    }
}
