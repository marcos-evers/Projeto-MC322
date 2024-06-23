package sigmabank.model.register;

/**
 * Exception thrown when an e-mail is invalid, using the current standard
 * e-mail regex.
 */
public class InvalidEmailException extends Exception {
    public InvalidEmailException(String message) {
        super(message);
    }
}