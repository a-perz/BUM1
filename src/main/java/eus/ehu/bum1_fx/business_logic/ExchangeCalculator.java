package eus.ehu.bum1_fx.business_logic;

//exterior/outside interface for the currency exchange business logic (just the Method Signatures (headers))

public interface ExchangeCalculator {

    /**
     * @return the list of avaliable currency codes with long names.
     */
    String[] getCurrencyLongNames();

    /**
     * Calculates the final exchange value after applying the commission.
     *
     * @param sourceCur currency code for the origin currency
     * @param amount amount to exchange
     * @param endCur currency code for the target currency
     * @return final exchanged amount after commission
     * @throws Exception if the conversion cannot be done
     */
    double getChangeValue(String sourceCur, double amount, String endCur) throws Exception;
      
}
