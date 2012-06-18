package net.nettape.server.command;



import java.io.*;
import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import net.nettape.Config;
import net.nettape.connection.ByteStream;
import net.nettape.connection.Connection;
import net.nettape.connection.InFileDelta;
import net.nettape.object.BackupHelper;
import net.nettape.object.Constants;
import net.nettape.object.FileInfo;
import net.nettape.object.IOHelper;
import net.nettape.object.Message;

import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;
import net.nettape.object.*;
import net.nettape.server.*;

public class SendBackupCommand extends ServerCommand
{
	private Backup backup;
	private BackupFileHandler backupFileHandler;
	private BackupFileHandler oldBackupFileHandler;
	private Backupset backupset;
	private Backupsetitem backupsetitem;
	private boolean backupExisted = false;
	private List<Backup> oldBackupList;
	private Backup oldBackup;
	public SendBackupCommand(Connection connection)
	{
		super(connection);
	}
	public void InitialiseBackup(int backupSetId)
	{
		// initialise backup 
		// - create backup folder
		// - get this.backupset from database
		// - save backup record in database
		
		this.backup = new Backup();
		this.backupFileHandler = new BackupFileHandler(backup, connection.dalClient);
		this.backup.setDatetime(Calendar.getInstance().getTime());
		this.backup.setName(this.backupFileHandler.CreateBackupFolder());
		this.backup.setIscdp(false);
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		session.beginTransaction();
		this.backupset = (Backupset)session.get(Backupset.class, backupSetId);
		this.backup.setBackupset(this.backupset);
		this.backup.setType(this.backupset.getType());
		this.backup.setFilepermissions(this.backupset.getFilepermissions());
		this.backup.setServerid(Integer.parseInt(Config.getProperty("ServerID")));
		session.save(backup);
		session.getTransaction().commit();

	}
	public void Execute()
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				int backupId = connection.receiveInt(); 
				int backupSetId = connection.receiveInt();
				// TODO: check if no other backup is running of the same backupset
				
				if(backupId == 0)
				{
					InitialiseBackup(backupSetId);
				}
				else
				{
					// this is a backup that already exists, but is now for some reason being continued
					// this is done intentionally after something went wrong
					
					Session session = HibernateUtil.getSessionFactory().getCurrentSession();
					session.beginTransaction();
					this.backup = (Backup)session.get(Backup.class, backupId);
					session.getTransaction().commit();
					
					if(this.backup == null)
					{
						InitialiseBackup(backupSetId);
					}
				}
				
				
				connection.sendInt(this.backup.getBackupid());
				
				/* this doesnt happen anymore with multithreading
				int backupSetItemId = 0;
				BackupHelper backupHelper = new BackupHelper();
				while((backupSetItemId = connection.receiveInt()) != 0)
				{
				
						Session session = HibernateUtil.getSessionFactory().getCurrentSession();
						session.beginTransaction();
						this.backupsetitem = (Backupsetitem)session.get(Backupsetitem.class, backupSetItemId);
						session.getTransaction().commit();
						if (this.backupsetitem != null)
						{
							SendBackupItemCommand sendBackupItemCommand = new SendBackupItemCommand(connection,this.backupset,this.backupsetitem,this.backup,this.backupFileHandler, backupHelper, backupExisted, false);
							sendBackupItemCommand.Execute();
							sendBackupItemCommand = null;
						}
						else
						{
							//send DONOTHING, because this backupsetitem does not exist anymore. backupset must have been changed.
							connection.sendObject(Constants.SendBackupCommand.DONOTHING );
						}
				}
				*/
				
		    }
			catch(Exception ex)
			{
				//This is a serverside error. Break the connection so client will rebegin.
				try
				{
					Message message = new Message();
					try
					{
						message.BackupId = this.backup.getBackupid();
					}
					catch(Exception ex4)
					{
						
					}
					message.ClientId = connection.dalClient.getClientid();
					message.Message = "An error occured on server when starting a backup.";
					message.Type = Constants.MessageType.SYSTEM_ERROR;
					message.UserId = connection.dalUser.getUserid();
					LogHandler logHandler = new LogHandler();
					logHandler.handle(message);
				}
				catch(Exception ex2)
				{
					
				}
				
				try
				{
					connection.GetSocket().close();
				}
				catch(Exception ex3)
				{
					
				}
			}
		}
	}

}
