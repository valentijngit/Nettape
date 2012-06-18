package net.nettape.server.command;



import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import net.nettape.client.gui.ui.object.Node;
import net.nettape.connection.ByteStream;
import net.nettape.connection.Connection;
import net.nettape.object.Constants;
import net.nettape.object.FileInfo;
import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;

public class SendBackupSetCommand extends ServerCommand
{

	public SendBackupSetCommand(Connection connection)
	{
		super(connection);
	}
	public void Execute()
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		        session.beginTransaction();

				net.nettape.object.BackupSet oBackupSet = new net.nettape.object.BackupSet();
				Backupset backupset = (Backupset) (connection.receiveObject());
			
				oBackupSet.backupset = backupset;
		        oBackupSet.backupsetitems = new HashSet<Backupsetitem>();
		        oBackupSet.filters = new HashSet<Filter>();
		        long length = (long) (connection.receiveLong());
		        for(int i = 0; i<length;i++)
		        {
		        	oBackupSet.backupsetitems.add((Backupsetitem) (connection.receiveObject()));
		        }
		        length = (long) (connection.receiveLong());
		        for(int i = 0; i<length;i++)
		        {
		        	oBackupSet.filters.add((Filter) (connection.receiveObject()));
		        }
		        Backupset newBackupset = null;
		        Backupset testBackupset = null;
		        if(backupset.getBackupsetid() != null)
		        {
			        testBackupset = (Backupset)session.get(Backupset.class, backupset.getBackupsetid());
			        if (testBackupset != null)
			        {
			        	//TODO: CHECK IF THIS WORKS!!! : delete all backupsetitems in this backupset, it is an update!
			        	session.createQuery("delete from Backupsetitem bsi where bsi.backupset.backupsetid = " + backupset.getBackupsetid()).executeUpdate();
		        		session.createQuery("delete from Criteria AS c where c.criteriaid in (select ic from Criteria ic where ic.filter.backupset.backupsetid = " + backupset.getBackupsetid() + ")").executeUpdate();
			        	session.createQuery("delete from Filter f where f.backupset.backupsetid = " + backupset.getBackupsetid()).executeUpdate();
				        testBackupset.setContinuousprotection(backupset.getContinuousprotection());
				        testBackupset.setCpinterval(backupset.getCpinterval());
				        testBackupset.setDeleted(backupset.getDeleted());
				        testBackupset.setDatedeactivated(backupset.getDatedeactivated());
				        testBackupset.setDatedeleted(backupset.getDatedeleted());
				        testBackupset.setEnablecompression(backupset.getEnablecompression());
				        testBackupset.setEnableencryption(backupset.getEnableencryption());
				        testBackupset.setFilepermissions(backupset.getFilepermissions());
				        testBackupset.setInfiledeltablocksize(backupset.getInfiledeltablocksize());
				        testBackupset.setInfiledeltamaxsize(backupset.getInfiledeltamaxsize());
				        testBackupset.setIsdeltas(backupset.getIsdeltas());
				        testBackupset.setName(backupset.getName());
				        testBackupset.setOfflinebackup(backupset.getOfflinebackup());
				        testBackupset.setRetention(backupset.getRetention());
				        testBackupset.setSort(backupset.getSort());
				        testBackupset.setType(backupset.getType());
				        testBackupset.setVolumeshadowcopy(backupset.getVolumeshadowcopy());
				        /*
				        if(backupset.getVersion() != null)
				        	testBackupset.setVersion(backupset.getVersion() + 1);
				        */
			        	newBackupset = testBackupset;
			        	session.save(newBackupset);
			        }
			        else
			        {
			        	newBackupset = backupset;
				        session.save(newBackupset);
			        }
		        }
		        else
		        {
		        	newBackupset = backupset;
			        session.save(newBackupset);
		        }
		        connection.sendInt(newBackupset.getBackupsetid());
		        int i=0;
		        for(Backupsetitem item : oBackupSet.backupsetitems)
		        {
		        	i++;
		        	Backupsetitem newItem = new Backupsetitem();
		        	newItem.setBackup(false);
		        	newItem.setBackupset(newBackupset);
		        	newItem.setBackupsetitemid(null);
		        	newItem.setIsfolder(item.getIsfolder());
		        	newItem.setPath(item.getPath());
		        	newItem.setRoot(item.getRoot());
		        	newItem.setType(item.getType());
		        	session.save(newItem);
		        	if ( i % 20 == 0 ) 
		        	{
		        		// for batch inserts, don't forget to flush now and then to avoid out of memory problems
		        		session.flush();
		                session.clear();
		        	}
		        }
		        for(Filter item : oBackupSet.filters)
		        {
		        	i++;
		        	Filter newItem = new Filter();
		        	newItem.setBackupset(newBackupset);
		        	newItem.setApplytofiles(item.getApplytofiles());
		        	newItem.setApplytofolders(item.getApplytofolders());
		        	newItem.setInclude(item.getInclude());
		        	newItem.setName(item.getName());
		        	newItem.setToppath(item.getToppath());
		        	session.save(newItem);
		        	if ( i % 20 == 0 ) 
		        	{
		        		// for batch inserts, don't forget to flush now and then to avoid out of memory problems
		        		session.flush();
		                session.clear();
		        	}
		        	Iterator<Criteria> iter = item.getCriterias().iterator();
					while (iter.hasNext()) {
						Criteria criteria = iter.next();
						Criteria newCriteria = new Criteria();
						newCriteria.setFilter(newItem);
						newCriteria.setMatchcase(criteria.getMatchcase());
						newCriteria.setPattern(criteria.getPattern());
						newCriteria.setType(criteria.getType());
						session.save(newCriteria);
					}
		        }

		        session.getTransaction().commit();
		    }
			catch(Exception ex)
			{
				System.out.println("Error receiving a file.");
			}
		}
	}
}
