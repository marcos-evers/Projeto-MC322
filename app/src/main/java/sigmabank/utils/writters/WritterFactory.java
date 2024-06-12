package sigmabank.utils.writters;

public class WritterFactory {

    public enum WritterType {
        CLIENT_PERSONAL,
        CLIENT_ENTERPRISE,
        ACCOUNT,
        LOAN
    }

    /**
     * Create an instance of WritterXML based on the WritterType.
     * 
     * @param writterType the type of writter to create.
     * @return a new instance of WritterXML.
     */
    public static WritterXML<?> createWritter(WritterType writterType) {
        switch (writterType) {
            case CLIENT_PERSONAL -> {
                return new ClientPersonalWritter();
            }
            case CLIENT_ENTERPRISE -> {
                return new ClientEnterpriseWritter();
            }
            case ACCOUNT -> {
                return new AccountWritter();
            }
            case LOAN -> {
                return new LoanWritter();
            }
            default -> throw new IllegalArgumentException("Invalid writter type.");
        }
    }
}