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
 * Home object for domain model class Languagesettings.
 * @see net.nettape.dal.object.Languagesettings
 * @author Hibernate Tools
 */
public class LanguagesettingsHome {

    private static final Log log = LogFactory.getLog(LanguagesettingsHome.class);

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
    
    public void persist(Languagesettings transientInstance) {
        log.debug("persisting Languagesettings instance");
        try {
            sessionFactory.getCurrentSession().persist(transientInstance);
            log.debug("persist successful");
        }
        catch (RuntimeException re) {
            log.error("persist failed", re);
            throw re;
        }
    }
    
    public void attachDirty(Languagesettings instance) {
        log.debug("attaching dirty Languagesettings instance");
        try {
            sessionFactory.getCurrentSession().saveOrUpdate(instance);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void attachClean(Languagesettings instance) {
        log.debug("attaching clean Languagesettings instance");
        try {
            sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
            log.debug("attach successful");
        }
        catch (RuntimeException re) {
            log.error("attach failed", re);
            throw re;
        }
    }
    
    public void delete(Languagesettings persistentInstance) {
        log.debug("deleting Languagesettings instance");
        try {
            sessionFactory.getCurrentSession().delete(persistentInstance);
            log.debug("delete successful");
        }
        catch (RuntimeException re) {
            log.error("delete failed", re);
            throw re;
        }
    }
    
    public Languagesettings merge(Languagesettings detachedInstance) {
        log.debug("merging Languagesettings instance");
        try {
            Languagesettings result = (Languagesettings) sessionFactory.getCurrentSession()
                    .merge(detachedInstance);
            log.debug("merge successful");
            return result;
        }
        catch (RuntimeException re) {
            log.error("merge failed", re);
            throw re;
        }
    }
    
    public Languagesettings findById( java.lang.Integer id) {
        log.debug("getting Languagesettings instance with id: " + id);
        try {
            Languagesettings instance = (Languagesettings) sessionFactory.getCurrentSession()
                    .get("net.nettape.dal.object.Languagesettings", id);
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
    
    public List findByExample(Languagesettings instance) {
        log.debug("finding Languagesettings instance by example");
        try {
            List results = sessionFactory.getCurrentSession()
                    .createCriteria("net.nettape.dal.object.Languagesettings")
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

