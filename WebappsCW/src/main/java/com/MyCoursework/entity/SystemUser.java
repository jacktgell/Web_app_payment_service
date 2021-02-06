package com.MyCoursework.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 *
 * @author parisis
 */
@Entity
public class SystemUser implements Serializable {

    
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @NotNull
    String username;
    
    @NotNull
    String userpassword;

    @NotNull
    String currency;
    
    @NotNull
    double balance;
    
    @NotNull
    String type;

    public SystemUser() {
    }

    public SystemUser(String username, String userpassword, String currency, double balance, String type) {
        this.username = username;
        this.userpassword = userpassword;
        this.currency = currency;
        this.balance = balance;
        this.type = type;
        System.out.println("system user 1");
    }

    public Long getId() {
        System.out.println("system user 2");
        return id;
    }

    public void setId(Long id) {
        System.out.println("system user 3");
        this.id = id;
    }

    public String getUsername() {
        System.out.println("system user 4");
        return username;
    }

    public void setUsername(String username) {
        System.out.println("system user 5");
        this.username = username;
    }

    public double getBalance() {
        System.out.println("system user 6");
        return balance;
    }

    public void setBalance(double balance) {
        System.out.println("system user 7");
        this.balance = balance;
    }

    public String getUserpassword() {
        System.out.println("system user 8");
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        System.out.println("system user 9");
        this.userpassword = userpassword;
    }
    
    public String getCurrency() {
        System.out.println("system user 10");
        return currency;
    }

    public void setCurrency(String currency) {
        System.out.println("system user 11");
        this.currency = currency;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public int hashCode() {
        System.out.println("system user 12");
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.username);
        hash = 97 * hash + Objects.hashCode(this.userpassword);
        hash = 97 * hash + Objects.hashCode(this.currency);
        hash = 97 * hash + Objects.hashCode(this.balance);
        hash = 97 * hash + Objects.hashCode(this.type);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        System.out.println("system user 13");
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final SystemUser other = (SystemUser) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        if (!Objects.equals(this.userpassword, other.userpassword)) {
            return false;
        }
        if (!Objects.equals(this.balance, other.balance)) {
            return false;
        }
        if (!Objects.equals(this.currency, other.currency)) {
            return false;
        }
        return Objects.equals(this.type, other.type);
    }
}
