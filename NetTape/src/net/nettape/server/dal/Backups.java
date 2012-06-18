package net.nettape.server.dal;

import java.util.ArrayList;
import java.util.List;
import net.nettape.dal.object.*;

import net.nettape.dal.HibernateUtil;


import org.hibernate.Session;

public class Backups {

	public static Backup GetBackup(int backupId)
	{	
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction(); 
		Backup backup = (Backup)session.get(Backup.class, backupId);
        session.getTransaction().commit();
        return backup;
	}
	
	public static List<Backup> GetAllBackupsForBackupSetDescending(int backupSetId)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String query = "select b from Backup b where b.backupset.backupsetid = :bsid order by b.datetime desc";
        ArrayList<Backup> backupList = new ArrayList<Backup>();
        backupList = (ArrayList<Backup>) session
	        .createQuery(query)
	        .setParameter("bsid", backupSetId)
	        .list(); 
        session.getTransaction().commit();
        return backupList;
	}
	
	public static List<Backup> GetAllBackupsForBackupSetDescending(int backupSetId, int backupId)
	{
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        
        Backup backup = (Backup)session.get(Backup.class, backupId);
        
        ArrayList<Backup> backupList = new ArrayList<Backup>();
        String query = "select b from Backup b where b.datetime <= '" + backup.getDatetime() + "' and b.backupset.backupsetid = :bsid order by b.datetime desc";
        backupList = (ArrayList<Backup>) session
	        .createQuery(query)
	        .setParameter("bsid", backupSetId)
	        .list(); 
        session.getTransaction().commit();
        return backupList;
	}
}
