package net.nettape.server.command;



import java.io.*;
import java.util.ArrayList;

import net.nettape.connection.ByteStream;
import net.nettape.connection.Connection;
import net.nettape.object.*;
import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;

public class ReceiveBackupsCommand extends ServerCommand
{

	public ReceiveBackupsCommand(Connection connection)
	{
		super(connection);
	}
	public void Execute()
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				int clientId = connection.receiveInt();
				
				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		        session.beginTransaction();

		        ArrayList<Backup> backupList = (ArrayList<Backup>) session
			        .createQuery("select b from Backup b where b.backupset.client.clientid = :cid order by b.datetime desc")
			        .setParameter("cid", clientId)
			        .list(); 
		        session.getTransaction().commit();

		        connection.sendInt(backupList.size());
		        for(Backup item : backupList)
		        {
		        	ReceiveBackupCommand receiveBackupCommand = new ReceiveBackupCommand(connection,item);
		        	receiveBackupCommand.Execute();

		        }


				
		    }
			catch(Exception ex)
			{
				int test = 1;
			}
		}
	}
}
