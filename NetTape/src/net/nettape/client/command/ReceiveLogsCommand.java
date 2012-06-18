package net.nettape.client.command;

import java.io.*;
import java.net.SocketException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import net.nettape.client.MessageHandler;
import net.nettape.connection.*;
import net.nettape.server.command.ServerCommand;
import net.nettape.object.BackupSet;
import net.nettape.object.BackupWithItems;
import net.nettape.object.Constants;
import net.nettape.object.Message;
import net.nettape.dal.object.*;


public class ReceiveLogsCommand extends ClientCommand
{

	private Shell shell;
	private int backupsetId;
	private int type;
	public ReceiveLogsCommand(Connection connection, int backupsetId, int type, Shell aShell)
	{
		super(connection);
		this.shell = aShell;
		this.backupsetId = backupsetId;
		this.type = type;
	}

	public Set<Log> Execute() throws Exception, SocketException
	{
		try
		{
			if(connection.IsLoggedIn())
			{
				connection.SendCommand(Command.RECEIVELOGS);
				connection.sendInt(backupsetId);
				connection.sendInt(type);
				Set<Log> logList = new HashSet<Log>();
				int len = connection.receiveInt();
				for (int i=0; i<len; i++)
				{
					logList.add((Log)connection.receiveObject());
				}
				
		        return logList;
	
			}
			else
			{
				throw new SocketException();
			}
		}
		catch(SocketException sex)
		{
			this.shell.getDisplay().syncExec(new Runnable() {
				public void run() {
					MessageHandler.HandleMessage(null,false,"There is a problem with the connection to the server.",false,null,shell);
				}
			});
			throw sex;

		}
	}
}

