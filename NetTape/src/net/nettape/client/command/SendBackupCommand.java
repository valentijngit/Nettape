package net.nettape.client.command;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.swt.widgets.Display;

import net.nettape.connection.*;
import net.nettape.server.command.ServerCommand;
import net.nettape.server.dal.Backups;
import net.nettape.object.BackupSet;
import net.nettape.object.Constants;
import net.nettape.object.BackupsetitemComparator; 
import net.nettape.dal.object.*;
import net.nettape.client.ShadowHandler;
import net.nettape.client.Global;
import net.nettape.client.MessageHandler;
import net.nettape.client.gui.ui.DoBackupWindow;
import net.nettape.object.FileInfo;
import net.nettape.object.Message;


public class SendBackupCommand extends ClientCommand
{

	private BackupSet backupSet;
	public boolean cancel = false;
	private int backupId = 0;
	private Backup backup;
	private ShadowHandler shadowHandler = null;
	private DoBackupWindow backupWindow;
	private LinkedList<Thread> runningBackupitemList;
	public SendBackupCommand(Connection connection, BackupSet backupSet, DoBackupWindow backupWindow)
	{
		super(connection);
		this.backupWindow = backupWindow;
		this.backupSet = backupSet;
	}

	public void Execute(boolean reConnect) throws Exception
	{
		if(reConnect) 
		{
			// try to login again in a loop (with cancel function)
			while(!backupWindow.isCancelled())
			{
				try{
					LoginCommand loginCommand = new LoginCommand(this.connection.ServerName, this.connection.connectionType, this.connection.dalUser.getUsername(),this.connection.dalUser.getPassword());
					if(loginCommand.Execute())
					{
						connection = loginCommand.GetConnection();
	 					break;
					}
				}
				catch(Exception ex)
				{
				}
			}

			
		}
		if(connection.IsLoggedIn() && !backupWindow.isCancelled())
		{
			try
			{
				//sort the backupsetitems per root
				ArrayList<Backupsetitem> backupsetitemslist = new ArrayList<Backupsetitem>(backupSet.backupsetitems); 
				this.runningBackupitemList = new LinkedList<Thread>();
				Collections.sort(backupsetitemslist, new BackupsetitemComparator());
				connection.SendCommand(Command.SENDBACKUP);
				connection.sendInt(this.backupId);
				connection.sendInt(this.backupSet.backupset.getBackupsetid());
				this.backupId = connection.receiveInt();
				String oldRoot = "";
				String newRoot = "";
				final int size = backupsetitemslist.size();
				if(size > 1)
				{
					backupWindow.shell.getDisplay().syncExec(new Runnable() {
						public void run() {
							backupWindow.setMaximumProgressBar(size);
						}
					});
				}
				int index = 1;
				
				backupsetitemslist:
				for(Backupsetitem backupsetitem : backupsetitemslist )
				{
			    	if(!backupWindow.isCancelled())
			    	{
						final int i = index;
						final String label = backupsetitem.getPath();
						if(size > 1)
						{
							backupWindow.shell.getDisplay().syncExec(new Runnable() {
								public void run() {
									backupWindow.setTextLabelAction(label);
									backupWindow.setSelectionProgressBar(i);
								}
							});
						}
						index += 1;
						newRoot = backupsetitem.getRoot();
						if(newRoot != oldRoot)
						{
							if(shadowHandler != null) 
							{
								try
								{
									shadowHandler.closeShadow();
								}
								catch(Exception ex)
								{
									//shadowcopy could not start, but go ahead! 
									backupWindow.shell.getDisplay().syncExec(new Runnable() {
										public void run() {
											Message message = new Message();
											message.BackupId = SendBackupCommand.this.backupId;
											message.Message = "Shadow copy could not be stopped.";
											message.Type = Constants.MessageType.BACKUP_ERROR;
											MessageHandler.HandleMessage(null,false,"Shadow copy could not be stopped.",true,message,backupWindow.shell);
										}
									});
								}

							}
							try
							{
								//TODO, change false in true, and test shadowcopy!!!
								shadowHandler = new ShadowHandler(newRoot, false);
							}
							catch(Exception ex)
							{
								//shadowcopy could not start, but go ahead! 
								backupWindow.shell.getDisplay().syncExec(new Runnable() {
									public void run() {
										Message message = new Message();
										message.BackupId = SendBackupCommand.this.backupId;
										message.Message = "Shadow copy could not be started.";
										message.Type = Constants.MessageType.BACKUP_ERROR;
										MessageHandler.HandleMessage(null,false,"Shadow copy could not be started.",true,message,backupWindow.shell);
									}
								});
								shadowHandler = new ShadowHandler(newRoot,false);
							}
							oldRoot = newRoot;
						}
						SendBackupItemCommand sendBackupItemCommand = new SendBackupItemCommand(connection,backupsetitem,shadowHandler, this.backupId, backupWindow,backupsetitemslist.size(),this.backupSet, this.runningBackupitemList);
						sendBackupItemCommand.Execute(false);
						sendBackupItemCommand = null;
			    	}
			    	else
			    	{
						connection.Logout();
			    		break backupsetitemslist;
			    		
			    	}
				}
				while(true)
				{
					if(this.CleanAndCountThreads() == 0)
						break;
				}
				if(shadowHandler != null) 
				{
					try
					{
						shadowHandler.closeShadow();
					}
					catch(Exception ex)
					{
						//shadowcopy could not start, but go ahead! 
						backupWindow.shell.getDisplay().syncExec(new Runnable() {
							public void run() {
								Message message = new Message();
								message.BackupId = SendBackupCommand.this.backupId;
								message.Message = "Shadow copy could not be stopped.";
								message.Type = Constants.MessageType.BACKUP_ERROR;
								MessageHandler.HandleMessage(null,false,"Shadow copy could not be stopped.",true,message,backupWindow.shell);
							}
						});
					}

				}
			}
			catch(SocketException ex)
			{
				//TODO, put information on screen about reconnection in progress. After certain time, put try again button on screen.
				connection.Logout();
				Execute(true);
			}
			catch(IOException ex)
			{
				//TODO, put information on screen about reconnection in progress. After certain time, put try again button on screen.
				connection.Logout();
				Execute(true);
			}
			catch(Exception ex)
			{
				backupWindow.shell.getDisplay().syncExec(new Runnable() {
					public void run() {
						Message message = new Message();
						message.BackupId = backupId;
						message.Message = "There was a problem with backup of a file.";
						MessageHandler.HandleMessage(null,false,"",true,message,backupWindow.shell);
					}
				});
				connection.Logout();
			}
		
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

