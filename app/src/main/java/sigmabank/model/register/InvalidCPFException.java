package sigmabank.model.register;

/**
 * Exception thrown when a CPF is invalid.
 */
public class InvalidCPFException extends Exception {
    public InvalidCPFException(String message) {
        super(message);
    }
}