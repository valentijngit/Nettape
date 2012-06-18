package net.nettape.server.command;



import java.io.*;
import java.util.ArrayList;

import net.nettape.connection.ByteStream;
import net.nettape.connection.Connection;
import net.nettape.object.*;
import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;

public class ReceiveLogsCommand extends ServerCommand
{

	public ReceiveLogsCommand(Connection connection)
	{
		super(connection);
	}
	public void Execute()
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				int backupsetId = connection.receiveInt();
				int type = connection.receiveInt();
				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		        session.beginTransaction();
		        ArrayList<Log> logList = new ArrayList<Log>();
		        if(type == Constants.MessageType.BACKUP.ordinal())
		        {
			        logList = (ArrayList<Log>) session
				        .createQuery("select l from Log l where l.backup.backupsetId = :bsid and l.type = :type")
				        .setParameter("bsid", backupsetId)
				        .setParameter("type", type)
				        .list(); 
			        session.getTransaction().commit();
		        }
		        if(type == Constants.MessageType.BACKUP_ERROR.ordinal())
		        {
			        logList = (ArrayList<Log>) session
				        .createQuery("select l from Log l where l.backup.backupsetId = :bsid and l.type = :type")
				        .setParameter("bsid", backupsetId)
				        .setParameter("type", type)
				        .list(); 
			        session.getTransaction().commit();
		        }
		        if(type == Constants.MessageType.RESTORE.ordinal())
		        {
			        logList = (ArrayList<Log>) session
				        .createQuery("select l from Log l where l.restoreitem.backupitem.backupsetitem.backupsetId = :bsid and l.type = :type")
				        .setParameter("bsid", backupsetId)
				        .setParameter("type", type)
				        .list(); 
			        session.getTransaction().commit();
		        }
		        if(type == Constants.MessageType.RESTORE_ERROR.ordinal())
		        {
			        logList = (ArrayList<Log>) session
				        .createQuery("select l from Log l where l.restoreitem.backupitem.backupsetitem.backupsetId = :bsid and l.type = :type")
				        .setParameter("bsid", backupsetId)
				        .setParameter("type", type)
				        .list(); 
			        session.getTransaction().commit();
		        }
		        connection.sendInt(logList.size());
		        for(Log item : logList)
		        {
		        	connection.sendObject(item);
		        }


				
		    }
			catch(Exception ex)
			{
				System.out.println("Error sending logs.");
			}
		}
	}
}
