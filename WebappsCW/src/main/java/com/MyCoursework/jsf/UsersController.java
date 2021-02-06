package com.MyCoursework.jsf;

import com.MyCoursework.ejb.UserDao;
import com.MyCoursework.entity.SystemUser;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

@ViewScoped
@Named("usersCtlr")
public class UsersController implements Serializable {
    
    @EJB
    private UserDao userService;
    
    private List<SystemUser> users;
     
    public UsersController(){
        
    }
    
    @PostConstruct
    public void init(){
        users = userService.read();
    }
    
    public List<SystemUser> getUsers() {
        return users;
    }

    public void setUsers(List<SystemUser> users) {
        this.users = users;
    }
}
