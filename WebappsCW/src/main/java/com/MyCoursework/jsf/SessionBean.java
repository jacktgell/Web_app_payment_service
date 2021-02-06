package com.MyCoursework.jsf;

import com.MyCoursework.entity.SystemUser;
import java.io.Serializable;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 * This allows that the user information logged in, is available
 * 
 */
@SessionScoped
@Named("sessionBean")
public class SessionBean implements Serializable{
    
    private SystemUser user;
    
    public SessionBean(){
        
    }

    public SystemUser getUser() {
        return user;
    }

    public void setUser(SystemUser user) {
        this.user = user;
    }
    
    
    
}
