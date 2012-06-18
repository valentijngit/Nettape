package net.nettape.dal.object;
// Generated 11-jan-2012 20:37:06 by Hibernate Tools 3.4.0.CR1


import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Example;

/**
 * Home object for domain model class ReluserAdgroup.
 * @see net.nettape.dal.object.ReluserAdgroup
 * @author Hibernate Tools
 */
public class ReluserAdgroupHome {

    private static final Log log = LogFactory.getLog(ReluserAdgroupHome.class);

    private final SessionFactory sessionFactory = getSessionFactory();
    
    protected SessionFactory getSessionFactory() {
        try {
            return (SessionFactory) new InitialContext().lookup("SessionFactory");
        }
        catch (Exception e) {
            log.error("Could not locate SessionFactory in JNDI", e);
            throw new IllegalStateException("Could not locate SessionFactory in JNDI");
        }
    }
    
    public void persist(ReluserAdgroup transientInstance) {
        log.debug("persisting ReluserAdgroup instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }
    
    public void attachDirty(ReluserAdgroup instance) {
        log.debug("attaching dirty ReluserAdgroup instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(ReluserAdgroup instance) {
        log.debug("attaching clean ReluserAdgroup instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void delete(ReluserAdgroup persistentInstance) {
        log.debug("deleting ReluserAdgroup instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public ReluserAdgroup merge(ReluserAdgroup detachedInstance) {
        log.debug("merging ReluserAdgroup instance");
        try {
            ReluserAdgroup result = (ReluserAdgroup) sessionFactory.getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
    public ReluserAdgroup findById( java.lang.Integer id) {
        log.debug("getting ReluserAdgroup instance with id: " + id);
        try {
            ReluserAdgroup instance = (ReluserAdgroup) sessionFactory.getCurrentSession()
                    .get("net.nettape.dal.object.ReluserAdgroup", id);
            if (instance==null) {
                log.debug("get successful, no instance found");
            }
            else {
                log.debug("get successful, instance found");
            }
            return instance;
        }
        catch (RuntimeException re) {
            log.error("get failed", re);
            throw re;
        }
    }
    
    public List findByExample(ReluserAdgroup instance) {
        log.debug("finding ReluserAdgroup instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("net.nettape.dal.object.ReluserAdgroup")
                    .add(Example.create(instance))
            .list();
            log.debug("find by example successful, result size: " + results.size());
            return results;
        }
        catch (RuntimeException re) {
            log.error("find by example failed", re);
            throw re;
        }
    } 
}

