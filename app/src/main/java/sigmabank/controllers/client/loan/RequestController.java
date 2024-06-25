package sigmabank.controllers.client.loan;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Client;
import sigmabank.net.LoanConnection;

public class RequestController extends BaseController<Client> {
    @FXML private Text greeting;
    @FXML private TextField value;
    @FXML private Text fee;

    @Override
    public void initData() throws IOException {
        this.greeting.setText("Olá, " + this.object.getName().split(" ")[0] + "!");
        value.textProperty().addListener((observable, oldValue, newValue) -> valueChanged(newValue));
    }

    /**
     * 
     * @param e
     * @throws IOException
     */
    public void goBack(Event e) throws IOException {
        this.loadView("client/home", "Home", this.object);
    }

    /**
     * 
     * @param newValue
     * @return
     */
    private BigDecimal getValueToLoan(String newValue) {
        try {
            return new BigDecimal(newValue);
        } catch (Exception err) {
            return null;
        }
    }

    /**
     * 
     * @param newValue
     */
    public void valueChanged(String newValue) {
        BigDecimal valueToLoan = this.getValueToLoan(newValue);
        BigDecimal feeValue = this.getFee(valueToLoan);

        this.fee.setText(feeValue == null ? "-" : feeValue + "%");
    }

    /**
     * 
     * @param valueToLoan
     * @return
     */
    private BigDecimal getFee(BigDecimal valueToLoan) {
        if (valueToLoan == null || valueToLoan.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        return Loan.calculateFee(valueToLoan);
    }

    /**
     * 
     * @param e
     * @throws IOException
     */
    public void confirm(Event e) throws IOException {
        BigDecimal valueToLoan = this.getValueToLoan(this.value.getText());
        BigDecimal feeValue = this.getFee(valueToLoan);
        if (feeValue == null) {
            BaseController.errorDialog("Insira um valor válido.");
            return;
        }

        LoanConnection conn = new LoanConnection("http://localhost:8000/loan");
        conn.send(Map.of(
            "value", valueToLoan,
            "uuid", this.object.getUUID(),
            "startday", LocalDate.now()
        ));

        this.goBack(e);
    }
    
    /**
     * 
     * @param e
     * @throws IOException
     */
    public void leave(ActionEvent e) throws IOException {
        this.loadView("client/home", "Home", this.object);
    }
}
