package net.nettape.client.command;

import java.io.*;
import java.util.LinkedList;

import net.nettape.connection.*;
import net.nettape.server.command.ServerCommand;
import net.nettape.object.BackupSet;
import net.nettape.dal.object.*;


public class ReceiveSchedulesCommand extends ClientCommand
{

	private Backupset backupset;
	public ReceiveSchedulesCommand(Connection connection, Backupset backupset)
	{
		super(connection);
		this.backupset = backupset;
	}

	public LinkedList<Schedule> Execute() throws Exception
	{
		if(connection.IsLoggedIn())
		{
			connection.SendCommand(Command.RECEIVESCHEDULES);
			connection.sendInt(backupset.getBackupsetid());
			LinkedList<Schedule> scheduleList = new LinkedList<Schedule>();
			int len = connection.receiveInt();
			for (int i=0; i<len; i++)
			{
				scheduleList.add((new ReceiveScheduleCommand(connection)).Execute());
			}
			
	        return scheduleList;

		}
		return null;
	}
}

