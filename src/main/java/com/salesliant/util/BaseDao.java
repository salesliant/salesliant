package com.salesliant.util;

import com.salesliant.client.Config;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.metamodel.SingularAttribute;
import org.apache.commons.beanutils.BeanUtils;

public class BaseDao<T> {

    private final Class<T> voClass;
    private EntityManager em;
    private String error;

    enum ActionType {
        LOAD, CREATE, UPDATE, DELETE
    };

    public BaseDao(Class<T> clazz) {
        this.voClass = clazz;
    }

    public List<T> read() {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        cq.select(root);
        TypedQuery<T> q = em.createQuery(cq);
        List<T> list = q.getResultList();
        return list;
    }

    public List<T> read(SingularAttribute sa, Object obj) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        cq.select(root);
        cq.where(root.get(sa).in(obj));
        TypedQuery<T> q = em.createQuery(cq);
        List<T> list = q.getResultList();
        return list;
    }

    public List<T> read(SingularAttribute sa1, Object obj1, SingularAttribute sa2, Object obj2) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        cq.select(root);
        cq.where(root.get(sa1).in(obj1), root.get(sa2).in(obj2));
        TypedQuery<T> q = em.createQuery(cq);
        List<T> list = q.getResultList();

        return list;
    }

    public List<T> read(SingularAttribute sa1, Object obj1, SingularAttribute sa2, Object obj2, SingularAttribute sa3, Object obj3) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        cq.select(root);
        cq.where(root.get(sa1).in(obj1), root.get(sa2).in(obj2), root.get(sa3).in(obj3));
        TypedQuery<T> q = em.createQuery(cq);
        List<T> list = q.getResultList();
        return list;
    }

    public List<T> read1or2(SingularAttribute sa1, Object obj1, SingularAttribute sa2, Object obj2) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        cq.select(root);
        Predicate predicate1 = builder.equal(root.get(sa1), obj1);
        Predicate predicate2 = builder.equal(root.get(sa2), obj2);
        Predicate finalPredicate = builder.or(predicate1, predicate2);
        cq.where(finalPredicate);
        TypedQuery<T> q = em.createQuery(cq);
        List<T> list = q.getResultList();
        return list;
    }

    public List<T> readOrderBy(SingularAttribute sa, String s) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        if (s.equals(AppConstants.ORDER_BY_ASC)) {
            cq.orderBy(builder.asc(root.get(sa)));
        }
        if (s.equals(AppConstants.ORDER_BY_DESC)) {
            cq.orderBy(builder.desc(root.get(sa)));
        }
        cq.select(root);
        TypedQuery<T> q = em.createQuery(cq);
        List<T> list = q.getResultList();
        return list;
    }

    public List<T> readOrderBy(SingularAttribute sa1, Object obj, SingularAttribute sa2, String s) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        if (s.equals(AppConstants.ORDER_BY_ASC)) {
            cq.orderBy(builder.asc(root.get(sa2)));
        }
        if (s.equals(AppConstants.ORDER_BY_DESC)) {
            cq.orderBy(builder.desc(root.get(sa2)));
        }
        cq.select(root);
        cq.where(root.get(sa1).in(obj));
        TypedQuery<T> q = em.createQuery(cq);
        List<T> list = q.getResultList();
        return list;
    }

    public List<T> readOrderBy(SingularAttribute sa1, Object obj1, SingularAttribute sa2, Object obj2, SingularAttribute sa3, String s) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        if (s.equals(AppConstants.ORDER_BY_ASC)) {
            cq.orderBy(builder.asc(root.get(sa3)));
        }
        if (s.equals(AppConstants.ORDER_BY_DESC)) {
            cq.orderBy(builder.desc(root.get(sa3)));
        }
        cq.select(root);
        cq.where(root.get(sa1).in(obj1), root.get(sa2).in(obj2));
        TypedQuery<T> q = em.createQuery(cq);
        List<T> list = q.getResultList();
        return list;
    }

    public List<T> readOrderByIsNotNull(SingularAttribute sa1, Object obj1, SingularAttribute sa2, SingularAttribute sa3, String s) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        if (s.equals(AppConstants.ORDER_BY_ASC)) {
            cq.orderBy(builder.asc(root.get(sa3)));
        }
        if (s.equals(AppConstants.ORDER_BY_DESC)) {
            cq.orderBy(builder.desc(root.get(sa3)));
        }
        cq.select(root);
        cq.where(root.get(sa1).in(obj1), root.get(sa2).isNotNull());
        TypedQuery<T> q = em.createQuery(cq);
        List<T> list = q.getResultList();
        return list;
    }

    public List<T> readOrderBy(SingularAttribute sa1, Object obj1, SingularAttribute sa2, Object obj2, SingularAttribute sa3, Object obj3, SingularAttribute sa4, String s) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        if (s.equals(AppConstants.ORDER_BY_ASC)) {
            cq.orderBy(builder.asc(root.get(sa4)));
        }
        if (s.equals(AppConstants.ORDER_BY_DESC)) {
            cq.orderBy(builder.desc(root.get(sa4)));
        }
        cq.select(root);
        cq.where(root.get(sa1).in(obj1), root.get(sa2).in(obj2), root.get(sa3).in(obj3));
        TypedQuery<T> q = em.createQuery(cq);
        List<T> list = q.getResultList();
        return list;
    }

    public List<T> readBetweenDate(SingularAttribute sa1, Object obj, SingularAttribute sa2, LocalDateTime fromDate, LocalDateTime toDate) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        cq.select(root);
        cq.where(builder.and(
                builder.between(root.get(sa2), Timestamp.valueOf(fromDate), Timestamp.valueOf(toDate)),
                builder.equal(root.get(sa1), obj))
        ).orderBy(
                builder.desc(root.get(sa2).as(Date.class))
        );
        TypedQuery<T> q = em.createQuery(cq);
        List<T> list = q.getResultList();
        return list;
    }

    public List<T> readBetweenDate(SingularAttribute sa1, Object obj1, SingularAttribute sa2, Object obj2, SingularAttribute sa3, LocalDateTime fromDate, LocalDateTime toDate) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        cq.select(root);
        cq.where(builder.and(
                builder.between(root.get(sa3), Timestamp.valueOf(fromDate), Timestamp.valueOf(toDate)),
                builder.equal(root.get(sa1), obj1),
                builder.equal(root.get(sa2), obj2))
        ).orderBy(
                builder.desc(root.get(sa3).as(Date.class))
        );
        TypedQuery<T> q = em.createQuery(cq);
        List<T> list = q.getResultList();
        return list;
    }

    public T find(SingularAttribute sa, Object obj) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        cq.select(root);
        cq.where(root.get(sa).in(obj));
        TypedQuery<T> q = em.createQuery(cq);
        List<T> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public boolean exists(SingularAttribute sa, Object obj) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        cq.select(root);
        Predicate condition = builder.equal(root.get(sa), obj);
        cq.where(condition);
        TypedQuery<T> q = em.createQuery(cq);
        return q.getResultList().size() > 0;
    }

    public boolean exists(SingularAttribute sa1, Object obj1, SingularAttribute sa2, Object obj2) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        cq.select(root);
        cq.where(root.get(sa1).in(obj1), root.get(sa2).in(obj2));
        TypedQuery<T> q = em.createQuery(cq);
        return q.getResultList().size() > 0;
    }

    public boolean exists(SingularAttribute sa1, Object obj1, SingularAttribute sa2, Object obj2, SingularAttribute sa3, Object obj3) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        cq.select(root);
        cq.where(root.get(sa1).in(obj1), root.get(sa2).in(obj2), root.get(sa3).in(obj3));
        TypedQuery<T> q = em.createQuery(cq);
        return q.getResultList().size() > 0;
    }

    public T find(SingularAttribute sa1, Object obj1, SingularAttribute sa2, Object obj2) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        cq.select(root);
        cq.where(root.get(sa1).in(obj1), root.get(sa2).in(obj2));
        TypedQuery<T> q = em.createQuery(cq);
        List<T> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public T find(SingularAttribute sa1, Object obj1, SingularAttribute sa2, Object obj2, SingularAttribute sa3, Object obj3) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        cq.select(root);
        cq.where(root.get(sa1).in(obj1), root.get(sa2).in(obj2), root.get(sa3).in(obj3));
        TypedQuery<T> q = em.createQuery(cq);
        List<T> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public T find(SingularAttribute sa1, String string, SingularAttribute sa2, Object obj) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = builder.createQuery(voClass);
        Root<T> root = cq.from(voClass);
        cq.select(root);
        cq.where(root.get(sa1).in(string), root.get(sa2).in(obj));
        TypedQuery<T> q = em.createQuery(cq);
        List<T> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public Number findMax(SingularAttribute sa1, Object obj1, SingularAttribute sa2) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Number> cq = builder.createQuery(Number.class);
        Root<T> root = cq.from(voClass);
        cq.select(builder.max(root.get(sa2)));
        cq.where(root.get(sa1).in(obj1));
        TypedQuery<Number> q = em.createQuery(cq);
        List<Number> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public Number findMaxNumber(SingularAttribute sa1, Object obj1, SingularAttribute sa2, Object obj2, SingularAttribute sa3) {
        try {
            em = Config.createEntityManager();
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<Number> cq = builder.createQuery(Number.class);
            Root<T> root = cq.from(voClass);
            cq.select(builder.max(root.get(sa3)));
            cq.where(root.get(sa1).in(obj1), root.get(sa2).in(obj2));
            TypedQuery<Number> q = em.createQuery(cq);
            List<Number> list = q.getResultList();
            if (list == null || list.isEmpty()) {
                return null;
            }
            return list.get(0);
        } catch (ClassCastException ex) {
        }
        return null;
    }

    public Number findMaxNumber(SingularAttribute sa1, Object obj1, SingularAttribute sa2) {
        try {
            em = Config.createEntityManager();
            CriteriaBuilder builder = em.getCriteriaBuilder();
            CriteriaQuery<Number> cq = builder.createQuery(Number.class);
            Root<T> root = cq.from(voClass);
            cq.select(builder.max(root.get(sa2)));
            cq.where(root.get(sa1).in(obj1));
            TypedQuery<Number> q = em.createQuery(cq);
            List<Number> list = q.getResultList();
            if (list == null || list.isEmpty()) {
                return null;
            }
            return list.get(0);
        } catch (ClassCastException ex) {
        }
        return null;
    }

    public Timestamp findMinTimestamp(SingularAttribute sa1, Object obj, SingularAttribute sa2) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Timestamp> cq = builder.createQuery(Timestamp.class);
        Root<T> root = cq.from(voClass);
        cq.select(builder.min(root.get(sa2)));
        cq.where(root.get(sa1).in(obj));
        TypedQuery<Timestamp> q = em.createQuery(cq);
        List<Timestamp> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public Timestamp findMinTimestamp(SingularAttribute sa1, Object obj, SingularAttribute sa2, Integer id2, SingularAttribute sa3) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<Timestamp> cq = builder.createQuery(Timestamp.class);
        Root<T> root = cq.from(voClass);
        cq.select(builder.min(root.get(sa3)));
        cq.where(root.get(sa1).in(obj), root.get(sa2).in(id2));
        TypedQuery<Timestamp> q = em.createQuery(cq);
        List<Timestamp> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public Date findMinDate(SingularAttribute sa1, Object obj, SingularAttribute sa2) {
        em = Config.createEntityManager();
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<java.sql.Date> cq = builder.createQuery(java.sql.Date.class);
        Root<T> root = cq.from(voClass);
        cq.select(builder.min(root.get(sa2)));
        cq.where(root.get(sa1).in(obj));
        TypedQuery<java.sql.Date> q = em.createQuery(cq);
        List<java.sql.Date> list = q.getResultList();
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.get(0);
    }

    public T insert(T record) {
        error = null;
        try {
            em = Config.createEntityManager();
            em.getTransaction().begin();
            em.persist(record);
            em.getTransaction().commit();
            em.getEntityManagerFactory().getCache().evictAll();
            em.close();
        } catch (PersistenceException ex) {
            doExceptionRoutine(ActionType.CREATE);
            System.out.print(ex.getCause().toString());
        }
        return record;
    }

    public T delete(T record) {
        em = Config.createEntityManager();
        em.getTransaction().begin();
        Object id = em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(record);
        Object o = em.find(record.getClass(), id);
        em.merge(o);
        em.remove(o);
        em.getTransaction().commit();
        em.getEntityManagerFactory().getCache().evictAll();
        em.close();
        return record;
    }

    public T update(T record) {
        error = null;
        em = Config.createEntityManager();
        T obj = null;
        Method getVersionMethod = null;
        Method setVersionMethod = null;
        try {
            em.getTransaction().begin();
            obj = em.merge(record);
            em.getTransaction().commit();
            em.getEntityManagerFactory().getCache().evictAll();
        } catch (PersistenceException ex) {
            getExceptionCode(ex);
            return record;
        } finally {
            em.close();
        }
        try {
            getVersionMethod = record.getClass().getMethod("getVersion");
            setVersionMethod = record.getClass().getMethod("setVersion", Integer.class);
        } catch (NoSuchMethodException | SecurityException ignored) {
        } finally {
            if (getVersionMethod != null && setVersionMethod != null) {
                try {
                    Integer version = (Integer) getVersionMethod.invoke(obj);
                    setVersionMethod.invoke(record, version);
                } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException ignored) {
                }
            }
        }
        return obj;
    }

    private void getExceptionCode(Exception e) {
        error = e.getCause().toString();
        System.out.print(error);
    }

    public String getErrorMessage() {
        return error;
    }

    private void doExceptionRoutine(ActionType action) {
        String s = "";
        switch (action) {
            case CREATE:
                s = " creating ";
                break;
            case UPDATE:
                s = " updating ";
                break;
            case DELETE:
                s = " deleting ";
                break;
            case LOAD:
                s = " loading ";
                break;
            default:
                throw new IllegalArgumentException("Invalid action: " + action);
        }
        error = "An database exception has occurred while" + s + " the entity. \r\n" + "Please check the log to view the technical details.";
    }

    public Date getSystemDate() {
        em = Config.createEntityManager();
        Query query = em.createNativeQuery("SELECT CURRENT_TIMESTAMP");
        Timestamp dateItem = (Timestamp) query.getSingleResult();
        return dateItem;
    }

    protected void copyProperties(Object newObject, Object oldObject) {
        try {
            BeanUtils.copyProperties(newObject, oldObject);
        } catch (IllegalAccessException | InvocationTargetException ex) {
        }
    }
}
