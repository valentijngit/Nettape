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


public class ReceiveBackupsCommand extends ClientCommand
{

	private Shell shell;
	public ReceiveBackupsCommand(Connection connection, Shell aShell)
	{
		super(connection);
		this.shell = aShell;
	}

	public Set<BackupWithItems> Execute() throws Exception, SocketException
	{
		try
		{
			if(connection.IsLoggedIn())
			{
				connection.SendCommand(Command.RECEIVEBACKUPS);
				connection.sendInt(connection.dalClient.getClientid());
				Set<BackupWithItems> oBackupList = new HashSet<BackupWithItems>();
				int len = connection.receiveInt();
				for (int i=0; i<len; i++)
				{
					oBackupList.add((new ReceiveBackupCommand(connection)).Execute());
				}
				
		        return oBackupList;
	
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
		catch(Exception ex)
		{
			throw ex;
		}
	}
}

