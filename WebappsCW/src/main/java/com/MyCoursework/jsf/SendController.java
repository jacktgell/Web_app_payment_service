package com.MyCoursework.jsf;

import com.MyCoursework.data.Operations;
import com.MyCoursework.ejb.TransactionDao;
import com.MyCoursework.ejb.UserDao;
import com.MyCoursework.entity.SystemUser;
import com.MyCoursework.entity.Transactions;
import java.io.Serializable;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;


@ViewScoped
@Named("sendCtlr")
public class SendController implements Serializable{
    
    @EJB
    private UserDao userService;
    
    @EJB
    private TransactionDao tranService;
    
    @Inject
    private SessionBean sessionBean;
    
    private String userToSend;
    private double amount;
    private SystemUser user;
    private String message;
    
    public SendController(){
        message = "";
    }
    
    public void send(){
        message = "";
        long senderUserId = sessionBean.getUser().getId();
        boolean transactionValid = true;
        
        if(this.userToSend.trim().equals("")){
            message = "The field user shouldn't be empty";
            transactionValid = false;
        }
        
        if(this.userToSend.equals(sessionBean.getUser().getUsername())){
             message = "You can't send money to your self";
            transactionValid = false;
        }
        
        if(amount <= 0){
            message = "The amount must be greather than 0";
            transactionValid = false;
        }
        
        double balanceUser = userService.getUserBalance(senderUserId);
        
        if(amount > balanceUser){
            message = "You do not have enough money";
            transactionValid = false;
        }
        
        user = userService.getUserInfo(this.userToSend);
        if(user == null){
            message = "The user does not exist";
            transactionValid = false;
        }
        
        if (transactionValid){
            
            //Convert the currency
            String strConvertedAmount = Operations.convertCurrency(sessionBean.getUser().getCurrency(), 
                    user.getCurrency(), amount);
            double convertedAmount = Double.valueOf(strConvertedAmount);
            
            //Update the balance
            double newBalance = userService.updateBalance(senderUserId, (-1 * amount));
            userService.updateBalance(user.getId(), (convertedAmount));
            sessionBean.getUser().setBalance(newBalance);
            
            //Register the transaction
            Transactions t = new Transactions();
            t.setUserSend(senderUserId);
            t.setUserReceive(user.getId());
            t.setAmount(amount);
            t.setCurrency(sessionBean.getUser().getCurrency());
            t.setType(1); //Send money
            t.setStatus(2); //completed status
            
            tranService.register(t);
            
            message = "Transaction was succesfully";
        }
        
        
        FacesContext.getCurrentInstance().getPartialViewContext().getRenderIds().add("frmSend:olbMessage");
    }

    public String getUserToSend() {
        return userToSend;
    }

    public void setUserToSend(String userToSend) {
        this.userToSend = userToSend;
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
    
    
}
