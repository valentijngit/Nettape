package net.nettape.server.command;



import java.io.*;
import java.util.ArrayList;

import net.nettape.connection.ByteStream;
import net.nettape.connection.Connection;
import net.nettape.object.FileInfo;
import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;

public class ReceiveBackupSetCommand extends ServerCommand
{

	private Backupset backupset;
	
	public ReceiveBackupSetCommand(Connection connection, Backupset backupset)
	{
		super(connection);
		this.backupset = backupset;
	}
	public ReceiveBackupSetCommand(Connection connection)
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
				if(backupset == null)
				{
					int backupsetId = connection.receiveInt(); 
					this.backupset = (Backupset)session.get(Backupset.class, backupsetId);
				}

		        ArrayList<Backupsetitem> backupSetItemList = (ArrayList<Backupsetitem>) session
			        .createQuery("select bsi from Backupsetitem bsi where bsi.backupset.backupsetid = :bsid")
			        .setParameter("bsid", backupset.getBackupsetid())
			        .list(); 
		        session.getTransaction().commit();

		        connection.sendObject(this.backupset);
		        connection.sendInt(backupSetItemList.size());

		        for(Backupsetitem item : backupSetItemList)
		        {
		        	connection.sendObject(item);
		        }

				session = HibernateUtil.getSessionFactory().getCurrentSession();
		        session.beginTransaction();
				if(backupset == null)
				{
					int backupsetId = connection.receiveInt(); 
					this.backupset = (Backupset)session.get(Backupset.class, backupsetId);
				}

		        ArrayList<Filter> filterList = (ArrayList<Filter>) session
			        .createQuery("select f from Filter f left join fetch f.criterias where f.backupset.backupsetid = :bsid")
			        .setParameter("bsid", backupset.getBackupsetid())
			        .list(); 
		        session.getTransaction().commit();

		        connection.sendInt(filterList.size());

		        for(Filter item : filterList)
		        {
		        	connection.sendObject(item);
		        }
		        
				
		    }
			catch(Exception ex)
			{
				System.out.println("Error sending backupset.");
			}
		}
	}
}
