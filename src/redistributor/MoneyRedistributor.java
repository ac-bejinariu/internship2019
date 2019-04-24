package redistributor;

import account.Account;
import account.BankAccount;
import account.IntervalInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MoneyRedistributor implements Redistributor
{
    /**
     * redistributes money between accounts so that the interests give maximum profits
     * @param accounts - the list of accounts
     * @return the list that contains the accounts with redistributed amounts of money
     */
    @Override
    public List<Account> redistributeMoney(List<Account> accounts)
    {
        float money=extractMoney(accounts);

        List<Float> distribution=new ArrayList<>();
        accounts.forEach(account -> distribution.add(account.getAvailableAmount()));

        List<Float> bestDistribution=getDistribution(accounts,money,distribution);

        for(int i=0;i<accounts.size();i++)
        {
            ((BankAccount)accounts.get(i)).setAvailableAmount(bestDistribution.get(i));
        }

        return accounts;
    }

    /**
     * removes all the money in the accounts
     * @param accounts - the list of accounts
     * @return the sum of removed money quantities
     */
    private float extractMoney(List<Account> accounts)
    {
        float money=0;

        for(Account account:accounts)
        {
            money+=account.getAvailableAmount();
            account.withdrawMoney(account.getAvailableAmount());
        }

        return money;
    }

    /**
     * calculates the sum of yearly interests of all given accounts
     * @param accounts - the list of given accounts
     * @return the sum
     */
    private float calculateSumOfInterests(List<Account> accounts)
    {
        float money=0;

        for(Account account:accounts)
        {
            money+=account.getPerYearProfit();
        }

        return money;
    }

    /**
     * calculates the sum of yearly interests of all given accounts if they had the given amounts of money
     * @param accounts - the list of given accounts
     * @param amounts - the list of given amounts of money for each account
     * @return the sum
     */
    private float calculateSumOfInterests(List<Account> accounts,List<Float> amounts)
    {
        List<Account> copyAccounts=getCopies(accounts);

        for(int i=0;i<amounts.size();i++)
        {
            ((BankAccount)copyAccounts.get(i)).setAvailableAmount(amounts.get(i));
        }

        return(calculateSumOfInterests(copyAccounts));
    }

    /**
     * calculates the amount of money required to be added to the given account so that he fills his current interest interval
     * @param account - the given account
     * @return the amount of money required
     */
    private float getAmountToFillCurrentInterval(Account account)
    {
        Map<IntervalInterface,Float> interestInformation=account.getInterestInformation();

        float amountToFill=-1;

        for(Map.Entry<IntervalInterface,Float> info:interestInformation.entrySet())
        {
            IntervalInterface interval=info.getKey();

            if(interval.getStart()<=account.getAvailableAmount() && interval.getEnd()>account.getAvailableAmount())
            {
                amountToFill=interval.getEnd()-interval.getStart();
                break;
            }
        }

        return amountToFill;
    }

    /**
     * compares the yearly profits of the 2 distributions of money among the given accounts accounts
     * @param accounts - the given accounts
     * @param distribution1 - the first given distribution of money
     * @param distribution2 - the second given distribution of money
     * @return -1 if the distribution1 gives less yearly profits from interests than distribution2,
     *          0 if they give an equal amount of profit
     *          1 if distribution2 gives more profit than distribution1
     */
    private int compareDistributions(List<Account> accounts,List<Float> distribution1,List<Float> distribution2)
    {
        float profit1=calculateSumOfInterests(accounts,distribution1);
        float profit2=calculateSumOfInterests(accounts,distribution2);

        return Float.compare(profit1,profit2);
    }

    /**
     * finds the best distribution of money among accounts so that profits from interests are maximised
     * @param accounts - the given accounts
     * @param availableMoney - the given available money
     * @param bestDistribution - the best distribution found yet
     * @return the best distribution of money
     */
    private List<Float> getDistribution(List<Account> accounts,float availableMoney,List<Float> bestDistribution)
    {
        List<Float> amountsForEachAccount = new ArrayList<>();

        accounts.forEach(account -> amountsForEachAccount.add(account.getAvailableAmount()));

        if (availableMoney == 0)
        {
            return amountsForEachAccount;
        }

        for (Account account:accounts)
        {
            float amountToFill = getAmountToFillCurrentInterval(account);

            if (amountToFill != -1)
            {
                float amountToDeposit = Math.min(amountToFill, availableMoney);

                account.depositMoney(amountToDeposit);
                List<Float> distribution = getDistribution(accounts, availableMoney - amountToDeposit, bestDistribution);
                account.withdrawMoney(amountToDeposit);

                if (compareDistributions(accounts, bestDistribution, distribution) == -1)
                {
                    bestDistribution = distribution;
                }
            }
        }

        return bestDistribution;
    }

    /**
     * copies all given accounts
     * @param accounts - the given list of accounts
     * @return the list of copies
     */
    private List<Account> getCopies(List<Account> accounts)
    {
        List<Account> accountCopies=new ArrayList<>();

        for(Account account:accounts)
        {
            Account copyAccount=new BankAccount(account.getAccountType(),account.getExpirationDate(),account.getInterestInformation(),account.getAvailableAmount());
            accountCopies.add(copyAccount);
        }

        return accountCopies;
    }
}
