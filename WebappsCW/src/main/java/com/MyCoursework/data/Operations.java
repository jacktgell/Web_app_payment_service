package com.MyCoursework.data;

import com.MyCoursework.thrift.Server;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.ClientBuilder;
import static javax.ws.rs.core.MediaType.TEXT_PLAIN;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

public class Operations {

    /**
     * Consumes the ws that converts currency
     * @param currency1
     * @param currency2
     * @param amount
     * @return 
     */
    public static String convertCurrency(String currency1, String currency2, double amount) {

        ExternalContext externalContext = (ExternalContext) FacesContext.getCurrentInstance().getExternalContext();
        String contextPath = ((ServletContext) externalContext.getContext()).getContextPath();
        StringBuffer requestURL = ((HttpServletRequest) externalContext.getRequest()).getRequestURL();

        int i = requestURL.indexOf(contextPath);
        String address = requestURL.substring(0, i);
        String realPath = address + contextPath;

        String url = realPath + "/rest/conversion/" + currency1 + "/" + currency2 + "/" + amount;

        try {
            String response
                    = ClientBuilder.newClient()
                            .target(
                                    URI.create(new URL(url).toExternalForm()))
                            .request(TEXT_PLAIN)
                            .get(String.class);
            return response;
        } catch (MalformedURLException ex) {
            Logger.getLogger(Operations.class.getName()).log(Level.SEVERE, null, ex);
        }

        return null;
    }
    
    /**
     * Request the current timestamp to thrief server
     * @return 
     */
    public static Date getCurrentTimestamp(){
        String strDate = "1990-01-01 00:00:00";
        Date ts;
        TTransport transport;

        try {
            transport = new TSocket("localhost", 10000);
            transport.open();

            TProtocol protocol = new TBinaryProtocol(transport);
            Server.Client client = new Server.Client(protocol);

            strDate = client.getTimeStamp();

            transport.close();

        } catch (TException x) {
            /*Date date = Calendar.getInstance().getTime();
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");
            strDate = dateFormat.format(date);*/
            //strDate = "1987-08-23 10:45:00";
            //Thrieft service is down
        }
        
        try {
            ts = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss").parse(strDate);
        } catch (ParseException ex) {
            ts = Calendar.getInstance().getTime(); 
        }
        
        return ts;
    }
}
