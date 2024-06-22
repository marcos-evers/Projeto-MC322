package sigmabank.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Client;

public class RequestLoanController extends BaseController<Client> {
    @FXML private Text greeting;
    @FXML private TextField value;
    @FXML private Text fee;

    @Override
    public void initData() throws IOException {
        this.greeting.setText("Olá, " + this.object.getName() + "!");
        value.textProperty().addListener((observable, oldValue, newValue) -> valueChanged(newValue));
    }

    public void goBack(Event e) throws IOException {
        this.loadView("client_page", "Home", this.object);
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

        // TODO define how to calculate the fee as a function of `valueToLoan`
        // then set this text no `fee`
        BigDecimal newFeeValue = /*f(valueToLoan)*/valueToLoan;

        return newFeeValue;
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
