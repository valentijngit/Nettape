package net.nettape.server.command;



import java.io.*;
import java.util.ArrayList;

import net.nettape.connection.ByteStream;
import net.nettape.connection.Connection;
import net.nettape.object.*;
import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;

public class ReceiveBackupSetsCommand extends ServerCommand
{

	public ReceiveBackupSetsCommand(Connection connection)
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

		        ArrayList<Backupset> backupSetList = (ArrayList<Backupset>) session
			        .createQuery("select bs from Backupset bs where bs.client.clientid = :cid")
			        .setParameter("cid", clientId)
			        .list(); 
		        session.getTransaction().commit();

		        connection.sendInt(backupSetList.size());
		        for(Backupset item : backupSetList)
		        {
		        	ReceiveBackupSetCommand receiveBackupSetCommand = new ReceiveBackupSetCommand(connection,item);
		        	receiveBackupSetCommand.Execute();

		        }


				
		    }
			catch(Exception ex)
			{
				System.out.println("Error sending backupsets.");
			}
		}
	}
}
