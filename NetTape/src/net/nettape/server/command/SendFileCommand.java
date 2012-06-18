package net.nettape.server.command;



import java.io.*;

import net.nettape.connection.ByteStream;
import net.nettape.connection.Connection;
import net.nettape.object.FileInfo;
import org.hibernate.Session;
import net.nettape.dal.*;
import net.nettape.dal.object.*;


public class SendFileCommand extends ServerCommand
{

	public SendFileCommand(Connection connection)
	{
		super(connection);
	}
	public void Execute()
	{
		if(connection.IsLoggedIn())
		{
			try
			{
				FileInfo fileInfo = (FileInfo) (connection.receiveObject());
				File file = new File(fileInfo.GetPath()+".new");
				connection.receiveFileOnServer(new FileOutputStream(file));
				


			}
			catch(Exception ex)
			{
				System.out.println("Error receiving a file.");
			}
		}
	}
}
