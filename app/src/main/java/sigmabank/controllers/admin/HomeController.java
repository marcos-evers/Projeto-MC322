package sigmabank.controllers.admin;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
        ApprovalConnection conn = new ApprovalConnection("http://localhost:8000/toapproval");
        List<Object> temp = conn.fetch(Map.of());
        
        List<Client> clients = (List<Client>) temp.get(0);
        List<Loan> loans = (List<Loan>) temp.get(1);

        for (Client client : clients) {
            accountRequests.getChildren().add(this.getView("admin/client_item", client, this.object));
        }
        this.amountClients.setText(clients.size() + " contas pendentes");
        
        for (Loan loan : loans) {
            loanRequests.getChildren().add(this.getView("admin/loan_item", loan, this.object));
        }
        this.amountLoans.setText(loans.size() + " novas solicitações");
    }
    
    public void leave(ActionEvent e) {
        Platform.exit();
    }
}
