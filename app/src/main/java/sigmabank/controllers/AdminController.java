package sigmabank.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Admin;
import sigmabank.model.register.Client;

public class AdminController extends BaseController<Admin> {
    @FXML private VBox accountRequests;
    @FXML private Text amountClients;
    
    @FXML private VBox loanRequests;
    @FXML private Text amountLoans;

    private List<Client> clients = new ArrayList<>();
    private List<Loan> loans = new ArrayList<>();

    @Override
    public void initData() throws IOException {
        // TODO delete this hardcoded stuff
        try {
            clients.add(new Client("Nomeeee nomoe nomaeoaemone", LocalDate.now().minusYears(19), "42250341869", "."));
            clients.add(new Client("Nomeeee nomoe nomaeoaemone", LocalDate.now().minusYears(19), "42250341869", "."));
            clients.add(new Client("Nomeeee nomoe nomaeoaemone", LocalDate.now().minusYears(19), "42250341869", "."));
            clients.add(new Client("Nomeeee nomoe nomaeoaemone", LocalDate.now().minusYears(19), "42250341869", "."));
            clients.add(new Client("Nomeeee nomoe nomaeoaemone", LocalDate.now().minusYears(19), "42250341869", "."));
            clients.add(new Client("Nomeeee nomoe nomaeoaemone", LocalDate.now().minusYears(19), "42250341869", "."));
            clients.add(new Client("Nomeeee nomoe nomaeoaemone", LocalDate.now().minusYears(19), "42250341869", "."));
        } catch (Exception err) {
            err.printStackTrace();
        }
        loans.add(new Loan(BigDecimal.valueOf(10000), BigDecimal.valueOf(0.03), UUID.randomUUID(), null, LocalDate.now().minusYears(2), BigDecimal.valueOf(4193), LocalDate.now().minusWeeks(74)));
        loans.add(new Loan(BigDecimal.valueOf(10000), BigDecimal.valueOf(0.03), UUID.randomUUID(), null, LocalDate.now().minusYears(2), BigDecimal.valueOf(4193), LocalDate.now().minusWeeks(74)));
        loans.add(new Loan(BigDecimal.valueOf(10000), BigDecimal.valueOf(0.03), UUID.randomUUID(), null, LocalDate.now().minusYears(2), BigDecimal.valueOf(4193), LocalDate.now().minusWeeks(74)));
        loans.add(new Loan(BigDecimal.valueOf(10000), BigDecimal.valueOf(0.03), UUID.randomUUID(), null, LocalDate.now().minusYears(2), BigDecimal.valueOf(4193), LocalDate.now().minusWeeks(74)));
        loans.add(new Loan(BigDecimal.valueOf(10000), BigDecimal.valueOf(0.03), UUID.randomUUID(), null, LocalDate.now().minusYears(2), BigDecimal.valueOf(4193), LocalDate.now().minusWeeks(74)));
        loans.add(new Loan(BigDecimal.valueOf(10000), BigDecimal.valueOf(0.03), UUID.randomUUID(), null, LocalDate.now().minusYears(2), BigDecimal.valueOf(4193), LocalDate.now().minusWeeks(74)));
        loans.add(new Loan(BigDecimal.valueOf(10000), BigDecimal.valueOf(0.03), UUID.randomUUID(), null, LocalDate.now().minusYears(2), BigDecimal.valueOf(4193), LocalDate.now().minusWeeks(74)));
        loans.add(new Loan(BigDecimal.valueOf(10000), BigDecimal.valueOf(0.03), UUID.randomUUID(), null, LocalDate.now().minusYears(2), BigDecimal.valueOf(4193), LocalDate.now().minusWeeks(74)));


        for (Client client : clients) {
            accountRequests.getChildren().add(BaseController.getView("client_item", this, client));
        }
        this.amountClients.setText(clients.size() + " contas pendentes");
        
        for (Loan loan : loans) {
            loanRequests.getChildren().add(BaseController.getView("loan_request_item", this, loan));
        }
        this.amountLoans.setText(loans.size() + " novas solicitações");
    }
    
    public void leave(ActionEvent e) {
        Platform.exit();
    }
}
