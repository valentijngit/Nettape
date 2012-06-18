package net.nettape.server.command;



import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Calendar;
import net.nettape.Config;
import net.nettape.connection.ByteStream;
import net.nettape.connection.Connection;
import net.nettape.connection.InFileDelta;
import net.nettape.object.BackupHelper;
import net.nettape.object.FileInfo;
import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;
import net.nettape.object.*;
import net.nettape.object.Constants.BackupItemType;
import net.nettape.server.*;
import net.nettape.server.dal.Backups;
import net.nettape.exception.*;

public class SendBackupItemCommand extends ServerCommand
{
	private Backupitem backupitem = null;
	private Backup backup = null;
	private BackupFileHandler backupFileHandler = null;
	private boolean isCDP = false;
	
	public SendBackupItemCommand(Connection connection)
	{
		super(connection);
		
	}
	public void Execute() throws Exception
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				// a backupitem is sent through, it holds the info about what to backup
				this.backupitem = (Backupitem)connection.receiveObject();
				if(this.backupitem.getPath().indexOf("rIkkkaOCErQZI-E")> -1)
				{
					int test =1; 
				}
				this.backup = Backups.GetBackup(backupitem.getBackupid());
			
				this.backupFileHandler = new BackupFileHandler(this.backup,this.connection.dalClient);
				this.isCDP = this.backup.getIscdp();
				
				
				if(! this.backupitem.getIsfolder())
				{
					// this backupitem is a file
					SmartPath smartPath = this.backupFileHandler.CreateEncodedPath(this.backupitem.getPath());
					HandleBackupItem(smartPath);
				}
				else 
				{
					// this backupitem is a folder, so create this folder
					SmartPath smartPath = this.backupFileHandler.CreateEncodedPath(this.backupitem.getPath());
					this.backupFileHandler.CreateFolder(smartPath);
				}

		    }
			catch(Exception ex)
			{
				throw ex;
			}
		}
	}
	public void HandleBackupItem(SmartPath smartPath) throws Exception
	{
		//search for file in backup folder by path, if found send donothing
		//do this only if backup existed and if not CDP (CDP can send the same file more than once)
		boolean goAhead = true;
		if(!isCDP)
		{
			if(backupFileHandler.FilesExist(smartPath))
			{
		    	connection.sendObject(Constants.SendBackupCommand.DONOTHING);
				goAhead = false;
			}
		}
		if(goAhead)
		{
			if((this.backup.getType() == Constants.BackupSetType.SNAPSHOT.type))
				HandleBackupItemAsSnapshot(smartPath);
			else if(this.backup.getType() == Constants.BackupSetType.DIFFERENTIAL.type)
				HandleBackupItemDifferentially(smartPath);
			else if (this.backup.getType() == Constants.BackupSetType.INCREMENTAL.type)
				HandleBackupItemIncrementally(smartPath);
		}
	}
	public void HandleBackupItemAsSnapshot(SmartPath smartPath) throws Exception
	{
    	connection.sendObject(Constants.SendBackupCommand.SENDFILE);
    	this.backupFileHandler.OpenFileForOutput(smartPath, Constants.BackupItemType.SNAPSHOT, false, false);
    	connection.receiveFileOnServer(this.backupFileHandler.getFileOutputStream());
    	backupFileHandler.CloseFileForOutput();
    	
    	HandlePermissions(smartPath, BackupItemType.SNAPSHOT);
	}
	public void HandleBackupItemDifferentially(SmartPath smartPath) throws Exception
	{
    	Boolean didBackup = true;
    	try
    	{
        	// recreate backup file
        	FileRebuilder fileRebuilder = new FileRebuilder();
			RandomAccessFile raf = fileRebuilder.Rebuild(smartPath, connection, connection.dalClient, null);
			
			// send the cheksums
        	InFileDelta inFileDelta = new InFileDelta(connection);
        	connection.sendObject(Constants.SendBackupCommand.RECEIVECHECKSUMS);
        	inFileDelta.makeSignatures(raf, raf.length(), connection.getObjectOutputStream());
        	raf.close();
        	fileRebuilder.RemoveTempFiles();
        	Constants.SendBackupCommand command = (Constants.SendBackupCommand)connection.receiveObject();
        	
        	if(command == Constants.SendBackupCommand.SENDDELTAS)
        	{
            	this.backupFileHandler.OpenFileForOutput(smartPath,Constants.BackupItemType.DIFFERENTIAL,true,false);
            	inFileDelta.readDeltas(connection.getObjectInputStream(), this.backupFileHandler.getFileOutputStream());
            	this.backupFileHandler.CloseFileForOutput();
        	}
        	if(command == Constants.SendBackupCommand.SENDFILE)
        	{
            	this.backupFileHandler.OpenFileForOutput(smartPath,Constants.BackupItemType.DIFFERENTIAL,false,false);
            	connection.receiveFileOnServer(this.backupFileHandler.getFileOutputStream());
            	backupFileHandler.CloseFileForOutput();
        	}
        	if(command == Constants.SendBackupCommand.DONOTHING)
        	{
        		didBackup = false;
        	}
    	}
    	catch(NoSnapshotException ex)
    	{
        	// this file has no snapshot yet
        	HandleBackupItemAsSnapshot(smartPath);
        	didBackup = false;
    	}
    	catch(Exception ex)
    	{
    		// TODO: another error occurred, write in error log
    		didBackup = false;
    	}

    	if(didBackup)
    	{
			HandlePermissions(smartPath,BackupItemType.DIFFERENTIAL);
    	}
    	
	}
	public void HandleBackupItemIncrementally(SmartPath smartPath) throws Exception
	{
    	Boolean didBackup = true;
    	try
    	{
        	// recreate backup file
        	FileRebuilder fileRebuilder = new FileRebuilder();
			RandomAccessFile raf = fileRebuilder.Rebuild(smartPath, connection, connection.dalClient, null);
			// send the cheksums
        	InFileDelta inFileDelta = new InFileDelta(connection);
        	connection.sendObject(Constants.SendBackupCommand.RECEIVECHECKSUMS);
        	inFileDelta.makeSignatures(raf, raf.length(), connection.getObjectOutputStream());
        	raf.close();
        	fileRebuilder.RemoveTempFiles();
        	Constants.SendBackupCommand command = (Constants.SendBackupCommand)connection.receiveObject();
        	
        	if(command == Constants.SendBackupCommand.SENDDELTAS)
        	{
            	this.backupFileHandler.OpenFileForOutput(smartPath, Constants.BackupItemType.INCREMENTAL, true,false);
            	inFileDelta.readDeltas(connection.getObjectInputStream(), this.backupFileHandler.getFileOutputStream());
            	this.backupFileHandler.CloseFileForOutput();        		
        	}
        	if(command == Constants.SendBackupCommand.SENDFILE)
        	{
    	    	this.backupFileHandler.OpenFileForOutput(smartPath, Constants.BackupItemType.INCREMENTAL, false,false);
            	connection.receiveFileOnServer(this.backupFileHandler.getFileOutputStream());
            	backupFileHandler.CloseFileForOutput();
        	}
        	if(command == Constants.SendBackupCommand.DONOTHING)
    		{
    			// TODO: file does not have to be backuped, write in log
    			didBackup = false;
    		}
        	
    	}
    	catch(NoSnapshotException ex)
    	{
        	// this file has no snapshot yet
        	HandleBackupItemAsSnapshot(smartPath);
        	didBackup = false;
    	}
    	catch(Exception ex)
    	{
    		// TODO: another error occurred, write in error log
    		didBackup = false;
    	}
    	if(didBackup)
    	{
        	HandlePermissions(smartPath,BackupItemType.INCREMENTAL);
    	}
	}
	
	private void HandlePermissions(SmartPath smartPath, Constants.BackupItemType type) throws Exception
	{
		if(this.backup.getFilepermissions() != null)
		{
			if(this.backup.getFilepermissions())
			{
				Permissions permissions = (Permissions)connection.receiveObject();
				this.backupFileHandler.OpenFileForOutput(smartPath,type,false,true);
	            ObjectOutputStream oos = new ObjectOutputStream(this.backupFileHandler.getFileOutputStream());
				oos.writeObject(permissions);
				this.backupFileHandler.CloseFileForOutput();
				
			}
		}
	}
	
	
}
