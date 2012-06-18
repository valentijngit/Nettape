package net.nettape.client.command;

import java.io.*;
import java.util.LinkedList;

import net.nettape.connection.*;
import net.nettape.server.command.ServerCommand;
import net.nettape.object.BackupSet;
import net.nettape.dal.object.*;


public class ReceiveBackupSetsCommand extends ClientCommand
{

	
	public ReceiveBackupSetsCommand(Connection connection)
	{
		super(connection);

	}

	public LinkedList<BackupSet> Execute() throws Exception
	{
		if(connection.IsLoggedIn())
		{
			connection.SendCommand(Command.RECEIVEBACKUPSETS);
			connection.sendInt(connection.dalClient.getClientid());
			LinkedList<BackupSet> oBackupSetList = new LinkedList<BackupSet>();
			int len = connection.receiveInt();
			for (int i=0; i<len; i++)
			{
				oBackupSetList.add((new ReceiveBackupSetCommand(connection)).Execute());
			}
			
	        return oBackupSetList;

		}
		return null;
	}
}

