package com.MyCoursework.jsf;

import com.MyCoursework.data.Operations;
import com.MyCoursework.ejb.UserDao;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

/**
 * This bean registers admin and normal users
 * 
 */
@Named
@RequestScoped
public class RegistrationBean {

    @EJB
    UserDao usrSrv;
    
    String username;
    String userpassword;
    String currency;

    public RegistrationBean() {

    }

    //call the injected EJB
    public String register() {
        //Convert 1000 GBP to user's currency
        String strAmount = Operations.convertCurrency("GBP", currency, 1000);
        double amount = Double.valueOf(strAmount);
        usrSrv.registerUser(username, userpassword, currency, amount, "User");
        return "index";
    }
    
    public String registerAdmin(){
        //GBP as default currency
        usrSrv.registerUser(username, userpassword, "GBP", 0, "Admin");
        return "main";
    }
    
    public UserDao getUsrSrv() {
        return usrSrv;
    }

    public void setUsrSrv(UserDao usrSrv) {
        this.usrSrv = usrSrv;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    
    /*public List<SystemUser> getUserList() {
        return usrSrv.getUserList();
    }*/
}
