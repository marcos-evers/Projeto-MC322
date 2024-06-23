package sigmabank.controllers.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Admin;
import sigmabank.model.register.Client;
import sigmabank.net.ApprovalConnection;

public class HomeController extends BaseController<Admin> {
    @FXML private VBox accountRequests;
    @FXML private Text amountClients;
    
    @FXML private VBox loanRequests;
    @FXML private Text amountLoans;

    
    @Override
    public void initData() throws IOException {
        // TODO delete this hardcoded stuff
        // try {
        //     clients.add(new Client("Nomeeee nomoe nomaeoaemone", LocalDate.now().minusYears(19), "42250341869"));
        //     clients.add(new Client("Nomeeee nomoe nomaeoaemone", LocalDate.now().minusYears(19), "42250341869"));
        //     clients.add(new Client("Nomeeee nomoe nomaeoaemone", LocalDate.now().minusYears(19), "42250341869"));
        //     clients.add(new Client("Nomeeee nomoe nomaeoaemone", LocalDate.now().minusYears(19), "42250341869"));
        //     clients.add(new Client("Nomeeee nomoe nomaeoaemone", LocalDate.now().minusYears(19), "42250341869"));
        //     clients.add(new Client("Nomeeee nomoe nomaeoaemone", LocalDate.now().minusYears(19), "42250341869"));
        //     clients.add(new Client("Nomeeee nomoe nomaeoaemone", LocalDate.now().minusYears(19), "42250341869"));
        // } catch (Exception err) {
        //     err.printStackTrace();
        // }
        // loans.add(new Loan(BigDecimal.valueOf(10000), BigDecimal.valueOf(0.03), UUID.randomUUID(), null, LocalDate.now().minusYears(2), BigDecimal.valueOf(4193), LocalDate.now().minusWeeks(74)));
        // loans.add(new Loan(BigDecimal.valueOf(10000), BigDecimal.valueOf(0.03), UUID.randomUUID(), null, LocalDate.now().minusYears(2), BigDecimal.valueOf(4193), LocalDate.now().minusWeeks(74)));
        // loans.add(new Loan(BigDecimal.valueOf(10000), BigDecimal.valueOf(0.03), UUID.randomUUID(), null, LocalDate.now().minusYears(2), BigDecimal.valueOf(4193), LocalDate.now().minusWeeks(74)));
        // loans.add(new Loan(BigDecimal.valueOf(10000), BigDecimal.valueOf(0.03), UUID.randomUUID(), null, LocalDate.now().minusYears(2), BigDecimal.valueOf(4193), LocalDate.now().minusWeeks(74)));
        // loans.add(new Loan(BigDecimal.valueOf(10000), BigDecimal.valueOf(0.03), UUID.randomUUID(), null, LocalDate.now().minusYears(2), BigDecimal.valueOf(4193), LocalDate.now().minusWeeks(74)));
        // loans.add(new Loan(BigDecimal.valueOf(10000), BigDecimal.valueOf(0.03), UUID.randomUUID(), null, LocalDate.now().minusYears(2), BigDecimal.valueOf(4193), LocalDate.now().minusWeeks(74)));
        // loans.add(new Loan(BigDecimal.valueOf(10000), BigDecimal.valueOf(0.03), UUID.randomUUID(), null, LocalDate.now().minusYears(2), BigDecimal.valueOf(4193), LocalDate.now().minusWeeks(74)));
        // loans.add(new Loan(BigDecimal.valueOf(10000), BigDecimal.valueOf(0.03), UUID.randomUUID(), null, LocalDate.now().minusYears(2), BigDecimal.valueOf(4193), LocalDate.now().minusWeeks(74)));
        
        ApprovalConnection conn = new ApprovalConnection("http://localhost:8000/toapproval");
        List<Object> temp = conn.fetch(Map.of());
        
        List<Client> clients = (List<Client>) temp.get(0);
        List<Loan> loans = (List<Loan>) temp.get(1);

        System.out.println(clients);
        System.out.println(loans);

        for (Client client : clients) {
            accountRequests.getChildren().add(this.getView("admin/client_item", client));
        }
        this.amountClients.setText(clients.size() + " contas pendentes");
        
        for (Loan loan : loans) {
            loanRequests.getChildren().add(this.getView("admin/loan_item", loan));
        }
        this.amountLoans.setText(loans.size() + " novas solicitações");
    }
    
    public void leave(ActionEvent e) {
        Platform.exit();
    }
}
