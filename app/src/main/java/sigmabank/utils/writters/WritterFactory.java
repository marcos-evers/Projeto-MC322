package sigmabank.utils.writters;

public class WritterFactory {

    public enum WritterType {
        CLIENT,
        LOAN,
        ASSETINVESTMENT,
        RATEINVESTMENT,
        ACCOUNT
    }

    /**
     * Create an instance of a specific writer based on the WritterType.
     *
     * @param writterType the type of writer to create.
     * @return a new instance of WritterXML for the specified type.
     * @throws IllegalArgumentException if an invalid writer type is provided.
     */
    @SuppressWarnings("unchecked")
    public static <T> WritterXML<T> createWritter(WritterType writterType) {
        switch (writterType) {
            case CLIENT:
                return (WritterXML<T>) new ClientWritter();
            case LOAN:
                return (WritterXML<T>) new LoanWritter();
            case ASSETINVESTMENT:
                return (WritterXML<T>) new AssetInvestmentWritter();
            case RATEINVESTMENT:
                return (WritterXML<T>) new RateInvestmentWritter();
            default:
                throw new IllegalArgumentException("Invalid writter type.");
        }
    }
}
