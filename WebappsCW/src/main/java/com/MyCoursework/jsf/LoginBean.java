package com.MyCoursework.jsf;

import com.MyCoursework.ejb.UserDao;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;

@Named
@RequestScoped
public class LoginBean {

    @EJB
    UserDao usrSrv;
    
    String username;
    String userpassword;
    String currency;
    String type;

    public LoginBean() {
    }

    //call the injected EJB
    public String register() {
        usrSrv.registerUser(username, userpassword, currency, 1000.0, type);
        return "index";
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
    
    /**
    public List<SystemUser> getUserList() {
        return usrSrv.getUserList();
    }*/
}
