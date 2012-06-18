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
 * Home object for domain model class Backupsetitem.
 * @see net.nettape.dal.object.Backupsetitem
 * @author Hibernate Tools
 */
public class BackupsetitemHome {

    private static final Log log = LogFactory.getLog(BackupsetitemHome.class);

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
    
    public void persist(Backupsetitem transientInstance) {
        log.debug("persisting Backupsetitem instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }
    
    public void attachDirty(Backupsetitem instance) {
        log.debug("attaching dirty Backupsetitem instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Backupsetitem instance) {
        log.debug("attaching clean Backupsetitem instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void delete(Backupsetitem persistentInstance) {
        log.debug("deleting Backupsetitem instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Backupsetitem merge(Backupsetitem detachedInstance) {
        log.debug("merging Backupsetitem instance");
        try {
            Backupsetitem result = (Backupsetitem) sessionFactory.getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
    public Backupsetitem findById( java.lang.Integer id) {
        log.debug("getting Backupsetitem instance with id: " + id);
        try {
            Backupsetitem instance = (Backupsetitem) sessionFactory.getCurrentSession()
                    .get("net.nettape.dal.object.Backupsetitem", id);
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
    
    public List findByExample(Backupsetitem instance) {
        log.debug("finding Backupsetitem instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("net.nettape.dal.object.Backupsetitem")
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

