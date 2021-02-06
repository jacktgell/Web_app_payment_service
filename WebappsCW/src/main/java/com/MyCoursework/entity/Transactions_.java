package com.MyCoursework.entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2020-04-11T17:22:11")
@StaticMetamodel(Transactions.class)
public class Transactions_ {
    
    public static volatile SingularAttribute<SystemUser, Long> id;
    public static volatile SingularAttribute<SystemUser, Long> userSend;
    public static volatile SingularAttribute<SystemUser, Long> userReceive;
    public static volatile SingularAttribute<SystemUser, Double> amount;
    public static volatile SingularAttribute<SystemUser, String> currency;
    public static volatile SingularAttribute<SystemUser, Integer> status;
    public static volatile SingularAttribute<SystemUser, Date> created;
}
