package util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Logger
{
    DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    Date date = new Date();

    public Logger(Object obj, String msg)
    {
        System.out.println("[" + dateFormat.format(date)+"]"+obj.getClass()+": " + msg);
    }
}
