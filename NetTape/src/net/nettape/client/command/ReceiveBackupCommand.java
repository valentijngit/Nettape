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


public class ReceiveBackupCommand extends ClientCommand
{

	private int backupId = 0;
	public ReceiveBackupCommand(Connection connection)
	{
		super(connection, false);

	}
	public ReceiveBackupCommand(Connection connection, int backupId)
	{
		super(connection);
		this.backupId = backupId;
	}

	public BackupWithItems Execute() throws Exception
	{
		try{
			if(connection.IsLoggedIn())
			{
				if(backupId !=0)
				{
					connection.SendCommand(Command.RECEIVEBACKUP);
					connection.sendInt(backupId);
				}
				BackupWithItems backupWithItems = new BackupWithItems();
				backupWithItems.backup = (Backup) (connection.receiveObject()); 
		        Set<Backupitem> backupitems = new HashSet<Backupitem>();
		        Backupitem backupitem;
		        while((backupitem = (Backupitem) (connection.receiveObject())).getBackupitemid() != -1)
		        {
		        	backupitems.add(backupitem);
		        }
		        backupWithItems.backupitems = backupitems;
		        return backupWithItems;
	
			}
			return null;
		}
		catch(SocketException sex)
		{
			throw sex;
		}
	}
}

