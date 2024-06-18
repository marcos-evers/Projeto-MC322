package sigmabank.utils.readers;

import sigmabank.model.investment.AssetInvestment;
import sigmabank.model.investment.RateInvestment;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Client;

public class ReaderFactory {
    /**
     * Create an instance of a specific reader based on the ReaderType.
     *
     * @param objClass the class of reader to create.
     * @return a new instance of ReaderXML for the specified class.
     * @throws IllegalArgumentException if an invalid writer type is provided.
     */
    @SuppressWarnings("unchecked")
    public static <T> ReaderXML<T> createReader(Class<?> objClass) {
        if (objClass.equals(Client.class))
            return (ReaderXML<T>) new ClientReader();
        if (objClass.equals(Loan.class))
            return (ReaderXML<T>) new LoanReader();
        if (objClass.equals(AssetInvestment.class))
            return (ReaderXML<T>) new AssetInvestmentReader();
        if (objClass.equals(RateInvestment.class))
            return (ReaderXML<T>) new RateInvestmentReader();
        throw new IllegalArgumentException("");
    }
}
