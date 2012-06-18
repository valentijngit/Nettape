package net.nettape.client.command;

import java.io.*;

import net.nettape.connection.*;
import net.nettape.server.command.ServerCommand;
import net.nettape.object.BackupSet;
import net.nettape.dal.object.*;


public class SendBackupSetCommand extends ClientCommand
{

	private BackupSet backupSet;
	
	public SendBackupSetCommand(Connection connection, BackupSet backupSet)
	{
		super(connection);
		
		this.backupSet = backupSet;
	}

	public void Execute()
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				connection.SendCommand(Command.SENDBACKUPSET);
				connection.sendObject(this.backupSet.backupset);
				connection.sendLong(this.backupSet.backupsetitems.size());
				for( Backupsetitem item: this.backupSet.backupsetitems)
				{
					connection.sendObject(item);
				}
				connection.sendLong(this.backupSet.filters.size());
				for( Filter item: this.backupSet.filters)
				{
					connection.sendObject(item);
				}
				this.backupSet.backupset.setBackupsetid(connection.receiveInt());
			}
			catch(Exception ex)
			{
				System.out.println("Error sending a file.");
			}
		}
	}
}

