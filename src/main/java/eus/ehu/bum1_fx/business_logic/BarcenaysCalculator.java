package eus.ehu.bum1_fx.business_logic;


//implements the ExchangeCalculator Interface (just all its Method Sigantures (headers) implemented) and uses CommissionCalculator, Currency and ForexOperator


public class BarcenaysCalculator implements ExchangeCalculator{

    @Override
    public String[] getCurrencyLongNames() {
        return Currency.longNames();
    }

    @Override
    public double getChangeValue(String sourceCur, double amount, String endCur) throws Exception {
        ForexOperator operator = new ForexOperator(sourceCur, amount, endCur); //generate exchange w/ given variables
        double endAmount = operator.getChangeValue(); //get amount from URL web page
        CommissionCalculator calculator = new CommissionCalculator(endAmount, endCur); //calculate commission

        return endAmount - calculator.calculateCommission(); //final amount
    }



   //simplified interface that hides the complexity of coordinating Currency, ForexOperator, and CommissionCalculator.


}
