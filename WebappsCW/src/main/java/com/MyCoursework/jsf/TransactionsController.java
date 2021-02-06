package com.MyCoursework.jsf;

import com.MyCoursework.ejb.TransactionDao;
import com.MyCoursework.entity.Transactions;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

@ViewScoped
@Named("transactionsCtlr")
public class TransactionsController implements Serializable{
    
    @Inject
    private SessionBean sessionBean;
    
    @EJB
    private TransactionDao tranService;
    
    private List<Transactions> transactions;
    
    public TransactionsController(){
        
    }
    
    @PostConstruct
    public void init(){
        long userId = sessionBean.getUser().getId();
        if(sessionBean.getUser().getType().equals("User")){
            transactions = tranService.readByUser(userId);
        }
        else{
            transactions = tranService.read();
        }
    }

    public List<Transactions> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transactions> transactions) {
        this.transactions = transactions;
    }
    
    public Long getUserId(){
        return sessionBean.getUser().getId();
    }
    
    /**
     * Determinates the text to display, related to the type of transaction.
     * I.E. if I send money, that record is shown as "send money", but the 
     * same record for the other user is shown as "receive money"
     * @param idTrans
     * @return 
     */
    public String typeTransaction(long idTrans){
        String type = "";
        for(Transactions t : transactions){
            if(t.getId() == idTrans){
                if(t.getType() == 1){ 
                    if (t.getUserSend().compareTo( getUserId()) == 0){
                        type = "Send Money"; 
                    }
                    else{
                        type = "Receive Money"; 
                    }
                }//Someone send money to other
                else{
                     type = "Request money"; 
                }
                break;
            }
        }
        
        return type;
    }
    
    /**
     * In a same table's column show the information of who send/receive the
     * transaction.
     * @param idTrans
     * @return 
     */
    public long tofromUser(long idTrans){
        long idUser = 0;
        for(Transactions t : transactions){
            if(t.getId().equals(idTrans)){
                
                if(t.getType() == 1){
                    if(t.getUserSend().equals(getUserId())){
                        idUser = t.getUserReceive();
                    }
                    else{
                         idUser = t.getUserSend();
                    }
                }
                else{
                    if(t.getUserSend().equals(getUserId())){
                        idUser = t.getUserReceive();
                    }
                    else{
                         idUser = t.getUserSend();
                    }
                }
                break;
            }
        }
        
        return idUser;
    }
    
    public String toStatusString(int status){
        String strStatus = "";
        switch(status){
            case 1:
                strStatus = "Pendent";
                break;
            case 2:
                strStatus = "Completed";
                break;
            case 3:
                strStatus = "Rejected";
                break;
        }
        
        return strStatus;
    }
    
    /**
     * Determines if the user logged in is admin or normal user. This helps to
     * show or hide columns
     * @return 
     */
    public boolean isShowedForAdmin(){
     return sessionBean.getUser().getType().equals("Admin");
    }
    
}
