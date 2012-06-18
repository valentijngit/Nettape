package net.nettape.server.command;



import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Calendar;
import net.nettape.Config;
import net.nettape.connection.ByteStream;
import net.nettape.connection.Connection;
import net.nettape.connection.InFileDelta;
import net.nettape.object.Constants;
import net.nettape.object.FileInfo;
import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;
import net.nettape.object.*;
import net.nettape.server.*;
import net.nettape.server.dal.Backups;

public class ReceiveRestoreCommand extends ServerCommand
{

	private BackupFileHandler backupFileHandler;
	private BackupFileHandler oldBackupFileHandler;

	private Backupsetitem backupsetitem;
	private Restore restore;
	public ReceiveRestoreCommand(Connection connection)
	{
		super(connection);
	}
	public void Execute()
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				// create a new restore object in dal 
 
				boolean overwrite = false;
				boolean restorepermissions = false;
				if(connection.receiveInt() == 1)
					overwrite = true;
				if(connection.receiveInt() == 1)
					restorepermissions = true;
				String restorepath = null;
				if(connection.receiveInt() == 1)
					restorepath = connection.receiveString();
				//TODO: receive a command which indicates if a backupid or a restoreid is sent
				//TODO: do not create a new restore if a restoreid is sent.
				Session session = HibernateUtil.getSessionFactory().getCurrentSession();
				session.beginTransaction();

				restore = new Restore();
				restore.setDatetime(Calendar.getInstance().getTime());
				restore.setOverwrite(overwrite);
				restore.setRestorepermissions(restorepermissions);
				restore.setRestorepath(restorepath);
				session.save(restore);
				session.getTransaction().commit();
				
				int numberOfRestoreitems = connection.receiveInt();
				
				for(int i = 0; i< numberOfRestoreitems;i++)
				{
					try
					{
						//received backupitemids are always for files, never folders, client should handle that
						int BackupId = connection.receiveInt();
						if(BackupId != 0)
						{
							Backupitem backupitem = (Backupitem)connection.receiveObject();
							Backup thisBackup = Backups.GetBackup(backupitem.getBackupid());
							
							BackupFileHandler backupFileHandler = new BackupFileHandler(thisBackup, connection.dalClient);							
							SmartPath smartPath = backupFileHandler.FindEncodedPath(backupitem.getPath());
							if(backupitem.getIsdeltas())
							{
								// ok start rebuilding the file
					        	FileRebuilder fileRebuilder = new FileRebuilder();
					        	smartPath.Version = backupitem.getVersion();
								RandomAccessFile raf = fileRebuilder.Rebuild(smartPath, connection, connection.dalClient, this.restore );
					        	connection.sendObject(Constants.ReceiveRestoreCommand.RECEIVEFILE);
					        	connection.sendFileOnServer(raf, raf.length());
								raf.close();
								fileRebuilder.RemoveTempFiles();
								fileRebuilder = null;
							}
							else
							{
								//just send this, because it's a complete file, not deltas
								FileVersion fileVersion = backupFileHandler.SearchSpecificVersion(smartPath, backupitem.getVersion());
								backupFileHandler.OpenFileForInput(fileVersion);
					        	connection.sendObject(Constants.ReceiveRestoreCommand.RECEIVEFILE);
					        	RandomAccessFile raf = backupFileHandler.getRandomAccessFile();
								connection.sendFileOnServer(raf,raf.length());
								backupFileHandler.CloseFileForInput();
						
							}
							if(restore.getRestorepermissions())
							{
								//TODO: if restore permissions, send the permissions object
								// this permissions object was serialized to a permissions backup file
								FileVersion fileVersion = backupFileHandler.SearchSpecificVersion(smartPath, backupitem.getVersion());
								fileVersion.IsPermissions = true;
								backupFileHandler.OpenFileForPermissionsInput(fileVersion);
					        	ObjectInputStream ois = new ObjectInputStream(backupFileHandler.getFileInputStream());
					        	Permissions permissions = (Permissions)ois.readObject();
					        	this.connection.sendObject(permissions);
								backupFileHandler.CloseFileForInput();
							}
							
							session = HibernateUtil.getSessionFactory().getCurrentSession();
							session.beginTransaction();
							Restoreitem restoreitem = new Restoreitem();
							restoreitem.setDatetime(Calendar.getInstance().getTime());
							restoreitem.setPath(backupitem.getPath());
							restoreitem.setRestore(restore);
							restoreitem.setType((short)1);
							session.save(restoreitem);
							session.getTransaction().commit();
						}

						

						
					}
					catch(Exception ex)
					{
						String test = "";
			        	connection.sendObject(Constants.ReceiveRestoreCommand.GOTONEXT);
						//TODO backup of backupsetitem did not succeed, write in log.
					}
					

				}
				
			



		    }

			catch(Exception ex)
			{
				//TODO backup did not succeed.
				System.out.println(ex.getMessage());
			}
		}
	}
	
	
}
