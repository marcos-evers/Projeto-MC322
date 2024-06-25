package sigmabank.model.register;

/**
 * Exception thrown when a birth date is invalid, less than 18 years old.
 */
public class InvalidBirthDateException extends Exception {
    public InvalidBirthDateException(String message) {
        super(message);
    }
}
