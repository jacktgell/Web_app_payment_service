package com.MyCoursework.jsf;

import com.MyCoursework.data.Operations;
import com.MyCoursework.ejb.TransactionDao;
import com.MyCoursework.ejb.UserDao;
import com.MyCoursework.entity.SystemUser;
import com.MyCoursework.entity.Transactions;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@ViewScoped
@Named("mainCtlr")
public class MainController implements Serializable {

    @EJB
    private UserDao userService;
    
    @EJB
    private TransactionDao tranService;
    
    private String userName;
    
    private List<Transactions> transactions;
    
    @Inject
    private SessionBean sessionBean;
    
    private String message;
    
    
    public MainController(){
        //this.idUser = 0;
        this.userName = "";
    }
    
    @PostConstruct
    public void init(){
        
        //In case that visit main.xhtml first time
        if(sessionBean.getUser() == null){
            //Get the username  logged in
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            HttpServletRequest request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            this.userName = request.getUserPrincipal().getName();
            
            //Get all the user information
            SystemUser user = userService.getUserInfo(this.userName);
            //Is accessible the user information in a session bean
            sessionBean.setUser(user);
            
            //makes the username is accesible easly
            /*FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("USERNAME", this.userName);
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("IDUSER", user.getId());
            FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("CURRENCYUSER", user.getcurrency());*/
        }
         //In case that visit main.xhtml for second, third... time
        else{
            FacesContext.getCurrentInstance().getExternalContext().getSession(true);
            
            userName = sessionBean.getUser().getUsername();
            //update the balance every refresh main page
            sessionBean.getUser().setBalance(userService.getUserInfo(this.userName).getBalance());
        }
        
        //The logged in user is a normal  User?
        if(sessionBean.getUser().getType().equals("User")){
            getRequests();
        }
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public SystemUser getUser(){
        return sessionBean.getUser();
    }

    public List<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transactions> transactions) {
        this.transactions = transactions;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public void getRequests(){
        transactions = tranService.readRequestByUser(sessionBean.getUser().getId());
    }
    
    //Accept request
    public void acceptRequest(Transactions t){
        boolean transactionValid = true;
        SystemUser senderRequest = userService.getUserInfo(t.getUserSend());
        SystemUser receiverRequest = userService.getUserInfo(t.getUserReceive());
        
        String strConvertedAmount = Operations.convertCurrency(senderRequest.getCurrency(), 
                    receiverRequest.getCurrency(), t.getAmount());
        double convertedAmount = Double.valueOf(strConvertedAmount);
        
        if(convertedAmount > receiverRequest.getBalance()){
            message = "You do not have enough money";
            transactionValid = false;
        }
        
        if (transactionValid){
            double newBalance = userService.updateBalance(receiverRequest.getId(), (-1 * convertedAmount));
            userService.updateBalance(t.getUserSend(), (t.getAmount()));
            sessionBean.getUser().setBalance(newBalance);
            
            t.setStatus(2); //completed status
            tranService.updateStatus(t.getId(), 2);
            message = "Transaction was succesfully";
        }
        
        getRequests();
    }
    
    //Reject request
    public void rejectRequest(Transactions t){
        tranService.updateStatus(t.getId(), 3); //rejected status
        message = "Request rejected";
        getRequests();
    }
    
    //Close session
    public void exit(){
        try {
            String path = FacesContext.getCurrentInstance().getExternalContext().getRequestContextPath();
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            FacesContext.getCurrentInstance().getExternalContext().redirect(path + "/faces/index.xhtml");
        } catch (IOException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
