package account;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Map;

public class BankAccount implements Account
{
    private AccountType accountType;
    private LocalDate expirationDate;
    private Map<IntervalInterface,Float> interestInformation;
    private float availableAmount;
    private float maxAmount=0;

    /**
     * constructor
     * @param accountType - the type of the account
     * @param expirationDate - the expiration date of the account
     * @param interestInformation - map of pairs where the key is the interval and the value is the corresponding interest rate
     * @param availableAmount - the currently available amount of money
     */
    public BankAccount(AccountType accountType, LocalDate expirationDate, Map<IntervalInterface,Float> interestInformation, float availableAmount)
    {
        this.accountType = accountType;
        this.expirationDate = expirationDate;
        this.interestInformation = interestInformation;
        this.availableAmount = availableAmount;

        for(Map.Entry<IntervalInterface,Float> entry:interestInformation.entrySet())
        {
            IntervalInterface interval=entry.getKey();

            if(interval.getEnd()>maxAmount)
            {
                maxAmount=interval.getEnd();
            }
        }
    }

    /**
     * constructor
     * @param accountType - the type of the account
     * @param expirationDate - the expiration date of the account
     * @param interestInformation - map of pairs where the key is the interval and the value is the corresponding interest rate
     */
    public BankAccount(AccountType accountType, LocalDate expirationDate, Map<IntervalInterface,Float> interestInformation)
    {
        this(accountType,expirationDate,interestInformation,0);
    }

    /**
     * getter
     * @return the type of teh account
     */
    @Override
    public AccountType getAccountType()
    {
        return accountType;
    }

    /**
     * getter
     * @return the expiration date of the account
     */
    @Override
    public LocalDate getExpirationDate()
    {
        return expirationDate;
    }

    /**
     * getter
     * @return the map of pairs where the key is the interval and the value is the corresponding interest rate
     */
    @Override
    public Map<IntervalInterface,Float> getInterestInformation()
    {
        return Collections.unmodifiableMap(interestInformation);
    }

    /**
     * getter
     * @return the amount of money stored into teh account
     */
    @Override
    public float getAvailableAmount()
    {
        return availableAmount;
    }

    /**
     * setter
     * @param availableAmount - the new available amount of money
     */
    public void setAvailableAmount(float availableAmount)
    {
        this.availableAmount = availableAmount;
    }

    /**
     * calculates the yearly profit taking into account the interest rate(for each interval) and the currently stored money
     * @return the yearly profit
     */
    @Override
    public float getPerYearProfit()
    {
        float remainingMoney=availableAmount;

        if(remainingMoney==0)
        {
            return 0;
        }

        float profit=0;

        for(Map.Entry<IntervalInterface,Float> entry:interestInformation.entrySet())
        {
            IntervalInterface interval=entry.getKey();
            float rate=entry.getValue();
            float moneyInThisInterval=Math.min(interval.getEnd()-interval.getStart(),remainingMoney);

            profit=profit+rate/100*moneyInThisInterval;

            remainingMoney-=moneyInThisInterval;

            if(remainingMoney<=0)
            {
                return profit;
            }
        }

        return profit;
    }

    /**
     * calculates the monthly profit taking into account the interest rate(for each interval) and the currently stored money
     * @return the monthly profit
     */
    @Override
    public float getPerMonthProfit()
    {
        return getPerYearProfit()/12;
    }

    /**
     * calculates profit after a certain number of months taking into account the interest rate(for each interval) and the currently stored money
     * @param numberOfMonths - the number of months
     * @return the profit
     */
    @Override
    public float getProfitAfterMonths(int numberOfMonths)
    {
        return getPerMonthProfit()*numberOfMonths;
    }

    /**
     * updates the currently stored money by making the difference between them and the amount to be withdrawn
     * @param amount - the amount of money to be withdrawn
     * @return false if there is not enough stored money, true otherwise
     */
    @Override
    public boolean withdrawMoney(float amount)
    {
        if(amount>availableAmount)
        {
            return false;
        }

        availableAmount-=amount;

        return true;
    }

    /**
     * updates the currently stored money by adding the given amount to them
     * @param amount - the amount to be deposited
     * @return false if the operation would make the account to overflow, true otherwise
     */
    @Override
    public boolean depositMoney(float amount)
    {
        if(availableAmount+amount>maxAmount)
        {
            return false;
        }

        availableAmount+=amount;

        return true;
    }
}
