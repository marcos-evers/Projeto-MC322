package sigmabank.utils.writters;

import sigmabank.model.investment.AssetInvestment;
import sigmabank.model.investment.RateInvestment;
import sigmabank.model.loan.Loan;
import sigmabank.model.register.Client;

public class WritterFactory {
    /**
     * Create an instance of a specific writer based on the WriterType.
     *
     * @param objClass the class of writer to create.
     * @return a new instance of WriterXML for the specified class.
     * @throws IllegalArgumentException if an invalid writer type is provided.
     */
    @SuppressWarnings("unchecked")
    public static <T> WritterXML<T> createWritter(Class<?> objClass) {
        if (objClass.equals(Client.class))
            return (WritterXML<T>) new ClientWritter();
        if (objClass.equals(Loan.class))
            return (WritterXML<T>) new LoanWritter();
        if (objClass.equals(AssetInvestment.class))
            return (WritterXML<T>) new AssetInvestmentWritter();
        if (objClass.equals(RateInvestment.class))
            return (WritterXML<T>) new RateInvestmentWritter();
        throw new IllegalArgumentException("");
    }
}
