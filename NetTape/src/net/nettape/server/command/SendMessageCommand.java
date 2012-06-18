package net.nettape.server.command;



import java.io.*;
import java.util.ArrayList;

import net.nettape.connection.ByteStream;
import net.nettape.connection.Connection;
import net.nettape.object.Message;
import net.nettape.server.LogHandler;

import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;


public class SendMessageCommand extends ServerCommand
{

	public SendMessageCommand(Connection connection)
	{
		super(connection);
	}
	public void Execute()
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				Message message = (Message) (connection.receiveObject());
				LogHandler logHandler = new LogHandler();
				logHandler.handle(message);
			}
			catch(Exception ex)
			{
				// write a log entry saying that an error was not correctly written to the log
			}
		}
	}
}
