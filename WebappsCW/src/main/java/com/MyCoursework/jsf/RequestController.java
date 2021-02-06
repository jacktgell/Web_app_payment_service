package com.MyCoursework.jsf;

import com.MyCoursework.ejb.TransactionDao;
import com.MyCoursework.ejb.UserDao;
import com.MyCoursework.entity.SystemUser;
import com.MyCoursework.entity.Transactions;
import java.io.Serializable;
import java.util.Date;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Controls the view for do requests
 * 
 */
@ViewScoped
@Named("RequestCtlr")
public class RequestController implements Serializable {
    
    @EJB
    private UserDao userService;
    
    @EJB
    private TransactionDao tranService;
    
    @Inject
    private SessionBean sessionBean;
    
    private String userToRequest;
    private double amount;
    private SystemUser user;
    private String message;
    
    public RequestController(){
        this.message = "";
    }

    public String getUserToRequest() {
        return userToRequest;
    }

    public void setUserToRequest(String userToRequest) {
        this.userToRequest = userToRequest;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public void request(){
        message = "";
        long userId = sessionBean.getUser().getId();
        String currency = sessionBean.getUser().getCurrency();
        boolean transactionValid = true;
        
        if(this.userToRequest.trim().equals("")){
            message = "The field user shouldn't be empty";
            transactionValid = false;
        }
        
        if(this.userToRequest.equals(sessionBean.getUser().getUsername())){
             message = "You can't request money to your self";
            transactionValid = false;
        }
        
        if(amount <= 0){
            message = "The amount must be greather than 0";
            transactionValid = false;
        }
        
        user = userService.getUserInfo(this.userToRequest);
        if(user == null){
            message = "The user does not exist";
            transactionValid = false;
        }
        
        if (transactionValid){
            Transactions t = new Transactions();
            t.setUserSend(userId);
            t.setUserReceive(user.getId());
            t.setAmount(amount);
            t.setCurrency(currency);
            t.setType(2); //Request
            t.setStatus(1); //Pending status
            t.setCreated(new Date());
            tranService.register(t);
            
            message = "Transaction was succesfully";
        }
        
        //Allows that the messages is displayed
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("frmRequest:olbMessage");
    }
    
}
