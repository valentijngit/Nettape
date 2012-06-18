package net.nettape.client.command;

import java.io.*;
import java.util.ArrayList;

import net.nettape.connection.*;
import net.nettape.server.command.ServerCommand;
import net.nettape.object.BackupSet;
import net.nettape.dal.object.*;


public class ReceiveScheduleCommand extends ClientCommand
{

	private int scheduleId = 0;
	public ReceiveScheduleCommand(Connection connection)
	{
		super(connection, false);

	}
	public ReceiveScheduleCommand(Connection connection, int scheduleId)
	{
		super(connection);
		this.scheduleId = scheduleId;
	}

	public Schedule Execute() throws Exception
	{
		if(connection.IsLoggedIn())
		{
			if(scheduleId !=0)
			{
				connection.SendCommand(Command.RECEIVESCHEDULE);
				connection.sendInt(scheduleId);
			}
			Schedule schedule = (Schedule) (connection.receiveObject());
		
	        return schedule;

		}
		return null;
	}
}

