package reader;

import account.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class BankAccountReader extends AbstractReader
{
    /**
     * converts the line into an Account
     * @param line - the line to convert
     * @return an Account
     */
    @Override
    public Account getAccountFromLine(String line)
    {
        String[] information=line.split(";");

        float amount=Float.parseFloat(information[4]);

        DateTimeFormatter formatter=DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDate expirationDate=LocalDate.parse(information[3],formatter);

        AccountType type=AccountType.valueOf(information[0]);

        Map<IntervalInterface,Float> interestInformation=new HashMap<>();
        String[] rates=information[1].split(" ");
        String[] intervals=information[2].split(" ");

        for(int i=0;i<intervals.length;i++)
        {
            float rate=Float.parseFloat(rates[i]);
            float intervalStart=Float.parseFloat(intervals[i].split("-")[0]);
            float intervalEnd=Float.parseFloat(intervals[i].split("-")[1]);
            IntervalInterface interval=new Interval(intervalStart,intervalEnd);

            interestInformation.put(interval,rate);
        }

        return new BankAccount(type,expirationDate,interestInformation,amount);
    }
}
