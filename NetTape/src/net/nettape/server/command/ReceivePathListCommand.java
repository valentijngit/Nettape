package net.nettape.server.command;



import java.io.*;
import java.util.ArrayList;
import java.util.List;

import net.nettape.connection.ByteStream;
import net.nettape.connection.Connection;
import net.nettape.object.FileInfo;
import net.nettape.server.BackupFileHandler;
import net.nettape.server.SmartPath;

import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;

public class ReceivePathListCommand extends ServerCommand
{

	private Backup backup;
	private Backupitem backupitem;
	
	public ReceivePathListCommand(Connection connection)
	{
		super(connection);
	
	}
	public void Execute() throws Exception
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				
				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		        session.beginTransaction();
				int backupId = connection.receiveInt(); 
				this.backup = (Backup)session.get(Backup.class, backupId);
				session.getTransaction().commit();
				
				int useBackupRoot = connection.receiveInt();
				if(useBackupRoot == 0)
				{
					this.backupitem = (Backupitem)connection.receiveObject();
				}
				if(this.backup != null)
				{
					connection.sendObject(this.backup);
			        BackupFileHandler backupFileHandler = new BackupFileHandler(this.backup,connection.dalClient);
					List<Backupitem> backupitemList;
			        if(this.backupitem == null)
					{
			        	SmartPath smartPath = new SmartPath("","",this.backup.getBackupid());
						backupitemList = backupFileHandler.GetFilesAndFolders(smartPath);
					}
					else
					{
						SmartPath smartPath = backupFileHandler.FindEncodedPath(backupitem.getPath());
						backupitemList = backupFileHandler.GetFilesAndFolders(smartPath);
					}
			        for(Backupitem backupitem : backupitemList)
			        {
		            	connection.sendObject(backupitem);
			        }
			        Backupitem backupitem = new Backupitem();
			        backupitem.setBackupitemid(0);
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
