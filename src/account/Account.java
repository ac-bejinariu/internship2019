package account;

import java.time.LocalDate;
import java.util.Map;

public interface Account
{
    /**
     * getter
     * @return the type of teh account
     */
    AccountType getAccountType();

    /**
     * getter
     * @return the map of pairs where the key is the interval and the value is the corresponding interest rate
     */
    Map<IntervalInterface,Float> getInterestInformation();

    /**
     * getter
     * @return the expiration date of the account
     */
    LocalDate getExpirationDate();

    /**
     * getter
     * @return the amount of money stored into teh account
     */
    float getAvailableAmount();

    /**
     * calculates the yearly profit taking into account the interest rate(for each interval) and the currently stored money
     * @return the yearly profit
     */
    float getPerYearProfit();

    /**
     * calculates the monthly profit taking into account the interest rate(for each interval) and the currently stored money
     * @return the monthly profit
     */
    float getPerMonthProfit();

    /**
     * calculates profit after a certain number of months taking into account the interest rate(for each interval) and the currently stored money
     * @param numberOfMonths - the number of months
     * @return the profit
     */
    float getProfitAfterMonths(int numberOfMonths);

    /**
     * updates the currently stored money by making the difference between them and the amount to be withdrawn
     * @param amount - the amount of money to be withdrawn
     * @return false if there is not enough stored money, true otherwise
     */
    boolean withdrawMoney(float amount);

    /**
     * updates the currently stored money by adding the given amount to them
     * @param amount - the amount to be deposited
     * @return false if the operation would make the account to overflow, true otherwise
     */
    boolean depositMoney(float amount);
}
