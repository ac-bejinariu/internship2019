package launcher;

import account.Account;
import reader.BankAccountReader;
import reader.Reader;
import redistributor.MoneyRedistributor;
import redistributor.Redistributor;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        Reader reader=new BankAccountReader();
        List<Account> accounts=reader.readAccounts("src/reader/files/bank_accounts.txt");

        Redistributor redistributor=new MoneyRedistributor();
        List<Account> newAccounts=redistributor.redistributeMoney(accounts);

        int numberOfMonths=39;

        for(Account account:newAccounts)
        {
            System.out.print("Amount of money stored in the " + account.getAccountType() + " account: ");
            System.out.println(account.getAvailableAmount());

            System.out.print("The profit after "+ numberOfMonths +" months: ");
            System.out.println(account.getProfitAfterMonths(numberOfMonths));

            System.out.println();
        }
    }
}
