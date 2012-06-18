package net.nettape.client.command;

import java.io.*;
import java.util.ArrayList;

import net.nettape.connection.*;
import net.nettape.server.command.ServerCommand;
import net.nettape.object.BackupSet;
import net.nettape.dal.object.*;


public class CheckBackupSetVersionCommand extends ClientCommand
{

	private int backupsetId = 0;
	public CheckBackupSetVersionCommand(Connection connection)
	{
		super(connection,false);

	}
	public CheckBackupSetVersionCommand(Connection connection, int backupsetId)
	{
		super(connection);
		this.backupsetId = backupsetId;
	}

	public Long Execute() throws Exception
	{
		if(connection.IsLoggedIn())
		{
			if(backupsetId !=0)
			{
				connection.SendCommand(Command.CHECKBACKUPSETVERSION);
				connection.sendInt(backupsetId);
			}
			return connection.receiveLong();
		

		}
		return null;
	}
}

