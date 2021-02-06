package com.MyCoursework.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Transactions implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NotNull
    private Long userSend;

    @NotNull
    private Long userReceive;

    @NotNull
    private double amount;
    
    @NotNull
    private String currency;
    
    @NotNull
    private int type;

    @NotNull
    private int status;

    @NotNull
    private Date created;

    public Transactions() {

    }

    public Transactions(Long userSend, Long userReceive, double amount, String currency, int type, int status, Date created, Date updated) {
        this.userSend = userSend;
        this.userReceive = userReceive;
        this.amount = amount;
        this.currency = currency;
        this.type = type;
        this.status = status;
        this.created = created;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserSend() {
        return userSend;
    }

    public void setUserSend(Long userSend) {
        this.userSend = userSend;
    }

    public Long getUserReceive() {
        return userReceive;
    }

    public void setUserReceive(Long userReceive) {
        this.userReceive = userReceive;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
    
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    

    @Override
    public int hashCode() {
        System.out.println("system user 12");
        int hash = 5;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.userSend);
        hash = 97 * hash + Objects.hashCode(this.userReceive);
        hash = 97 * hash + Objects.hashCode(this.amount);
        hash = 97 * hash + Objects.hashCode(this.currency);
        hash = 97 * hash + Objects.hashCode(this.type);
        hash = 97 * hash + Objects.hashCode(this.status);
        hash = 97 * hash + Objects.hashCode(this.created);
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
        final Transactions other = (Transactions) obj;
        
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.userSend, other.userSend)) {
            return false;
        }
        if (!Objects.equals(this.userReceive, other.userReceive)) {
            return false;
        }
        if (!Objects.equals(this.amount, other.amount)) {
            return false;
        }
        if (!Objects.equals(this.currency, other.currency)) {
            return false;
        }
        if (!Objects.equals(this.type, other.type)) {
            return false;
        }
        if (!Objects.equals(this.status, other.status)) {
            return false;
        }
        return Objects.equals(this.created, other.created);
    }

}
