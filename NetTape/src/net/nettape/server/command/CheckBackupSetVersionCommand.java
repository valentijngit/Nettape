package net.nettape.server.command;



import java.io.*;
import java.util.ArrayList;

import net.nettape.connection.ByteStream;
import net.nettape.connection.Connection;
import net.nettape.object.FileInfo;
import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;

public class CheckBackupSetVersionCommand extends ServerCommand
{

	private Backupset backupset;
	
	public CheckBackupSetVersionCommand(Connection connection, Backupset backupset)
	{
		super(connection);
		this.backupset = backupset;
	}
	public CheckBackupSetVersionCommand(Connection connection)
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

		        session.getTransaction().commit();

		        connection.sendLong(this.backupset.getVersion());

				
		    }
			catch(Exception ex)
			{
				System.out.println("Error sending backupset.");
			}
		}
	}
}
