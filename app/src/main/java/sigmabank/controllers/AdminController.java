package sigmabank.controllers;

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
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Admin;
import sigmabank.model.register.Client;

public class AdminController extends BaseController<Admin> {
    @FXML private VBox accountRequests;
    @FXML private Text amountClients;
    
    @FXML private VBox loanRequests;
    @FXML private Text amountLoans;

    @Override
    public void initData(Admin admin) {
        List<Client> clients = new ArrayList<>();
        List<Loan> loans = new ArrayList<>();
        

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


        try {
            for (Client client : clients) {
                accountRequests.getChildren().add(BaseController.getView(
                    "client_item",
                    this,
                    Map.of("cpf", client.getCpf(), "name", client.getName())
                ));
            }
            amountClients.setText(clients.size() + " contas pendentes");
            
            for (Loan loan : loans) {
                // TODO preicsa retrieve o loan e o cliente junto pra poder passar o nome do cliente aqui
                loanRequests.getChildren().add(BaseController.getView(
                    "loan_request_item",
                    this,
                    Map.of("amount", loan.getAmount().toString(), "name", loan.getClientUUID().toString())
                ));
            }
            amountLoans.setText(loans.size() + " novas solicitações");
        } catch (Exception err) {
            err.printStackTrace();
        }
    }
    
    public void leave(ActionEvent e) {
        Platform.exit();
    }
}
