package sigmabank.model.register;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import javax.management.InvalidAttributeValueException;

class RegisterTest {

    @Test
    void settersValidation() throws InvalidAttributeValueException {
        Register register = new Register("john", LocalDate.parse("2000-01-01"));
        String testEmails[] = {
            "john@gmail.com",
            "j0_hn@gmail.com",
            "john@yahoo.com",
            "john@ic.unicamp.br"
        };
        String testPhoneNumbers[] = {
            "+55(85)91234-5678",
            "(1)91234-5678",
            "91234-5678",
            "+5591234-5678"
        };
        String testAddresses[] = {
            "Rua Abc, 123, 60123-123",
            "Avenida Abc, 123, 60123-123",
            "Rua 1, 1234, 13011-121, Complemento",
            "Avenida Abc123, 123, 60123-123"
        };

        try {
            register.setEmail("Not email"); 
            register.setPhoneNumber("Not phone number"); 
            register.setAddress("Not address"); 
        } catch (InvalidAttributeValueException e) {}

        for (String email : testEmails)
            register.setEmail(email);

        for (String phoneNumber : testPhoneNumbers)
            register.setPhoneNumber(phoneNumber);
        
        for (String address : testAddresses)
            register.setAddress(address);
    }
}
