package com.mycoursework.thriftimplementation;

import com.mycoursework.thrifttsserver.Server;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import org.apache.thrift.TException;

public class TimeStampImplementation implements Server.Iface {

    @Override
    public String getTimeStamp() throws TException {
        Date date = Calendar.getInstance().getTime();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
        String strDate = dateFormat.format(date);
        System.err.println("Timestamp requested : " + strDate);
        return strDate;
    }
    
}
