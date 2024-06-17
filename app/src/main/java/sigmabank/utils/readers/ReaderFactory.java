package sigmabank.utils.readers;


public class ReaderFactory {
    public enum ReaderType {
        CLIENT,
        LOAN,
        ASSETINVESTMENT,
        RATEINVESTMENT,
        ACCOUNT
    }

    /**
     * Create an instance of a specific reader based on the ReaderType.
     *
     * @param writterType the type of reader to create.
     * @return a new instance of ReaderXML for the specified type.
     * @throws IllegalArgumentException if an invalid writer type is provided.
     */
    @SuppressWarnings("unchecked")
    public static <T> ReaderXML<T> createReader(ReaderType readertype) {
        switch (readertype) {
            case CLIENT:
                return (ReaderXML<T>) new ClientReader();
            case LOAN:
                return (ReaderXML<T>) new LoanReader();
            case ASSETINVESTMENT:
                return (ReaderXML<T>) new AssetInvestmentReader();
            case RATEINVESTMENT:
                return (ReaderXML<T>) new RateInvestmentReader();
            default:
                throw new IllegalArgumentException("Invalid writter type.");
        }
    }
}
