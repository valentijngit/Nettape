package net.nettape.client.command;

import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import net.nettape.connection.*;
import net.nettape.object.BackupWithItems;
import net.nettape.server.command.ServerCommand;
import net.nettape.dal.object.*;


public class ReceiveSettingsCommand extends ClientCommand
{

	private int backupId = 0;
	public ReceiveSettingsCommand(Connection connection)
	{
		super(connection, false);

	}
	public ReceiveSettingsCommand(Connection connection, int backupId)
	{
		super(connection);
		this.backupId = backupId;
	}

	public Settings Execute() throws Exception
	{
		try{
			if(connection.IsLoggedIn())
			{
				if(backupId !=0)
				{
					connection.SendCommand(Command.RECEIVESETTINGS);
				}
				return (Settings) (connection.receiveObject());
			}
			return null;
		}
		catch(SocketException sex)
		{
			throw sex;
		}
	}
}

