package reader;

import account.Account;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class AbstractReader implements Reader
{
    /**
     * reads a file that contains information.
     * @param fileName - the name of the file.
     * @return a list of Accounts with the information parsed from the file
     */
    @Override
    public List<Account> readAccounts(String fileName)
    {
        List<Account> list=new ArrayList<>();
        File file=new File(fileName);

        try
        {
            BufferedReader bufferedReader=new BufferedReader(new FileReader(file));

            String line="";

            while((line=bufferedReader.readLine())!=null)
            {
                list.add(getAccountFromLine(line));
            }

            bufferedReader.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * converts the line into an Account
     * @param line - the line to convert
     * @return an Account
     */
    public abstract Account getAccountFromLine(String line);
}
