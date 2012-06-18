package net.nettape.client.command;

import java.io.*;
import java.net.SocketException;
import java.util.List;

import java.util.Set;

import net.nettape.connection.*;
import net.nettape.server.command.ServerCommand;
import net.nettape.object.BackupSet;
import net.nettape.object.Constants;
import net.nettape.object.IOHelper;
import net.nettape.dal.object.*;
import net.nettape.client.ShadowHandler;
import net.nettape.client.FilePermissions;
import net.nettape.client.SmartFile;
import net.nettape.object.*;


public class ReceiveRestoreCommand extends ClientCommand
{

	private RestoreWithBackupItems restore;
	public boolean Cancel = false;

	public ReceiveRestoreCommand(Connection connection, RestoreWithBackupItems restore)
	{
		super(connection);
		this.restore = restore;

	}

	public void Execute(boolean reConnect) throws ConnectionException
	{
		if(reConnect) 
		{
			// try to login again in a loop (with cancel function)
			while(!this.Cancel)
			{
				try{
					LoginCommand loginCommand = new LoginCommand(this.connection.ServerName, this.connection.connectionType , this.connection.dalUser.getUsername(),this.connection.dalUser.getPassword());
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
		if(connection.IsLoggedIn() && !this.Cancel)
		{
			try
			{
				connection.SendCommand(Command.RECEIVERESTORE);
				
				if(this.restore.restore.getOverwrite()) 
					connection.sendInt(1);
				else
					connection.sendInt(0);
				if(this.restore.restore.getRestorepermissions())
					connection.sendInt(1);
				else
					connection.sendInt(0);
				if(this.restore.restore.getRestorepath() != null)
				{
					connection.sendInt(1);
					connection.sendString(this.restore.restore.getRestorepath());				
				}
				else
					connection.sendInt(0);
				
				connection.sendInt(restore.restoreItems.size());
				
				FileOutputStream fos;
				boolean goAhead= true;
				String newPath = "";
				IOHelper ioHelper = new IOHelper();
				for (RestoreItem restoreItem : restore.restoreItems)
				{
					
					goAhead = true;
					String fullName = "";
					if(restore.restore.getRestorepath() == null)
					{
						//restore to the original path
						
						IOHelper.Filename filename = ioHelper.new Filename(ioHelper.makePathAbstract(restoreItem.backupitem.getPath()),'/','.');
						newPath = ioHelper.translateAbstractPath(restoreItem.backupitem.getPath());
						ioHelper.createFoldersInPath(filename.path());
						fullName = filename.fullname();
						if(restore.restore.getOverwrite())
						{
							File test = new File(newPath);
							if(test.exists()) test.delete();
						}
						else
						{
							File test = new File(newPath);
							if(test.exists()) goAhead = false;
						}
						
					}
					else
					{
						//restore to another path

						IOHelper.Filename filename = ioHelper.new Filename(ioHelper.makePathAbstract(restoreItem.backupitem.getPath()),'/','.');
						String newDirectory = ioHelper.makePathAbstract(ioHelper.checkFolderForSeperatorEnd(restore.restore.getRestorepath()) + ioHelper.checkFolderForSeperatorEnd(filename.path()));
						newDirectory = ioHelper.StripSpecialChars(newDirectory);
						newPath = newDirectory + filename.filename() + "." + filename.extension();
						newPath = ioHelper.translateAbstractPath(newPath);
						ioHelper.createFoldersInPath(ioHelper.translateAbstractPath(newDirectory));
						fullName = filename.fullname();
						if(restore.restore.getOverwrite())
						{
							File test = new File(newPath);
							if(test.exists()) test.delete();
						}
						else
						{
							File test = new File(newPath);
							if(test.exists()) goAhead = false;
						}

					}
					if(goAhead)
					{
						connection.sendInt(restoreItem.backupitem.getBackupid());
						connection.sendObject(restoreItem.backupitem);
						Constants.ReceiveRestoreCommand receiveRestoreCommand = (Constants.ReceiveRestoreCommand)connection.receiveObject();
						if(receiveRestoreCommand == Constants.ReceiveRestoreCommand.RECEIVEFILE)
						{
							SmartFile smartFile = new SmartFile(newPath);
							connection.receiveFileOnClient(smartFile);
							
							if(restore.restore.getRestorepermissions()) 
							{
								Permissions permissions = (Permissions)(connection.receiveObject());
								FilePermissions.SetPermissions(newPath, permissions);
							}
						}
					}
					else
					{
						connection.sendInt(0);
					}


					
				}
				
				
			}
			catch(SocketException ex)
			{
				Execute(true);
			}
			catch(IOException ex)
			{
				Execute(false);
			}
			catch(Exception ex)
			{
				Execute(false);
			}
		
		}
	}
	
}

