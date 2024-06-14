package sigmabank.model.investment;

import java.math.BigDecimal;

/**
 * Interface for defining the operations related to an investment.
 */
public interface InvestmentOperations {

    /**
     * Retrieve a portion or all of the investment.
     * 
     */
    public void retrieveInvestment(BigDecimal amount);

    /**
     * Invest more money into the investment.
     * 
     * @param amount the amount to be invested as a BigDecimal.
     */
    public void investMore(BigDecimal amount);
}