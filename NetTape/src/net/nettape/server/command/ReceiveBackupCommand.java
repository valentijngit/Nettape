package net.nettape.server.command;



import java.io.*;
import java.util.ArrayList;
import java.util.List;

import net.nettape.connection.ByteStream;
import net.nettape.connection.Connection;
import net.nettape.object.FileInfo;
import net.nettape.server.BackupFileHandler;

import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;

public class ReceiveBackupCommand extends ServerCommand
{

	private Backup backup;
	
	public ReceiveBackupCommand(Connection connection, Backup backup)
	{
		super(connection);
		this.backup = backup;
	}
	public ReceiveBackupCommand(Connection connection)
	{
		super(connection);
	
	}
	public void Execute() throws Exception
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				
				if(this.backup == null)
				{
					Session session = HibernateUtil.getSessionFactory().getCurrentSession();
			        session.beginTransaction();
					int backupId = connection.receiveInt(); 
					this.backup = (Backup)session.get(Backup.class, backupId);
			        session.getTransaction().commit();
				}
				if(this.backup != null)
				{
			        connection.sendObject(this.backup);
			        BackupFileHandler backupFileHandler = new BackupFileHandler(this.backup,connection.dalClient);
			        List<Backupitem> backupitemList = backupFileHandler.GetFilesAndFoldersRecursive();
			        for(Backupitem backupitem : backupitemList)
			        {
		            	connection.sendObject(backupitem);
			        }
			        Backupitem backupitem = new Backupitem();
			        backupitem.setBackupitemid(-1);
			        connection.sendObject(backupitem);
				}
				else
				{
					this.backup = new Backup();
					this.backup.setBackupid(0);
					connection.sendObject(this.backup);
				}
		    }
			catch(Exception ex)
			{
				throw ex;
			}
		}
	}
}
