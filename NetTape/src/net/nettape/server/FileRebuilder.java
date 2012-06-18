package net.nettape.server;

import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.nio.file.FileSystems;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.id.GUIDGenerator;

import net.nettape.connection.Connection;
import net.nettape.connection.InFileDelta;
import net.nettape.dal.HibernateUtil;
import net.nettape.dal.object.Backup;
import net.nettape.dal.object.Backuphistory;
import net.nettape.dal.object.Backupitem;
import net.nettape.dal.object.Backupset;
import net.nettape.dal.object.Backupsetitem;
import net.nettape.dal.object.Client;
import net.nettape.dal.object.Restore;
import net.nettape.object.Constants;
import net.nettape.object.Constants.BackupItemType;
import net.nettape.object.IOHelper;
import net.nettape.server.dal.Backups;
import net.nettape.exception.*;

public class FileRebuilder {
	
	private String filePath;
	private UUID uUID;
	private List<FileVersion> fileVersionTrack;
	
	public RandomAccessFile Rebuild(SmartPath smartPath, Connection connection, Client client, Restore restore) throws Exception, NoSnapshotException
	{
		net.nettape.dal.object.Settings settings = net.nettape.server.Settings.getSettings();
		IOHelper ioHelper = new IOHelper();
		String userhome = ioHelper.checkFolderForSeperatorEnd(settings.getUserhome());
		
		Backup backup = Backups.GetBackup(smartPath.BackupId);
		if(restore == null)
		{
			fileVersionTrack = GetFileTrack(smartPath,backup.getBackupset().getBackupsetid(),client,-1,-1);
			this.filePath = userhome + client.getClientid() + File.separator + backup.getName() + "_ifc" + File.separator;
		}
		else
		{
			fileVersionTrack = GetFileTrack(smartPath,backup.getBackupset().getBackupsetid(),client,smartPath.BackupId,smartPath.Version);
			this.filePath = userhome + client.getClientid() + File.separator + restore.getRestoreid() + "_ifc" + File.separator;
		}
		ioHelper.createFoldersInPath(this.filePath);
		return Rebuild(connection,client);
	}
	
	private RandomAccessFile Rebuild(Connection connection, Client client) throws Exception, NoSnapshotException
	{
		// rebuilding this file involves rebuilding with all the deltas since the last snapshot occured
		try
		{
			//first create a UUID for this rebuild so that the temp files to rebuild are not mixed by other rebuilds
			this.uUID = UUID.randomUUID(); 
			
			//TODO: look in other files to see where the incr temp files are deleted and add UUID in the path there
			
			FileVersion snapshotFileVersion = this.fileVersionTrack.get(this.fileVersionTrack.size() -1);
			Backup lastSnapshotBackup = Backups.GetBackup(snapshotFileVersion.Backupid);
			
		    TempFileHandler tempFileHandlerInput = new TempFileHandler(this.filePath + this.uUID + "incrDeltaInput.tmp");
		    TempFileHandler tempFileHandlerOutput = new TempFileHandler(this.filePath + this.uUID + "incrDeltaResult.tmp");
		    InFileDelta inFileDelta = new InFileDelta(connection);
			BackupFileHandler backupFileHandler = new BackupFileHandler(lastSnapshotBackup, client);
			backupFileHandler.OpenFileForInput(snapshotFileVersion);
			RandomAccessFile raf = backupFileHandler.getRandomAccessFile();
			if(this.fileVersionTrack.size() > 1)
			{	
				for(int i = this.fileVersionTrack.size() - 2; i>=0; i--)
				{
					FileVersion fileVersion = this.fileVersionTrack.get(i);
					Backup thisBackup = Backups.GetBackup(fileVersion.Backupid);
					tempFileHandlerOutput.OpenFileForOutput();
					BackupFileHandler backupDeltasHandler = new BackupFileHandler(thisBackup, client);
					backupDeltasHandler.OpenFileForInput(fileVersion);
					inFileDelta.rebuildFile(raf,backupDeltasHandler.getRandomAccessFile(),tempFileHandlerOutput.getFileOutputStream(), (long)0);
					backupDeltasHandler.CloseFileForInput();
					tempFileHandlerOutput.CloseFileForOutput();
					raf.close();
					tempFileHandlerInput.Delete();
					tempFileHandlerOutput.Rename(this.filePath + this.uUID + "incrDeltaInput.tmp");
					tempFileHandlerInput.OpenFileForInput(0);
					raf = tempFileHandlerInput.getFileInputStream();
				}
				
				backupFileHandler.CloseFileForInput();
				return raf;
				
			}
			else
			{
				//there is only a snapshot
				return raf;
			}
		}
		catch(NoSnapshotException ex)
		{
			throw ex;
		}
		catch(Exception ex)
		{
			throw ex;
		}
			

	}

	
	
	public void RemoveTempFiles()
	{
		new File(this.filePath + this.uUID + "incrDeltaInput.tmp").delete();
		new File(this.filePath + this.uUID + "incrDeltaResult.tmp").delete();
	}
	
