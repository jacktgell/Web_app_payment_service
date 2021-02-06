package com.MyCoursework.ejb;

import com.MyCoursework.entity.SystemUser;
import com.MyCoursework.entity.SystemUserGroup;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * The closer DAO implementation
 */
@Stateless
public class UserDao{

    @PersistenceContext
    EntityManager em;
    //Query q = em.createNativeQuery("username varchar(255) NOT NULL,userpassword varchar(255) NOT NULL,currency varchar(255) NOT NULL,balance double NOT NULL").executeUpdate();
    public UserDao() {
        
    }
    
    
    public boolean registerUser(String username, String userpassword, String currency, double balance, String type) {
        
        Query query = em.createNativeQuery("SELECT USERNAME FROM APP.SYSTEMUSER");
        List results = query.getResultList();
        System.out.println("userservice 1");
            
        try {
            System.out.println("userservice 2");
            
            if(results.contains(username)){
                System.out.println("make a unique username");
            }else{
                System.out.println("userservice 3");
                //If mistake, select GBP as default currency
                if(!currency.equals("USD") && !currency.equals("EUR"))
                    currency = "GBP";

                SystemUser sys_user;
                SystemUserGroup sys_user_group;
                System.out.println("userservice 4");

                MessageDigest md = MessageDigest.getInstance("SHA-256");
                String passwd = userpassword;
                md.update(passwd.getBytes("UTF-8"));
                byte[] digest = md.digest();
                StringBuffer sb = new StringBuffer();
                System.out.println("userservice 5");
                for (int i = 0; i < digest.length; i++) {
                    sb.append(Integer.toString((digest[i] & 0xff) + 0x100, 16).substring(1));
                }
                System.out.println("userservice 6");
                String paswdToStoreInDB = sb.toString();

                // apart from the default constructor which is required by JPA
                // you need to also implement a constructor that will make the following code succeed
                sys_user = new SystemUser(username, paswdToStoreInDB, currency, balance, type);
                sys_user_group = new SystemUserGroup(username, "users");
                System.out.println("userservice 7");
                
                em.persist(sys_user);
                em.persist(sys_user_group);
                System.out.println("userservice 8");
            }
        }   catch (UnsupportedEncodingException | NoSuchAlgorithmException ex) {
                System.out.println("userservice catched");
                Logger.getLogger(UserDao.class.getName()).log(Level.SEVERE, null, ex);
                return false;
        }
        System.out.println("userservice 4");
        return true;
    }
    
    public List<SystemUser> read() {
        List<SystemUser> results;
        Query query = em.createNativeQuery("SELECT ID, USERNAME, BALANCE, CURRENCY, TYPE FROM APP.SYSTEMUSER", SystemUser.class);
        
        try{
            results = query.getResultList();
        }
        catch(javax.persistence.NoResultException ex){
            return new ArrayList<>();
        }
        
        return results;
    }
    
    public SystemUser getUserInfo(String username){
        System.out.println("Getting user "  + username +  " information");
         
        Query query = em.createNativeQuery("SELECT ID, USERNAME, BALANCE, CURRENCY, TYPE FROM APP.SYSTEMUSER WHERE USERNAME = ?1", SystemUser.class);
        query.setParameter(1, username);
        
        SystemUser user;
        try{
            user = (SystemUser)query.getSingleResult();
        }
        catch(javax.persistence.NoResultException ex){
            System.out.println("User not founded");
            return null;
        }
        
       
        System.out.println(user.getUsername());
        System.out.println(user.getId());
        System.out.println(user.getBalance());
        System.out.println(user.getCurrency());
        return user;
    }
    
    public SystemUser getUserInfo(Long id){
        System.out.println("Getting user information with id "  + id);
         
        Query query = em.createNativeQuery("SELECT ID, USERNAME, BALANCE, CURRENCY, TYPE FROM APP.SYSTEMUSER WHERE ID = ?1", SystemUser.class);
        query.setParameter(1, id);
        
        SystemUser user;
        try{
            user = (SystemUser)query.getSingleResult();
        }
        catch(javax.persistence.NoResultException ex){
            System.out.println("User not founded");
            return null;
        }
        
       
        System.out.println(user.getUsername());
        System.out.println(user.getId());
        System.out.println(user.getBalance());
        System.out.println(user.getCurrency());
        return user;
    }
    
    
    public double getUserBalance(Long id){
        System.out.println("Getting blance of the user with id:"  + id);
        
        Query query = em.createNativeQuery("SELECT ID, USERNAME, BALANCE, CURRENCY, TYPE FROM APP.SYSTEMUSER WHERE ID = ?1", SystemUser.class);
        query.setParameter(1, id);
        SystemUser user = (SystemUser)query.getSingleResult();
        return user.getBalance();
    }
    
    public boolean update(SystemUser u){
        SystemUser user = em.find(SystemUser.class, u.getId());
        
        user.setUsername(u.getUsername());
        user.setUserpassword(u.getUserpassword());
        user.setCurrency(u.getCurrency());
        user.setBalance(u.getBalance());
        user.setType(u.getType());
        
        em.persist(user);
        em.flush();
        return true;        
    }
    
    public double updateBalance(Long id, double amount){
        SystemUser user = em.find(SystemUser.class, id);
        user.setBalance(user.getBalance() + amount);
        em.persist(user);
        em.flush();
        
        return user.getBalance();
    }
}












