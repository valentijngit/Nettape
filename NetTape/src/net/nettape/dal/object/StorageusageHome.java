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
 * Home object for domain model class Storageusage.
 * @see net.nettape.dal.object.Storageusage
 * @author Hibernate Tools
 */
public class StorageusageHome {

    private static final Log log = LogFactory.getLog(StorageusageHome.class);

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
    
    public void persist(Storageusage transientInstance) {
        log.debug("persisting Storageusage instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }
    
    public void attachDirty(Storageusage instance) {
        log.debug("attaching dirty Storageusage instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Storageusage instance) {
        log.debug("attaching clean Storageusage instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void delete(Storageusage persistentInstance) {
        log.debug("deleting Storageusage instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Storageusage merge(Storageusage detachedInstance) {
        log.debug("merging Storageusage instance");
        try {
            Storageusage result = (Storageusage) sessionFactory.getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
    public Storageusage findById( java.lang.Integer id) {
        log.debug("getting Storageusage instance with id: " + id);
        try {
            Storageusage instance = (Storageusage) sessionFactory.getCurrentSession()
                    .get("net.nettape.dal.object.Storageusage", id);
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
    
    public List findByExample(Storageusage instance) {
        log.debug("finding Storageusage instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("net.nettape.dal.object.Storageusage")
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

