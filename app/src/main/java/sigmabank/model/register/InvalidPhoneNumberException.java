package sigmabank.model.register;

/**
 * Exception thrown when phone number is invalid, i.e doesn't match the regex:
 * "^\\+?[0-9()-]*$"
 */
public class InvalidPhoneNumberException extends Exception {
    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}
