package com.MyCoursework.ejb;

import com.MyCoursework.data.Operations;
import com.MyCoursework.entity.Transactions;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Date;

/**
 * Is the implementation of DAO (or closest to)
 */
@Stateless
public class TransactionDao {

    @PersistenceContext(unitName = "WebappsPU")
    EntityManager em;

    public TransactionDao() {

    }

    public Transactions read(Long id) {
        Transactions e = em.find(Transactions.class, id);
        return e;
    }

    public List<Transactions> read() {
        Query query = em.createNativeQuery("SELECT * FROM APP.TRANSACTIONS", Transactions.class);

        List<Transactions> results;

        try {
            results = query.getResultList();
        } catch (javax.persistence.NoResultException ex) {
            return new ArrayList<>();
        }

        return results;
    }

    public List<Transactions> readByUser(Long id) {
        Query query = em.createNativeQuery("SELECT DISTINCT * FROM APP.TRANSACTIONS WHERE USERSEND = ?1 OR USERRECEIVE = ?2", Transactions.class);
        query.setParameter(1, id);
        query.setParameter(2, id);

        List<Transactions> results;

        try {
            results = query.getResultList();
        } catch (javax.persistence.NoResultException ex) {
            return new ArrayList<>();
        }

        return results;
    }

    public List<Transactions> readRequestByUser(Long id) {
        Query query = em.createNativeQuery("SELECT DISTINCT * FROM APP.TRANSACTIONS WHERE USERRECEIVE = ?1 AND TYPE = 2 AND status = 1", Transactions.class);
        query.setParameter(1, id);

        List<Transactions> results;

        try {
            results = query.getResultList();
        } catch (javax.persistence.NoResultException ex) {
            return new ArrayList<>();
        }

        return results;
    }

    public boolean register(Transactions t) {
        System.out.println("Creating transaction");
        Date ts = Operations.getCurrentTimestamp();
        t.setCreated(ts);
        em.persist(t);
        em.flush();
        return true;
    }

    public boolean update(Transactions t) {

        //In this applications, only is required update the status
        Transactions e = em.find(Transactions.class, t.getId());
        //e.setUserSend(t.getUserSend());
        //e.setUserReceive(t.getUserReceive());
        //e.setAmount(t.getAmount());
        //e.setCurrency(t.getCurrency());
        //e.setType(t.getType());
        e.setStatus(t.getStatus());
        //e.setCreated(t.getCreated());
        em.persist(e);
        em.flush();
        return true;
    }

    public void updateStatus(long id, int status) {
        Transactions e = em.find(Transactions.class, id);
        e.setStatus(status);
        em.persist(e);
        em.flush();

    }
}
