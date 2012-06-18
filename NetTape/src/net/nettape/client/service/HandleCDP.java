package net.nettape.client.service;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.teamdev.filewatch.FileEvent;
import com.teamdev.filewatch.FileEventsAdapter;
import com.teamdev.filewatch.FileWatcher;
import com.teamdev.filewatch.WatchingAttributes;

import net.nettape.client.MessageHandler;
import net.nettape.client.ShadowHandler;
import net.nettape.client.command.SendBackupItemCommand;
import net.nettape.connection.Command;
import net.nettape.connection.Connection;
import net.nettape.connection.ConnectionFactory;
import net.nettape.dal.object.Backupsetitem;
import net.nettape.object.BackupSet;
import net.nettape.object.BackupsetitemComparator;
import net.nettape.object.Constants;
import net.nettape.object.Message;
import net.nettape.object.ModifiedFile;
import net.nettape.object.BackupHelper;
import net.nettape.client.command.*;



public class HandleCDP implements Runnable
{
	private Connection connection;
	private BackupSet backupSet;
	private BlockingQueue<ModifiedFile> fileList =
	    new LinkedBlockingQueue<ModifiedFile>();
	private List<String> roots = 
		new ArrayList<String>();
	private SendCDPCommand sendCDPCommand;
	private BackupHelper backupHelper;
	private LinkedList<FileWatcher> watcherList = new LinkedList<FileWatcher>();
	public HandleCDP(Connection connection, BackupSet backupSet)
	{
		this.connection = connection;
		this.backupSet = backupSet;
	}

