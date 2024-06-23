package sigmabank.controllers.client.loan;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sigmabank.controllers.BaseController;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Client;

public class RequestController extends BaseController<Client> {
    @FXML private Text greeting;
    @FXML private TextField value;
    @FXML private Text fee;

    @Override
    public void initData() throws IOException {
        this.greeting.setText("Olá, " + this.object.getName() + "!");
        value.textProperty().addListener((observable, oldValue, newValue) -> valueChanged(newValue));
    }

    public void goBack(Event e) throws IOException {
        this.loadView("client/home", "Home", this.object);
    }

    private BigDecimal getValueToLoan(String newValue) {
        try {
            return new BigDecimal(newValue);
        } catch (Exception err) {
            return null;
        }
    }

    public void valueChanged(String newValue) {
        BigDecimal valueToLoan = this.getValueToLoan(newValue);
        BigDecimal feeValue = this.getFee(valueToLoan);

        this.fee.setText(feeValue == null ? "-" : feeValue + "%");
    }

    private BigDecimal getFee(BigDecimal valueToLoan) {
        if (valueToLoan == null || valueToLoan.compareTo(BigDecimal.ZERO) <= 0) {
            return null;
        }

        return Loan.calculateFee(valueToLoan);
    }

    public void confirm(Event e) throws IOException {
        BigDecimal valueToLoan = this.getValueToLoan(this.value.getText());
        BigDecimal feeValue = this.getFee(valueToLoan);
        if (feeValue == null) {
            BaseController.errorDialog("Insira um valor válido.");
            return;
        }

        Loan loan = new Loan(valueToLoan, this.object.getUUID(), LocalDate.now());
        // TODO database stuff to insert this loan

        this.goBack(e);
    }
    
    public void leave(ActionEvent e) {
        Platform.exit();
    }
}
