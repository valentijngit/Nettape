package net.nettape.client.command;

import net.nettape.object.Message; 
import net.nettape.connection.*;


public class SendMessageCommand extends ClientCommand
{

	private Message message;
	
	public SendMessageCommand(Connection connection, Message message)
	{
		super(connection,false);
		this.message = message;
	}

	public boolean Execute()
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				connection.SendCommand(Command.SENDMESSAGE);
				connection.sendObject(this.message);
				return true;
			}
			catch(Exception ex)
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}

