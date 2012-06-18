package net.nettape.client.command;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.net.SocketException;

import net.nettape.client.ShadowHandler;
import net.nettape.client.SmartFile;
import net.nettape.client.gui.ui.DoBackupWindow;
import net.nettape.connection.*;
import net.nettape.object.BackupSet;
import net.nettape.dal.object.*;
import net.nettape.object.*;


public class SendBackupItemCommand extends ClientCommand
{
	private LinkedList<Thread> runningBackupitemList;
	private ShadowHandler shadowHandler;
	private Backupsetitem backupsetitem;
	private BackupSet backupSet;
	private int backupId;
	private DoBackupWindow backupWindow;
	private int size;
	public boolean cancel = false;
	
	
	public SendBackupItemCommand(Connection connection, Backupsetitem backupsetitem, ShadowHandler shadowHandler, int backupId, DoBackupWindow backupWindow, int size, BackupSet backupSet, LinkedList<Thread> runningBackupitemList)
	{
		super(connection, false);
		this.backupsetitem = backupsetitem;
		this.shadowHandler = shadowHandler;
		this.backupWindow = backupWindow;
		this.size = size;
		this.runningBackupitemList = runningBackupitemList;
		this.backupId = backupId;
		this.backupSet = backupSet;
	}

	public void Execute(boolean sendOnlyFile) throws SocketException, IOException, Exception
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				
				//NOTE: decide if folder by local getisfolder property, because of CDP
				if(this.backupsetitem.getIsfolder())
				{
					Backupitem backupitem = CreateBackupitem(this.backupsetitem);
					SendFolderBackupitem(backupitem,sendOnlyFile);
					
				}
				else
				{
				    if(size <= 1 && (backupWindow != null))
				    {
						backupWindow.shell.getDisplay().syncExec(new Runnable() {
							public void run() {
								backupWindow.setMaximumProgressBar(1);
							}
						});
				    }
					
					HandleBackupItem(backupsetitem.getPath());
											
				    if(size <= 1 && (backupWindow != null))
					{
						final String label = backupsetitem.getPath();
						backupWindow.shell.getDisplay().syncExec(new Runnable() {
							public void run() {
								backupWindow.setTextLabelAction(label);
								backupWindow.setSelectionProgressBar(1);
							}
						});
					}
				
				}
			}
			catch(SocketException sex)
			{
				throw sex;
			}
			catch(IOException ioex)
			{
				throw ioex;
			}
			catch(Exception ex)
			{
				throw ex;
			}
		}
	}
	private void HandleBackupItem(String path) throws SocketException,Exception
	{
		//Do not check on the server if backupset has changed, just continue backup if backupset has changed.
		
		//Create new thread for the backup of this item if not too many threads exist, otherwise block
		
		if(runningBackupitemList == null)
			runningBackupitemList = new LinkedList<Thread>();
		Backupitem backupitem = CreateBackupitem(path);
		outerwhile:
			while(true)
			{
				if(this.CleanAndCountThreads() < 10)
				{
					Thread thread;
					SmartFile openFile = null;
					if(!shadowHandler.canOpenFileWithoutShadow(path))
					{
						openFile = shadowHandler.openFileWithShadowCopy(path);
					}
					thread = new Thread(new SendBackupItemCommandThread(this.connection,backupitem,this.backupSet,path, this.shadowHandler, openFile));
					thread.start();
					runningBackupitemList.add(thread);
					break outerwhile;
				}
			}
	}
	
	private void HandleBackupFolder(String path, boolean sendOnlyFile, boolean topLevel) throws SocketException, Exception
	{

		IOHelper iOHelper = new IOHelper();
		path = iOHelper.translateAbstractPath(iOHelper.makePathAbstract(path));

		if(!sendOnlyFile)
		{
			
		    File folder = new File(path);
		    File[] filesArray = folder.listFiles();
		    if(filesArray != null)
		    {
			    final List<File> listOfFiles = Arrays.asList(filesArray);
			    Collections.sort(listOfFiles);
			    setMaximumProgressBar(listOfFiles.size(),topLevel);
		    	int i = 0;
			    for(File thisFile : listOfFiles) 
			    {
			    	if(!backupWindow.isCancelled())
			    	{
				    	final String label = thisFile.getPath();
				    	final int index = i;
				    	increaseProgressBar(index,label,topLevel);
				    	if(this.cancel) break;
				    	if (thisFile.isFile())
				    	{
					        HandleBackupItem(thisFile.getPath());
				    	} 
				    	else if (thisFile.isDirectory()) 
				    	{
				    		Backupitem backupitem = CreateBackupitem(thisFile.getPath());
				    		SendFolderBackupitem(backupitem,sendOnlyFile);
				    	}
			    	}
			    	else
			    	{
						connection.Logout();
						break;
			    	}
			    	i +=1;
	
			    }
		    }
		}
		else
		{
			HandleBackupItem(path);
		}
	}
	
	private void increaseProgressBar(final int index, final String label, boolean topLevel)
	{
    	if(backupWindow != null)
    	{
	    	if(topLevel)
	    	{
			    if(size <= 1)
				{
					backupWindow.shell.getDisplay().syncExec(new Runnable() {
						public void run() {
							backupWindow.setTextLabelAction(label);
							backupWindow.setSelectionProgressBar(index + 1);
						}
					});
				}
			    else
			    {
					backupWindow.shell.getDisplay().syncExec(new Runnable() {
						public void run() {
							backupWindow.setTextSubLabelAction(label);
							backupWindow.setSelectionSubProgressBar(index + 1);
						}
					});
			    }
	    	
	    	}
	    	else
	    	{
	    		if(size <= 1)
	    		{
					backupWindow.shell.getDisplay().syncExec(new Runnable() {
						public void run() {
							backupWindow.setTextSubLabelAction(label);
							backupWindow.setSelectionSubProgressBar(index + 1);
						}
					});
	    			
	    		}
	    		
	    	}
    	}

	}
	private void setMaximumProgressBar(final int size, boolean topLevel)
	{
	    if(backupWindow != null)
	    {
	    	if(topLevel)
	    	{
			    if(size <= 1)
			    {
					backupWindow.shell.getDisplay().syncExec(new Runnable() {
						public void run() {
							backupWindow.setMaximumProgressBar(size);
						}
					});
			    }
			    else
			    {
					backupWindow.shell.getDisplay().syncExec(new Runnable() {
						public void run() {
							backupWindow.setMaximumSubProgressBar(size);
						}
					});
			    }
	    	}
	    	else
	    	{
			    if(size <= 1)
			    {
					backupWindow.shell.getDisplay().syncExec(new Runnable() {
						public void run() {
							backupWindow.setMaximumProgressBar(size);
						}
					});
			    }
	    		
	    	}
	    }

	}
	
	private Backupitem CreateBackupitem(Backupsetitem backupsetitem)
	{
		IOHelper iOHelper = new IOHelper();
		Backupitem backupitem = new Backupitem();
		backupitem.setBackupitemid(0);
		backupitem.setPath(iOHelper.makePathAbstract(backupsetitem.getPath()));
		backupitem.setBackupid(this.backupId);
		backupitem.setBackupsetitemid(backupsetitem.getBackupsetitemid());
		backupitem.setIsdeltas(this.backupSet.backupset.getIsdeltas());
		backupitem.setIsfolder(backupsetitem.getIsfolder());
		//put type of backupset here because the server is looking there to know what type of backupset it should be
		//this does not mean the resulting file on the server could not have a different type
		backupitem.setType(this.backupSet.backupset.getType());
		backupitem.setVersion(-1);
		return backupitem;
	}
	
	private Backupitem CreateBackupitem(String path)
	{
		IOHelper iOHelper = new IOHelper();
		path = iOHelper.makePathAbstract(path);
		Backupitem backupitem = new Backupitem();
		backupitem.setBackupitemid(0);
		backupitem.setPath(path);
		backupitem.setBackupid(this.backupId);
		backupitem.setBackupsetitemid(backupsetitem.getBackupsetitemid());
		backupitem.setIsdeltas(this.backupSet.backupset.getIsdeltas());
		backupitem.setIsfolder(new File(iOHelper.translateAbstractPath(path)).isDirectory());
		//put type of backupset here because the server is looking there to know what type of backupset it should be
		//this does not mean the resulting file on the server could not have a different type
		backupitem.setType(this.backupSet.backupset.getType());
		backupitem.setVersion(-1);
		return backupitem;
	}
	
	private void SendFolderBackupitem(Backupitem backupitem, boolean sendOnlyFile) throws Exception
	{
		//since in multithreading, all backupitems should be handled through seperate connections, create one here
		LoginCommand loginCommand = new LoginCommand(this.connection.ServerName, this.connection.connectionType, this.connection.dalUser.getUsername(),this.connection.dalUser.getPassword());
		if(loginCommand.Execute())
		{
			Connection folderConnection = loginCommand.GetConnection();
			folderConnection.SendCommand(Command.SENDBACKUPITEM);
			folderConnection.sendObject(backupitem);
			LogoutCommand logoutCommand = new LogoutCommand(folderConnection);
			logoutCommand.Execute();
			folderConnection = null;
			HandleBackupFolder(backupitem.getPath(),sendOnlyFile, true);
		}
	}
	
	private int CleanAndCountThreads()
	{
		LinkedList<Thread> runningBackupitemsToRemove = new LinkedList<Thread>();
		int threadAmount = 0;
		for(Thread runningBackupitem : runningBackupitemList)
		{
			
			if(runningBackupitem.isAlive())
			{
				threadAmount++;
			}
			else
			{
				runningBackupitemsToRemove.add(runningBackupitem);
				runningBackupitem = null;
			}
		}
		for(Thread runningBackupitem : runningBackupitemsToRemove)
		{
			runningBackupitemList.remove(runningBackupitem);
		}
		return threadAmount;
	}

}

