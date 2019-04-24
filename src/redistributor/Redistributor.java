package redistributor;

import account.Account;

import java.util.List;

public interface Redistributor
{
    /**
     * redistributes money between accounts so that the interests give maximum profits
     * @param accounts - the list of accounts
     * @return the list that contains the accounts with redistributed amounts of money
     */
    List<Account> redistributeMoney(List<Account> accounts);
}
