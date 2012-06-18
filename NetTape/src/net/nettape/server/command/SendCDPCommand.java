package net.nettape.server.command;



import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import net.nettape.Config;
import net.nettape.connection.ByteStream;
import net.nettape.connection.Connection;
import net.nettape.connection.InFileDelta;
import net.nettape.object.FileInfo;
import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;
import net.nettape.object.*;
import net.nettape.server.*;

public class SendCDPCommand extends ServerCommand
{
	private Backup backup;
	private BackupFileHandler backupFileHandler;
	private BackupFileHandler oldBackupFileHandler;
	private Backupset backupset;
	private Backupsetitem backupsetitem;
	public SendCDPCommand(Connection connection)
	{
		super(connection);
	}
	public void Execute()
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				// create a new backup object in dal and create backup file
				int backupSetId = connection.receiveInt(); 
				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
				session.beginTransaction();
				this.backupset = (Backupset)session.get(Backupset.class, backupSetId);
				this.backup = new Backup();
				backup.setDatetime(Calendar.getInstance().getTime());
				backup.setBackupset(this.backupset);
				backup.setServerid(Integer.parseInt(Config.getProperty("ServerID")));
				backup.setIscdp(true);
				this.backupFileHandler = new BackupFileHandler(backup, connection.dalClient);
				backup.setName(this.backupFileHandler.CreateBackupFolder());
				session.save(backup);
				session.getTransaction().commit();
				connection.sendInt(backup.getBackupid());
		    }

			catch(Exception ex)
			{
				//TODO backup did not succeed.
				System.out.println(ex.getMessage());
			}
		}
	}

}
