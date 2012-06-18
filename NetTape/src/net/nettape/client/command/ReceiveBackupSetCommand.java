package net.nettape.client.command;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import net.nettape.connection.*;
import net.nettape.server.command.ServerCommand;
import net.nettape.object.BackupSet;
import net.nettape.dal.object.*;


public class ReceiveBackupSetCommand extends ClientCommand
{

	private int backupsetId = 0;
	public ReceiveBackupSetCommand(Connection connection)
	{
		super(connection, false);

	}
	public ReceiveBackupSetCommand(Connection connection, int backupsetId)
	{
		super(connection);
		this.backupsetId = backupsetId;
	}

	public BackupSet Execute() throws Exception
	{
		if(connection.IsLoggedIn())
		{
			if(backupsetId !=0)
			{
				connection.SendCommand(Command.RECEIVEBACKUPSET);
				connection.sendInt(backupsetId);
			}
			net.nettape.object.BackupSet oBackupSet = new net.nettape.object.BackupSet();
			Backupset backupset = (Backupset) (connection.receiveObject());
		
			oBackupSet.backupset = backupset;
	        oBackupSet.backupsetitems = new HashSet<Backupsetitem>();
	        oBackupSet.filters = new HashSet<net.nettape.dal.object.Filter>();
	        int length = (int) (connection.receiveInt());
	        for(int i = 0; i<length;i++)
	        {
	        	oBackupSet.backupsetitems.add((Backupsetitem) (connection.receiveObject()));
	        }
	        length = (int) (connection.receiveInt());
	        for(int i = 0; i<length;i++)
	        {
	        	oBackupSet.filters.add((net.nettape.dal.object.Filter) (connection.receiveObject()));
	        }
	        oBackupSet.backupset.setFilters(oBackupSet.filters);
	        return oBackupSet;

		}
		return null;
	}
}

