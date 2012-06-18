package net.nettape.client.command;
import java.io.*;
import java.net.SocketException;
import java.util.List;

import net.nettape.connection.Command;
import net.nettape.connection.Connection;
import net.nettape.connection.InFileDelta;
import net.nettape.dal.object.*;
import net.nettape.object.BackupSet;
import net.nettape.object.Constants;
import net.nettape.object.Permissions;
import net.nettape.client.FileCopier;
import net.nettape.client.ShadowHandler;
import net.nettape.client.FilePermissions;
import net.nettape.client.SmartFile;
import net.nettape.client.command.*;



public class SendBackupItemCommandThread implements Runnable
{
	private Connection parentConnection;
	private Connection connection;
	private Backupitem backupitem;
	private BackupSet backupSet;
	private String path;
	private ShadowHandler shadowHandler;
	private SmartFile passedFile = null;
	public SendBackupItemCommandThread(Connection connection, Backupitem backupitem, BackupSet backupSet, String path, ShadowHandler shadowHandler, SmartFile passedFile)
	{
		this.parentConnection = connection;
		this.backupitem = backupitem;
		this.path = path;
		this.backupSet = backupSet;
		this.shadowHandler = shadowHandler;
		this.passedFile = passedFile;
	}
	

	public void run()
	{
		try
		{
			SmartFile openFile = null;
			if(passedFile == null)
			{
				FileCopier fileCopier = new FileCopier();
				openFile = fileCopier.openFile(path);
			}
			else
			{
				openFile = passedFile;
			}
			if(openFile != null)
			{
				LoginCommand loginCommand = new LoginCommand(this.parentConnection.ServerName, this.parentConnection.connectionType, this.parentConnection.dalUser.getUsername(),this.parentConnection.dalUser.getPassword());
			
				if(loginCommand.Execute())
				{
					this.connection = loginCommand.GetConnection();
					this.connection.SendCommand(Command.SENDBACKUPITEM);
					this.connection.sendObject(this.backupitem);
					//backup item
					Constants.SendBackupCommand sendBackupCommand = (Constants.SendBackupCommand)this.connection.receiveObject();
					if(Constants.SendBackupCommand.SENDFILE == sendBackupCommand)
					{
						// there is no snapshot of this file yet, so send it
						try
						{
							connection.sendFileOnClient(openFile);
						}
						catch(Exception ex)
						{
							// catch exception because we want to start over because the server is waiting 
							throw new SocketException();
						}
					}
					else if(Constants.SendBackupCommand.RECEIVECHECKSUMS == sendBackupCommand)
					{
						// be ready to receive the checksums
						try
						{
							InFileDelta inFileDelta = new InFileDelta(connection);
							List sums = inFileDelta.readSignatures(connection.getObjectInputStream());
							
							//TODO: check if backupset is isdeltas 
							//TODO: make deltas to temp file and check if file has changed!
							
							InputStream is = openFile.GetInputStream();
							if(inFileDelta.isDifferent(sums, is))
							{
								if(backupitem.getIsdeltas())
								{
									this.connection.sendObject(Constants.SendBackupCommand.SENDDELTAS);
									is.close();
									is = openFile.GetInputStream();
									inFileDelta.makeDeltas(sums, is, connection.getObjectOutputStream());
								}
								else
								{
									this.connection.sendObject(Constants.SendBackupCommand.SENDFILE);
									connection.sendFileOnClient(openFile);
								}
							}
							else
							{
								this.connection.sendObject(Constants.SendBackupCommand.DONOTHING);
							}
							is.close();
						}
						catch(Exception ex)
						{
							// catch exception because we want to start over because the server is waiting 
							throw new SocketException();
						}
					}
					else if(Constants.SendBackupCommand.DONOTHING == sendBackupCommand)
					{
					}
					// TODO SEND PERMISSIONS IF NECESSARY
					if(this.backupSet.backupset.getFilepermissions() != null)
					{
						if(this.backupSet.backupset.getFilepermissions())
						{
							Permissions permissions = new Permissions();
							FilePermissions.GetPermissions(openFile.GetAbsolutePath(), permissions);
							connection.sendObject(permissions);
						}
					}
					LogoutCommand logoutCommand = new LogoutCommand(this.connection);
					logoutCommand.Execute();
					this.connection = null;
				}
				openFile.Delete();
			}
		}
		catch (Exception ex)
		{
		}
	}
}
