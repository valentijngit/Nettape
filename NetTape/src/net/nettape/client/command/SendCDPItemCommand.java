package net.nettape.client.command;

import java.io.*;
import java.net.SocketException;
import java.util.List;

import net.nettape.client.ShadowHandler;
import net.nettape.client.MessageHandler;
import net.nettape.connection.*;
import net.nettape.server.command.ServerCommand;
import net.nettape.object.BackupSet;
import net.nettape.object.Constants;
import net.nettape.object.FileInfo;
import net.nettape.object.BackupHelper;
import net.nettape.object.Message;
import net.nettape.dal.object.*;


public class SendCDPItemCommand extends ClientCommand
{

	private String path;
	private ShadowHandler fileHandler;
	private Constants.SendBackupCommand sendBackupCommand;
	private Backupsetitem backupsetitem;
	private BackupSet backupSet;
	private int backupId;
	public boolean cancel = false;
	private BackupHelper backupHelper;
	public SendCDPItemCommand(Connection connection, int backupId, Backupsetitem backupsetitem, ShadowHandler fileHandler, BackupHelper backupHelper, BackupSet backupSet)
	{
		super(connection);
		this.backupId = backupId;
		this.backupsetitem = backupsetitem;
		this.fileHandler = fileHandler;
		this.backupHelper = backupHelper;
		this.backupSet = backupSet;
	}

	public void Execute()
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				connection.SendCommand(Command.SENDCDPITEM);
				SendBackupItemCommand sendBackupItemCommand = new SendBackupItemCommand(connection, backupsetitem, fileHandler, this.backupId , null, 0, backupSet,null);
				sendBackupItemCommand.Execute(true);
				
			}
			catch(SocketException ex)
			{
				connection.Logout();
			}
			catch(IOException ex)
			{
				connection.Logout();
			}
			catch(Exception ex)
			{
				Message message = new Message();
				message.BackupId = backupId;
				message.Message = "There was a problem in backup of a file in continuous data backup.";
				MessageHandler.HandleMessage(null,false,"",true,message,null);
				connection.Logout();
			}
		}
	}
}

