package com.MyCoursework.ejb;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
 
/**
 * This singleton is the responsible for create the first admin account
 */
@Singleton
@Startup
public class StartupBean {
    
    @EJB
    UserDao usrSrv;
    
    @PostConstruct
    public void init() {
        
        if (usrSrv.getUserInfo("admin1") == null){
            usrSrv.registerUser("admin1", "admin1", "GBP", 0, "Admin");
        }
    }
}