	public FileVersion GetLatestFileVersion(SmartPath smartPath, int backupSetId, Client client) throws NoSnapshotException, Exception
	{
		// this gets the latest version of a file throughout all backups in history
		// this is only used when doing an incremental backup of a file
		
		// get all backups for backup set order by date descending
		List<Backup> backupList = Backups.GetAllBackupsForBackupSetDescending(backupSetId);
		
		// search in all backups for latest version until found
		FileVersion fileVersion = new FileVersion();
		boolean found = false;
		for(Backup backup: backupList)
		{
			BackupFileHandler backupFileHandler = new BackupFileHandler(backup,client);
			fileVersion = backupFileHandler.SearchLatestVersion(smartPath);
			if(fileVersion.Version > -1)
			{
				found = true;
				break;
			}
		}
		if(found)
		{
			return fileVersion;
		}
		else
		{
			throw new NoSnapshotException();
		}
			
		
	}

	public List<FileVersion> GetFileTrack(SmartPath smartPath, int backupSetId, Client client, int backupId, int version) throws NoSnapshotException, Exception
	{
		// Gets a history of FileVersions for a file throughout the history of backups IN THIS BACKUPSET! until a snapshot is found
				// Always deltas, incremental and differential are always withing deltas scope. 
				// Client setting of incremental or differential does not matter when rebuilding here.
				// in case of incremental, this will give all versions that are also incremental until the snapshot is found
				// in case of differential, this will give the latest version and the snapshot
				// To see if this is incremental or differential, we have to look at the path end of the latest version of the file
				
		// this gets the latest track of a file throughout all backups of a backupset in history
		// this is only used when doing a rebuild of a file (in case of incremental backup or restore)
		
		// get all backups for backup set order by date descending
		
		List<Backup> backupList;
		if(backupId > -1)
		{
			backupList = Backups.GetAllBackupsForBackupSetDescending(backupSetId, backupId);
		}
		else
		{
			backupList = Backups.GetAllBackupsForBackupSetDescending(backupSetId);
		}
		List<FileVersion> fileVersionList = new ArrayList<FileVersion>();
		boolean snapshotFound = false;
		boolean lookForSnapshot = false;
		
		//go through all backups of this backupset
		for(Backup backup: backupList)
		{
			if(snapshotFound) break;
			//search for latest file in this backup
			BackupFileHandler backupFileHandler = new BackupFileHandler(backup,client);
			SmartPath newSmartPath = backupFileHandler.FindEncodedPath(smartPath.DecodedPath);
			newSmartPath.Version = smartPath.Version;
			smartPath = newSmartPath;
			FileVersion fileVersion;
			
			if(version == -1 || backupId != backup.getBackupid())
			{
				fileVersion = backupFileHandler.SearchLatestVersion(smartPath);
			}
			else
			{
				fileVersion = backupFileHandler.SearchSpecificVersion(smartPath, smartPath.Version);
			}
			if(fileVersion.Version > -1)
			{
				
				if(fileVersion.BackupItemType == BackupItemType.SNAPSHOT)
				{
					fileVersionList.add(fileVersion);
					snapshotFound = true;
					break;
				}
				if(fileVersion.BackupItemType == BackupItemType.DIFFERENTIAL)
				{
					fileVersionList.add(fileVersion);
					lookForSnapshot = true;
				}
				if(fileVersion.BackupItemType == BackupItemType.INCREMENTAL)
				{
					if(!lookForSnapshot)
					{
						fileVersionList.add(fileVersion);
					}
				}
				
				// get the previous version until no version in this backup is left
				boolean goAhead = true;
				while(goAhead)
				{
					FileVersion previousFileVersion = backupFileHandler.SearchPreviousVersion(smartPath, fileVersion.Version);
					// get out of the inner while when there are no versions left
					if(previousFileVersion.Version == -1)
					{
						goAhead = false;
					}
					else
					{
						if(previousFileVersion.BackupItemType == BackupItemType.SNAPSHOT)
						{
							fileVersionList.add(previousFileVersion);
							snapshotFound = true;
							goAhead = false;
						}
						if(previousFileVersion.BackupItemType == BackupItemType.DIFFERENTIAL)
						{
							fileVersionList.add(previousFileVersion);
							lookForSnapshot = true;
						}
						if(previousFileVersion.BackupItemType == BackupItemType.INCREMENTAL)
						{
							if(!lookForSnapshot)
							{
								fileVersionList.add(previousFileVersion);
							}
						}
					}
				}
			}
		}
		if(snapshotFound)
		{
			return fileVersionList;
		}
		else
		{
			throw new NoSnapshotException();
		}
			
		
	}

	
	class ExecuteThread extends Thread 
	{
		public ExecuteThread() {
		}

		public void run() {
			try
			{
			}
			catch(Exception ex)
			{
			}

		}
	}
}

