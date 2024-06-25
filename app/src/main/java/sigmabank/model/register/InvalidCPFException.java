package sigmabank.model.register;

/**
 * Exception thrown when a CPF is invalid using the verification numbers of the CPF.
 */
public class InvalidCPFException extends Exception {
    public InvalidCPFException(String message) {
        super(message);
    }
}