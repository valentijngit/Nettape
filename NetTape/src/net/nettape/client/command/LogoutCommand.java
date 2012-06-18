package net.nettape.client.command;



import java.net.*;
import java.util.Scanner;
import java.io.*;

import net.nettape.connection.*;
import net.nettape.dal.object.*;



public class LogoutCommand 
{
	private Connection connection;

	public LogoutCommand(Connection connection)
	{
		this.connection = connection;
	}
	
	public Connection GetConnection()
	{
		return this.connection;
	}
	
    
	public void Execute() throws Exception
	{
		
		try
		{
			connection.SendCommand(Command.LOGOUT);
			// just wait for an int here before shutting down the connection, sp the server doesnt get a closed socket before done
			connection.receiveInt();
			connection.Logout();

		}
		catch(Exception ex)
		{
			throw ex;
		}
		
	}
}
