package reader;

import account.Account;

import java.util.List;

public interface Reader
{
    /**
     * reads a file that contains information.
     * @param fileName - the name of the file.
     * @return a list of Accounts with the information parsed from the file
     */
    List<Account> readAccounts(String fileName);

    /**
     * converts the line into an Account
     * @param line - the line to convert
     * @return an Account
     */
    Account getAccountFromLine(String line);
}
