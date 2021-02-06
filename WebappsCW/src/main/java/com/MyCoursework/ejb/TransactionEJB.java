package com.MyCoursework.ejb;

import com.MyCoursework.entity.ExerciseEntity;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import static javax.ejb.TransactionAttributeType.REQUIRED;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@TransactionAttribute(REQUIRED)
public class TransactionEJB {

    @PersistenceContext(unitName = "WebappsPU")
    EntityManager em;

    public int read(long id) {
        ExerciseEntity e = em.find(ExerciseEntity.class, id);
        return e.getRecordValue();
    }

    public void write(long id, int value) {
        ExerciseEntity e = em.find(ExerciseEntity.class, id);
        e.setRecordValue(value);
        em.persist(e);
        em.flush();
    }

}
