package sigmabank.model.register;

/**
 * Exception thrown when an address is invalid using the following regex:
 * "^(Rua|Avenida) [a-zA-Z0-9 ]+, [0-9]+, [0-9]{5}-[0-9]{3}"
 */
public class InvalidAddressException extends Exception {
    public InvalidAddressException(String message) {
        super(message);
    }
}
