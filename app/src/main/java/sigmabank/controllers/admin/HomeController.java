package sigmabank.controllers.admin;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Admin;
import sigmabank.model.register.Client;
import sigmabank.net.ApprovalConnection;
import sigmabank.net.UpdateConnection;

public class HomeController extends BaseController<Admin> {
    @FXML private VBox accountRequests;
    @FXML private Text amountClients;
    
    @FXML private VBox loanRequests;
    @FXML private Text amountLoans;

    private void reset() {
        for (int i = this.accountRequests.getChildren().size() - 1; i > 0; --i) {
            this.accountRequests.getChildren().remove(i);
        }

        for (int i = this.loanRequests.getChildren().size() - 1; i > 0; --i) {
            this.loanRequests.getChildren().remove(i);
        }
    }
    
    @Override
    public void initData() throws IOException {
        new UpdateConnection("http://localhost:8000/update").send(new HashMap<>());

        ApprovalConnection conn = new ApprovalConnection("http://localhost:8000/toapproval");
        List<Object> temp = conn.fetch(Map.of());
        
        List<Client> clients = (List<Client>) temp.get(0);
        List<Loan> loans = (List<Loan>) temp.get(1);

        this.reset();
        
        for (Client client : clients) {
            this.accountRequests.getChildren().add(this.getView("admin/client_item", client, this.object));
        }
        this.amountClients.setText(clients.size() + " contas pendentes");
        
        for (Loan loan : loans) {
            this.loanRequests.getChildren().add(this.getView("admin/loan_item", loan, this.object));
        }
        this.amountLoans.setText(loans.size() + " novas solicitações");
    }
    
    /**
     * 
     * @param e
     * @throws IOException
     */
    public void leave(ActionEvent e) throws IOException {
        this.loadView("auth/login", "Login", null);
    }
}