	public void run()
	{
		try
		{
			backupHelper = new BackupHelper();
			sendCDPCommand = new SendCDPCommand(connection,backupSet);
			sendCDPCommand.Execute(false);
			// ok, we need a watcher for this
			// and don't use volume shadow copy for this
			String oldRoot = "";
			ArrayList<Backupsetitem> backupsetitemslist = new ArrayList<Backupsetitem>(backupSet.backupsetitems);
			Collections.sort(backupsetitemslist, new BackupsetitemComparator());
			for(Backupsetitem backupsetitem : backupsetitemslist )
			{
				if(oldRoot != backupsetitem.getRoot())
				{
					if(! rootAlreadyWatched(backupsetitem.getRoot()))
					{
						try
						{
						roots.add(backupsetitem.getRoot());
						File watchingFolder = new File(backupsetitem.getRoot() + File.separator);
				        FileWatcher watcher = FileWatcher.create(watchingFolder);
				        oldRoot = backupsetitem.getRoot();
				        watcher.addListener(new FileEventsAdapter() {
				            @Override
				            public void fileAdded(FileEvent.Added e) {
				            	cdpFileAdded(e);
				            }

				            @Override
				            public void fileChanged(FileEvent.Changed e) {
				                cdpFileChanged(e);
				            }

				            @Override
				            public void fileRenamed(FileEvent.Renamed e) {
				                cdpFileRenamed(e);
				            }

				        });
				        Set<WatchingAttributes> watchingAttributes = new HashSet<WatchingAttributes>();
				        // Monitor file names
				        watchingAttributes.add(WatchingAttributes.Subtree);
				        watchingAttributes.add(WatchingAttributes.ModifiedDate);
				        watchingAttributes.add(WatchingAttributes.FileName);

				        watcher.setOptions(watchingAttributes);

				        watcher.start();
				        watcherList.add(watcher);
						}
						catch(Exception ex2)
						{
			            	Message message = new Message();
			            	message.Message = "Could not start a continuous data protection on " + backupsetitem.getRoot() + File.separator + ".";
			            	message.Type = Constants.MessageType.BACKUP_ERROR;
			            	message.BackupId = sendCDPCommand.backupId;
			            	MessageHandler.HandleMessage(null, false, "", true, message, null);
						} 
					}

				}
			}
			int delay = 10000;   // delay for 10 sec.
			int period = backupSet.backupset.getCpinterval();
			final Timer timer = new Timer();

			timer.scheduleAtFixedRate(new TimerTask() {
			        public void run() {
			            try
				            {
			            	LinkedList<ModifiedFile> fileListToRemove = new LinkedList<ModifiedFile>();
				        	for(ModifiedFile modFile : fileList)
				        	{
				        		try
				        		{
					            	// check if backupset version has changed, if so stop this thread.
					            	// CheckBackupSetVersionCommand ! (do not get the complete backupset from the server)
					            	CheckBackupSetVersionCommand checkBackupSetVersionCommand = new CheckBackupSetVersionCommand(connection,modFile.backupsetitem.getBackupset().getBackupsetid());
					            	if (modFile.backupsetitem.getBackupset().getVersion() == checkBackupSetVersionCommand.Execute())
					            	{
					        			ShadowHandler fileHandler = new ShadowHandler(modFile.root , false);
					        			if(fileHandler.canOpenFile(modFile.backupsetitem.getPath()))
					        			{
						        			modFile.backupsetitem.setIsfolder(fileHandler.isDirectory(modFile.backupsetitem.getPath()));
											SendCDPItemCommand sendCDPItemCommand = new SendCDPItemCommand(connection,sendCDPCommand.backupId,modFile.backupsetitem,fileHandler,backupHelper, backupSet);
											sendCDPItemCommand.Execute();
											fileListToRemove.add(modFile);
					        			}
					            	}
					            	else
					            	{
					            		// The version of the backupset has changed. Stop this handleCDP thread!
					            		// A new thread will automatically be created by the timer in EyeOfGod.
					            		timer.cancel();
					            		
					            		for (FileWatcher watcher : watcherList)
					            		{
					            			watcher.stop();
					            			watcher = null;
					            		}
					            		// TODO: check if this stops the HandleCDP thread (exists the run method)
					            	}
					            
				        		}
				        		catch(Exception ex)
				        		{
				        			//NOTE: no error, could still be copying or creatig or changing
				        		}
				        		
				        	}
				        	for(ModifiedFile file : fileListToRemove)
				        	{
				        		fileList.remove(file);
				        	}
			            }
			            catch(Exception ex)
			            {
			            	
			            }
			        }
			    }, delay, period);
			

		}
		catch (Exception ex)
		{
		}
	}
	private void cdpFileAdded(FileEvent.Added e)
	{
		String root = ((FileWatcher)(e.getSource())).getFolder().getPath();
		File file = e.getFile();
		ModifiedFile modFile;
		if((modFile = needToBackup(root,file.getPath())) != null)
		{
			boolean add = true;
			for(ModifiedFile thisModFile : fileList)
			{
				if((thisModFile.path.equals(modFile.path)) && ! thisModFile.isRenamed)
				{
					add = false;
				}
			}
			if(add)	fileList.add(modFile);
			
		}
				
	}
	private void cdpFileChanged(FileEvent.Changed e)
	{
		String root = ((FileWatcher)(e.getSource())).getFolder().getPath();
		File file = e.getFile();
		ModifiedFile modFile;
		if((modFile = needToBackup(root,file.getPath())) != null)
		{
			boolean add = true;
			for(ModifiedFile thisModFile : fileList)
			{
				if((thisModFile.path.equals(modFile.path))&& ! thisModFile.isRenamed)
				{
					add = false;
				}
			}
			if(add) fileList.add(modFile);
		}
				
	}
	private void cdpFileRenamed(FileEvent.Renamed e)
	{
		String root = ((FileWatcher)(e.getSource())).getFolder().getPath();
		File file = e.getFile();
		ModifiedFile modFile;
		if((modFile = needToBackup(root,file.getPath())) != null)
		{
			boolean add = true;
			for(ModifiedFile thisModFile : fileList)
			{
				if((thisModFile.path.equals(modFile.path)) && thisModFile.isRenamed )
				{
					add = false;
				}
			}
			if(add) fileList.add(modFile);
		}
				
	}
	private ModifiedFile needToBackup(String root, String path)
	{
		for(Backupsetitem backupsetitem : backupSet.backupsetitems)
		{
			if(root.equals( backupsetitem.getRoot() + File.separator))
			{
				if(backupsetitem.getIsfolder())
				{
					// this is a folder, all subfolders and files in it have to be watched for changes
					if(path.toLowerCase().indexOf(backupsetitem.getPath().toLowerCase()) == 0)
					{
						// this file is in the folder and needs to be backupped, so add it to the queue
						// also make a special backupsetitem, so we know which file in the folder to send
						
						// WARNING: UGLY CODE
						Backupsetitem backupsetitemThisFileOnly = new Backupsetitem();
						backupsetitemThisFileOnly.setBackupsetitemid(backupsetitem.getBackupsetitemid());
						backupsetitemThisFileOnly.setPath(path);
						backupsetitemThisFileOnly.setRoot(backupsetitem.getRoot());
						backupsetitemThisFileOnly.setType(backupsetitem.getType());
						backupsetitemThisFileOnly.setBackupset(backupSet.backupset);
						ModifiedFile modFile = new ModifiedFile();
						modFile.dateTime = Calendar.getInstance().getTime();
						modFile.root = root;
						modFile.path = path;
						modFile.backupsetitem = backupsetitemThisFileOnly;
						modFile.isDeltas = backupSet.backupset.getIsdeltas();
						return modFile;
					}
				}
				else
				{
					if(backupsetitem.getPath() == path)
					{
						// this file needs to be backupped, so add it to the queue
						ModifiedFile modFile = new ModifiedFile();
						modFile.dateTime = Calendar.getInstance().getTime();
						modFile.root = root;
						modFile.path = path;
						modFile.backupsetitem = backupsetitem;
						modFile.isDeltas = backupSet.backupset.getIsdeltas();
						return modFile;
					}

				}
				
			}
		}
		return null;

	}
	private boolean rootAlreadyWatched(String root)
	{
		for(String thisRoot : roots)
		{
			if(root == thisRoot)
			{
				return true;
			}
		}
		return false;
	}
}
